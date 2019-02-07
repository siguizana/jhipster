package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Surveille;
import com.test.repository.SurveilleRepository;
import com.test.service.SurveilleService;
import com.test.service.dto.SurveilleDTO;
import com.test.service.mapper.SurveilleMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.test.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SurveilleResource REST controller.
 *
 * @see SurveilleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class SurveilleResourceIntTest {

    private static final Instant DEFAULT_DATE_DEBUT_SURVEILLANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT_SURVEILLANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_FIN_SURVEILLANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN_SURVEILLANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SurveilleRepository surveilleRepository;

    @Autowired
    private SurveilleMapper surveilleMapper;

    @Autowired
    private SurveilleService surveilleService;

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

    private MockMvc restSurveilleMockMvc;

    private Surveille surveille;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SurveilleResource surveilleResource = new SurveilleResource(surveilleService);
        this.restSurveilleMockMvc = MockMvcBuilders.standaloneSetup(surveilleResource)
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
    public static Surveille createEntity(EntityManager em) {
        Surveille surveille = new Surveille()
            .dateDebutSurveillance(DEFAULT_DATE_DEBUT_SURVEILLANCE)
            .dateFinSurveillance(DEFAULT_DATE_FIN_SURVEILLANCE);
        return surveille;
    }

    @Before
    public void initTest() {
        surveille = createEntity(em);
    }

    @Test
    @Transactional
    public void createSurveille() throws Exception {
        int databaseSizeBeforeCreate = surveilleRepository.findAll().size();

        // Create the Surveille
        SurveilleDTO surveilleDTO = surveilleMapper.toDto(surveille);
        restSurveilleMockMvc.perform(post("/api/surveilles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(surveilleDTO)))
            .andExpect(status().isCreated());

        // Validate the Surveille in the database
        List<Surveille> surveilleList = surveilleRepository.findAll();
        assertThat(surveilleList).hasSize(databaseSizeBeforeCreate + 1);
        Surveille testSurveille = surveilleList.get(surveilleList.size() - 1);
        assertThat(testSurveille.getDateDebutSurveillance()).isEqualTo(DEFAULT_DATE_DEBUT_SURVEILLANCE);
        assertThat(testSurveille.getDateFinSurveillance()).isEqualTo(DEFAULT_DATE_FIN_SURVEILLANCE);
    }

    @Test
    @Transactional
    public void createSurveilleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = surveilleRepository.findAll().size();

        // Create the Surveille with an existing ID
        surveille.setId(1L);
        SurveilleDTO surveilleDTO = surveilleMapper.toDto(surveille);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurveilleMockMvc.perform(post("/api/surveilles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(surveilleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Surveille in the database
        List<Surveille> surveilleList = surveilleRepository.findAll();
        assertThat(surveilleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSurveilles() throws Exception {
        // Initialize the database
        surveilleRepository.saveAndFlush(surveille);

        // Get all the surveilleList
        restSurveilleMockMvc.perform(get("/api/surveilles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(surveille.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebutSurveillance").value(hasItem(DEFAULT_DATE_DEBUT_SURVEILLANCE.toString())))
            .andExpect(jsonPath("$.[*].dateFinSurveillance").value(hasItem(DEFAULT_DATE_FIN_SURVEILLANCE.toString())));
    }
    
    @Test
    @Transactional
    public void getSurveille() throws Exception {
        // Initialize the database
        surveilleRepository.saveAndFlush(surveille);

        // Get the surveille
        restSurveilleMockMvc.perform(get("/api/surveilles/{id}", surveille.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(surveille.getId().intValue()))
            .andExpect(jsonPath("$.dateDebutSurveillance").value(DEFAULT_DATE_DEBUT_SURVEILLANCE.toString()))
            .andExpect(jsonPath("$.dateFinSurveillance").value(DEFAULT_DATE_FIN_SURVEILLANCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSurveille() throws Exception {
        // Get the surveille
        restSurveilleMockMvc.perform(get("/api/surveilles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSurveille() throws Exception {
        // Initialize the database
        surveilleRepository.saveAndFlush(surveille);

        int databaseSizeBeforeUpdate = surveilleRepository.findAll().size();

        // Update the surveille
        Surveille updatedSurveille = surveilleRepository.findById(surveille.getId()).get();
        // Disconnect from session so that the updates on updatedSurveille are not directly saved in db
        em.detach(updatedSurveille);
        updatedSurveille
            .dateDebutSurveillance(UPDATED_DATE_DEBUT_SURVEILLANCE)
            .dateFinSurveillance(UPDATED_DATE_FIN_SURVEILLANCE);
        SurveilleDTO surveilleDTO = surveilleMapper.toDto(updatedSurveille);

        restSurveilleMockMvc.perform(put("/api/surveilles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(surveilleDTO)))
            .andExpect(status().isOk());

        // Validate the Surveille in the database
        List<Surveille> surveilleList = surveilleRepository.findAll();
        assertThat(surveilleList).hasSize(databaseSizeBeforeUpdate);
        Surveille testSurveille = surveilleList.get(surveilleList.size() - 1);
        assertThat(testSurveille.getDateDebutSurveillance()).isEqualTo(UPDATED_DATE_DEBUT_SURVEILLANCE);
        assertThat(testSurveille.getDateFinSurveillance()).isEqualTo(UPDATED_DATE_FIN_SURVEILLANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingSurveille() throws Exception {
        int databaseSizeBeforeUpdate = surveilleRepository.findAll().size();

        // Create the Surveille
        SurveilleDTO surveilleDTO = surveilleMapper.toDto(surveille);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurveilleMockMvc.perform(put("/api/surveilles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(surveilleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Surveille in the database
        List<Surveille> surveilleList = surveilleRepository.findAll();
        assertThat(surveilleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSurveille() throws Exception {
        // Initialize the database
        surveilleRepository.saveAndFlush(surveille);

        int databaseSizeBeforeDelete = surveilleRepository.findAll().size();

        // Delete the surveille
        restSurveilleMockMvc.perform(delete("/api/surveilles/{id}", surveille.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Surveille> surveilleList = surveilleRepository.findAll();
        assertThat(surveilleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Surveille.class);
        Surveille surveille1 = new Surveille();
        surveille1.setId(1L);
        Surveille surveille2 = new Surveille();
        surveille2.setId(surveille1.getId());
        assertThat(surveille1).isEqualTo(surveille2);
        surveille2.setId(2L);
        assertThat(surveille1).isNotEqualTo(surveille2);
        surveille1.setId(null);
        assertThat(surveille1).isNotEqualTo(surveille2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SurveilleDTO.class);
        SurveilleDTO surveilleDTO1 = new SurveilleDTO();
        surveilleDTO1.setId(1L);
        SurveilleDTO surveilleDTO2 = new SurveilleDTO();
        assertThat(surveilleDTO1).isNotEqualTo(surveilleDTO2);
        surveilleDTO2.setId(surveilleDTO1.getId());
        assertThat(surveilleDTO1).isEqualTo(surveilleDTO2);
        surveilleDTO2.setId(2L);
        assertThat(surveilleDTO1).isNotEqualTo(surveilleDTO2);
        surveilleDTO1.setId(null);
        assertThat(surveilleDTO1).isNotEqualTo(surveilleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(surveilleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(surveilleMapper.fromId(null)).isNull();
    }
}
