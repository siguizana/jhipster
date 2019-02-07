package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.CritereChoixMembreJury;
import com.test.repository.CritereChoixMembreJuryRepository;
import com.test.service.CritereChoixMembreJuryService;
import com.test.service.dto.CritereChoixMembreJuryDTO;
import com.test.service.mapper.CritereChoixMembreJuryMapper;
import com.test.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.test.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CritereChoixMembreJuryResource REST controller.
 *
 * @see CritereChoixMembreJuryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class CritereChoixMembreJuryResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Float DEFAULT_NOTE_PAR_DEFAUT = 1F;
    private static final Float UPDATED_NOTE_PAR_DEFAUT = 2F;

    @Autowired
    private CritereChoixMembreJuryRepository critereChoixMembreJuryRepository;

    @Autowired
    private CritereChoixMembreJuryMapper critereChoixMembreJuryMapper;

    @Autowired
    private CritereChoixMembreJuryService critereChoixMembreJuryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCritereChoixMembreJuryMockMvc;

    private CritereChoixMembreJury critereChoixMembreJury;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CritereChoixMembreJuryResource critereChoixMembreJuryResource = new CritereChoixMembreJuryResource(critereChoixMembreJuryService);
        this.restCritereChoixMembreJuryMockMvc = MockMvcBuilders.standaloneSetup(critereChoixMembreJuryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CritereChoixMembreJury createEntity(EntityManager em) {
        CritereChoixMembreJury critereChoixMembreJury = new CritereChoixMembreJury()
            .libelle(DEFAULT_LIBELLE)
            .noteParDefaut(DEFAULT_NOTE_PAR_DEFAUT);
        return critereChoixMembreJury;
    }

    @Before
    public void initTest() {
        critereChoixMembreJury = createEntity(em);
    }

    @Test
    @Transactional
    public void createCritereChoixMembreJury() throws Exception {
        int databaseSizeBeforeCreate = critereChoixMembreJuryRepository.findAll().size();

        // Create the CritereChoixMembreJury
        CritereChoixMembreJuryDTO critereChoixMembreJuryDTO = critereChoixMembreJuryMapper.toDto(critereChoixMembreJury);
        restCritereChoixMembreJuryMockMvc.perform(post("/api/critere-choix-membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critereChoixMembreJuryDTO)))
            .andExpect(status().isCreated());

        // Validate the CritereChoixMembreJury in the database
        List<CritereChoixMembreJury> critereChoixMembreJuryList = critereChoixMembreJuryRepository.findAll();
        assertThat(critereChoixMembreJuryList).hasSize(databaseSizeBeforeCreate + 1);
        CritereChoixMembreJury testCritereChoixMembreJury = critereChoixMembreJuryList.get(critereChoixMembreJuryList.size() - 1);
        assertThat(testCritereChoixMembreJury.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCritereChoixMembreJury.getNoteParDefaut()).isEqualTo(DEFAULT_NOTE_PAR_DEFAUT);
    }

    @Test
    @Transactional
    public void createCritereChoixMembreJuryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = critereChoixMembreJuryRepository.findAll().size();

        // Create the CritereChoixMembreJury with an existing ID
        critereChoixMembreJury.setId(1L);
        CritereChoixMembreJuryDTO critereChoixMembreJuryDTO = critereChoixMembreJuryMapper.toDto(critereChoixMembreJury);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCritereChoixMembreJuryMockMvc.perform(post("/api/critere-choix-membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critereChoixMembreJuryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CritereChoixMembreJury in the database
        List<CritereChoixMembreJury> critereChoixMembreJuryList = critereChoixMembreJuryRepository.findAll();
        assertThat(critereChoixMembreJuryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = critereChoixMembreJuryRepository.findAll().size();
        // set the field null
        critereChoixMembreJury.setLibelle(null);

        // Create the CritereChoixMembreJury, which fails.
        CritereChoixMembreJuryDTO critereChoixMembreJuryDTO = critereChoixMembreJuryMapper.toDto(critereChoixMembreJury);

        restCritereChoixMembreJuryMockMvc.perform(post("/api/critere-choix-membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critereChoixMembreJuryDTO)))
            .andExpect(status().isBadRequest());

        List<CritereChoixMembreJury> critereChoixMembreJuryList = critereChoixMembreJuryRepository.findAll();
        assertThat(critereChoixMembreJuryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoteParDefautIsRequired() throws Exception {
        int databaseSizeBeforeTest = critereChoixMembreJuryRepository.findAll().size();
        // set the field null
        critereChoixMembreJury.setNoteParDefaut(null);

        // Create the CritereChoixMembreJury, which fails.
        CritereChoixMembreJuryDTO critereChoixMembreJuryDTO = critereChoixMembreJuryMapper.toDto(critereChoixMembreJury);

        restCritereChoixMembreJuryMockMvc.perform(post("/api/critere-choix-membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critereChoixMembreJuryDTO)))
            .andExpect(status().isBadRequest());

        List<CritereChoixMembreJury> critereChoixMembreJuryList = critereChoixMembreJuryRepository.findAll();
        assertThat(critereChoixMembreJuryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCritereChoixMembreJuries() throws Exception {
        // Initialize the database
        critereChoixMembreJuryRepository.saveAndFlush(critereChoixMembreJury);

        // Get all the critereChoixMembreJuryList
        restCritereChoixMembreJuryMockMvc.perform(get("/api/critere-choix-membre-juries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(critereChoixMembreJury.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].noteParDefaut").value(hasItem(DEFAULT_NOTE_PAR_DEFAUT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCritereChoixMembreJury() throws Exception {
        // Initialize the database
        critereChoixMembreJuryRepository.saveAndFlush(critereChoixMembreJury);

        // Get the critereChoixMembreJury
        restCritereChoixMembreJuryMockMvc.perform(get("/api/critere-choix-membre-juries/{id}", critereChoixMembreJury.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(critereChoixMembreJury.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.noteParDefaut").value(DEFAULT_NOTE_PAR_DEFAUT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCritereChoixMembreJury() throws Exception {
        // Get the critereChoixMembreJury
        restCritereChoixMembreJuryMockMvc.perform(get("/api/critere-choix-membre-juries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCritereChoixMembreJury() throws Exception {
        // Initialize the database
        critereChoixMembreJuryRepository.saveAndFlush(critereChoixMembreJury);

        int databaseSizeBeforeUpdate = critereChoixMembreJuryRepository.findAll().size();

        // Update the critereChoixMembreJury
        CritereChoixMembreJury updatedCritereChoixMembreJury = critereChoixMembreJuryRepository.findById(critereChoixMembreJury.getId()).get();
        // Disconnect from session so that the updates on updatedCritereChoixMembreJury are not directly saved in db
        em.detach(updatedCritereChoixMembreJury);
        updatedCritereChoixMembreJury
            .libelle(UPDATED_LIBELLE)
            .noteParDefaut(UPDATED_NOTE_PAR_DEFAUT);
        CritereChoixMembreJuryDTO critereChoixMembreJuryDTO = critereChoixMembreJuryMapper.toDto(updatedCritereChoixMembreJury);

        restCritereChoixMembreJuryMockMvc.perform(put("/api/critere-choix-membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critereChoixMembreJuryDTO)))
            .andExpect(status().isOk());

        // Validate the CritereChoixMembreJury in the database
        List<CritereChoixMembreJury> critereChoixMembreJuryList = critereChoixMembreJuryRepository.findAll();
        assertThat(critereChoixMembreJuryList).hasSize(databaseSizeBeforeUpdate);
        CritereChoixMembreJury testCritereChoixMembreJury = critereChoixMembreJuryList.get(critereChoixMembreJuryList.size() - 1);
        assertThat(testCritereChoixMembreJury.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCritereChoixMembreJury.getNoteParDefaut()).isEqualTo(UPDATED_NOTE_PAR_DEFAUT);
    }

    @Test
    @Transactional
    public void updateNonExistingCritereChoixMembreJury() throws Exception {
        int databaseSizeBeforeUpdate = critereChoixMembreJuryRepository.findAll().size();

        // Create the CritereChoixMembreJury
        CritereChoixMembreJuryDTO critereChoixMembreJuryDTO = critereChoixMembreJuryMapper.toDto(critereChoixMembreJury);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCritereChoixMembreJuryMockMvc.perform(put("/api/critere-choix-membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critereChoixMembreJuryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CritereChoixMembreJury in the database
        List<CritereChoixMembreJury> critereChoixMembreJuryList = critereChoixMembreJuryRepository.findAll();
        assertThat(critereChoixMembreJuryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCritereChoixMembreJury() throws Exception {
        // Initialize the database
        critereChoixMembreJuryRepository.saveAndFlush(critereChoixMembreJury);

        int databaseSizeBeforeDelete = critereChoixMembreJuryRepository.findAll().size();

        // Delete the critereChoixMembreJury
        restCritereChoixMembreJuryMockMvc.perform(delete("/api/critere-choix-membre-juries/{id}", critereChoixMembreJury.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CritereChoixMembreJury> critereChoixMembreJuryList = critereChoixMembreJuryRepository.findAll();
        assertThat(critereChoixMembreJuryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CritereChoixMembreJury.class);
        CritereChoixMembreJury critereChoixMembreJury1 = new CritereChoixMembreJury();
        critereChoixMembreJury1.setId(1L);
        CritereChoixMembreJury critereChoixMembreJury2 = new CritereChoixMembreJury();
        critereChoixMembreJury2.setId(critereChoixMembreJury1.getId());
        assertThat(critereChoixMembreJury1).isEqualTo(critereChoixMembreJury2);
        critereChoixMembreJury2.setId(2L);
        assertThat(critereChoixMembreJury1).isNotEqualTo(critereChoixMembreJury2);
        critereChoixMembreJury1.setId(null);
        assertThat(critereChoixMembreJury1).isNotEqualTo(critereChoixMembreJury2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CritereChoixMembreJuryDTO.class);
        CritereChoixMembreJuryDTO critereChoixMembreJuryDTO1 = new CritereChoixMembreJuryDTO();
        critereChoixMembreJuryDTO1.setId(1L);
        CritereChoixMembreJuryDTO critereChoixMembreJuryDTO2 = new CritereChoixMembreJuryDTO();
        assertThat(critereChoixMembreJuryDTO1).isNotEqualTo(critereChoixMembreJuryDTO2);
        critereChoixMembreJuryDTO2.setId(critereChoixMembreJuryDTO1.getId());
        assertThat(critereChoixMembreJuryDTO1).isEqualTo(critereChoixMembreJuryDTO2);
        critereChoixMembreJuryDTO2.setId(2L);
        assertThat(critereChoixMembreJuryDTO1).isNotEqualTo(critereChoixMembreJuryDTO2);
        critereChoixMembreJuryDTO1.setId(null);
        assertThat(critereChoixMembreJuryDTO1).isNotEqualTo(critereChoixMembreJuryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(critereChoixMembreJuryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(critereChoixMembreJuryMapper.fromId(null)).isNull();
    }
}
