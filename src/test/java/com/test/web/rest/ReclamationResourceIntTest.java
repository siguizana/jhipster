package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Reclamation;
import com.test.repository.ReclamationRepository;
import com.test.service.ReclamationService;
import com.test.service.dto.ReclamationDTO;
import com.test.service.mapper.ReclamationMapper;
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

/**
 * Test class for the ReclamationResource REST controller.
 *
 * @see ReclamationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class ReclamationResourceIntTest {

    private static final String DEFAULT_MOTIF_RECLAMATION = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF_RECLAMATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RECLAMATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECLAMATION = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_NOTE_RECLAMATION = 1F;
    private static final Float UPDATED_NOTE_RECLAMATION = 2F;

    private static final Boolean DEFAULT_STATUT_RECLAMATION = false;
    private static final Boolean UPDATED_STATUT_RECLAMATION = true;

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private ReclamationMapper reclamationMapper;

    @Autowired
    private ReclamationService reclamationService;

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

    private MockMvc restReclamationMockMvc;

    private Reclamation reclamation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReclamationResource reclamationResource = new ReclamationResource(reclamationService);
        this.restReclamationMockMvc = MockMvcBuilders.standaloneSetup(reclamationResource)
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
    public static Reclamation createEntity(EntityManager em) {
        Reclamation reclamation = new Reclamation()
            .motifReclamation(DEFAULT_MOTIF_RECLAMATION)
            .dateReclamation(DEFAULT_DATE_RECLAMATION)
            .noteReclamation(DEFAULT_NOTE_RECLAMATION)
            .statutReclamation(DEFAULT_STATUT_RECLAMATION);
        return reclamation;
    }

    @Before
    public void initTest() {
        reclamation = createEntity(em);
    }

    @Test
    @Transactional
    public void createReclamation() throws Exception {
        int databaseSizeBeforeCreate = reclamationRepository.findAll().size();

        // Create the Reclamation
        ReclamationDTO reclamationDTO = reclamationMapper.toDto(reclamation);
        restReclamationMockMvc.perform(post("/api/reclamations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reclamationDTO)))
            .andExpect(status().isCreated());

        // Validate the Reclamation in the database
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeCreate + 1);
        Reclamation testReclamation = reclamationList.get(reclamationList.size() - 1);
        assertThat(testReclamation.getMotifReclamation()).isEqualTo(DEFAULT_MOTIF_RECLAMATION);
        assertThat(testReclamation.getDateReclamation()).isEqualTo(DEFAULT_DATE_RECLAMATION);
        assertThat(testReclamation.getNoteReclamation()).isEqualTo(DEFAULT_NOTE_RECLAMATION);
        assertThat(testReclamation.isStatutReclamation()).isEqualTo(DEFAULT_STATUT_RECLAMATION);
    }

    @Test
    @Transactional
    public void createReclamationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reclamationRepository.findAll().size();

        // Create the Reclamation with an existing ID
        reclamation.setId(1L);
        ReclamationDTO reclamationDTO = reclamationMapper.toDto(reclamation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReclamationMockMvc.perform(post("/api/reclamations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reclamationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reclamation in the database
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMotifReclamationIsRequired() throws Exception {
        int databaseSizeBeforeTest = reclamationRepository.findAll().size();
        // set the field null
        reclamation.setMotifReclamation(null);

        // Create the Reclamation, which fails.
        ReclamationDTO reclamationDTO = reclamationMapper.toDto(reclamation);

        restReclamationMockMvc.perform(post("/api/reclamations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reclamationDTO)))
            .andExpect(status().isBadRequest());

        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReclamations() throws Exception {
        // Initialize the database
        reclamationRepository.saveAndFlush(reclamation);

        // Get all the reclamationList
        restReclamationMockMvc.perform(get("/api/reclamations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reclamation.getId().intValue())))
            .andExpect(jsonPath("$.[*].motifReclamation").value(hasItem(DEFAULT_MOTIF_RECLAMATION.toString())))
            .andExpect(jsonPath("$.[*].dateReclamation").value(hasItem(DEFAULT_DATE_RECLAMATION.toString())))
            .andExpect(jsonPath("$.[*].noteReclamation").value(hasItem(DEFAULT_NOTE_RECLAMATION.doubleValue())))
            .andExpect(jsonPath("$.[*].statutReclamation").value(hasItem(DEFAULT_STATUT_RECLAMATION.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getReclamation() throws Exception {
        // Initialize the database
        reclamationRepository.saveAndFlush(reclamation);

        // Get the reclamation
        restReclamationMockMvc.perform(get("/api/reclamations/{id}", reclamation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reclamation.getId().intValue()))
            .andExpect(jsonPath("$.motifReclamation").value(DEFAULT_MOTIF_RECLAMATION.toString()))
            .andExpect(jsonPath("$.dateReclamation").value(DEFAULT_DATE_RECLAMATION.toString()))
            .andExpect(jsonPath("$.noteReclamation").value(DEFAULT_NOTE_RECLAMATION.doubleValue()))
            .andExpect(jsonPath("$.statutReclamation").value(DEFAULT_STATUT_RECLAMATION.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingReclamation() throws Exception {
        // Get the reclamation
        restReclamationMockMvc.perform(get("/api/reclamations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReclamation() throws Exception {
        // Initialize the database
        reclamationRepository.saveAndFlush(reclamation);

        int databaseSizeBeforeUpdate = reclamationRepository.findAll().size();

        // Update the reclamation
        Reclamation updatedReclamation = reclamationRepository.findById(reclamation.getId()).get();
        // Disconnect from session so that the updates on updatedReclamation are not directly saved in db
        em.detach(updatedReclamation);
        updatedReclamation
            .motifReclamation(UPDATED_MOTIF_RECLAMATION)
            .dateReclamation(UPDATED_DATE_RECLAMATION)
            .noteReclamation(UPDATED_NOTE_RECLAMATION)
            .statutReclamation(UPDATED_STATUT_RECLAMATION);
        ReclamationDTO reclamationDTO = reclamationMapper.toDto(updatedReclamation);

        restReclamationMockMvc.perform(put("/api/reclamations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reclamationDTO)))
            .andExpect(status().isOk());

        // Validate the Reclamation in the database
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeUpdate);
        Reclamation testReclamation = reclamationList.get(reclamationList.size() - 1);
        assertThat(testReclamation.getMotifReclamation()).isEqualTo(UPDATED_MOTIF_RECLAMATION);
        assertThat(testReclamation.getDateReclamation()).isEqualTo(UPDATED_DATE_RECLAMATION);
        assertThat(testReclamation.getNoteReclamation()).isEqualTo(UPDATED_NOTE_RECLAMATION);
        assertThat(testReclamation.isStatutReclamation()).isEqualTo(UPDATED_STATUT_RECLAMATION);
    }

    @Test
    @Transactional
    public void updateNonExistingReclamation() throws Exception {
        int databaseSizeBeforeUpdate = reclamationRepository.findAll().size();

        // Create the Reclamation
        ReclamationDTO reclamationDTO = reclamationMapper.toDto(reclamation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReclamationMockMvc.perform(put("/api/reclamations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reclamationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reclamation in the database
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReclamation() throws Exception {
        // Initialize the database
        reclamationRepository.saveAndFlush(reclamation);

        int databaseSizeBeforeDelete = reclamationRepository.findAll().size();

        // Delete the reclamation
        restReclamationMockMvc.perform(delete("/api/reclamations/{id}", reclamation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reclamation.class);
        Reclamation reclamation1 = new Reclamation();
        reclamation1.setId(1L);
        Reclamation reclamation2 = new Reclamation();
        reclamation2.setId(reclamation1.getId());
        assertThat(reclamation1).isEqualTo(reclamation2);
        reclamation2.setId(2L);
        assertThat(reclamation1).isNotEqualTo(reclamation2);
        reclamation1.setId(null);
        assertThat(reclamation1).isNotEqualTo(reclamation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReclamationDTO.class);
        ReclamationDTO reclamationDTO1 = new ReclamationDTO();
        reclamationDTO1.setId(1L);
        ReclamationDTO reclamationDTO2 = new ReclamationDTO();
        assertThat(reclamationDTO1).isNotEqualTo(reclamationDTO2);
        reclamationDTO2.setId(reclamationDTO1.getId());
        assertThat(reclamationDTO1).isEqualTo(reclamationDTO2);
        reclamationDTO2.setId(2L);
        assertThat(reclamationDTO1).isNotEqualTo(reclamationDTO2);
        reclamationDTO1.setId(null);
        assertThat(reclamationDTO1).isNotEqualTo(reclamationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reclamationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reclamationMapper.fromId(null)).isNull();
    }
}
