package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Candidat;
import com.test.repository.CandidatRepository;
import com.test.service.CandidatService;
import com.test.service.dto.CandidatDTO;
import com.test.service.mapper.CandidatMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.test.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.test.domain.enumeration.Sexe;
/**
 * Test class for the CandidatResource REST controller.
 *
 * @see CandidatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class CandidatResourceIntTest {

    private static final String DEFAULT_CODE_CANDIDAT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_CANDIDAT = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_CANDIDAT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CANDIDAT = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_CANDIDAT = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_CANDIDAT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_PAYS_NAISSANCE = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.MASCULIN;
    private static final Sexe UPDATED_SEXE = Sexe.FEMININ;

    private static final String DEFAULT_TELEPHONE_CANDIDAT = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_CANDIDAT = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITE_CANDIDAT = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITE_CANDIDAT = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULE_CANDIDAT = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE_CANDIDAT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ENREGISTREMENT_CANDIDAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENREGISTREMENT_CANDIDAT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DEBUT_CARIERE_CANDIDAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT_CARIERE_CANDIDAT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private CandidatMapper candidatMapper;

    @Autowired
    private CandidatService candidatService;

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

    private MockMvc restCandidatMockMvc;

    private Candidat candidat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidatResource candidatResource = new CandidatResource(candidatService);
        this.restCandidatMockMvc = MockMvcBuilders.standaloneSetup(candidatResource)
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
    public static Candidat createEntity(EntityManager em) {
        Candidat candidat = new Candidat()
            .codeCandidat(DEFAULT_CODE_CANDIDAT)
            .nomCandidat(DEFAULT_NOM_CANDIDAT)
            .prenomCandidat(DEFAULT_PRENOM_CANDIDAT)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lieuNaissance(DEFAULT_LIEU_NAISSANCE)
            .paysNaissance(DEFAULT_PAYS_NAISSANCE)
            .sexe(DEFAULT_SEXE)
            .telephoneCandidat(DEFAULT_TELEPHONE_CANDIDAT)
            .nationaliteCandidat(DEFAULT_NATIONALITE_CANDIDAT)
            .matriculeCandidat(DEFAULT_MATRICULE_CANDIDAT)
            .dateEnregistrementCandidat(DEFAULT_DATE_ENREGISTREMENT_CANDIDAT)
            .dateDebutCariereCandidat(DEFAULT_DATE_DEBUT_CARIERE_CANDIDAT);
        return candidat;
    }

    @Before
    public void initTest() {
        candidat = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidat() throws Exception {
        int databaseSizeBeforeCreate = candidatRepository.findAll().size();

        // Create the Candidat
        CandidatDTO candidatDTO = candidatMapper.toDto(candidat);
        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatDTO)))
            .andExpect(status().isCreated());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeCreate + 1);
        Candidat testCandidat = candidatList.get(candidatList.size() - 1);
        assertThat(testCandidat.getCodeCandidat()).isEqualTo(DEFAULT_CODE_CANDIDAT);
        assertThat(testCandidat.getNomCandidat()).isEqualTo(DEFAULT_NOM_CANDIDAT);
        assertThat(testCandidat.getPrenomCandidat()).isEqualTo(DEFAULT_PRENOM_CANDIDAT);
        assertThat(testCandidat.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testCandidat.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testCandidat.getPaysNaissance()).isEqualTo(DEFAULT_PAYS_NAISSANCE);
        assertThat(testCandidat.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testCandidat.getTelephoneCandidat()).isEqualTo(DEFAULT_TELEPHONE_CANDIDAT);
        assertThat(testCandidat.getNationaliteCandidat()).isEqualTo(DEFAULT_NATIONALITE_CANDIDAT);
        assertThat(testCandidat.getMatriculeCandidat()).isEqualTo(DEFAULT_MATRICULE_CANDIDAT);
        assertThat(testCandidat.getDateEnregistrementCandidat()).isEqualTo(DEFAULT_DATE_ENREGISTREMENT_CANDIDAT);
        assertThat(testCandidat.getDateDebutCariereCandidat()).isEqualTo(DEFAULT_DATE_DEBUT_CARIERE_CANDIDAT);
    }

    @Test
    @Transactional
    public void createCandidatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidatRepository.findAll().size();

        // Create the Candidat with an existing ID
        candidat.setId(1L);
        CandidatDTO candidatDTO = candidatMapper.toDto(candidat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomCandidatIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatRepository.findAll().size();
        // set the field null
        candidat.setNomCandidat(null);

        // Create the Candidat, which fails.
        CandidatDTO candidatDTO = candidatMapper.toDto(candidat);

        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatDTO)))
            .andExpect(status().isBadRequest());

        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomCandidatIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatRepository.findAll().size();
        // set the field null
        candidat.setPrenomCandidat(null);

        // Create the Candidat, which fails.
        CandidatDTO candidatDTO = candidatMapper.toDto(candidat);

        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatDTO)))
            .andExpect(status().isBadRequest());

        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatRepository.findAll().size();
        // set the field null
        candidat.setDateNaissance(null);

        // Create the Candidat, which fails.
        CandidatDTO candidatDTO = candidatMapper.toDto(candidat);

        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatDTO)))
            .andExpect(status().isBadRequest());

        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLieuNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatRepository.findAll().size();
        // set the field null
        candidat.setLieuNaissance(null);

        // Create the Candidat, which fails.
        CandidatDTO candidatDTO = candidatMapper.toDto(candidat);

        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatDTO)))
            .andExpect(status().isBadRequest());

        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaysNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatRepository.findAll().size();
        // set the field null
        candidat.setPaysNaissance(null);

        // Create the Candidat, which fails.
        CandidatDTO candidatDTO = candidatMapper.toDto(candidat);

        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatDTO)))
            .andExpect(status().isBadRequest());

        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexeIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatRepository.findAll().size();
        // set the field null
        candidat.setSexe(null);

        // Create the Candidat, which fails.
        CandidatDTO candidatDTO = candidatMapper.toDto(candidat);

        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatDTO)))
            .andExpect(status().isBadRequest());

        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNationaliteCandidatIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidatRepository.findAll().size();
        // set the field null
        candidat.setNationaliteCandidat(null);

        // Create the Candidat, which fails.
        CandidatDTO candidatDTO = candidatMapper.toDto(candidat);

        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatDTO)))
            .andExpect(status().isBadRequest());

        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCandidats() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList
        restCandidatMockMvc.perform(get("/api/candidats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidat.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeCandidat").value(hasItem(DEFAULT_CODE_CANDIDAT.toString())))
            .andExpect(jsonPath("$.[*].nomCandidat").value(hasItem(DEFAULT_NOM_CANDIDAT.toString())))
            .andExpect(jsonPath("$.[*].prenomCandidat").value(hasItem(DEFAULT_PRENOM_CANDIDAT.toString())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].paysNaissance").value(hasItem(DEFAULT_PAYS_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].telephoneCandidat").value(hasItem(DEFAULT_TELEPHONE_CANDIDAT.toString())))
            .andExpect(jsonPath("$.[*].nationaliteCandidat").value(hasItem(DEFAULT_NATIONALITE_CANDIDAT.toString())))
            .andExpect(jsonPath("$.[*].matriculeCandidat").value(hasItem(DEFAULT_MATRICULE_CANDIDAT.toString())))
            .andExpect(jsonPath("$.[*].dateEnregistrementCandidat").value(hasItem(DEFAULT_DATE_ENREGISTREMENT_CANDIDAT.toString())))
            .andExpect(jsonPath("$.[*].dateDebutCariereCandidat").value(hasItem(DEFAULT_DATE_DEBUT_CARIERE_CANDIDAT.toString())));
    }
    
    @Test
    @Transactional
    public void getCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get the candidat
        restCandidatMockMvc.perform(get("/api/candidats/{id}", candidat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidat.getId().intValue()))
            .andExpect(jsonPath("$.codeCandidat").value(DEFAULT_CODE_CANDIDAT.toString()))
            .andExpect(jsonPath("$.nomCandidat").value(DEFAULT_NOM_CANDIDAT.toString()))
            .andExpect(jsonPath("$.prenomCandidat").value(DEFAULT_PRENOM_CANDIDAT.toString()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuNaissance").value(DEFAULT_LIEU_NAISSANCE.toString()))
            .andExpect(jsonPath("$.paysNaissance").value(DEFAULT_PAYS_NAISSANCE.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.telephoneCandidat").value(DEFAULT_TELEPHONE_CANDIDAT.toString()))
            .andExpect(jsonPath("$.nationaliteCandidat").value(DEFAULT_NATIONALITE_CANDIDAT.toString()))
            .andExpect(jsonPath("$.matriculeCandidat").value(DEFAULT_MATRICULE_CANDIDAT.toString()))
            .andExpect(jsonPath("$.dateEnregistrementCandidat").value(DEFAULT_DATE_ENREGISTREMENT_CANDIDAT.toString()))
            .andExpect(jsonPath("$.dateDebutCariereCandidat").value(DEFAULT_DATE_DEBUT_CARIERE_CANDIDAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCandidat() throws Exception {
        // Get the candidat
        restCandidatMockMvc.perform(get("/api/candidats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        int databaseSizeBeforeUpdate = candidatRepository.findAll().size();

        // Update the candidat
        Candidat updatedCandidat = candidatRepository.findById(candidat.getId()).get();
        // Disconnect from session so that the updates on updatedCandidat are not directly saved in db
        em.detach(updatedCandidat);
        updatedCandidat
            .codeCandidat(UPDATED_CODE_CANDIDAT)
            .nomCandidat(UPDATED_NOM_CANDIDAT)
            .prenomCandidat(UPDATED_PRENOM_CANDIDAT)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .paysNaissance(UPDATED_PAYS_NAISSANCE)
            .sexe(UPDATED_SEXE)
            .telephoneCandidat(UPDATED_TELEPHONE_CANDIDAT)
            .nationaliteCandidat(UPDATED_NATIONALITE_CANDIDAT)
            .matriculeCandidat(UPDATED_MATRICULE_CANDIDAT)
            .dateEnregistrementCandidat(UPDATED_DATE_ENREGISTREMENT_CANDIDAT)
            .dateDebutCariereCandidat(UPDATED_DATE_DEBUT_CARIERE_CANDIDAT);
        CandidatDTO candidatDTO = candidatMapper.toDto(updatedCandidat);

        restCandidatMockMvc.perform(put("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatDTO)))
            .andExpect(status().isOk());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeUpdate);
        Candidat testCandidat = candidatList.get(candidatList.size() - 1);
        assertThat(testCandidat.getCodeCandidat()).isEqualTo(UPDATED_CODE_CANDIDAT);
        assertThat(testCandidat.getNomCandidat()).isEqualTo(UPDATED_NOM_CANDIDAT);
        assertThat(testCandidat.getPrenomCandidat()).isEqualTo(UPDATED_PRENOM_CANDIDAT);
        assertThat(testCandidat.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testCandidat.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testCandidat.getPaysNaissance()).isEqualTo(UPDATED_PAYS_NAISSANCE);
        assertThat(testCandidat.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testCandidat.getTelephoneCandidat()).isEqualTo(UPDATED_TELEPHONE_CANDIDAT);
        assertThat(testCandidat.getNationaliteCandidat()).isEqualTo(UPDATED_NATIONALITE_CANDIDAT);
        assertThat(testCandidat.getMatriculeCandidat()).isEqualTo(UPDATED_MATRICULE_CANDIDAT);
        assertThat(testCandidat.getDateEnregistrementCandidat()).isEqualTo(UPDATED_DATE_ENREGISTREMENT_CANDIDAT);
        assertThat(testCandidat.getDateDebutCariereCandidat()).isEqualTo(UPDATED_DATE_DEBUT_CARIERE_CANDIDAT);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidat() throws Exception {
        int databaseSizeBeforeUpdate = candidatRepository.findAll().size();

        // Create the Candidat
        CandidatDTO candidatDTO = candidatMapper.toDto(candidat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidatMockMvc.perform(put("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        int databaseSizeBeforeDelete = candidatRepository.findAll().size();

        // Delete the candidat
        restCandidatMockMvc.perform(delete("/api/candidats/{id}", candidat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidat.class);
        Candidat candidat1 = new Candidat();
        candidat1.setId(1L);
        Candidat candidat2 = new Candidat();
        candidat2.setId(candidat1.getId());
        assertThat(candidat1).isEqualTo(candidat2);
        candidat2.setId(2L);
        assertThat(candidat1).isNotEqualTo(candidat2);
        candidat1.setId(null);
        assertThat(candidat1).isNotEqualTo(candidat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidatDTO.class);
        CandidatDTO candidatDTO1 = new CandidatDTO();
        candidatDTO1.setId(1L);
        CandidatDTO candidatDTO2 = new CandidatDTO();
        assertThat(candidatDTO1).isNotEqualTo(candidatDTO2);
        candidatDTO2.setId(candidatDTO1.getId());
        assertThat(candidatDTO1).isEqualTo(candidatDTO2);
        candidatDTO2.setId(2L);
        assertThat(candidatDTO1).isNotEqualTo(candidatDTO2);
        candidatDTO1.setId(null);
        assertThat(candidatDTO1).isNotEqualTo(candidatDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(candidatMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(candidatMapper.fromId(null)).isNull();
    }
}
