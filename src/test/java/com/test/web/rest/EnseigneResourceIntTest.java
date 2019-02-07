package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Enseigne;
import com.test.repository.EnseigneRepository;
import com.test.service.EnseigneService;
import com.test.service.dto.EnseigneDTO;
import com.test.service.mapper.EnseigneMapper;
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
 * Test class for the EnseigneResource REST controller.
 *
 * @see EnseigneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class EnseigneResourceIntTest {

    private static final String DEFAULT_CLASSE_ENSEIGNE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSE_ENSEIGNE = "BBBBBBBBBB";

    @Autowired
    private EnseigneRepository enseigneRepository;

    @Autowired
    private EnseigneMapper enseigneMapper;

    @Autowired
    private EnseigneService enseigneService;

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

    private MockMvc restEnseigneMockMvc;

    private Enseigne enseigne;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnseigneResource enseigneResource = new EnseigneResource(enseigneService);
        this.restEnseigneMockMvc = MockMvcBuilders.standaloneSetup(enseigneResource)
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
    public static Enseigne createEntity(EntityManager em) {
        Enseigne enseigne = new Enseigne()
            .classeEnseigne(DEFAULT_CLASSE_ENSEIGNE);
        return enseigne;
    }

    @Before
    public void initTest() {
        enseigne = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnseigne() throws Exception {
        int databaseSizeBeforeCreate = enseigneRepository.findAll().size();

        // Create the Enseigne
        EnseigneDTO enseigneDTO = enseigneMapper.toDto(enseigne);
        restEnseigneMockMvc.perform(post("/api/enseignes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseigneDTO)))
            .andExpect(status().isCreated());

        // Validate the Enseigne in the database
        List<Enseigne> enseigneList = enseigneRepository.findAll();
        assertThat(enseigneList).hasSize(databaseSizeBeforeCreate + 1);
        Enseigne testEnseigne = enseigneList.get(enseigneList.size() - 1);
        assertThat(testEnseigne.getClasseEnseigne()).isEqualTo(DEFAULT_CLASSE_ENSEIGNE);
    }

    @Test
    @Transactional
    public void createEnseigneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enseigneRepository.findAll().size();

        // Create the Enseigne with an existing ID
        enseigne.setId(1L);
        EnseigneDTO enseigneDTO = enseigneMapper.toDto(enseigne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnseigneMockMvc.perform(post("/api/enseignes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseigneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enseigne in the database
        List<Enseigne> enseigneList = enseigneRepository.findAll();
        assertThat(enseigneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkClasseEnseigneIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseigneRepository.findAll().size();
        // set the field null
        enseigne.setClasseEnseigne(null);

        // Create the Enseigne, which fails.
        EnseigneDTO enseigneDTO = enseigneMapper.toDto(enseigne);

        restEnseigneMockMvc.perform(post("/api/enseignes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseigneDTO)))
            .andExpect(status().isBadRequest());

        List<Enseigne> enseigneList = enseigneRepository.findAll();
        assertThat(enseigneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnseignes() throws Exception {
        // Initialize the database
        enseigneRepository.saveAndFlush(enseigne);

        // Get all the enseigneList
        restEnseigneMockMvc.perform(get("/api/enseignes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enseigne.getId().intValue())))
            .andExpect(jsonPath("$.[*].classeEnseigne").value(hasItem(DEFAULT_CLASSE_ENSEIGNE.toString())));
    }
    
    @Test
    @Transactional
    public void getEnseigne() throws Exception {
        // Initialize the database
        enseigneRepository.saveAndFlush(enseigne);

        // Get the enseigne
        restEnseigneMockMvc.perform(get("/api/enseignes/{id}", enseigne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enseigne.getId().intValue()))
            .andExpect(jsonPath("$.classeEnseigne").value(DEFAULT_CLASSE_ENSEIGNE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnseigne() throws Exception {
        // Get the enseigne
        restEnseigneMockMvc.perform(get("/api/enseignes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnseigne() throws Exception {
        // Initialize the database
        enseigneRepository.saveAndFlush(enseigne);

        int databaseSizeBeforeUpdate = enseigneRepository.findAll().size();

        // Update the enseigne
        Enseigne updatedEnseigne = enseigneRepository.findById(enseigne.getId()).get();
        // Disconnect from session so that the updates on updatedEnseigne are not directly saved in db
        em.detach(updatedEnseigne);
        updatedEnseigne
            .classeEnseigne(UPDATED_CLASSE_ENSEIGNE);
        EnseigneDTO enseigneDTO = enseigneMapper.toDto(updatedEnseigne);

        restEnseigneMockMvc.perform(put("/api/enseignes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseigneDTO)))
            .andExpect(status().isOk());

        // Validate the Enseigne in the database
        List<Enseigne> enseigneList = enseigneRepository.findAll();
        assertThat(enseigneList).hasSize(databaseSizeBeforeUpdate);
        Enseigne testEnseigne = enseigneList.get(enseigneList.size() - 1);
        assertThat(testEnseigne.getClasseEnseigne()).isEqualTo(UPDATED_CLASSE_ENSEIGNE);
    }

    @Test
    @Transactional
    public void updateNonExistingEnseigne() throws Exception {
        int databaseSizeBeforeUpdate = enseigneRepository.findAll().size();

        // Create the Enseigne
        EnseigneDTO enseigneDTO = enseigneMapper.toDto(enseigne);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnseigneMockMvc.perform(put("/api/enseignes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enseigneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enseigne in the database
        List<Enseigne> enseigneList = enseigneRepository.findAll();
        assertThat(enseigneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnseigne() throws Exception {
        // Initialize the database
        enseigneRepository.saveAndFlush(enseigne);

        int databaseSizeBeforeDelete = enseigneRepository.findAll().size();

        // Delete the enseigne
        restEnseigneMockMvc.perform(delete("/api/enseignes/{id}", enseigne.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Enseigne> enseigneList = enseigneRepository.findAll();
        assertThat(enseigneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enseigne.class);
        Enseigne enseigne1 = new Enseigne();
        enseigne1.setId(1L);
        Enseigne enseigne2 = new Enseigne();
        enseigne2.setId(enseigne1.getId());
        assertThat(enseigne1).isEqualTo(enseigne2);
        enseigne2.setId(2L);
        assertThat(enseigne1).isNotEqualTo(enseigne2);
        enseigne1.setId(null);
        assertThat(enseigne1).isNotEqualTo(enseigne2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnseigneDTO.class);
        EnseigneDTO enseigneDTO1 = new EnseigneDTO();
        enseigneDTO1.setId(1L);
        EnseigneDTO enseigneDTO2 = new EnseigneDTO();
        assertThat(enseigneDTO1).isNotEqualTo(enseigneDTO2);
        enseigneDTO2.setId(enseigneDTO1.getId());
        assertThat(enseigneDTO1).isEqualTo(enseigneDTO2);
        enseigneDTO2.setId(2L);
        assertThat(enseigneDTO1).isNotEqualTo(enseigneDTO2);
        enseigneDTO1.setId(null);
        assertThat(enseigneDTO1).isNotEqualTo(enseigneDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(enseigneMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(enseigneMapper.fromId(null)).isNull();
    }
}
