package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.RetraitDiplome;
import com.test.repository.RetraitDiplomeRepository;
import com.test.service.RetraitDiplomeService;
import com.test.service.dto.RetraitDiplomeDTO;
import com.test.service.mapper.RetraitDiplomeMapper;
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
 * Test class for the RetraitDiplomeResource REST controller.
 *
 * @see RetraitDiplomeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class RetraitDiplomeResourceIntTest {

    private static final LocalDate DEFAULT_DATE_RETRAIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RETRAIT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RetraitDiplomeRepository retraitDiplomeRepository;

    @Autowired
    private RetraitDiplomeMapper retraitDiplomeMapper;

    @Autowired
    private RetraitDiplomeService retraitDiplomeService;

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

    private MockMvc restRetraitDiplomeMockMvc;

    private RetraitDiplome retraitDiplome;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RetraitDiplomeResource retraitDiplomeResource = new RetraitDiplomeResource(retraitDiplomeService);
        this.restRetraitDiplomeMockMvc = MockMvcBuilders.standaloneSetup(retraitDiplomeResource)
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
    public static RetraitDiplome createEntity(EntityManager em) {
        RetraitDiplome retraitDiplome = new RetraitDiplome()
            .dateRetrait(DEFAULT_DATE_RETRAIT);
        return retraitDiplome;
    }

    @Before
    public void initTest() {
        retraitDiplome = createEntity(em);
    }

    @Test
    @Transactional
    public void createRetraitDiplome() throws Exception {
        int databaseSizeBeforeCreate = retraitDiplomeRepository.findAll().size();

        // Create the RetraitDiplome
        RetraitDiplomeDTO retraitDiplomeDTO = retraitDiplomeMapper.toDto(retraitDiplome);
        restRetraitDiplomeMockMvc.perform(post("/api/retrait-diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retraitDiplomeDTO)))
            .andExpect(status().isCreated());

        // Validate the RetraitDiplome in the database
        List<RetraitDiplome> retraitDiplomeList = retraitDiplomeRepository.findAll();
        assertThat(retraitDiplomeList).hasSize(databaseSizeBeforeCreate + 1);
        RetraitDiplome testRetraitDiplome = retraitDiplomeList.get(retraitDiplomeList.size() - 1);
        assertThat(testRetraitDiplome.getDateRetrait()).isEqualTo(DEFAULT_DATE_RETRAIT);
    }

    @Test
    @Transactional
    public void createRetraitDiplomeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = retraitDiplomeRepository.findAll().size();

        // Create the RetraitDiplome with an existing ID
        retraitDiplome.setId(1L);
        RetraitDiplomeDTO retraitDiplomeDTO = retraitDiplomeMapper.toDto(retraitDiplome);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRetraitDiplomeMockMvc.perform(post("/api/retrait-diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retraitDiplomeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RetraitDiplome in the database
        List<RetraitDiplome> retraitDiplomeList = retraitDiplomeRepository.findAll();
        assertThat(retraitDiplomeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateRetraitIsRequired() throws Exception {
        int databaseSizeBeforeTest = retraitDiplomeRepository.findAll().size();
        // set the field null
        retraitDiplome.setDateRetrait(null);

        // Create the RetraitDiplome, which fails.
        RetraitDiplomeDTO retraitDiplomeDTO = retraitDiplomeMapper.toDto(retraitDiplome);

        restRetraitDiplomeMockMvc.perform(post("/api/retrait-diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retraitDiplomeDTO)))
            .andExpect(status().isBadRequest());

        List<RetraitDiplome> retraitDiplomeList = retraitDiplomeRepository.findAll();
        assertThat(retraitDiplomeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRetraitDiplomes() throws Exception {
        // Initialize the database
        retraitDiplomeRepository.saveAndFlush(retraitDiplome);

        // Get all the retraitDiplomeList
        restRetraitDiplomeMockMvc.perform(get("/api/retrait-diplomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(retraitDiplome.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateRetrait").value(hasItem(DEFAULT_DATE_RETRAIT.toString())));
    }
    
    @Test
    @Transactional
    public void getRetraitDiplome() throws Exception {
        // Initialize the database
        retraitDiplomeRepository.saveAndFlush(retraitDiplome);

        // Get the retraitDiplome
        restRetraitDiplomeMockMvc.perform(get("/api/retrait-diplomes/{id}", retraitDiplome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(retraitDiplome.getId().intValue()))
            .andExpect(jsonPath("$.dateRetrait").value(DEFAULT_DATE_RETRAIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRetraitDiplome() throws Exception {
        // Get the retraitDiplome
        restRetraitDiplomeMockMvc.perform(get("/api/retrait-diplomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRetraitDiplome() throws Exception {
        // Initialize the database
        retraitDiplomeRepository.saveAndFlush(retraitDiplome);

        int databaseSizeBeforeUpdate = retraitDiplomeRepository.findAll().size();

        // Update the retraitDiplome
        RetraitDiplome updatedRetraitDiplome = retraitDiplomeRepository.findById(retraitDiplome.getId()).get();
        // Disconnect from session so that the updates on updatedRetraitDiplome are not directly saved in db
        em.detach(updatedRetraitDiplome);
        updatedRetraitDiplome
            .dateRetrait(UPDATED_DATE_RETRAIT);
        RetraitDiplomeDTO retraitDiplomeDTO = retraitDiplomeMapper.toDto(updatedRetraitDiplome);

        restRetraitDiplomeMockMvc.perform(put("/api/retrait-diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retraitDiplomeDTO)))
            .andExpect(status().isOk());

        // Validate the RetraitDiplome in the database
        List<RetraitDiplome> retraitDiplomeList = retraitDiplomeRepository.findAll();
        assertThat(retraitDiplomeList).hasSize(databaseSizeBeforeUpdate);
        RetraitDiplome testRetraitDiplome = retraitDiplomeList.get(retraitDiplomeList.size() - 1);
        assertThat(testRetraitDiplome.getDateRetrait()).isEqualTo(UPDATED_DATE_RETRAIT);
    }

    @Test
    @Transactional
    public void updateNonExistingRetraitDiplome() throws Exception {
        int databaseSizeBeforeUpdate = retraitDiplomeRepository.findAll().size();

        // Create the RetraitDiplome
        RetraitDiplomeDTO retraitDiplomeDTO = retraitDiplomeMapper.toDto(retraitDiplome);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRetraitDiplomeMockMvc.perform(put("/api/retrait-diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retraitDiplomeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RetraitDiplome in the database
        List<RetraitDiplome> retraitDiplomeList = retraitDiplomeRepository.findAll();
        assertThat(retraitDiplomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRetraitDiplome() throws Exception {
        // Initialize the database
        retraitDiplomeRepository.saveAndFlush(retraitDiplome);

        int databaseSizeBeforeDelete = retraitDiplomeRepository.findAll().size();

        // Delete the retraitDiplome
        restRetraitDiplomeMockMvc.perform(delete("/api/retrait-diplomes/{id}", retraitDiplome.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RetraitDiplome> retraitDiplomeList = retraitDiplomeRepository.findAll();
        assertThat(retraitDiplomeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RetraitDiplome.class);
        RetraitDiplome retraitDiplome1 = new RetraitDiplome();
        retraitDiplome1.setId(1L);
        RetraitDiplome retraitDiplome2 = new RetraitDiplome();
        retraitDiplome2.setId(retraitDiplome1.getId());
        assertThat(retraitDiplome1).isEqualTo(retraitDiplome2);
        retraitDiplome2.setId(2L);
        assertThat(retraitDiplome1).isNotEqualTo(retraitDiplome2);
        retraitDiplome1.setId(null);
        assertThat(retraitDiplome1).isNotEqualTo(retraitDiplome2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RetraitDiplomeDTO.class);
        RetraitDiplomeDTO retraitDiplomeDTO1 = new RetraitDiplomeDTO();
        retraitDiplomeDTO1.setId(1L);
        RetraitDiplomeDTO retraitDiplomeDTO2 = new RetraitDiplomeDTO();
        assertThat(retraitDiplomeDTO1).isNotEqualTo(retraitDiplomeDTO2);
        retraitDiplomeDTO2.setId(retraitDiplomeDTO1.getId());
        assertThat(retraitDiplomeDTO1).isEqualTo(retraitDiplomeDTO2);
        retraitDiplomeDTO2.setId(2L);
        assertThat(retraitDiplomeDTO1).isNotEqualTo(retraitDiplomeDTO2);
        retraitDiplomeDTO1.setId(null);
        assertThat(retraitDiplomeDTO1).isNotEqualTo(retraitDiplomeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(retraitDiplomeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(retraitDiplomeMapper.fromId(null)).isNull();
    }
}
