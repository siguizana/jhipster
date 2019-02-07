package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.DeroulementScolarite;
import com.test.repository.DeroulementScolariteRepository;
import com.test.service.DeroulementScolariteService;
import com.test.service.dto.DeroulementScolariteDTO;
import com.test.service.mapper.DeroulementScolariteMapper;
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
 * Test class for the DeroulementScolariteResource REST controller.
 *
 * @see DeroulementScolariteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class DeroulementScolariteResourceIntTest {

    private static final String DEFAULT_ANNE_SCOLARITE = "AAAAAAAAAA";
    private static final String UPDATED_ANNE_SCOLARITE = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSE = "BBBBBBBBBB";

    @Autowired
    private DeroulementScolariteRepository deroulementScolariteRepository;

    @Autowired
    private DeroulementScolariteMapper deroulementScolariteMapper;

    @Autowired
    private DeroulementScolariteService deroulementScolariteService;

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

    private MockMvc restDeroulementScolariteMockMvc;

    private DeroulementScolarite deroulementScolarite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeroulementScolariteResource deroulementScolariteResource = new DeroulementScolariteResource(deroulementScolariteService);
        this.restDeroulementScolariteMockMvc = MockMvcBuilders.standaloneSetup(deroulementScolariteResource)
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
    public static DeroulementScolarite createEntity(EntityManager em) {
        DeroulementScolarite deroulementScolarite = new DeroulementScolarite()
            .anneScolarite(DEFAULT_ANNE_SCOLARITE)
            .classe(DEFAULT_CLASSE);
        return deroulementScolarite;
    }

    @Before
    public void initTest() {
        deroulementScolarite = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeroulementScolarite() throws Exception {
        int databaseSizeBeforeCreate = deroulementScolariteRepository.findAll().size();

        // Create the DeroulementScolarite
        DeroulementScolariteDTO deroulementScolariteDTO = deroulementScolariteMapper.toDto(deroulementScolarite);
        restDeroulementScolariteMockMvc.perform(post("/api/deroulement-scolarites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deroulementScolariteDTO)))
            .andExpect(status().isCreated());

        // Validate the DeroulementScolarite in the database
        List<DeroulementScolarite> deroulementScolariteList = deroulementScolariteRepository.findAll();
        assertThat(deroulementScolariteList).hasSize(databaseSizeBeforeCreate + 1);
        DeroulementScolarite testDeroulementScolarite = deroulementScolariteList.get(deroulementScolariteList.size() - 1);
        assertThat(testDeroulementScolarite.getAnneScolarite()).isEqualTo(DEFAULT_ANNE_SCOLARITE);
        assertThat(testDeroulementScolarite.getClasse()).isEqualTo(DEFAULT_CLASSE);
    }

    @Test
    @Transactional
    public void createDeroulementScolariteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deroulementScolariteRepository.findAll().size();

        // Create the DeroulementScolarite with an existing ID
        deroulementScolarite.setId(1L);
        DeroulementScolariteDTO deroulementScolariteDTO = deroulementScolariteMapper.toDto(deroulementScolarite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeroulementScolariteMockMvc.perform(post("/api/deroulement-scolarites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deroulementScolariteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeroulementScolarite in the database
        List<DeroulementScolarite> deroulementScolariteList = deroulementScolariteRepository.findAll();
        assertThat(deroulementScolariteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAnneScolariteIsRequired() throws Exception {
        int databaseSizeBeforeTest = deroulementScolariteRepository.findAll().size();
        // set the field null
        deroulementScolarite.setAnneScolarite(null);

        // Create the DeroulementScolarite, which fails.
        DeroulementScolariteDTO deroulementScolariteDTO = deroulementScolariteMapper.toDto(deroulementScolarite);

        restDeroulementScolariteMockMvc.perform(post("/api/deroulement-scolarites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deroulementScolariteDTO)))
            .andExpect(status().isBadRequest());

        List<DeroulementScolarite> deroulementScolariteList = deroulementScolariteRepository.findAll();
        assertThat(deroulementScolariteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = deroulementScolariteRepository.findAll().size();
        // set the field null
        deroulementScolarite.setClasse(null);

        // Create the DeroulementScolarite, which fails.
        DeroulementScolariteDTO deroulementScolariteDTO = deroulementScolariteMapper.toDto(deroulementScolarite);

        restDeroulementScolariteMockMvc.perform(post("/api/deroulement-scolarites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deroulementScolariteDTO)))
            .andExpect(status().isBadRequest());

        List<DeroulementScolarite> deroulementScolariteList = deroulementScolariteRepository.findAll();
        assertThat(deroulementScolariteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeroulementScolarites() throws Exception {
        // Initialize the database
        deroulementScolariteRepository.saveAndFlush(deroulementScolarite);

        // Get all the deroulementScolariteList
        restDeroulementScolariteMockMvc.perform(get("/api/deroulement-scolarites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deroulementScolarite.getId().intValue())))
            .andExpect(jsonPath("$.[*].anneScolarite").value(hasItem(DEFAULT_ANNE_SCOLARITE.toString())))
            .andExpect(jsonPath("$.[*].classe").value(hasItem(DEFAULT_CLASSE.toString())));
    }
    
    @Test
    @Transactional
    public void getDeroulementScolarite() throws Exception {
        // Initialize the database
        deroulementScolariteRepository.saveAndFlush(deroulementScolarite);

        // Get the deroulementScolarite
        restDeroulementScolariteMockMvc.perform(get("/api/deroulement-scolarites/{id}", deroulementScolarite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deroulementScolarite.getId().intValue()))
            .andExpect(jsonPath("$.anneScolarite").value(DEFAULT_ANNE_SCOLARITE.toString()))
            .andExpect(jsonPath("$.classe").value(DEFAULT_CLASSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeroulementScolarite() throws Exception {
        // Get the deroulementScolarite
        restDeroulementScolariteMockMvc.perform(get("/api/deroulement-scolarites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeroulementScolarite() throws Exception {
        // Initialize the database
        deroulementScolariteRepository.saveAndFlush(deroulementScolarite);

        int databaseSizeBeforeUpdate = deroulementScolariteRepository.findAll().size();

        // Update the deroulementScolarite
        DeroulementScolarite updatedDeroulementScolarite = deroulementScolariteRepository.findById(deroulementScolarite.getId()).get();
        // Disconnect from session so that the updates on updatedDeroulementScolarite are not directly saved in db
        em.detach(updatedDeroulementScolarite);
        updatedDeroulementScolarite
            .anneScolarite(UPDATED_ANNE_SCOLARITE)
            .classe(UPDATED_CLASSE);
        DeroulementScolariteDTO deroulementScolariteDTO = deroulementScolariteMapper.toDto(updatedDeroulementScolarite);

        restDeroulementScolariteMockMvc.perform(put("/api/deroulement-scolarites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deroulementScolariteDTO)))
            .andExpect(status().isOk());

        // Validate the DeroulementScolarite in the database
        List<DeroulementScolarite> deroulementScolariteList = deroulementScolariteRepository.findAll();
        assertThat(deroulementScolariteList).hasSize(databaseSizeBeforeUpdate);
        DeroulementScolarite testDeroulementScolarite = deroulementScolariteList.get(deroulementScolariteList.size() - 1);
        assertThat(testDeroulementScolarite.getAnneScolarite()).isEqualTo(UPDATED_ANNE_SCOLARITE);
        assertThat(testDeroulementScolarite.getClasse()).isEqualTo(UPDATED_CLASSE);
    }

    @Test
    @Transactional
    public void updateNonExistingDeroulementScolarite() throws Exception {
        int databaseSizeBeforeUpdate = deroulementScolariteRepository.findAll().size();

        // Create the DeroulementScolarite
        DeroulementScolariteDTO deroulementScolariteDTO = deroulementScolariteMapper.toDto(deroulementScolarite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeroulementScolariteMockMvc.perform(put("/api/deroulement-scolarites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deroulementScolariteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeroulementScolarite in the database
        List<DeroulementScolarite> deroulementScolariteList = deroulementScolariteRepository.findAll();
        assertThat(deroulementScolariteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeroulementScolarite() throws Exception {
        // Initialize the database
        deroulementScolariteRepository.saveAndFlush(deroulementScolarite);

        int databaseSizeBeforeDelete = deroulementScolariteRepository.findAll().size();

        // Delete the deroulementScolarite
        restDeroulementScolariteMockMvc.perform(delete("/api/deroulement-scolarites/{id}", deroulementScolarite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeroulementScolarite> deroulementScolariteList = deroulementScolariteRepository.findAll();
        assertThat(deroulementScolariteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeroulementScolarite.class);
        DeroulementScolarite deroulementScolarite1 = new DeroulementScolarite();
        deroulementScolarite1.setId(1L);
        DeroulementScolarite deroulementScolarite2 = new DeroulementScolarite();
        deroulementScolarite2.setId(deroulementScolarite1.getId());
        assertThat(deroulementScolarite1).isEqualTo(deroulementScolarite2);
        deroulementScolarite2.setId(2L);
        assertThat(deroulementScolarite1).isNotEqualTo(deroulementScolarite2);
        deroulementScolarite1.setId(null);
        assertThat(deroulementScolarite1).isNotEqualTo(deroulementScolarite2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeroulementScolariteDTO.class);
        DeroulementScolariteDTO deroulementScolariteDTO1 = new DeroulementScolariteDTO();
        deroulementScolariteDTO1.setId(1L);
        DeroulementScolariteDTO deroulementScolariteDTO2 = new DeroulementScolariteDTO();
        assertThat(deroulementScolariteDTO1).isNotEqualTo(deroulementScolariteDTO2);
        deroulementScolariteDTO2.setId(deroulementScolariteDTO1.getId());
        assertThat(deroulementScolariteDTO1).isEqualTo(deroulementScolariteDTO2);
        deroulementScolariteDTO2.setId(2L);
        assertThat(deroulementScolariteDTO1).isNotEqualTo(deroulementScolariteDTO2);
        deroulementScolariteDTO1.setId(null);
        assertThat(deroulementScolariteDTO1).isNotEqualTo(deroulementScolariteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(deroulementScolariteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(deroulementScolariteMapper.fromId(null)).isNull();
    }
}
