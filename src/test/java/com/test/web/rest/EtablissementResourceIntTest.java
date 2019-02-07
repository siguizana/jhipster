package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Etablissement;
import com.test.repository.EtablissementRepository;
import com.test.service.EtablissementService;
import com.test.service.dto.EtablissementDTO;
import com.test.service.mapper.EtablissementMapper;
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
 * Test class for the EtablissementResource REST controller.
 *
 * @see EtablissementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class EtablissementResourceIntTest {

    private static final String DEFAULT_CODE_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ETABLISSEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_ETABLISSEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_RESPONSABLE_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_RESPONSABLE_ETABLISSEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_RESPONSABLE_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_RESPONSABLE_ETABLISSEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACTACT_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTACT_ETABLISSEMENT = "BBBBBBBBBB";

    @Autowired
    private EtablissementRepository etablissementRepository;

    @Autowired
    private EtablissementMapper etablissementMapper;

    @Autowired
    private EtablissementService etablissementService;

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

    private MockMvc restEtablissementMockMvc;

    private Etablissement etablissement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtablissementResource etablissementResource = new EtablissementResource(etablissementService);
        this.restEtablissementMockMvc = MockMvcBuilders.standaloneSetup(etablissementResource)
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
    public static Etablissement createEntity(EntityManager em) {
        Etablissement etablissement = new Etablissement()
            .codeEtablissement(DEFAULT_CODE_ETABLISSEMENT)
            .libelleEtablissement(DEFAULT_LIBELLE_ETABLISSEMENT)
            .nomResponsableEtablissement(DEFAULT_NOM_RESPONSABLE_ETABLISSEMENT)
            .prenomResponsableEtablissement(DEFAULT_PRENOM_RESPONSABLE_ETABLISSEMENT)
            .contactactEtablissement(DEFAULT_CONTACTACT_ETABLISSEMENT);
        return etablissement;
    }

    @Before
    public void initTest() {
        etablissement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtablissement() throws Exception {
        int databaseSizeBeforeCreate = etablissementRepository.findAll().size();

        // Create the Etablissement
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);
        restEtablissementMockMvc.perform(post("/api/etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etablissementDTO)))
            .andExpect(status().isCreated());

        // Validate the Etablissement in the database
        List<Etablissement> etablissementList = etablissementRepository.findAll();
        assertThat(etablissementList).hasSize(databaseSizeBeforeCreate + 1);
        Etablissement testEtablissement = etablissementList.get(etablissementList.size() - 1);
        assertThat(testEtablissement.getCodeEtablissement()).isEqualTo(DEFAULT_CODE_ETABLISSEMENT);
        assertThat(testEtablissement.getLibelleEtablissement()).isEqualTo(DEFAULT_LIBELLE_ETABLISSEMENT);
        assertThat(testEtablissement.getNomResponsableEtablissement()).isEqualTo(DEFAULT_NOM_RESPONSABLE_ETABLISSEMENT);
        assertThat(testEtablissement.getPrenomResponsableEtablissement()).isEqualTo(DEFAULT_PRENOM_RESPONSABLE_ETABLISSEMENT);
        assertThat(testEtablissement.getContactactEtablissement()).isEqualTo(DEFAULT_CONTACTACT_ETABLISSEMENT);
    }

    @Test
    @Transactional
    public void createEtablissementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etablissementRepository.findAll().size();

        // Create the Etablissement with an existing ID
        etablissement.setId(1L);
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtablissementMockMvc.perform(post("/api/etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etablissementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Etablissement in the database
        List<Etablissement> etablissementList = etablissementRepository.findAll();
        assertThat(etablissementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleEtablissementIsRequired() throws Exception {
        int databaseSizeBeforeTest = etablissementRepository.findAll().size();
        // set the field null
        etablissement.setLibelleEtablissement(null);

        // Create the Etablissement, which fails.
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        restEtablissementMockMvc.perform(post("/api/etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etablissementDTO)))
            .andExpect(status().isBadRequest());

        List<Etablissement> etablissementList = etablissementRepository.findAll();
        assertThat(etablissementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomResponsableEtablissementIsRequired() throws Exception {
        int databaseSizeBeforeTest = etablissementRepository.findAll().size();
        // set the field null
        etablissement.setNomResponsableEtablissement(null);

        // Create the Etablissement, which fails.
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        restEtablissementMockMvc.perform(post("/api/etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etablissementDTO)))
            .andExpect(status().isBadRequest());

        List<Etablissement> etablissementList = etablissementRepository.findAll();
        assertThat(etablissementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomResponsableEtablissementIsRequired() throws Exception {
        int databaseSizeBeforeTest = etablissementRepository.findAll().size();
        // set the field null
        etablissement.setPrenomResponsableEtablissement(null);

        // Create the Etablissement, which fails.
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        restEtablissementMockMvc.perform(post("/api/etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etablissementDTO)))
            .andExpect(status().isBadRequest());

        List<Etablissement> etablissementList = etablissementRepository.findAll();
        assertThat(etablissementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactactEtablissementIsRequired() throws Exception {
        int databaseSizeBeforeTest = etablissementRepository.findAll().size();
        // set the field null
        etablissement.setContactactEtablissement(null);

        // Create the Etablissement, which fails.
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        restEtablissementMockMvc.perform(post("/api/etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etablissementDTO)))
            .andExpect(status().isBadRequest());

        List<Etablissement> etablissementList = etablissementRepository.findAll();
        assertThat(etablissementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEtablissements() throws Exception {
        // Initialize the database
        etablissementRepository.saveAndFlush(etablissement);

        // Get all the etablissementList
        restEtablissementMockMvc.perform(get("/api/etablissements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etablissement.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeEtablissement").value(hasItem(DEFAULT_CODE_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].libelleEtablissement").value(hasItem(DEFAULT_LIBELLE_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].nomResponsableEtablissement").value(hasItem(DEFAULT_NOM_RESPONSABLE_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].prenomResponsableEtablissement").value(hasItem(DEFAULT_PRENOM_RESPONSABLE_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].contactactEtablissement").value(hasItem(DEFAULT_CONTACTACT_ETABLISSEMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getEtablissement() throws Exception {
        // Initialize the database
        etablissementRepository.saveAndFlush(etablissement);

        // Get the etablissement
        restEtablissementMockMvc.perform(get("/api/etablissements/{id}", etablissement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etablissement.getId().intValue()))
            .andExpect(jsonPath("$.codeEtablissement").value(DEFAULT_CODE_ETABLISSEMENT.toString()))
            .andExpect(jsonPath("$.libelleEtablissement").value(DEFAULT_LIBELLE_ETABLISSEMENT.toString()))
            .andExpect(jsonPath("$.nomResponsableEtablissement").value(DEFAULT_NOM_RESPONSABLE_ETABLISSEMENT.toString()))
            .andExpect(jsonPath("$.prenomResponsableEtablissement").value(DEFAULT_PRENOM_RESPONSABLE_ETABLISSEMENT.toString()))
            .andExpect(jsonPath("$.contactactEtablissement").value(DEFAULT_CONTACTACT_ETABLISSEMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEtablissement() throws Exception {
        // Get the etablissement
        restEtablissementMockMvc.perform(get("/api/etablissements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtablissement() throws Exception {
        // Initialize the database
        etablissementRepository.saveAndFlush(etablissement);

        int databaseSizeBeforeUpdate = etablissementRepository.findAll().size();

        // Update the etablissement
        Etablissement updatedEtablissement = etablissementRepository.findById(etablissement.getId()).get();
        // Disconnect from session so that the updates on updatedEtablissement are not directly saved in db
        em.detach(updatedEtablissement);
        updatedEtablissement
            .codeEtablissement(UPDATED_CODE_ETABLISSEMENT)
            .libelleEtablissement(UPDATED_LIBELLE_ETABLISSEMENT)
            .nomResponsableEtablissement(UPDATED_NOM_RESPONSABLE_ETABLISSEMENT)
            .prenomResponsableEtablissement(UPDATED_PRENOM_RESPONSABLE_ETABLISSEMENT)
            .contactactEtablissement(UPDATED_CONTACTACT_ETABLISSEMENT);
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(updatedEtablissement);

        restEtablissementMockMvc.perform(put("/api/etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etablissementDTO)))
            .andExpect(status().isOk());

        // Validate the Etablissement in the database
        List<Etablissement> etablissementList = etablissementRepository.findAll();
        assertThat(etablissementList).hasSize(databaseSizeBeforeUpdate);
        Etablissement testEtablissement = etablissementList.get(etablissementList.size() - 1);
        assertThat(testEtablissement.getCodeEtablissement()).isEqualTo(UPDATED_CODE_ETABLISSEMENT);
        assertThat(testEtablissement.getLibelleEtablissement()).isEqualTo(UPDATED_LIBELLE_ETABLISSEMENT);
        assertThat(testEtablissement.getNomResponsableEtablissement()).isEqualTo(UPDATED_NOM_RESPONSABLE_ETABLISSEMENT);
        assertThat(testEtablissement.getPrenomResponsableEtablissement()).isEqualTo(UPDATED_PRENOM_RESPONSABLE_ETABLISSEMENT);
        assertThat(testEtablissement.getContactactEtablissement()).isEqualTo(UPDATED_CONTACTACT_ETABLISSEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingEtablissement() throws Exception {
        int databaseSizeBeforeUpdate = etablissementRepository.findAll().size();

        // Create the Etablissement
        EtablissementDTO etablissementDTO = etablissementMapper.toDto(etablissement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtablissementMockMvc.perform(put("/api/etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etablissementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Etablissement in the database
        List<Etablissement> etablissementList = etablissementRepository.findAll();
        assertThat(etablissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtablissement() throws Exception {
        // Initialize the database
        etablissementRepository.saveAndFlush(etablissement);

        int databaseSizeBeforeDelete = etablissementRepository.findAll().size();

        // Delete the etablissement
        restEtablissementMockMvc.perform(delete("/api/etablissements/{id}", etablissement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Etablissement> etablissementList = etablissementRepository.findAll();
        assertThat(etablissementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etablissement.class);
        Etablissement etablissement1 = new Etablissement();
        etablissement1.setId(1L);
        Etablissement etablissement2 = new Etablissement();
        etablissement2.setId(etablissement1.getId());
        assertThat(etablissement1).isEqualTo(etablissement2);
        etablissement2.setId(2L);
        assertThat(etablissement1).isNotEqualTo(etablissement2);
        etablissement1.setId(null);
        assertThat(etablissement1).isNotEqualTo(etablissement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtablissementDTO.class);
        EtablissementDTO etablissementDTO1 = new EtablissementDTO();
        etablissementDTO1.setId(1L);
        EtablissementDTO etablissementDTO2 = new EtablissementDTO();
        assertThat(etablissementDTO1).isNotEqualTo(etablissementDTO2);
        etablissementDTO2.setId(etablissementDTO1.getId());
        assertThat(etablissementDTO1).isEqualTo(etablissementDTO2);
        etablissementDTO2.setId(2L);
        assertThat(etablissementDTO1).isNotEqualTo(etablissementDTO2);
        etablissementDTO1.setId(null);
        assertThat(etablissementDTO1).isNotEqualTo(etablissementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(etablissementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(etablissementMapper.fromId(null)).isNull();
    }
}
