package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Epreuve;
import com.test.repository.EpreuveRepository;
import com.test.service.EpreuveService;
import com.test.service.dto.EpreuveDTO;
import com.test.service.mapper.EpreuveMapper;
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
 * Test class for the EpreuveResource REST controller.
 *
 * @see EpreuveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class EpreuveResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private EpreuveRepository epreuveRepository;

    @Autowired
    private EpreuveMapper epreuveMapper;

    @Autowired
    private EpreuveService epreuveService;

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

    private MockMvc restEpreuveMockMvc;

    private Epreuve epreuve;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EpreuveResource epreuveResource = new EpreuveResource(epreuveService);
        this.restEpreuveMockMvc = MockMvcBuilders.standaloneSetup(epreuveResource)
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
    public static Epreuve createEntity(EntityManager em) {
        Epreuve epreuve = new Epreuve()
            .libelle(DEFAULT_LIBELLE);
        return epreuve;
    }

    @Before
    public void initTest() {
        epreuve = createEntity(em);
    }

    @Test
    @Transactional
    public void createEpreuve() throws Exception {
        int databaseSizeBeforeCreate = epreuveRepository.findAll().size();

        // Create the Epreuve
        EpreuveDTO epreuveDTO = epreuveMapper.toDto(epreuve);
        restEpreuveMockMvc.perform(post("/api/epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epreuveDTO)))
            .andExpect(status().isCreated());

        // Validate the Epreuve in the database
        List<Epreuve> epreuveList = epreuveRepository.findAll();
        assertThat(epreuveList).hasSize(databaseSizeBeforeCreate + 1);
        Epreuve testEpreuve = epreuveList.get(epreuveList.size() - 1);
        assertThat(testEpreuve.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createEpreuveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = epreuveRepository.findAll().size();

        // Create the Epreuve with an existing ID
        epreuve.setId(1L);
        EpreuveDTO epreuveDTO = epreuveMapper.toDto(epreuve);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEpreuveMockMvc.perform(post("/api/epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epreuveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Epreuve in the database
        List<Epreuve> epreuveList = epreuveRepository.findAll();
        assertThat(epreuveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = epreuveRepository.findAll().size();
        // set the field null
        epreuve.setLibelle(null);

        // Create the Epreuve, which fails.
        EpreuveDTO epreuveDTO = epreuveMapper.toDto(epreuve);

        restEpreuveMockMvc.perform(post("/api/epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epreuveDTO)))
            .andExpect(status().isBadRequest());

        List<Epreuve> epreuveList = epreuveRepository.findAll();
        assertThat(epreuveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEpreuves() throws Exception {
        // Initialize the database
        epreuveRepository.saveAndFlush(epreuve);

        // Get all the epreuveList
        restEpreuveMockMvc.perform(get("/api/epreuves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(epreuve.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getEpreuve() throws Exception {
        // Initialize the database
        epreuveRepository.saveAndFlush(epreuve);

        // Get the epreuve
        restEpreuveMockMvc.perform(get("/api/epreuves/{id}", epreuve.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(epreuve.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEpreuve() throws Exception {
        // Get the epreuve
        restEpreuveMockMvc.perform(get("/api/epreuves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEpreuve() throws Exception {
        // Initialize the database
        epreuveRepository.saveAndFlush(epreuve);

        int databaseSizeBeforeUpdate = epreuveRepository.findAll().size();

        // Update the epreuve
        Epreuve updatedEpreuve = epreuveRepository.findById(epreuve.getId()).get();
        // Disconnect from session so that the updates on updatedEpreuve are not directly saved in db
        em.detach(updatedEpreuve);
        updatedEpreuve
            .libelle(UPDATED_LIBELLE);
        EpreuveDTO epreuveDTO = epreuveMapper.toDto(updatedEpreuve);

        restEpreuveMockMvc.perform(put("/api/epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epreuveDTO)))
            .andExpect(status().isOk());

        // Validate the Epreuve in the database
        List<Epreuve> epreuveList = epreuveRepository.findAll();
        assertThat(epreuveList).hasSize(databaseSizeBeforeUpdate);
        Epreuve testEpreuve = epreuveList.get(epreuveList.size() - 1);
        assertThat(testEpreuve.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingEpreuve() throws Exception {
        int databaseSizeBeforeUpdate = epreuveRepository.findAll().size();

        // Create the Epreuve
        EpreuveDTO epreuveDTO = epreuveMapper.toDto(epreuve);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEpreuveMockMvc.perform(put("/api/epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epreuveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Epreuve in the database
        List<Epreuve> epreuveList = epreuveRepository.findAll();
        assertThat(epreuveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEpreuve() throws Exception {
        // Initialize the database
        epreuveRepository.saveAndFlush(epreuve);

        int databaseSizeBeforeDelete = epreuveRepository.findAll().size();

        // Delete the epreuve
        restEpreuveMockMvc.perform(delete("/api/epreuves/{id}", epreuve.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Epreuve> epreuveList = epreuveRepository.findAll();
        assertThat(epreuveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Epreuve.class);
        Epreuve epreuve1 = new Epreuve();
        epreuve1.setId(1L);
        Epreuve epreuve2 = new Epreuve();
        epreuve2.setId(epreuve1.getId());
        assertThat(epreuve1).isEqualTo(epreuve2);
        epreuve2.setId(2L);
        assertThat(epreuve1).isNotEqualTo(epreuve2);
        epreuve1.setId(null);
        assertThat(epreuve1).isNotEqualTo(epreuve2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EpreuveDTO.class);
        EpreuveDTO epreuveDTO1 = new EpreuveDTO();
        epreuveDTO1.setId(1L);
        EpreuveDTO epreuveDTO2 = new EpreuveDTO();
        assertThat(epreuveDTO1).isNotEqualTo(epreuveDTO2);
        epreuveDTO2.setId(epreuveDTO1.getId());
        assertThat(epreuveDTO1).isEqualTo(epreuveDTO2);
        epreuveDTO2.setId(2L);
        assertThat(epreuveDTO1).isNotEqualTo(epreuveDTO2);
        epreuveDTO1.setId(null);
        assertThat(epreuveDTO1).isNotEqualTo(epreuveDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(epreuveMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(epreuveMapper.fromId(null)).isNull();
    }
}
