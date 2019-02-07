package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.MembreJuryJury;
import com.test.repository.MembreJuryJuryRepository;
import com.test.service.MembreJuryJuryService;
import com.test.service.dto.MembreJuryJuryDTO;
import com.test.service.mapper.MembreJuryJuryMapper;
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
 * Test class for the MembreJuryJuryResource REST controller.
 *
 * @see MembreJuryJuryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class MembreJuryJuryResourceIntTest {

    private static final String DEFAULT_FONCTION = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_SECTEUR = "AAAAAAAAAA";
    private static final String UPDATED_SECTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_QUARTIER = "AAAAAAAAAA";
    private static final String UPDATED_QUARTIER = "BBBBBBBBBB";

    private static final String DEFAULT_DIPLOME_ACADEMIQUE = "AAAAAAAAAA";
    private static final String UPDATED_DIPLOME_ACADEMIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_DIPLOME_PROFESSIONNEL = "AAAAAAAAAA";
    private static final String UPDATED_DIPLOME_PROFESSIONNEL = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_CHEF_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CHEF_ETABLISSEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_CHEF_ETABISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_CHEF_ETABISSEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_AVIS_CHEF_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_AVIS_CHEF_ETABLISSEMENT = "BBBBBBBBBB";

    @Autowired
    private MembreJuryJuryRepository membreJuryJuryRepository;

    @Mock
    private MembreJuryJuryRepository membreJuryJuryRepositoryMock;

    @Autowired
    private MembreJuryJuryMapper membreJuryJuryMapper;

    @Mock
    private MembreJuryJuryService membreJuryJuryServiceMock;

    @Autowired
    private MembreJuryJuryService membreJuryJuryService;

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

    private MockMvc restMembreJuryJuryMockMvc;

    private MembreJuryJury membreJuryJury;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MembreJuryJuryResource membreJuryJuryResource = new MembreJuryJuryResource(membreJuryJuryService);
        this.restMembreJuryJuryMockMvc = MockMvcBuilders.standaloneSetup(membreJuryJuryResource)
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
    public static MembreJuryJury createEntity(EntityManager em) {
        MembreJuryJury membreJuryJury = new MembreJuryJury()
            .fonction(DEFAULT_FONCTION)
            .status(DEFAULT_STATUS)
            .experience(DEFAULT_EXPERIENCE)
            .secteur(DEFAULT_SECTEUR)
            .quartier(DEFAULT_QUARTIER)
            .diplomeAcademique(DEFAULT_DIPLOME_ACADEMIQUE)
            .diplomeProfessionnel(DEFAULT_DIPLOME_PROFESSIONNEL)
            .nomChefEtablissement(DEFAULT_NOM_CHEF_ETABLISSEMENT)
            .prenomChefEtabissement(DEFAULT_PRENOM_CHEF_ETABISSEMENT)
            .avisChefEtablissement(DEFAULT_AVIS_CHEF_ETABLISSEMENT);
        return membreJuryJury;
    }

    @Before
    public void initTest() {
        membreJuryJury = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembreJuryJury() throws Exception {
        int databaseSizeBeforeCreate = membreJuryJuryRepository.findAll().size();

        // Create the MembreJuryJury
        MembreJuryJuryDTO membreJuryJuryDTO = membreJuryJuryMapper.toDto(membreJuryJury);
        restMembreJuryJuryMockMvc.perform(post("/api/membre-jury-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreJuryJuryDTO)))
            .andExpect(status().isCreated());

        // Validate the MembreJuryJury in the database
        List<MembreJuryJury> membreJuryJuryList = membreJuryJuryRepository.findAll();
        assertThat(membreJuryJuryList).hasSize(databaseSizeBeforeCreate + 1);
        MembreJuryJury testMembreJuryJury = membreJuryJuryList.get(membreJuryJuryList.size() - 1);
        assertThat(testMembreJuryJury.getFonction()).isEqualTo(DEFAULT_FONCTION);
        assertThat(testMembreJuryJury.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMembreJuryJury.getExperience()).isEqualTo(DEFAULT_EXPERIENCE);
        assertThat(testMembreJuryJury.getSecteur()).isEqualTo(DEFAULT_SECTEUR);
        assertThat(testMembreJuryJury.getQuartier()).isEqualTo(DEFAULT_QUARTIER);
        assertThat(testMembreJuryJury.getDiplomeAcademique()).isEqualTo(DEFAULT_DIPLOME_ACADEMIQUE);
        assertThat(testMembreJuryJury.getDiplomeProfessionnel()).isEqualTo(DEFAULT_DIPLOME_PROFESSIONNEL);
        assertThat(testMembreJuryJury.getNomChefEtablissement()).isEqualTo(DEFAULT_NOM_CHEF_ETABLISSEMENT);
        assertThat(testMembreJuryJury.getPrenomChefEtabissement()).isEqualTo(DEFAULT_PRENOM_CHEF_ETABISSEMENT);
        assertThat(testMembreJuryJury.getAvisChefEtablissement()).isEqualTo(DEFAULT_AVIS_CHEF_ETABLISSEMENT);
    }

    @Test
    @Transactional
    public void createMembreJuryJuryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membreJuryJuryRepository.findAll().size();

        // Create the MembreJuryJury with an existing ID
        membreJuryJury.setId(1L);
        MembreJuryJuryDTO membreJuryJuryDTO = membreJuryJuryMapper.toDto(membreJuryJury);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembreJuryJuryMockMvc.perform(post("/api/membre-jury-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreJuryJuryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MembreJuryJury in the database
        List<MembreJuryJury> membreJuryJuryList = membreJuryJuryRepository.findAll();
        assertThat(membreJuryJuryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMembreJuryJuries() throws Exception {
        // Initialize the database
        membreJuryJuryRepository.saveAndFlush(membreJuryJury);

        // Get all the membreJuryJuryList
        restMembreJuryJuryMockMvc.perform(get("/api/membre-jury-juries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membreJuryJury.getId().intValue())))
            .andExpect(jsonPath("$.[*].fonction").value(hasItem(DEFAULT_FONCTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].experience").value(hasItem(DEFAULT_EXPERIENCE.toString())))
            .andExpect(jsonPath("$.[*].secteur").value(hasItem(DEFAULT_SECTEUR.toString())))
            .andExpect(jsonPath("$.[*].quartier").value(hasItem(DEFAULT_QUARTIER.toString())))
            .andExpect(jsonPath("$.[*].diplomeAcademique").value(hasItem(DEFAULT_DIPLOME_ACADEMIQUE.toString())))
            .andExpect(jsonPath("$.[*].diplomeProfessionnel").value(hasItem(DEFAULT_DIPLOME_PROFESSIONNEL.toString())))
            .andExpect(jsonPath("$.[*].nomChefEtablissement").value(hasItem(DEFAULT_NOM_CHEF_ETABLISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].prenomChefEtabissement").value(hasItem(DEFAULT_PRENOM_CHEF_ETABISSEMENT.toString())))
            .andExpect(jsonPath("$.[*].avisChefEtablissement").value(hasItem(DEFAULT_AVIS_CHEF_ETABLISSEMENT.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMembreJuryJuriesWithEagerRelationshipsIsEnabled() throws Exception {
        MembreJuryJuryResource membreJuryJuryResource = new MembreJuryJuryResource(membreJuryJuryServiceMock);
        when(membreJuryJuryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMembreJuryJuryMockMvc = MockMvcBuilders.standaloneSetup(membreJuryJuryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMembreJuryJuryMockMvc.perform(get("/api/membre-jury-juries?eagerload=true"))
        .andExpect(status().isOk());

        verify(membreJuryJuryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMembreJuryJuriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        MembreJuryJuryResource membreJuryJuryResource = new MembreJuryJuryResource(membreJuryJuryServiceMock);
            when(membreJuryJuryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMembreJuryJuryMockMvc = MockMvcBuilders.standaloneSetup(membreJuryJuryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMembreJuryJuryMockMvc.perform(get("/api/membre-jury-juries?eagerload=true"))
        .andExpect(status().isOk());

            verify(membreJuryJuryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMembreJuryJury() throws Exception {
        // Initialize the database
        membreJuryJuryRepository.saveAndFlush(membreJuryJury);

        // Get the membreJuryJury
        restMembreJuryJuryMockMvc.perform(get("/api/membre-jury-juries/{id}", membreJuryJury.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(membreJuryJury.getId().intValue()))
            .andExpect(jsonPath("$.fonction").value(DEFAULT_FONCTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.experience").value(DEFAULT_EXPERIENCE.toString()))
            .andExpect(jsonPath("$.secteur").value(DEFAULT_SECTEUR.toString()))
            .andExpect(jsonPath("$.quartier").value(DEFAULT_QUARTIER.toString()))
            .andExpect(jsonPath("$.diplomeAcademique").value(DEFAULT_DIPLOME_ACADEMIQUE.toString()))
            .andExpect(jsonPath("$.diplomeProfessionnel").value(DEFAULT_DIPLOME_PROFESSIONNEL.toString()))
            .andExpect(jsonPath("$.nomChefEtablissement").value(DEFAULT_NOM_CHEF_ETABLISSEMENT.toString()))
            .andExpect(jsonPath("$.prenomChefEtabissement").value(DEFAULT_PRENOM_CHEF_ETABISSEMENT.toString()))
            .andExpect(jsonPath("$.avisChefEtablissement").value(DEFAULT_AVIS_CHEF_ETABLISSEMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMembreJuryJury() throws Exception {
        // Get the membreJuryJury
        restMembreJuryJuryMockMvc.perform(get("/api/membre-jury-juries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembreJuryJury() throws Exception {
        // Initialize the database
        membreJuryJuryRepository.saveAndFlush(membreJuryJury);

        int databaseSizeBeforeUpdate = membreJuryJuryRepository.findAll().size();

        // Update the membreJuryJury
        MembreJuryJury updatedMembreJuryJury = membreJuryJuryRepository.findById(membreJuryJury.getId()).get();
        // Disconnect from session so that the updates on updatedMembreJuryJury are not directly saved in db
        em.detach(updatedMembreJuryJury);
        updatedMembreJuryJury
            .fonction(UPDATED_FONCTION)
            .status(UPDATED_STATUS)
            .experience(UPDATED_EXPERIENCE)
            .secteur(UPDATED_SECTEUR)
            .quartier(UPDATED_QUARTIER)
            .diplomeAcademique(UPDATED_DIPLOME_ACADEMIQUE)
            .diplomeProfessionnel(UPDATED_DIPLOME_PROFESSIONNEL)
            .nomChefEtablissement(UPDATED_NOM_CHEF_ETABLISSEMENT)
            .prenomChefEtabissement(UPDATED_PRENOM_CHEF_ETABISSEMENT)
            .avisChefEtablissement(UPDATED_AVIS_CHEF_ETABLISSEMENT);
        MembreJuryJuryDTO membreJuryJuryDTO = membreJuryJuryMapper.toDto(updatedMembreJuryJury);

        restMembreJuryJuryMockMvc.perform(put("/api/membre-jury-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreJuryJuryDTO)))
            .andExpect(status().isOk());

        // Validate the MembreJuryJury in the database
        List<MembreJuryJury> membreJuryJuryList = membreJuryJuryRepository.findAll();
        assertThat(membreJuryJuryList).hasSize(databaseSizeBeforeUpdate);
        MembreJuryJury testMembreJuryJury = membreJuryJuryList.get(membreJuryJuryList.size() - 1);
        assertThat(testMembreJuryJury.getFonction()).isEqualTo(UPDATED_FONCTION);
        assertThat(testMembreJuryJury.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMembreJuryJury.getExperience()).isEqualTo(UPDATED_EXPERIENCE);
        assertThat(testMembreJuryJury.getSecteur()).isEqualTo(UPDATED_SECTEUR);
        assertThat(testMembreJuryJury.getQuartier()).isEqualTo(UPDATED_QUARTIER);
        assertThat(testMembreJuryJury.getDiplomeAcademique()).isEqualTo(UPDATED_DIPLOME_ACADEMIQUE);
        assertThat(testMembreJuryJury.getDiplomeProfessionnel()).isEqualTo(UPDATED_DIPLOME_PROFESSIONNEL);
        assertThat(testMembreJuryJury.getNomChefEtablissement()).isEqualTo(UPDATED_NOM_CHEF_ETABLISSEMENT);
        assertThat(testMembreJuryJury.getPrenomChefEtabissement()).isEqualTo(UPDATED_PRENOM_CHEF_ETABISSEMENT);
        assertThat(testMembreJuryJury.getAvisChefEtablissement()).isEqualTo(UPDATED_AVIS_CHEF_ETABLISSEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingMembreJuryJury() throws Exception {
        int databaseSizeBeforeUpdate = membreJuryJuryRepository.findAll().size();

        // Create the MembreJuryJury
        MembreJuryJuryDTO membreJuryJuryDTO = membreJuryJuryMapper.toDto(membreJuryJury);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembreJuryJuryMockMvc.perform(put("/api/membre-jury-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(membreJuryJuryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MembreJuryJury in the database
        List<MembreJuryJury> membreJuryJuryList = membreJuryJuryRepository.findAll();
        assertThat(membreJuryJuryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMembreJuryJury() throws Exception {
        // Initialize the database
        membreJuryJuryRepository.saveAndFlush(membreJuryJury);

        int databaseSizeBeforeDelete = membreJuryJuryRepository.findAll().size();

        // Delete the membreJuryJury
        restMembreJuryJuryMockMvc.perform(delete("/api/membre-jury-juries/{id}", membreJuryJury.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MembreJuryJury> membreJuryJuryList = membreJuryJuryRepository.findAll();
        assertThat(membreJuryJuryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembreJuryJury.class);
        MembreJuryJury membreJuryJury1 = new MembreJuryJury();
        membreJuryJury1.setId(1L);
        MembreJuryJury membreJuryJury2 = new MembreJuryJury();
        membreJuryJury2.setId(membreJuryJury1.getId());
        assertThat(membreJuryJury1).isEqualTo(membreJuryJury2);
        membreJuryJury2.setId(2L);
        assertThat(membreJuryJury1).isNotEqualTo(membreJuryJury2);
        membreJuryJury1.setId(null);
        assertThat(membreJuryJury1).isNotEqualTo(membreJuryJury2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MembreJuryJuryDTO.class);
        MembreJuryJuryDTO membreJuryJuryDTO1 = new MembreJuryJuryDTO();
        membreJuryJuryDTO1.setId(1L);
        MembreJuryJuryDTO membreJuryJuryDTO2 = new MembreJuryJuryDTO();
        assertThat(membreJuryJuryDTO1).isNotEqualTo(membreJuryJuryDTO2);
        membreJuryJuryDTO2.setId(membreJuryJuryDTO1.getId());
        assertThat(membreJuryJuryDTO1).isEqualTo(membreJuryJuryDTO2);
        membreJuryJuryDTO2.setId(2L);
        assertThat(membreJuryJuryDTO1).isNotEqualTo(membreJuryJuryDTO2);
        membreJuryJuryDTO1.setId(null);
        assertThat(membreJuryJuryDTO1).isNotEqualTo(membreJuryJuryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(membreJuryJuryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(membreJuryJuryMapper.fromId(null)).isNull();
    }
}
