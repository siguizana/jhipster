package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.EtapeExamen;
import com.test.repository.EtapeExamenRepository;
import com.test.service.EtapeExamenService;
import com.test.service.dto.EtapeExamenDTO;
import com.test.service.mapper.EtapeExamenMapper;
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
 * Test class for the EtapeExamenResource REST controller.
 *
 * @see EtapeExamenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class EtapeExamenResourceIntTest {

    private static final String DEFAULT_LIBELLE_ETAPE_EXAMEN = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_ETAPE_EXAMEN = "BBBBBBBBBB";

    @Autowired
    private EtapeExamenRepository etapeExamenRepository;

    @Autowired
    private EtapeExamenMapper etapeExamenMapper;

    @Autowired
    private EtapeExamenService etapeExamenService;

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

    private MockMvc restEtapeExamenMockMvc;

    private EtapeExamen etapeExamen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtapeExamenResource etapeExamenResource = new EtapeExamenResource(etapeExamenService);
        this.restEtapeExamenMockMvc = MockMvcBuilders.standaloneSetup(etapeExamenResource)
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
    public static EtapeExamen createEntity(EntityManager em) {
        EtapeExamen etapeExamen = new EtapeExamen()
            .libelleEtapeExamen(DEFAULT_LIBELLE_ETAPE_EXAMEN);
        return etapeExamen;
    }

    @Before
    public void initTest() {
        etapeExamen = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtapeExamen() throws Exception {
        int databaseSizeBeforeCreate = etapeExamenRepository.findAll().size();

        // Create the EtapeExamen
        EtapeExamenDTO etapeExamenDTO = etapeExamenMapper.toDto(etapeExamen);
        restEtapeExamenMockMvc.perform(post("/api/etape-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapeExamenDTO)))
            .andExpect(status().isCreated());

        // Validate the EtapeExamen in the database
        List<EtapeExamen> etapeExamenList = etapeExamenRepository.findAll();
        assertThat(etapeExamenList).hasSize(databaseSizeBeforeCreate + 1);
        EtapeExamen testEtapeExamen = etapeExamenList.get(etapeExamenList.size() - 1);
        assertThat(testEtapeExamen.getLibelleEtapeExamen()).isEqualTo(DEFAULT_LIBELLE_ETAPE_EXAMEN);
    }

    @Test
    @Transactional
    public void createEtapeExamenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etapeExamenRepository.findAll().size();

        // Create the EtapeExamen with an existing ID
        etapeExamen.setId(1L);
        EtapeExamenDTO etapeExamenDTO = etapeExamenMapper.toDto(etapeExamen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapeExamenMockMvc.perform(post("/api/etape-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapeExamenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EtapeExamen in the database
        List<EtapeExamen> etapeExamenList = etapeExamenRepository.findAll();
        assertThat(etapeExamenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleEtapeExamenIsRequired() throws Exception {
        int databaseSizeBeforeTest = etapeExamenRepository.findAll().size();
        // set the field null
        etapeExamen.setLibelleEtapeExamen(null);

        // Create the EtapeExamen, which fails.
        EtapeExamenDTO etapeExamenDTO = etapeExamenMapper.toDto(etapeExamen);

        restEtapeExamenMockMvc.perform(post("/api/etape-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapeExamenDTO)))
            .andExpect(status().isBadRequest());

        List<EtapeExamen> etapeExamenList = etapeExamenRepository.findAll();
        assertThat(etapeExamenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEtapeExamen() throws Exception {
        // Initialize the database
        etapeExamenRepository.saveAndFlush(etapeExamen);

        // Get all the etapeExamenList
        restEtapeExamenMockMvc.perform(get("/api/etape-examen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapeExamen.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleEtapeExamen").value(hasItem(DEFAULT_LIBELLE_ETAPE_EXAMEN.toString())));
    }
    
    @Test
    @Transactional
    public void getEtapeExamen() throws Exception {
        // Initialize the database
        etapeExamenRepository.saveAndFlush(etapeExamen);

        // Get the etapeExamen
        restEtapeExamenMockMvc.perform(get("/api/etape-examen/{id}", etapeExamen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etapeExamen.getId().intValue()))
            .andExpect(jsonPath("$.libelleEtapeExamen").value(DEFAULT_LIBELLE_ETAPE_EXAMEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEtapeExamen() throws Exception {
        // Get the etapeExamen
        restEtapeExamenMockMvc.perform(get("/api/etape-examen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtapeExamen() throws Exception {
        // Initialize the database
        etapeExamenRepository.saveAndFlush(etapeExamen);

        int databaseSizeBeforeUpdate = etapeExamenRepository.findAll().size();

        // Update the etapeExamen
        EtapeExamen updatedEtapeExamen = etapeExamenRepository.findById(etapeExamen.getId()).get();
        // Disconnect from session so that the updates on updatedEtapeExamen are not directly saved in db
        em.detach(updatedEtapeExamen);
        updatedEtapeExamen
            .libelleEtapeExamen(UPDATED_LIBELLE_ETAPE_EXAMEN);
        EtapeExamenDTO etapeExamenDTO = etapeExamenMapper.toDto(updatedEtapeExamen);

        restEtapeExamenMockMvc.perform(put("/api/etape-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapeExamenDTO)))
            .andExpect(status().isOk());

        // Validate the EtapeExamen in the database
        List<EtapeExamen> etapeExamenList = etapeExamenRepository.findAll();
        assertThat(etapeExamenList).hasSize(databaseSizeBeforeUpdate);
        EtapeExamen testEtapeExamen = etapeExamenList.get(etapeExamenList.size() - 1);
        assertThat(testEtapeExamen.getLibelleEtapeExamen()).isEqualTo(UPDATED_LIBELLE_ETAPE_EXAMEN);
    }

    @Test
    @Transactional
    public void updateNonExistingEtapeExamen() throws Exception {
        int databaseSizeBeforeUpdate = etapeExamenRepository.findAll().size();

        // Create the EtapeExamen
        EtapeExamenDTO etapeExamenDTO = etapeExamenMapper.toDto(etapeExamen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtapeExamenMockMvc.perform(put("/api/etape-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapeExamenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EtapeExamen in the database
        List<EtapeExamen> etapeExamenList = etapeExamenRepository.findAll();
        assertThat(etapeExamenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtapeExamen() throws Exception {
        // Initialize the database
        etapeExamenRepository.saveAndFlush(etapeExamen);

        int databaseSizeBeforeDelete = etapeExamenRepository.findAll().size();

        // Delete the etapeExamen
        restEtapeExamenMockMvc.perform(delete("/api/etape-examen/{id}", etapeExamen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EtapeExamen> etapeExamenList = etapeExamenRepository.findAll();
        assertThat(etapeExamenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapeExamen.class);
        EtapeExamen etapeExamen1 = new EtapeExamen();
        etapeExamen1.setId(1L);
        EtapeExamen etapeExamen2 = new EtapeExamen();
        etapeExamen2.setId(etapeExamen1.getId());
        assertThat(etapeExamen1).isEqualTo(etapeExamen2);
        etapeExamen2.setId(2L);
        assertThat(etapeExamen1).isNotEqualTo(etapeExamen2);
        etapeExamen1.setId(null);
        assertThat(etapeExamen1).isNotEqualTo(etapeExamen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtapeExamenDTO.class);
        EtapeExamenDTO etapeExamenDTO1 = new EtapeExamenDTO();
        etapeExamenDTO1.setId(1L);
        EtapeExamenDTO etapeExamenDTO2 = new EtapeExamenDTO();
        assertThat(etapeExamenDTO1).isNotEqualTo(etapeExamenDTO2);
        etapeExamenDTO2.setId(etapeExamenDTO1.getId());
        assertThat(etapeExamenDTO1).isEqualTo(etapeExamenDTO2);
        etapeExamenDTO2.setId(2L);
        assertThat(etapeExamenDTO1).isNotEqualTo(etapeExamenDTO2);
        etapeExamenDTO1.setId(null);
        assertThat(etapeExamenDTO1).isNotEqualTo(etapeExamenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(etapeExamenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(etapeExamenMapper.fromId(null)).isNull();
    }
}
