package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.CritereExamen;
import com.test.repository.CritereExamenRepository;
import com.test.service.CritereExamenService;
import com.test.service.dto.CritereExamenDTO;
import com.test.service.mapper.CritereExamenMapper;
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
 * Test class for the CritereExamenResource REST controller.
 *
 * @see CritereExamenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class CritereExamenResourceIntTest {

    private static final String DEFAULT_LIBELLE_CRITERE_EXAMEN = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CRITERE_EXAMEN = "BBBBBBBBBB";

    private static final Float DEFAULT_VALEUR = 1F;
    private static final Float UPDATED_VALEUR = 2F;

    @Autowired
    private CritereExamenRepository critereExamenRepository;

    @Autowired
    private CritereExamenMapper critereExamenMapper;

    @Autowired
    private CritereExamenService critereExamenService;

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

    private MockMvc restCritereExamenMockMvc;

    private CritereExamen critereExamen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CritereExamenResource critereExamenResource = new CritereExamenResource(critereExamenService);
        this.restCritereExamenMockMvc = MockMvcBuilders.standaloneSetup(critereExamenResource)
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
    public static CritereExamen createEntity(EntityManager em) {
        CritereExamen critereExamen = new CritereExamen()
            .libelleCritereExamen(DEFAULT_LIBELLE_CRITERE_EXAMEN)
            .valeur(DEFAULT_VALEUR);
        return critereExamen;
    }

    @Before
    public void initTest() {
        critereExamen = createEntity(em);
    }

    @Test
    @Transactional
    public void createCritereExamen() throws Exception {
        int databaseSizeBeforeCreate = critereExamenRepository.findAll().size();

        // Create the CritereExamen
        CritereExamenDTO critereExamenDTO = critereExamenMapper.toDto(critereExamen);
        restCritereExamenMockMvc.perform(post("/api/critere-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critereExamenDTO)))
            .andExpect(status().isCreated());

        // Validate the CritereExamen in the database
        List<CritereExamen> critereExamenList = critereExamenRepository.findAll();
        assertThat(critereExamenList).hasSize(databaseSizeBeforeCreate + 1);
        CritereExamen testCritereExamen = critereExamenList.get(critereExamenList.size() - 1);
        assertThat(testCritereExamen.getLibelleCritereExamen()).isEqualTo(DEFAULT_LIBELLE_CRITERE_EXAMEN);
        assertThat(testCritereExamen.getValeur()).isEqualTo(DEFAULT_VALEUR);
    }

    @Test
    @Transactional
    public void createCritereExamenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = critereExamenRepository.findAll().size();

        // Create the CritereExamen with an existing ID
        critereExamen.setId(1L);
        CritereExamenDTO critereExamenDTO = critereExamenMapper.toDto(critereExamen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCritereExamenMockMvc.perform(post("/api/critere-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critereExamenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CritereExamen in the database
        List<CritereExamen> critereExamenList = critereExamenRepository.findAll();
        assertThat(critereExamenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleCritereExamenIsRequired() throws Exception {
        int databaseSizeBeforeTest = critereExamenRepository.findAll().size();
        // set the field null
        critereExamen.setLibelleCritereExamen(null);

        // Create the CritereExamen, which fails.
        CritereExamenDTO critereExamenDTO = critereExamenMapper.toDto(critereExamen);

        restCritereExamenMockMvc.perform(post("/api/critere-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critereExamenDTO)))
            .andExpect(status().isBadRequest());

        List<CritereExamen> critereExamenList = critereExamenRepository.findAll();
        assertThat(critereExamenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValeurIsRequired() throws Exception {
        int databaseSizeBeforeTest = critereExamenRepository.findAll().size();
        // set the field null
        critereExamen.setValeur(null);

        // Create the CritereExamen, which fails.
        CritereExamenDTO critereExamenDTO = critereExamenMapper.toDto(critereExamen);

        restCritereExamenMockMvc.perform(post("/api/critere-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critereExamenDTO)))
            .andExpect(status().isBadRequest());

        List<CritereExamen> critereExamenList = critereExamenRepository.findAll();
        assertThat(critereExamenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCritereExamen() throws Exception {
        // Initialize the database
        critereExamenRepository.saveAndFlush(critereExamen);

        // Get all the critereExamenList
        restCritereExamenMockMvc.perform(get("/api/critere-examen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(critereExamen.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleCritereExamen").value(hasItem(DEFAULT_LIBELLE_CRITERE_EXAMEN.toString())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCritereExamen() throws Exception {
        // Initialize the database
        critereExamenRepository.saveAndFlush(critereExamen);

        // Get the critereExamen
        restCritereExamenMockMvc.perform(get("/api/critere-examen/{id}", critereExamen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(critereExamen.getId().intValue()))
            .andExpect(jsonPath("$.libelleCritereExamen").value(DEFAULT_LIBELLE_CRITERE_EXAMEN.toString()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCritereExamen() throws Exception {
        // Get the critereExamen
        restCritereExamenMockMvc.perform(get("/api/critere-examen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCritereExamen() throws Exception {
        // Initialize the database
        critereExamenRepository.saveAndFlush(critereExamen);

        int databaseSizeBeforeUpdate = critereExamenRepository.findAll().size();

        // Update the critereExamen
        CritereExamen updatedCritereExamen = critereExamenRepository.findById(critereExamen.getId()).get();
        // Disconnect from session so that the updates on updatedCritereExamen are not directly saved in db
        em.detach(updatedCritereExamen);
        updatedCritereExamen
            .libelleCritereExamen(UPDATED_LIBELLE_CRITERE_EXAMEN)
            .valeur(UPDATED_VALEUR);
        CritereExamenDTO critereExamenDTO = critereExamenMapper.toDto(updatedCritereExamen);

        restCritereExamenMockMvc.perform(put("/api/critere-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critereExamenDTO)))
            .andExpect(status().isOk());

        // Validate the CritereExamen in the database
        List<CritereExamen> critereExamenList = critereExamenRepository.findAll();
        assertThat(critereExamenList).hasSize(databaseSizeBeforeUpdate);
        CritereExamen testCritereExamen = critereExamenList.get(critereExamenList.size() - 1);
        assertThat(testCritereExamen.getLibelleCritereExamen()).isEqualTo(UPDATED_LIBELLE_CRITERE_EXAMEN);
        assertThat(testCritereExamen.getValeur()).isEqualTo(UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingCritereExamen() throws Exception {
        int databaseSizeBeforeUpdate = critereExamenRepository.findAll().size();

        // Create the CritereExamen
        CritereExamenDTO critereExamenDTO = critereExamenMapper.toDto(critereExamen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCritereExamenMockMvc.perform(put("/api/critere-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(critereExamenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CritereExamen in the database
        List<CritereExamen> critereExamenList = critereExamenRepository.findAll();
        assertThat(critereExamenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCritereExamen() throws Exception {
        // Initialize the database
        critereExamenRepository.saveAndFlush(critereExamen);

        int databaseSizeBeforeDelete = critereExamenRepository.findAll().size();

        // Delete the critereExamen
        restCritereExamenMockMvc.perform(delete("/api/critere-examen/{id}", critereExamen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CritereExamen> critereExamenList = critereExamenRepository.findAll();
        assertThat(critereExamenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CritereExamen.class);
        CritereExamen critereExamen1 = new CritereExamen();
        critereExamen1.setId(1L);
        CritereExamen critereExamen2 = new CritereExamen();
        critereExamen2.setId(critereExamen1.getId());
        assertThat(critereExamen1).isEqualTo(critereExamen2);
        critereExamen2.setId(2L);
        assertThat(critereExamen1).isNotEqualTo(critereExamen2);
        critereExamen1.setId(null);
        assertThat(critereExamen1).isNotEqualTo(critereExamen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CritereExamenDTO.class);
        CritereExamenDTO critereExamenDTO1 = new CritereExamenDTO();
        critereExamenDTO1.setId(1L);
        CritereExamenDTO critereExamenDTO2 = new CritereExamenDTO();
        assertThat(critereExamenDTO1).isNotEqualTo(critereExamenDTO2);
        critereExamenDTO2.setId(critereExamenDTO1.getId());
        assertThat(critereExamenDTO1).isEqualTo(critereExamenDTO2);
        critereExamenDTO2.setId(2L);
        assertThat(critereExamenDTO1).isNotEqualTo(critereExamenDTO2);
        critereExamenDTO1.setId(null);
        assertThat(critereExamenDTO1).isNotEqualTo(critereExamenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(critereExamenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(critereExamenMapper.fromId(null)).isNull();
    }
}
