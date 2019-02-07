package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.SalleComposition;
import com.test.repository.SalleCompositionRepository;
import com.test.service.SalleCompositionService;
import com.test.service.dto.SalleCompositionDTO;
import com.test.service.mapper.SalleCompositionMapper;
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
 * Test class for the SalleCompositionResource REST controller.
 *
 * @see SalleCompositionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class SalleCompositionResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CAPACITE_SALLE = "AAAAAAAAAA";
    private static final String UPDATED_CAPACITE_SALLE = "BBBBBBBBBB";

    @Autowired
    private SalleCompositionRepository salleCompositionRepository;

    @Autowired
    private SalleCompositionMapper salleCompositionMapper;

    @Autowired
    private SalleCompositionService salleCompositionService;

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

    private MockMvc restSalleCompositionMockMvc;

    private SalleComposition salleComposition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalleCompositionResource salleCompositionResource = new SalleCompositionResource(salleCompositionService);
        this.restSalleCompositionMockMvc = MockMvcBuilders.standaloneSetup(salleCompositionResource)
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
    public static SalleComposition createEntity(EntityManager em) {
        SalleComposition salleComposition = new SalleComposition()
            .libelle(DEFAULT_LIBELLE)
            .capaciteSalle(DEFAULT_CAPACITE_SALLE);
        return salleComposition;
    }

    @Before
    public void initTest() {
        salleComposition = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalleComposition() throws Exception {
        int databaseSizeBeforeCreate = salleCompositionRepository.findAll().size();

        // Create the SalleComposition
        SalleCompositionDTO salleCompositionDTO = salleCompositionMapper.toDto(salleComposition);
        restSalleCompositionMockMvc.perform(post("/api/salle-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salleCompositionDTO)))
            .andExpect(status().isCreated());

        // Validate the SalleComposition in the database
        List<SalleComposition> salleCompositionList = salleCompositionRepository.findAll();
        assertThat(salleCompositionList).hasSize(databaseSizeBeforeCreate + 1);
        SalleComposition testSalleComposition = salleCompositionList.get(salleCompositionList.size() - 1);
        assertThat(testSalleComposition.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSalleComposition.getCapaciteSalle()).isEqualTo(DEFAULT_CAPACITE_SALLE);
    }

    @Test
    @Transactional
    public void createSalleCompositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salleCompositionRepository.findAll().size();

        // Create the SalleComposition with an existing ID
        salleComposition.setId(1L);
        SalleCompositionDTO salleCompositionDTO = salleCompositionMapper.toDto(salleComposition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalleCompositionMockMvc.perform(post("/api/salle-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salleCompositionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalleComposition in the database
        List<SalleComposition> salleCompositionList = salleCompositionRepository.findAll();
        assertThat(salleCompositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = salleCompositionRepository.findAll().size();
        // set the field null
        salleComposition.setLibelle(null);

        // Create the SalleComposition, which fails.
        SalleCompositionDTO salleCompositionDTO = salleCompositionMapper.toDto(salleComposition);

        restSalleCompositionMockMvc.perform(post("/api/salle-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salleCompositionDTO)))
            .andExpect(status().isBadRequest());

        List<SalleComposition> salleCompositionList = salleCompositionRepository.findAll();
        assertThat(salleCompositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalleCompositions() throws Exception {
        // Initialize the database
        salleCompositionRepository.saveAndFlush(salleComposition);

        // Get all the salleCompositionList
        restSalleCompositionMockMvc.perform(get("/api/salle-compositions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salleComposition.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].capaciteSalle").value(hasItem(DEFAULT_CAPACITE_SALLE.toString())));
    }
    
    @Test
    @Transactional
    public void getSalleComposition() throws Exception {
        // Initialize the database
        salleCompositionRepository.saveAndFlush(salleComposition);

        // Get the salleComposition
        restSalleCompositionMockMvc.perform(get("/api/salle-compositions/{id}", salleComposition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salleComposition.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.capaciteSalle").value(DEFAULT_CAPACITE_SALLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSalleComposition() throws Exception {
        // Get the salleComposition
        restSalleCompositionMockMvc.perform(get("/api/salle-compositions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalleComposition() throws Exception {
        // Initialize the database
        salleCompositionRepository.saveAndFlush(salleComposition);

        int databaseSizeBeforeUpdate = salleCompositionRepository.findAll().size();

        // Update the salleComposition
        SalleComposition updatedSalleComposition = salleCompositionRepository.findById(salleComposition.getId()).get();
        // Disconnect from session so that the updates on updatedSalleComposition are not directly saved in db
        em.detach(updatedSalleComposition);
        updatedSalleComposition
            .libelle(UPDATED_LIBELLE)
            .capaciteSalle(UPDATED_CAPACITE_SALLE);
        SalleCompositionDTO salleCompositionDTO = salleCompositionMapper.toDto(updatedSalleComposition);

        restSalleCompositionMockMvc.perform(put("/api/salle-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salleCompositionDTO)))
            .andExpect(status().isOk());

        // Validate the SalleComposition in the database
        List<SalleComposition> salleCompositionList = salleCompositionRepository.findAll();
        assertThat(salleCompositionList).hasSize(databaseSizeBeforeUpdate);
        SalleComposition testSalleComposition = salleCompositionList.get(salleCompositionList.size() - 1);
        assertThat(testSalleComposition.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSalleComposition.getCapaciteSalle()).isEqualTo(UPDATED_CAPACITE_SALLE);
    }

    @Test
    @Transactional
    public void updateNonExistingSalleComposition() throws Exception {
        int databaseSizeBeforeUpdate = salleCompositionRepository.findAll().size();

        // Create the SalleComposition
        SalleCompositionDTO salleCompositionDTO = salleCompositionMapper.toDto(salleComposition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalleCompositionMockMvc.perform(put("/api/salle-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salleCompositionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalleComposition in the database
        List<SalleComposition> salleCompositionList = salleCompositionRepository.findAll();
        assertThat(salleCompositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalleComposition() throws Exception {
        // Initialize the database
        salleCompositionRepository.saveAndFlush(salleComposition);

        int databaseSizeBeforeDelete = salleCompositionRepository.findAll().size();

        // Delete the salleComposition
        restSalleCompositionMockMvc.perform(delete("/api/salle-compositions/{id}", salleComposition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalleComposition> salleCompositionList = salleCompositionRepository.findAll();
        assertThat(salleCompositionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalleComposition.class);
        SalleComposition salleComposition1 = new SalleComposition();
        salleComposition1.setId(1L);
        SalleComposition salleComposition2 = new SalleComposition();
        salleComposition2.setId(salleComposition1.getId());
        assertThat(salleComposition1).isEqualTo(salleComposition2);
        salleComposition2.setId(2L);
        assertThat(salleComposition1).isNotEqualTo(salleComposition2);
        salleComposition1.setId(null);
        assertThat(salleComposition1).isNotEqualTo(salleComposition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalleCompositionDTO.class);
        SalleCompositionDTO salleCompositionDTO1 = new SalleCompositionDTO();
        salleCompositionDTO1.setId(1L);
        SalleCompositionDTO salleCompositionDTO2 = new SalleCompositionDTO();
        assertThat(salleCompositionDTO1).isNotEqualTo(salleCompositionDTO2);
        salleCompositionDTO2.setId(salleCompositionDTO1.getId());
        assertThat(salleCompositionDTO1).isEqualTo(salleCompositionDTO2);
        salleCompositionDTO2.setId(2L);
        assertThat(salleCompositionDTO1).isNotEqualTo(salleCompositionDTO2);
        salleCompositionDTO1.setId(null);
        assertThat(salleCompositionDTO1).isNotEqualTo(salleCompositionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(salleCompositionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(salleCompositionMapper.fromId(null)).isNull();
    }
}
