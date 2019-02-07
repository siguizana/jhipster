package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.MembreJury;
import com.test.repository.MembreJuryRepository;
import com.test.service.MembreJuryService;
import com.test.service.dto.MembreJuryDTO;
import com.test.service.mapper.MembreJuryMapper;
import com.test.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.test.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MembreJuryResource REST controller.
 *
 * @see MembreJuryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class MembreJuryResourceIntTest {

    private static final String DEFAULT_NOM_MEMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MEMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_MEMBRE = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_MEMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CNIB = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CNIB = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    @Autowired
    private MembreJuryRepository membreJuryRepository;

    @Mock
    private MembreJuryRepository membreJuryRepositoryMock;

    @Autowired
    private MembreJuryMapper membreJuryMapper;

    @Mock
    private MembreJuryService membreJuryServiceMock;

    @Autowired
    private MembreJuryService membreJuryService;

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

    private MockMvc restMembreJuryMockMvc;

    private MembreJury membreJury;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MembreJuryResource membreJuryResource = new MembreJuryResource(membreJuryService);
        this.restMembreJuryMockMvc = MockMvcBuilders.standaloneSetup(membreJuryResource)
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
    public static MembreJury createEntity(EntityManager em) {
        MembreJury membreJury = new MembreJury()
            .nomMembre(DEFAULT_NOM_MEMBRE)
            .prenomMembre(DEFAULT_PRENOM_MEMBRE)
            .numeroCNIB(DEFAULT_NUMERO_CNIB)
            .matricule(DEFAULT_MATRICULE);
        return membreJury;
    }

    @Before
    public void initTest() {
        membreJury = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembreJury() throws Exception {
        int databaseSizeBeforeCreate = membreJuryRepository.findAll().size();

        // Create the MembreJury
        MembreJuryDTO membreJuryDTO = membreJuryMapper.toDto(membreJury);
        restMembreJuryMockMvc.perform(post("/api/membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreJuryDTO)))
            .andExpect(status().isCreated());

        // Validate the MembreJury in the database
        List<MembreJury> membreJuryList = membreJuryRepository.findAll();
        assertThat(membreJuryList).hasSize(databaseSizeBeforeCreate + 1);
        MembreJury testMembreJury = membreJuryList.get(membreJuryList.size() - 1);
        assertThat(testMembreJury.getNomMembre()).isEqualTo(DEFAULT_NOM_MEMBRE);
        assertThat(testMembreJury.getPrenomMembre()).isEqualTo(DEFAULT_PRENOM_MEMBRE);
        assertThat(testMembreJury.getNumeroCNIB()).isEqualTo(DEFAULT_NUMERO_CNIB);
        assertThat(testMembreJury.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
    }

    @Test
    @Transactional
    public void createMembreJuryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membreJuryRepository.findAll().size();

        // Create the MembreJury with an existing ID
        membreJury.setId(1L);
        MembreJuryDTO membreJuryDTO = membreJuryMapper.toDto(membreJury);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembreJuryMockMvc.perform(post("/api/membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreJuryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MembreJury in the database
        List<MembreJury> membreJuryList = membreJuryRepository.findAll();
        assertThat(membreJuryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomMembreIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreJuryRepository.findAll().size();
        // set the field null
        membreJury.setNomMembre(null);

        // Create the MembreJury, which fails.
        MembreJuryDTO membreJuryDTO = membreJuryMapper.toDto(membreJury);

        restMembreJuryMockMvc.perform(post("/api/membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreJuryDTO)))
            .andExpect(status().isBadRequest());

        List<MembreJury> membreJuryList = membreJuryRepository.findAll();
        assertThat(membreJuryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomMembreIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreJuryRepository.findAll().size();
        // set the field null
        membreJury.setPrenomMembre(null);

        // Create the MembreJury, which fails.
        MembreJuryDTO membreJuryDTO = membreJuryMapper.toDto(membreJury);

        restMembreJuryMockMvc.perform(post("/api/membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreJuryDTO)))
            .andExpect(status().isBadRequest());

        List<MembreJury> membreJuryList = membreJuryRepository.findAll();
        assertThat(membreJuryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroCNIBIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreJuryRepository.findAll().size();
        // set the field null
        membreJury.setNumeroCNIB(null);

        // Create the MembreJury, which fails.
        MembreJuryDTO membreJuryDTO = membreJuryMapper.toDto(membreJury);

        restMembreJuryMockMvc.perform(post("/api/membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreJuryDTO)))
            .andExpect(status().isBadRequest());

        List<MembreJury> membreJuryList = membreJuryRepository.findAll();
        assertThat(membreJuryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMembreJuries() throws Exception {
        // Initialize the database
        membreJuryRepository.saveAndFlush(membreJury);

        // Get all the membreJuryList
        restMembreJuryMockMvc.perform(get("/api/membre-juries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membreJury.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomMembre").value(hasItem(DEFAULT_NOM_MEMBRE.toString())))
            .andExpect(jsonPath("$.[*].prenomMembre").value(hasItem(DEFAULT_PRENOM_MEMBRE.toString())))
            .andExpect(jsonPath("$.[*].numeroCNIB").value(hasItem(DEFAULT_NUMERO_CNIB.toString())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMembreJuriesWithEagerRelationshipsIsEnabled() throws Exception {
        MembreJuryResource membreJuryResource = new MembreJuryResource(membreJuryServiceMock);
        when(membreJuryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMembreJuryMockMvc = MockMvcBuilders.standaloneSetup(membreJuryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMembreJuryMockMvc.perform(get("/api/membre-juries?eagerload=true"))
        .andExpect(status().isOk());

        verify(membreJuryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMembreJuriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        MembreJuryResource membreJuryResource = new MembreJuryResource(membreJuryServiceMock);
            when(membreJuryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMembreJuryMockMvc = MockMvcBuilders.standaloneSetup(membreJuryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMembreJuryMockMvc.perform(get("/api/membre-juries?eagerload=true"))
        .andExpect(status().isOk());

            verify(membreJuryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMembreJury() throws Exception {
        // Initialize the database
        membreJuryRepository.saveAndFlush(membreJury);

        // Get the membreJury
        restMembreJuryMockMvc.perform(get("/api/membre-juries/{id}", membreJury.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(membreJury.getId().intValue()))
            .andExpect(jsonPath("$.nomMembre").value(DEFAULT_NOM_MEMBRE.toString()))
            .andExpect(jsonPath("$.prenomMembre").value(DEFAULT_PRENOM_MEMBRE.toString()))
            .andExpect(jsonPath("$.numeroCNIB").value(DEFAULT_NUMERO_CNIB.toString()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMembreJury() throws Exception {
        // Get the membreJury
        restMembreJuryMockMvc.perform(get("/api/membre-juries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembreJury() throws Exception {
        // Initialize the database
        membreJuryRepository.saveAndFlush(membreJury);

        int databaseSizeBeforeUpdate = membreJuryRepository.findAll().size();

        // Update the membreJury
        MembreJury updatedMembreJury = membreJuryRepository.findById(membreJury.getId()).get();
        // Disconnect from session so that the updates on updatedMembreJury are not directly saved in db
        em.detach(updatedMembreJury);
        updatedMembreJury
            .nomMembre(UPDATED_NOM_MEMBRE)
            .prenomMembre(UPDATED_PRENOM_MEMBRE)
            .numeroCNIB(UPDATED_NUMERO_CNIB)
            .matricule(UPDATED_MATRICULE);
        MembreJuryDTO membreJuryDTO = membreJuryMapper.toDto(updatedMembreJury);

        restMembreJuryMockMvc.perform(put("/api/membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreJuryDTO)))
            .andExpect(status().isOk());

        // Validate the MembreJury in the database
        List<MembreJury> membreJuryList = membreJuryRepository.findAll();
        assertThat(membreJuryList).hasSize(databaseSizeBeforeUpdate);
        MembreJury testMembreJury = membreJuryList.get(membreJuryList.size() - 1);
        assertThat(testMembreJury.getNomMembre()).isEqualTo(UPDATED_NOM_MEMBRE);
        assertThat(testMembreJury.getPrenomMembre()).isEqualTo(UPDATED_PRENOM_MEMBRE);
        assertThat(testMembreJury.getNumeroCNIB()).isEqualTo(UPDATED_NUMERO_CNIB);
        assertThat(testMembreJury.getMatricule()).isEqualTo(UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void updateNonExistingMembreJury() throws Exception {
        int databaseSizeBeforeUpdate = membreJuryRepository.findAll().size();

        // Create the MembreJury
        MembreJuryDTO membreJuryDTO = membreJuryMapper.toDto(membreJury);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembreJuryMockMvc.perform(put("/api/membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreJuryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MembreJury in the database
        List<MembreJury> membreJuryList = membreJuryRepository.findAll();
        assertThat(membreJuryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMembreJury() throws Exception {
        // Initialize the database
        membreJuryRepository.saveAndFlush(membreJury);

        int databaseSizeBeforeDelete = membreJuryRepository.findAll().size();

        // Delete the membreJury
        restMembreJuryMockMvc.perform(delete("/api/membre-juries/{id}", membreJury.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MembreJury> membreJuryList = membreJuryRepository.findAll();
        assertThat(membreJuryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembreJury.class);
        MembreJury membreJury1 = new MembreJury();
        membreJury1.setId(1L);
        MembreJury membreJury2 = new MembreJury();
        membreJury2.setId(membreJury1.getId());
        assertThat(membreJury1).isEqualTo(membreJury2);
        membreJury2.setId(2L);
        assertThat(membreJury1).isNotEqualTo(membreJury2);
        membreJury1.setId(null);
        assertThat(membreJury1).isNotEqualTo(membreJury2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembreJuryDTO.class);
        MembreJuryDTO membreJuryDTO1 = new MembreJuryDTO();
        membreJuryDTO1.setId(1L);
        MembreJuryDTO membreJuryDTO2 = new MembreJuryDTO();
        assertThat(membreJuryDTO1).isNotEqualTo(membreJuryDTO2);
        membreJuryDTO2.setId(membreJuryDTO1.getId());
        assertThat(membreJuryDTO1).isEqualTo(membreJuryDTO2);
        membreJuryDTO2.setId(2L);
        assertThat(membreJuryDTO1).isNotEqualTo(membreJuryDTO2);
        membreJuryDTO1.setId(null);
        assertThat(membreJuryDTO1).isNotEqualTo(membreJuryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(membreJuryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(membreJuryMapper.fromId(null)).isNull();
    }
}
