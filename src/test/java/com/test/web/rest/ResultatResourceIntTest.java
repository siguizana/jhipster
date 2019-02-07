package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Resultat;
import com.test.repository.ResultatRepository;
import com.test.service.ResultatService;
import com.test.service.dto.ResultatDTO;
import com.test.service.mapper.ResultatMapper;
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
 * Test class for the ResultatResource REST controller.
 *
 * @see ResultatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class ResultatResourceIntTest {

    private static final Float DEFAULT_NOTE_PONDERE = 1F;
    private static final Float UPDATED_NOTE_PONDERE = 2F;

    @Autowired
    private ResultatRepository resultatRepository;

    @Autowired
    private ResultatMapper resultatMapper;

    @Autowired
    private ResultatService resultatService;

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

    private MockMvc restResultatMockMvc;

    private Resultat resultat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultatResource resultatResource = new ResultatResource(resultatService);
        this.restResultatMockMvc = MockMvcBuilders.standaloneSetup(resultatResource)
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
    public static Resultat createEntity(EntityManager em) {
        Resultat resultat = new Resultat()
            .notePondere(DEFAULT_NOTE_PONDERE);
        return resultat;
    }

    @Before
    public void initTest() {
        resultat = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultat() throws Exception {
        int databaseSizeBeforeCreate = resultatRepository.findAll().size();

        // Create the Resultat
        ResultatDTO resultatDTO = resultatMapper.toDto(resultat);
        restResultatMockMvc.perform(post("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatDTO)))
            .andExpect(status().isCreated());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeCreate + 1);
        Resultat testResultat = resultatList.get(resultatList.size() - 1);
        assertThat(testResultat.getNotePondere()).isEqualTo(DEFAULT_NOTE_PONDERE);
    }

    @Test
    @Transactional
    public void createResultatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultatRepository.findAll().size();

        // Create the Resultat with an existing ID
        resultat.setId(1L);
        ResultatDTO resultatDTO = resultatMapper.toDto(resultat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultatMockMvc.perform(post("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNotePondereIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultatRepository.findAll().size();
        // set the field null
        resultat.setNotePondere(null);

        // Create the Resultat, which fails.
        ResultatDTO resultatDTO = resultatMapper.toDto(resultat);

        restResultatMockMvc.perform(post("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatDTO)))
            .andExpect(status().isBadRequest());

        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResultats() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList
        restResultatMockMvc.perform(get("/api/resultats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].notePondere").value(hasItem(DEFAULT_NOTE_PONDERE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getResultat() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get the resultat
        restResultatMockMvc.perform(get("/api/resultats/{id}", resultat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultat.getId().intValue()))
            .andExpect(jsonPath("$.notePondere").value(DEFAULT_NOTE_PONDERE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingResultat() throws Exception {
        // Get the resultat
        restResultatMockMvc.perform(get("/api/resultats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultat() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        int databaseSizeBeforeUpdate = resultatRepository.findAll().size();

        // Update the resultat
        Resultat updatedResultat = resultatRepository.findById(resultat.getId()).get();
        // Disconnect from session so that the updates on updatedResultat are not directly saved in db
        em.detach(updatedResultat);
        updatedResultat
            .notePondere(UPDATED_NOTE_PONDERE);
        ResultatDTO resultatDTO = resultatMapper.toDto(updatedResultat);

        restResultatMockMvc.perform(put("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatDTO)))
            .andExpect(status().isOk());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeUpdate);
        Resultat testResultat = resultatList.get(resultatList.size() - 1);
        assertThat(testResultat.getNotePondere()).isEqualTo(UPDATED_NOTE_PONDERE);
    }

    @Test
    @Transactional
    public void updateNonExistingResultat() throws Exception {
        int databaseSizeBeforeUpdate = resultatRepository.findAll().size();

        // Create the Resultat
        ResultatDTO resultatDTO = resultatMapper.toDto(resultat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultatMockMvc.perform(put("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultat() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        int databaseSizeBeforeDelete = resultatRepository.findAll().size();

        // Delete the resultat
        restResultatMockMvc.perform(delete("/api/resultats/{id}", resultat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resultat.class);
        Resultat resultat1 = new Resultat();
        resultat1.setId(1L);
        Resultat resultat2 = new Resultat();
        resultat2.setId(resultat1.getId());
        assertThat(resultat1).isEqualTo(resultat2);
        resultat2.setId(2L);
        assertThat(resultat1).isNotEqualTo(resultat2);
        resultat1.setId(null);
        assertThat(resultat1).isNotEqualTo(resultat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultatDTO.class);
        ResultatDTO resultatDTO1 = new ResultatDTO();
        resultatDTO1.setId(1L);
        ResultatDTO resultatDTO2 = new ResultatDTO();
        assertThat(resultatDTO1).isNotEqualTo(resultatDTO2);
        resultatDTO2.setId(resultatDTO1.getId());
        assertThat(resultatDTO1).isEqualTo(resultatDTO2);
        resultatDTO2.setId(2L);
        assertThat(resultatDTO1).isNotEqualTo(resultatDTO2);
        resultatDTO1.setId(null);
        assertThat(resultatDTO1).isNotEqualTo(resultatDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(resultatMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(resultatMapper.fromId(null)).isNull();
    }
}
