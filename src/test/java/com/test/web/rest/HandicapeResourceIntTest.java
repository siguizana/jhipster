package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Handicape;
import com.test.repository.HandicapeRepository;
import com.test.service.HandicapeService;
import com.test.service.dto.HandicapeDTO;
import com.test.service.mapper.HandicapeMapper;
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
 * Test class for the HandicapeResource REST controller.
 *
 * @see HandicapeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class HandicapeResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private HandicapeRepository handicapeRepository;

    @Autowired
    private HandicapeMapper handicapeMapper;

    @Autowired
    private HandicapeService handicapeService;

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

    private MockMvc restHandicapeMockMvc;

    private Handicape handicape;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HandicapeResource handicapeResource = new HandicapeResource(handicapeService);
        this.restHandicapeMockMvc = MockMvcBuilders.standaloneSetup(handicapeResource)
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
    public static Handicape createEntity(EntityManager em) {
        Handicape handicape = new Handicape()
            .libelle(DEFAULT_LIBELLE);
        return handicape;
    }

    @Before
    public void initTest() {
        handicape = createEntity(em);
    }

    @Test
    @Transactional
    public void createHandicape() throws Exception {
        int databaseSizeBeforeCreate = handicapeRepository.findAll().size();

        // Create the Handicape
        HandicapeDTO handicapeDTO = handicapeMapper.toDto(handicape);
        restHandicapeMockMvc.perform(post("/api/handicapes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(handicapeDTO)))
            .andExpect(status().isCreated());

        // Validate the Handicape in the database
        List<Handicape> handicapeList = handicapeRepository.findAll();
        assertThat(handicapeList).hasSize(databaseSizeBeforeCreate + 1);
        Handicape testHandicape = handicapeList.get(handicapeList.size() - 1);
        assertThat(testHandicape.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createHandicapeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = handicapeRepository.findAll().size();

        // Create the Handicape with an existing ID
        handicape.setId(1L);
        HandicapeDTO handicapeDTO = handicapeMapper.toDto(handicape);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHandicapeMockMvc.perform(post("/api/handicapes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(handicapeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Handicape in the database
        List<Handicape> handicapeList = handicapeRepository.findAll();
        assertThat(handicapeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = handicapeRepository.findAll().size();
        // set the field null
        handicape.setLibelle(null);

        // Create the Handicape, which fails.
        HandicapeDTO handicapeDTO = handicapeMapper.toDto(handicape);

        restHandicapeMockMvc.perform(post("/api/handicapes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(handicapeDTO)))
            .andExpect(status().isBadRequest());

        List<Handicape> handicapeList = handicapeRepository.findAll();
        assertThat(handicapeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHandicapes() throws Exception {
        // Initialize the database
        handicapeRepository.saveAndFlush(handicape);

        // Get all the handicapeList
        restHandicapeMockMvc.perform(get("/api/handicapes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(handicape.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getHandicape() throws Exception {
        // Initialize the database
        handicapeRepository.saveAndFlush(handicape);

        // Get the handicape
        restHandicapeMockMvc.perform(get("/api/handicapes/{id}", handicape.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(handicape.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHandicape() throws Exception {
        // Get the handicape
        restHandicapeMockMvc.perform(get("/api/handicapes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHandicape() throws Exception {
        // Initialize the database
        handicapeRepository.saveAndFlush(handicape);

        int databaseSizeBeforeUpdate = handicapeRepository.findAll().size();

        // Update the handicape
        Handicape updatedHandicape = handicapeRepository.findById(handicape.getId()).get();
        // Disconnect from session so that the updates on updatedHandicape are not directly saved in db
        em.detach(updatedHandicape);
        updatedHandicape
            .libelle(UPDATED_LIBELLE);
        HandicapeDTO handicapeDTO = handicapeMapper.toDto(updatedHandicape);

        restHandicapeMockMvc.perform(put("/api/handicapes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(handicapeDTO)))
            .andExpect(status().isOk());

        // Validate the Handicape in the database
        List<Handicape> handicapeList = handicapeRepository.findAll();
        assertThat(handicapeList).hasSize(databaseSizeBeforeUpdate);
        Handicape testHandicape = handicapeList.get(handicapeList.size() - 1);
        assertThat(testHandicape.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingHandicape() throws Exception {
        int databaseSizeBeforeUpdate = handicapeRepository.findAll().size();

        // Create the Handicape
        HandicapeDTO handicapeDTO = handicapeMapper.toDto(handicape);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHandicapeMockMvc.perform(put("/api/handicapes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(handicapeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Handicape in the database
        List<Handicape> handicapeList = handicapeRepository.findAll();
        assertThat(handicapeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHandicape() throws Exception {
        // Initialize the database
        handicapeRepository.saveAndFlush(handicape);

        int databaseSizeBeforeDelete = handicapeRepository.findAll().size();

        // Delete the handicape
        restHandicapeMockMvc.perform(delete("/api/handicapes/{id}", handicape.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Handicape> handicapeList = handicapeRepository.findAll();
        assertThat(handicapeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Handicape.class);
        Handicape handicape1 = new Handicape();
        handicape1.setId(1L);
        Handicape handicape2 = new Handicape();
        handicape2.setId(handicape1.getId());
        assertThat(handicape1).isEqualTo(handicape2);
        handicape2.setId(2L);
        assertThat(handicape1).isNotEqualTo(handicape2);
        handicape1.setId(null);
        assertThat(handicape1).isNotEqualTo(handicape2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HandicapeDTO.class);
        HandicapeDTO handicapeDTO1 = new HandicapeDTO();
        handicapeDTO1.setId(1L);
        HandicapeDTO handicapeDTO2 = new HandicapeDTO();
        assertThat(handicapeDTO1).isNotEqualTo(handicapeDTO2);
        handicapeDTO2.setId(handicapeDTO1.getId());
        assertThat(handicapeDTO1).isEqualTo(handicapeDTO2);
        handicapeDTO2.setId(2L);
        assertThat(handicapeDTO1).isNotEqualTo(handicapeDTO2);
        handicapeDTO1.setId(null);
        assertThat(handicapeDTO1).isNotEqualTo(handicapeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(handicapeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(handicapeMapper.fromId(null)).isNull();
    }
}
