package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Jury;
import com.test.repository.JuryRepository;
import com.test.service.JuryService;
import com.test.service.dto.JuryDTO;
import com.test.service.mapper.JuryMapper;
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
 * Test class for the JuryResource REST controller.
 *
 * @see JuryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class JuryResourceIntTest {

    private static final String DEFAULT_LIBELLE_JURY = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_JURY = "BBBBBBBBBB";

    @Autowired
    private JuryRepository juryRepository;

    @Autowired
    private JuryMapper juryMapper;

    @Autowired
    private JuryService juryService;

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

    private MockMvc restJuryMockMvc;

    private Jury jury;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JuryResource juryResource = new JuryResource(juryService);
        this.restJuryMockMvc = MockMvcBuilders.standaloneSetup(juryResource)
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
    public static Jury createEntity(EntityManager em) {
        Jury jury = new Jury()
            .libelleJury(DEFAULT_LIBELLE_JURY);
        return jury;
    }

    @Before
    public void initTest() {
        jury = createEntity(em);
    }

    @Test
    @Transactional
    public void createJury() throws Exception {
        int databaseSizeBeforeCreate = juryRepository.findAll().size();

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);
        restJuryMockMvc.perform(post("/api/juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isCreated());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeCreate + 1);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getLibelleJury()).isEqualTo(DEFAULT_LIBELLE_JURY);
    }

    @Test
    @Transactional
    public void createJuryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = juryRepository.findAll().size();

        // Create the Jury with an existing ID
        jury.setId(1L);
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJuryMockMvc.perform(post("/api/juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleJuryIsRequired() throws Exception {
        int databaseSizeBeforeTest = juryRepository.findAll().size();
        // set the field null
        jury.setLibelleJury(null);

        // Create the Jury, which fails.
        JuryDTO juryDTO = juryMapper.toDto(jury);

        restJuryMockMvc.perform(post("/api/juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isBadRequest());

        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJuries() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        // Get all the juryList
        restJuryMockMvc.perform(get("/api/juries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jury.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleJury").value(hasItem(DEFAULT_LIBELLE_JURY.toString())));
    }
    
    @Test
    @Transactional
    public void getJury() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        // Get the jury
        restJuryMockMvc.perform(get("/api/juries/{id}", jury.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jury.getId().intValue()))
            .andExpect(jsonPath("$.libelleJury").value(DEFAULT_LIBELLE_JURY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJury() throws Exception {
        // Get the jury
        restJuryMockMvc.perform(get("/api/juries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJury() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        int databaseSizeBeforeUpdate = juryRepository.findAll().size();

        // Update the jury
        Jury updatedJury = juryRepository.findById(jury.getId()).get();
        // Disconnect from session so that the updates on updatedJury are not directly saved in db
        em.detach(updatedJury);
        updatedJury
            .libelleJury(UPDATED_LIBELLE_JURY);
        JuryDTO juryDTO = juryMapper.toDto(updatedJury);

        restJuryMockMvc.perform(put("/api/juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isOk());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
        Jury testJury = juryList.get(juryList.size() - 1);
        assertThat(testJury.getLibelleJury()).isEqualTo(UPDATED_LIBELLE_JURY);
    }

    @Test
    @Transactional
    public void updateNonExistingJury() throws Exception {
        int databaseSizeBeforeUpdate = juryRepository.findAll().size();

        // Create the Jury
        JuryDTO juryDTO = juryMapper.toDto(jury);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJuryMockMvc.perform(put("/api/juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(juryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Jury in the database
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJury() throws Exception {
        // Initialize the database
        juryRepository.saveAndFlush(jury);

        int databaseSizeBeforeDelete = juryRepository.findAll().size();

        // Delete the jury
        restJuryMockMvc.perform(delete("/api/juries/{id}", jury.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Jury> juryList = juryRepository.findAll();
        assertThat(juryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jury.class);
        Jury jury1 = new Jury();
        jury1.setId(1L);
        Jury jury2 = new Jury();
        jury2.setId(jury1.getId());
        assertThat(jury1).isEqualTo(jury2);
        jury2.setId(2L);
        assertThat(jury1).isNotEqualTo(jury2);
        jury1.setId(null);
        assertThat(jury1).isNotEqualTo(jury2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JuryDTO.class);
        JuryDTO juryDTO1 = new JuryDTO();
        juryDTO1.setId(1L);
        JuryDTO juryDTO2 = new JuryDTO();
        assertThat(juryDTO1).isNotEqualTo(juryDTO2);
        juryDTO2.setId(juryDTO1.getId());
        assertThat(juryDTO1).isEqualTo(juryDTO2);
        juryDTO2.setId(2L);
        assertThat(juryDTO1).isNotEqualTo(juryDTO2);
        juryDTO1.setId(null);
        assertThat(juryDTO1).isNotEqualTo(juryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(juryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(juryMapper.fromId(null)).isNull();
    }
}
