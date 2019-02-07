package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Dispense;
import com.test.repository.DispenseRepository;
import com.test.service.DispenseService;
import com.test.service.dto.DispenseDTO;
import com.test.service.mapper.DispenseMapper;
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
 * Test class for the DispenseResource REST controller.
 *
 * @see DispenseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class DispenseResourceIntTest {

    private static final String DEFAULT_MOTIT_DISPENSE = "AAAAAAAAAA";
    private static final String UPDATED_MOTIT_DISPENSE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PRENOM_MEDECIN = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PRENOM_MEDECIN = "BBBBBBBBBB";

    @Autowired
    private DispenseRepository dispenseRepository;

    @Mock
    private DispenseRepository dispenseRepositoryMock;

    @Autowired
    private DispenseMapper dispenseMapper;

    @Mock
    private DispenseService dispenseServiceMock;

    @Autowired
    private DispenseService dispenseService;

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

    private MockMvc restDispenseMockMvc;

    private Dispense dispense;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DispenseResource dispenseResource = new DispenseResource(dispenseService);
        this.restDispenseMockMvc = MockMvcBuilders.standaloneSetup(dispenseResource)
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
    public static Dispense createEntity(EntityManager em) {
        Dispense dispense = new Dispense()
            .motitDispense(DEFAULT_MOTIT_DISPENSE)
            .nomPrenomMedecin(DEFAULT_NOM_PRENOM_MEDECIN);
        return dispense;
    }

    @Before
    public void initTest() {
        dispense = createEntity(em);
    }

    @Test
    @Transactional
    public void createDispense() throws Exception {
        int databaseSizeBeforeCreate = dispenseRepository.findAll().size();

        // Create the Dispense
        DispenseDTO dispenseDTO = dispenseMapper.toDto(dispense);
        restDispenseMockMvc.perform(post("/api/dispenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispenseDTO)))
            .andExpect(status().isCreated());

        // Validate the Dispense in the database
        List<Dispense> dispenseList = dispenseRepository.findAll();
        assertThat(dispenseList).hasSize(databaseSizeBeforeCreate + 1);
        Dispense testDispense = dispenseList.get(dispenseList.size() - 1);
        assertThat(testDispense.getMotitDispense()).isEqualTo(DEFAULT_MOTIT_DISPENSE);
        assertThat(testDispense.getNomPrenomMedecin()).isEqualTo(DEFAULT_NOM_PRENOM_MEDECIN);
    }

    @Test
    @Transactional
    public void createDispenseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dispenseRepository.findAll().size();

        // Create the Dispense with an existing ID
        dispense.setId(1L);
        DispenseDTO dispenseDTO = dispenseMapper.toDto(dispense);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispenseMockMvc.perform(post("/api/dispenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dispense in the database
        List<Dispense> dispenseList = dispenseRepository.findAll();
        assertThat(dispenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMotitDispenseIsRequired() throws Exception {
        int databaseSizeBeforeTest = dispenseRepository.findAll().size();
        // set the field null
        dispense.setMotitDispense(null);

        // Create the Dispense, which fails.
        DispenseDTO dispenseDTO = dispenseMapper.toDto(dispense);

        restDispenseMockMvc.perform(post("/api/dispenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispenseDTO)))
            .andExpect(status().isBadRequest());

        List<Dispense> dispenseList = dispenseRepository.findAll();
        assertThat(dispenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomPrenomMedecinIsRequired() throws Exception {
        int databaseSizeBeforeTest = dispenseRepository.findAll().size();
        // set the field null
        dispense.setNomPrenomMedecin(null);

        // Create the Dispense, which fails.
        DispenseDTO dispenseDTO = dispenseMapper.toDto(dispense);

        restDispenseMockMvc.perform(post("/api/dispenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispenseDTO)))
            .andExpect(status().isBadRequest());

        List<Dispense> dispenseList = dispenseRepository.findAll();
        assertThat(dispenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDispenses() throws Exception {
        // Initialize the database
        dispenseRepository.saveAndFlush(dispense);

        // Get all the dispenseList
        restDispenseMockMvc.perform(get("/api/dispenses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispense.getId().intValue())))
            .andExpect(jsonPath("$.[*].motitDispense").value(hasItem(DEFAULT_MOTIT_DISPENSE.toString())))
            .andExpect(jsonPath("$.[*].nomPrenomMedecin").value(hasItem(DEFAULT_NOM_PRENOM_MEDECIN.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDispensesWithEagerRelationshipsIsEnabled() throws Exception {
        DispenseResource dispenseResource = new DispenseResource(dispenseServiceMock);
        when(dispenseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDispenseMockMvc = MockMvcBuilders.standaloneSetup(dispenseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDispenseMockMvc.perform(get("/api/dispenses?eagerload=true"))
        .andExpect(status().isOk());

        verify(dispenseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDispensesWithEagerRelationshipsIsNotEnabled() throws Exception {
        DispenseResource dispenseResource = new DispenseResource(dispenseServiceMock);
            when(dispenseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDispenseMockMvc = MockMvcBuilders.standaloneSetup(dispenseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDispenseMockMvc.perform(get("/api/dispenses?eagerload=true"))
        .andExpect(status().isOk());

            verify(dispenseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDispense() throws Exception {
        // Initialize the database
        dispenseRepository.saveAndFlush(dispense);

        // Get the dispense
        restDispenseMockMvc.perform(get("/api/dispenses/{id}", dispense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dispense.getId().intValue()))
            .andExpect(jsonPath("$.motitDispense").value(DEFAULT_MOTIT_DISPENSE.toString()))
            .andExpect(jsonPath("$.nomPrenomMedecin").value(DEFAULT_NOM_PRENOM_MEDECIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDispense() throws Exception {
        // Get the dispense
        restDispenseMockMvc.perform(get("/api/dispenses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDispense() throws Exception {
        // Initialize the database
        dispenseRepository.saveAndFlush(dispense);

        int databaseSizeBeforeUpdate = dispenseRepository.findAll().size();

        // Update the dispense
        Dispense updatedDispense = dispenseRepository.findById(dispense.getId()).get();
        // Disconnect from session so that the updates on updatedDispense are not directly saved in db
        em.detach(updatedDispense);
        updatedDispense
            .motitDispense(UPDATED_MOTIT_DISPENSE)
            .nomPrenomMedecin(UPDATED_NOM_PRENOM_MEDECIN);
        DispenseDTO dispenseDTO = dispenseMapper.toDto(updatedDispense);

        restDispenseMockMvc.perform(put("/api/dispenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispenseDTO)))
            .andExpect(status().isOk());

        // Validate the Dispense in the database
        List<Dispense> dispenseList = dispenseRepository.findAll();
        assertThat(dispenseList).hasSize(databaseSizeBeforeUpdate);
        Dispense testDispense = dispenseList.get(dispenseList.size() - 1);
        assertThat(testDispense.getMotitDispense()).isEqualTo(UPDATED_MOTIT_DISPENSE);
        assertThat(testDispense.getNomPrenomMedecin()).isEqualTo(UPDATED_NOM_PRENOM_MEDECIN);
    }

    @Test
    @Transactional
    public void updateNonExistingDispense() throws Exception {
        int databaseSizeBeforeUpdate = dispenseRepository.findAll().size();

        // Create the Dispense
        DispenseDTO dispenseDTO = dispenseMapper.toDto(dispense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispenseMockMvc.perform(put("/api/dispenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dispenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dispense in the database
        List<Dispense> dispenseList = dispenseRepository.findAll();
        assertThat(dispenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDispense() throws Exception {
        // Initialize the database
        dispenseRepository.saveAndFlush(dispense);

        int databaseSizeBeforeDelete = dispenseRepository.findAll().size();

        // Delete the dispense
        restDispenseMockMvc.perform(delete("/api/dispenses/{id}", dispense.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dispense> dispenseList = dispenseRepository.findAll();
        assertThat(dispenseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dispense.class);
        Dispense dispense1 = new Dispense();
        dispense1.setId(1L);
        Dispense dispense2 = new Dispense();
        dispense2.setId(dispense1.getId());
        assertThat(dispense1).isEqualTo(dispense2);
        dispense2.setId(2L);
        assertThat(dispense1).isNotEqualTo(dispense2);
        dispense1.setId(null);
        assertThat(dispense1).isNotEqualTo(dispense2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispenseDTO.class);
        DispenseDTO dispenseDTO1 = new DispenseDTO();
        dispenseDTO1.setId(1L);
        DispenseDTO dispenseDTO2 = new DispenseDTO();
        assertThat(dispenseDTO1).isNotEqualTo(dispenseDTO2);
        dispenseDTO2.setId(dispenseDTO1.getId());
        assertThat(dispenseDTO1).isEqualTo(dispenseDTO2);
        dispenseDTO2.setId(2L);
        assertThat(dispenseDTO1).isNotEqualTo(dispenseDTO2);
        dispenseDTO1.setId(null);
        assertThat(dispenseDTO1).isNotEqualTo(dispenseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dispenseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dispenseMapper.fromId(null)).isNull();
    }
}
