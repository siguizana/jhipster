package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.EpreuveSpecialiteOption;
import com.test.repository.EpreuveSpecialiteOptionRepository;
import com.test.service.EpreuveSpecialiteOptionService;
import com.test.service.dto.EpreuveSpecialiteOptionDTO;
import com.test.service.mapper.EpreuveSpecialiteOptionMapper;
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
 * Test class for the EpreuveSpecialiteOptionResource REST controller.
 *
 * @see EpreuveSpecialiteOptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class EpreuveSpecialiteOptionResourceIntTest {

    private static final Boolean DEFAULT_EPREUVE_RACHAT = false;
    private static final Boolean UPDATED_EPREUVE_RACHAT = true;

    private static final Boolean DEFAULT_EPREUVE_ADMISSIBILITE = false;
    private static final Boolean UPDATED_EPREUVE_ADMISSIBILITE = true;

    private static final Boolean DEFAULT_EPREUVE_FACULTATIVE = false;
    private static final Boolean UPDATED_EPREUVE_FACULTATIVE = true;

    private static final Float DEFAULT_NOTE_ELIMINATOIRE = 1F;
    private static final Float UPDATED_NOTE_ELIMINATOIRE = 2F;

    private static final Integer DEFAULT_COEFFICIENT = 1;
    private static final Integer UPDATED_COEFFICIENT = 2;

    @Autowired
    private EpreuveSpecialiteOptionRepository epreuveSpecialiteOptionRepository;

    @Autowired
    private EpreuveSpecialiteOptionMapper epreuveSpecialiteOptionMapper;

    @Autowired
    private EpreuveSpecialiteOptionService epreuveSpecialiteOptionService;

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

    private MockMvc restEpreuveSpecialiteOptionMockMvc;

    private EpreuveSpecialiteOption epreuveSpecialiteOption;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EpreuveSpecialiteOptionResource epreuveSpecialiteOptionResource = new EpreuveSpecialiteOptionResource(epreuveSpecialiteOptionService);
        this.restEpreuveSpecialiteOptionMockMvc = MockMvcBuilders.standaloneSetup(epreuveSpecialiteOptionResource)
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
    public static EpreuveSpecialiteOption createEntity(EntityManager em) {
        EpreuveSpecialiteOption epreuveSpecialiteOption = new EpreuveSpecialiteOption()
            .epreuveRachat(DEFAULT_EPREUVE_RACHAT)
            .epreuveAdmissibilite(DEFAULT_EPREUVE_ADMISSIBILITE)
            .epreuveFacultative(DEFAULT_EPREUVE_FACULTATIVE)
            .noteEliminatoire(DEFAULT_NOTE_ELIMINATOIRE)
            .coefficient(DEFAULT_COEFFICIENT);
        return epreuveSpecialiteOption;
    }

    @Before
    public void initTest() {
        epreuveSpecialiteOption = createEntity(em);
    }

    @Test
    @Transactional
    public void createEpreuveSpecialiteOption() throws Exception {
        int databaseSizeBeforeCreate = epreuveSpecialiteOptionRepository.findAll().size();

        // Create the EpreuveSpecialiteOption
        EpreuveSpecialiteOptionDTO epreuveSpecialiteOptionDTO = epreuveSpecialiteOptionMapper.toDto(epreuveSpecialiteOption);
        restEpreuveSpecialiteOptionMockMvc.perform(post("/api/epreuve-specialite-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epreuveSpecialiteOptionDTO)))
            .andExpect(status().isCreated());

        // Validate the EpreuveSpecialiteOption in the database
        List<EpreuveSpecialiteOption> epreuveSpecialiteOptionList = epreuveSpecialiteOptionRepository.findAll();
        assertThat(epreuveSpecialiteOptionList).hasSize(databaseSizeBeforeCreate + 1);
        EpreuveSpecialiteOption testEpreuveSpecialiteOption = epreuveSpecialiteOptionList.get(epreuveSpecialiteOptionList.size() - 1);
        assertThat(testEpreuveSpecialiteOption.isEpreuveRachat()).isEqualTo(DEFAULT_EPREUVE_RACHAT);
        assertThat(testEpreuveSpecialiteOption.isEpreuveAdmissibilite()).isEqualTo(DEFAULT_EPREUVE_ADMISSIBILITE);
        assertThat(testEpreuveSpecialiteOption.isEpreuveFacultative()).isEqualTo(DEFAULT_EPREUVE_FACULTATIVE);
        assertThat(testEpreuveSpecialiteOption.getNoteEliminatoire()).isEqualTo(DEFAULT_NOTE_ELIMINATOIRE);
        assertThat(testEpreuveSpecialiteOption.getCoefficient()).isEqualTo(DEFAULT_COEFFICIENT);
    }

    @Test
    @Transactional
    public void createEpreuveSpecialiteOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = epreuveSpecialiteOptionRepository.findAll().size();

        // Create the EpreuveSpecialiteOption with an existing ID
        epreuveSpecialiteOption.setId(1L);
        EpreuveSpecialiteOptionDTO epreuveSpecialiteOptionDTO = epreuveSpecialiteOptionMapper.toDto(epreuveSpecialiteOption);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEpreuveSpecialiteOptionMockMvc.perform(post("/api/epreuve-specialite-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epreuveSpecialiteOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EpreuveSpecialiteOption in the database
        List<EpreuveSpecialiteOption> epreuveSpecialiteOptionList = epreuveSpecialiteOptionRepository.findAll();
        assertThat(epreuveSpecialiteOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCoefficientIsRequired() throws Exception {
        int databaseSizeBeforeTest = epreuveSpecialiteOptionRepository.findAll().size();
        // set the field null
        epreuveSpecialiteOption.setCoefficient(null);

        // Create the EpreuveSpecialiteOption, which fails.
        EpreuveSpecialiteOptionDTO epreuveSpecialiteOptionDTO = epreuveSpecialiteOptionMapper.toDto(epreuveSpecialiteOption);

        restEpreuveSpecialiteOptionMockMvc.perform(post("/api/epreuve-specialite-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epreuveSpecialiteOptionDTO)))
            .andExpect(status().isBadRequest());

        List<EpreuveSpecialiteOption> epreuveSpecialiteOptionList = epreuveSpecialiteOptionRepository.findAll();
        assertThat(epreuveSpecialiteOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEpreuveSpecialiteOptions() throws Exception {
        // Initialize the database
        epreuveSpecialiteOptionRepository.saveAndFlush(epreuveSpecialiteOption);

        // Get all the epreuveSpecialiteOptionList
        restEpreuveSpecialiteOptionMockMvc.perform(get("/api/epreuve-specialite-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(epreuveSpecialiteOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].epreuveRachat").value(hasItem(DEFAULT_EPREUVE_RACHAT.booleanValue())))
            .andExpect(jsonPath("$.[*].epreuveAdmissibilite").value(hasItem(DEFAULT_EPREUVE_ADMISSIBILITE.booleanValue())))
            .andExpect(jsonPath("$.[*].epreuveFacultative").value(hasItem(DEFAULT_EPREUVE_FACULTATIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].noteEliminatoire").value(hasItem(DEFAULT_NOTE_ELIMINATOIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].coefficient").value(hasItem(DEFAULT_COEFFICIENT)));
    }
    
    @Test
    @Transactional
    public void getEpreuveSpecialiteOption() throws Exception {
        // Initialize the database
        epreuveSpecialiteOptionRepository.saveAndFlush(epreuveSpecialiteOption);

        // Get the epreuveSpecialiteOption
        restEpreuveSpecialiteOptionMockMvc.perform(get("/api/epreuve-specialite-options/{id}", epreuveSpecialiteOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(epreuveSpecialiteOption.getId().intValue()))
            .andExpect(jsonPath("$.epreuveRachat").value(DEFAULT_EPREUVE_RACHAT.booleanValue()))
            .andExpect(jsonPath("$.epreuveAdmissibilite").value(DEFAULT_EPREUVE_ADMISSIBILITE.booleanValue()))
            .andExpect(jsonPath("$.epreuveFacultative").value(DEFAULT_EPREUVE_FACULTATIVE.booleanValue()))
            .andExpect(jsonPath("$.noteEliminatoire").value(DEFAULT_NOTE_ELIMINATOIRE.doubleValue()))
            .andExpect(jsonPath("$.coefficient").value(DEFAULT_COEFFICIENT));
    }

    @Test
    @Transactional
    public void getNonExistingEpreuveSpecialiteOption() throws Exception {
        // Get the epreuveSpecialiteOption
        restEpreuveSpecialiteOptionMockMvc.perform(get("/api/epreuve-specialite-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEpreuveSpecialiteOption() throws Exception {
        // Initialize the database
        epreuveSpecialiteOptionRepository.saveAndFlush(epreuveSpecialiteOption);

        int databaseSizeBeforeUpdate = epreuveSpecialiteOptionRepository.findAll().size();

        // Update the epreuveSpecialiteOption
        EpreuveSpecialiteOption updatedEpreuveSpecialiteOption = epreuveSpecialiteOptionRepository.findById(epreuveSpecialiteOption.getId()).get();
        // Disconnect from session so that the updates on updatedEpreuveSpecialiteOption are not directly saved in db
        em.detach(updatedEpreuveSpecialiteOption);
        updatedEpreuveSpecialiteOption
            .epreuveRachat(UPDATED_EPREUVE_RACHAT)
            .epreuveAdmissibilite(UPDATED_EPREUVE_ADMISSIBILITE)
            .epreuveFacultative(UPDATED_EPREUVE_FACULTATIVE)
            .noteEliminatoire(UPDATED_NOTE_ELIMINATOIRE)
            .coefficient(UPDATED_COEFFICIENT);
        EpreuveSpecialiteOptionDTO epreuveSpecialiteOptionDTO = epreuveSpecialiteOptionMapper.toDto(updatedEpreuveSpecialiteOption);

        restEpreuveSpecialiteOptionMockMvc.perform(put("/api/epreuve-specialite-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epreuveSpecialiteOptionDTO)))
            .andExpect(status().isOk());

        // Validate the EpreuveSpecialiteOption in the database
        List<EpreuveSpecialiteOption> epreuveSpecialiteOptionList = epreuveSpecialiteOptionRepository.findAll();
        assertThat(epreuveSpecialiteOptionList).hasSize(databaseSizeBeforeUpdate);
        EpreuveSpecialiteOption testEpreuveSpecialiteOption = epreuveSpecialiteOptionList.get(epreuveSpecialiteOptionList.size() - 1);
        assertThat(testEpreuveSpecialiteOption.isEpreuveRachat()).isEqualTo(UPDATED_EPREUVE_RACHAT);
        assertThat(testEpreuveSpecialiteOption.isEpreuveAdmissibilite()).isEqualTo(UPDATED_EPREUVE_ADMISSIBILITE);
        assertThat(testEpreuveSpecialiteOption.isEpreuveFacultative()).isEqualTo(UPDATED_EPREUVE_FACULTATIVE);
        assertThat(testEpreuveSpecialiteOption.getNoteEliminatoire()).isEqualTo(UPDATED_NOTE_ELIMINATOIRE);
        assertThat(testEpreuveSpecialiteOption.getCoefficient()).isEqualTo(UPDATED_COEFFICIENT);
    }

    @Test
    @Transactional
    public void updateNonExistingEpreuveSpecialiteOption() throws Exception {
        int databaseSizeBeforeUpdate = epreuveSpecialiteOptionRepository.findAll().size();

        // Create the EpreuveSpecialiteOption
        EpreuveSpecialiteOptionDTO epreuveSpecialiteOptionDTO = epreuveSpecialiteOptionMapper.toDto(epreuveSpecialiteOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEpreuveSpecialiteOptionMockMvc.perform(put("/api/epreuve-specialite-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(epreuveSpecialiteOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EpreuveSpecialiteOption in the database
        List<EpreuveSpecialiteOption> epreuveSpecialiteOptionList = epreuveSpecialiteOptionRepository.findAll();
        assertThat(epreuveSpecialiteOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEpreuveSpecialiteOption() throws Exception {
        // Initialize the database
        epreuveSpecialiteOptionRepository.saveAndFlush(epreuveSpecialiteOption);

        int databaseSizeBeforeDelete = epreuveSpecialiteOptionRepository.findAll().size();

        // Delete the epreuveSpecialiteOption
        restEpreuveSpecialiteOptionMockMvc.perform(delete("/api/epreuve-specialite-options/{id}", epreuveSpecialiteOption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EpreuveSpecialiteOption> epreuveSpecialiteOptionList = epreuveSpecialiteOptionRepository.findAll();
        assertThat(epreuveSpecialiteOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EpreuveSpecialiteOption.class);
        EpreuveSpecialiteOption epreuveSpecialiteOption1 = new EpreuveSpecialiteOption();
        epreuveSpecialiteOption1.setId(1L);
        EpreuveSpecialiteOption epreuveSpecialiteOption2 = new EpreuveSpecialiteOption();
        epreuveSpecialiteOption2.setId(epreuveSpecialiteOption1.getId());
        assertThat(epreuveSpecialiteOption1).isEqualTo(epreuveSpecialiteOption2);
        epreuveSpecialiteOption2.setId(2L);
        assertThat(epreuveSpecialiteOption1).isNotEqualTo(epreuveSpecialiteOption2);
        epreuveSpecialiteOption1.setId(null);
        assertThat(epreuveSpecialiteOption1).isNotEqualTo(epreuveSpecialiteOption2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EpreuveSpecialiteOptionDTO.class);
        EpreuveSpecialiteOptionDTO epreuveSpecialiteOptionDTO1 = new EpreuveSpecialiteOptionDTO();
        epreuveSpecialiteOptionDTO1.setId(1L);
        EpreuveSpecialiteOptionDTO epreuveSpecialiteOptionDTO2 = new EpreuveSpecialiteOptionDTO();
        assertThat(epreuveSpecialiteOptionDTO1).isNotEqualTo(epreuveSpecialiteOptionDTO2);
        epreuveSpecialiteOptionDTO2.setId(epreuveSpecialiteOptionDTO1.getId());
        assertThat(epreuveSpecialiteOptionDTO1).isEqualTo(epreuveSpecialiteOptionDTO2);
        epreuveSpecialiteOptionDTO2.setId(2L);
        assertThat(epreuveSpecialiteOptionDTO1).isNotEqualTo(epreuveSpecialiteOptionDTO2);
        epreuveSpecialiteOptionDTO1.setId(null);
        assertThat(epreuveSpecialiteOptionDTO1).isNotEqualTo(epreuveSpecialiteOptionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(epreuveSpecialiteOptionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(epreuveSpecialiteOptionMapper.fromId(null)).isNull();
    }
}
