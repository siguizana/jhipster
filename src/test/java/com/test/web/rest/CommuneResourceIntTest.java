package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Commune;
import com.test.repository.CommuneRepository;
import com.test.service.CommuneService;
import com.test.service.dto.CommuneDTO;
import com.test.service.mapper.CommuneMapper;
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

import com.test.domain.enumeration.TypeCommune;
/**
 * Test class for the CommuneResource REST controller.
 *
 * @see CommuneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class CommuneResourceIntTest {

    private static final String DEFAULT_CODE_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_COMMUNE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_COMMUNE = "BBBBBBBBBB";

    private static final TypeCommune DEFAULT_TYPE_COMMUNE = TypeCommune.RURALE;
    private static final TypeCommune UPDATED_TYPE_COMMUNE = TypeCommune.URBAINE;

    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private CommuneMapper communeMapper;

    @Autowired
    private CommuneService communeService;

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

    private MockMvc restCommuneMockMvc;

    private Commune commune;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommuneResource communeResource = new CommuneResource(communeService);
        this.restCommuneMockMvc = MockMvcBuilders.standaloneSetup(communeResource)
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
    public static Commune createEntity(EntityManager em) {
        Commune commune = new Commune()
            .codeCommune(DEFAULT_CODE_COMMUNE)
            .libelleCommune(DEFAULT_LIBELLE_COMMUNE)
            .typeCommune(DEFAULT_TYPE_COMMUNE);
        return commune;
    }

    @Before
    public void initTest() {
        commune = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommune() throws Exception {
        int databaseSizeBeforeCreate = communeRepository.findAll().size();

        // Create the Commune
        CommuneDTO communeDTO = communeMapper.toDto(commune);
        restCommuneMockMvc.perform(post("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isCreated());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeCreate + 1);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getCodeCommune()).isEqualTo(DEFAULT_CODE_COMMUNE);
        assertThat(testCommune.getLibelleCommune()).isEqualTo(DEFAULT_LIBELLE_COMMUNE);
        assertThat(testCommune.getTypeCommune()).isEqualTo(DEFAULT_TYPE_COMMUNE);
    }

    @Test
    @Transactional
    public void createCommuneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = communeRepository.findAll().size();

        // Create the Commune with an existing ID
        commune.setId(1L);
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommuneMockMvc.perform(post("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleCommuneIsRequired() throws Exception {
        int databaseSizeBeforeTest = communeRepository.findAll().size();
        // set the field null
        commune.setLibelleCommune(null);

        // Create the Commune, which fails.
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        restCommuneMockMvc.perform(post("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isBadRequest());

        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeCommuneIsRequired() throws Exception {
        int databaseSizeBeforeTest = communeRepository.findAll().size();
        // set the field null
        commune.setTypeCommune(null);

        // Create the Commune, which fails.
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        restCommuneMockMvc.perform(post("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isBadRequest());

        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommunes() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList
        restCommuneMockMvc.perform(get("/api/communes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commune.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeCommune").value(hasItem(DEFAULT_CODE_COMMUNE.toString())))
            .andExpect(jsonPath("$.[*].libelleCommune").value(hasItem(DEFAULT_LIBELLE_COMMUNE.toString())))
            .andExpect(jsonPath("$.[*].typeCommune").value(hasItem(DEFAULT_TYPE_COMMUNE.toString())));
    }
    
    @Test
    @Transactional
    public void getCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get the commune
        restCommuneMockMvc.perform(get("/api/communes/{id}", commune.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commune.getId().intValue()))
            .andExpect(jsonPath("$.codeCommune").value(DEFAULT_CODE_COMMUNE.toString()))
            .andExpect(jsonPath("$.libelleCommune").value(DEFAULT_LIBELLE_COMMUNE.toString()))
            .andExpect(jsonPath("$.typeCommune").value(DEFAULT_TYPE_COMMUNE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommune() throws Exception {
        // Get the commune
        restCommuneMockMvc.perform(get("/api/communes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        int databaseSizeBeforeUpdate = communeRepository.findAll().size();

        // Update the commune
        Commune updatedCommune = communeRepository.findById(commune.getId()).get();
        // Disconnect from session so that the updates on updatedCommune are not directly saved in db
        em.detach(updatedCommune);
        updatedCommune
            .codeCommune(UPDATED_CODE_COMMUNE)
            .libelleCommune(UPDATED_LIBELLE_COMMUNE)
            .typeCommune(UPDATED_TYPE_COMMUNE);
        CommuneDTO communeDTO = communeMapper.toDto(updatedCommune);

        restCommuneMockMvc.perform(put("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isOk());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getCodeCommune()).isEqualTo(UPDATED_CODE_COMMUNE);
        assertThat(testCommune.getLibelleCommune()).isEqualTo(UPDATED_LIBELLE_COMMUNE);
        assertThat(testCommune.getTypeCommune()).isEqualTo(UPDATED_TYPE_COMMUNE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();

        // Create the Commune
        CommuneDTO communeDTO = communeMapper.toDto(commune);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommuneMockMvc.perform(put("/api/communes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(communeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        int databaseSizeBeforeDelete = communeRepository.findAll().size();

        // Delete the commune
        restCommuneMockMvc.perform(delete("/api/communes/{id}", commune.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commune.class);
        Commune commune1 = new Commune();
        commune1.setId(1L);
        Commune commune2 = new Commune();
        commune2.setId(commune1.getId());
        assertThat(commune1).isEqualTo(commune2);
        commune2.setId(2L);
        assertThat(commune1).isNotEqualTo(commune2);
        commune1.setId(null);
        assertThat(commune1).isNotEqualTo(commune2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommuneDTO.class);
        CommuneDTO communeDTO1 = new CommuneDTO();
        communeDTO1.setId(1L);
        CommuneDTO communeDTO2 = new CommuneDTO();
        assertThat(communeDTO1).isNotEqualTo(communeDTO2);
        communeDTO2.setId(communeDTO1.getId());
        assertThat(communeDTO1).isEqualTo(communeDTO2);
        communeDTO2.setId(2L);
        assertThat(communeDTO1).isNotEqualTo(communeDTO2);
        communeDTO1.setId(null);
        assertThat(communeDTO1).isNotEqualTo(communeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(communeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(communeMapper.fromId(null)).isNull();
    }
}
