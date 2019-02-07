package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Enseignant;
import com.test.repository.EnseignantRepository;
import com.test.service.EnseignantService;
import com.test.service.dto.EnseignantDTO;
import com.test.service.mapper.EnseignantMapper;
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
 * Test class for the EnseignantResource REST controller.
 *
 * @see EnseignantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class EnseignantResourceIntTest {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_ENSEIGNANT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ENSEIGNANT = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_ENSEIGNANT = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_ENSEIGNANT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_ENSEIGNANT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_ENSEIGNANT = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE_ENSEIGNANT = "AAAAAAAAAA";
    private static final String UPDATED_GRADE_ENSEIGNANT = "BBBBBBBBBB";

    private static final String DEFAULT_ECHELON_ENSEIGNANT = "AAAAAAAAAA";
    private static final String UPDATED_ECHELON_ENSEIGNANT = "BBBBBBBBBB";

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private EnseignantMapper enseignantMapper;

    @Autowired
    private EnseignantService enseignantService;

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

    private MockMvc restEnseignantMockMvc;

    private Enseignant enseignant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnseignantResource enseignantResource = new EnseignantResource(enseignantService);
        this.restEnseignantMockMvc = MockMvcBuilders.standaloneSetup(enseignantResource)
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
    public static Enseignant createEntity(EntityManager em) {
        Enseignant enseignant = new Enseignant()
            .matricule(DEFAULT_MATRICULE)
            .nomEnseignant(DEFAULT_NOM_ENSEIGNANT)
            .prenomEnseignant(DEFAULT_PRENOM_ENSEIGNANT)
            .contactEnseignant(DEFAULT_CONTACT_ENSEIGNANT)
            .gradeEnseignant(DEFAULT_GRADE_ENSEIGNANT)
            .echelonEnseignant(DEFAULT_ECHELON_ENSEIGNANT);
        return enseignant;
    }

    @Before
    public void initTest() {
        enseignant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnseignant() throws Exception {
        int databaseSizeBeforeCreate = enseignantRepository.findAll().size();

        // Create the Enseignant
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);
        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isCreated());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeCreate + 1);
        Enseignant testEnseignant = enseignantList.get(enseignantList.size() - 1);
        assertThat(testEnseignant.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testEnseignant.getNomEnseignant()).isEqualTo(DEFAULT_NOM_ENSEIGNANT);
        assertThat(testEnseignant.getPrenomEnseignant()).isEqualTo(DEFAULT_PRENOM_ENSEIGNANT);
        assertThat(testEnseignant.getContactEnseignant()).isEqualTo(DEFAULT_CONTACT_ENSEIGNANT);
        assertThat(testEnseignant.getGradeEnseignant()).isEqualTo(DEFAULT_GRADE_ENSEIGNANT);
        assertThat(testEnseignant.getEchelonEnseignant()).isEqualTo(DEFAULT_ECHELON_ENSEIGNANT);
    }

    @Test
    @Transactional
    public void createEnseignantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enseignantRepository.findAll().size();

        // Create the Enseignant with an existing ID
        enseignant.setId(1L);
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomEnseignantIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setNomEnseignant(null);

        // Create the Enseignant, which fails.
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);

        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomEnseignantIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setPrenomEnseignant(null);

        // Create the Enseignant, which fails.
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);

        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnseignants() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList
        restEnseignantMockMvc.perform(get("/api/enseignants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enseignant.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE.toString())))
            .andExpect(jsonPath("$.[*].nomEnseignant").value(hasItem(DEFAULT_NOM_ENSEIGNANT.toString())))
            .andExpect(jsonPath("$.[*].prenomEnseignant").value(hasItem(DEFAULT_PRENOM_ENSEIGNANT.toString())))
            .andExpect(jsonPath("$.[*].contactEnseignant").value(hasItem(DEFAULT_CONTACT_ENSEIGNANT.toString())))
            .andExpect(jsonPath("$.[*].gradeEnseignant").value(hasItem(DEFAULT_GRADE_ENSEIGNANT.toString())))
            .andExpect(jsonPath("$.[*].echelonEnseignant").value(hasItem(DEFAULT_ECHELON_ENSEIGNANT.toString())));
    }
    
    @Test
    @Transactional
    public void getEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", enseignant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enseignant.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE.toString()))
            .andExpect(jsonPath("$.nomEnseignant").value(DEFAULT_NOM_ENSEIGNANT.toString()))
            .andExpect(jsonPath("$.prenomEnseignant").value(DEFAULT_PRENOM_ENSEIGNANT.toString()))
            .andExpect(jsonPath("$.contactEnseignant").value(DEFAULT_CONTACT_ENSEIGNANT.toString()))
            .andExpect(jsonPath("$.gradeEnseignant").value(DEFAULT_GRADE_ENSEIGNANT.toString()))
            .andExpect(jsonPath("$.echelonEnseignant").value(DEFAULT_ECHELON_ENSEIGNANT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnseignant() throws Exception {
        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        int databaseSizeBeforeUpdate = enseignantRepository.findAll().size();

        // Update the enseignant
        Enseignant updatedEnseignant = enseignantRepository.findById(enseignant.getId()).get();
        // Disconnect from session so that the updates on updatedEnseignant are not directly saved in db
        em.detach(updatedEnseignant);
        updatedEnseignant
            .matricule(UPDATED_MATRICULE)
            .nomEnseignant(UPDATED_NOM_ENSEIGNANT)
            .prenomEnseignant(UPDATED_PRENOM_ENSEIGNANT)
            .contactEnseignant(UPDATED_CONTACT_ENSEIGNANT)
            .gradeEnseignant(UPDATED_GRADE_ENSEIGNANT)
            .echelonEnseignant(UPDATED_ECHELON_ENSEIGNANT);
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(updatedEnseignant);

        restEnseignantMockMvc.perform(put("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isOk());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeUpdate);
        Enseignant testEnseignant = enseignantList.get(enseignantList.size() - 1);
        assertThat(testEnseignant.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEnseignant.getNomEnseignant()).isEqualTo(UPDATED_NOM_ENSEIGNANT);
        assertThat(testEnseignant.getPrenomEnseignant()).isEqualTo(UPDATED_PRENOM_ENSEIGNANT);
        assertThat(testEnseignant.getContactEnseignant()).isEqualTo(UPDATED_CONTACT_ENSEIGNANT);
        assertThat(testEnseignant.getGradeEnseignant()).isEqualTo(UPDATED_GRADE_ENSEIGNANT);
        assertThat(testEnseignant.getEchelonEnseignant()).isEqualTo(UPDATED_ECHELON_ENSEIGNANT);
    }

    @Test
    @Transactional
    public void updateNonExistingEnseignant() throws Exception {
        int databaseSizeBeforeUpdate = enseignantRepository.findAll().size();

        // Create the Enseignant
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnseignantMockMvc.perform(put("/api/enseignants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        int databaseSizeBeforeDelete = enseignantRepository.findAll().size();

        // Delete the enseignant
        restEnseignantMockMvc.perform(delete("/api/enseignants/{id}", enseignant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enseignant.class);
        Enseignant enseignant1 = new Enseignant();
        enseignant1.setId(1L);
        Enseignant enseignant2 = new Enseignant();
        enseignant2.setId(enseignant1.getId());
        assertThat(enseignant1).isEqualTo(enseignant2);
        enseignant2.setId(2L);
        assertThat(enseignant1).isNotEqualTo(enseignant2);
        enseignant1.setId(null);
        assertThat(enseignant1).isNotEqualTo(enseignant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnseignantDTO.class);
        EnseignantDTO enseignantDTO1 = new EnseignantDTO();
        enseignantDTO1.setId(1L);
        EnseignantDTO enseignantDTO2 = new EnseignantDTO();
        assertThat(enseignantDTO1).isNotEqualTo(enseignantDTO2);
        enseignantDTO2.setId(enseignantDTO1.getId());
        assertThat(enseignantDTO1).isEqualTo(enseignantDTO2);
        enseignantDTO2.setId(2L);
        assertThat(enseignantDTO1).isNotEqualTo(enseignantDTO2);
        enseignantDTO1.setId(null);
        assertThat(enseignantDTO1).isNotEqualTo(enseignantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(enseignantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(enseignantMapper.fromId(null)).isNull();
    }
}
