package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.PieceAConviction;
import com.test.repository.PieceAConvictionRepository;
import com.test.service.PieceAConvictionService;
import com.test.service.dto.PieceAConvictionDTO;
import com.test.service.mapper.PieceAConvictionMapper;
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
 * Test class for the PieceAConvictionResource REST controller.
 *
 * @see PieceAConvictionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class PieceAConvictionResourceIntTest {

    private static final String DEFAULT_LIBELLE_PIECE_ACONVICTION = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_PIECE_ACONVICTION = "BBBBBBBBBB";

    @Autowired
    private PieceAConvictionRepository pieceAConvictionRepository;

    @Autowired
    private PieceAConvictionMapper pieceAConvictionMapper;

    @Autowired
    private PieceAConvictionService pieceAConvictionService;

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

    private MockMvc restPieceAConvictionMockMvc;

    private PieceAConviction pieceAConviction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PieceAConvictionResource pieceAConvictionResource = new PieceAConvictionResource(pieceAConvictionService);
        this.restPieceAConvictionMockMvc = MockMvcBuilders.standaloneSetup(pieceAConvictionResource)
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
    public static PieceAConviction createEntity(EntityManager em) {
        PieceAConviction pieceAConviction = new PieceAConviction()
            .libellePieceAconviction(DEFAULT_LIBELLE_PIECE_ACONVICTION);
        return pieceAConviction;
    }

    @Before
    public void initTest() {
        pieceAConviction = createEntity(em);
    }

    @Test
    @Transactional
    public void createPieceAConviction() throws Exception {
        int databaseSizeBeforeCreate = pieceAConvictionRepository.findAll().size();

        // Create the PieceAConviction
        PieceAConvictionDTO pieceAConvictionDTO = pieceAConvictionMapper.toDto(pieceAConviction);
        restPieceAConvictionMockMvc.perform(post("/api/piece-a-convictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceAConvictionDTO)))
            .andExpect(status().isCreated());

        // Validate the PieceAConviction in the database
        List<PieceAConviction> pieceAConvictionList = pieceAConvictionRepository.findAll();
        assertThat(pieceAConvictionList).hasSize(databaseSizeBeforeCreate + 1);
        PieceAConviction testPieceAConviction = pieceAConvictionList.get(pieceAConvictionList.size() - 1);
        assertThat(testPieceAConviction.getLibellePieceAconviction()).isEqualTo(DEFAULT_LIBELLE_PIECE_ACONVICTION);
    }

    @Test
    @Transactional
    public void createPieceAConvictionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pieceAConvictionRepository.findAll().size();

        // Create the PieceAConviction with an existing ID
        pieceAConviction.setId(1L);
        PieceAConvictionDTO pieceAConvictionDTO = pieceAConvictionMapper.toDto(pieceAConviction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPieceAConvictionMockMvc.perform(post("/api/piece-a-convictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceAConvictionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PieceAConviction in the database
        List<PieceAConviction> pieceAConvictionList = pieceAConvictionRepository.findAll();
        assertThat(pieceAConvictionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibellePieceAconvictionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pieceAConvictionRepository.findAll().size();
        // set the field null
        pieceAConviction.setLibellePieceAconviction(null);

        // Create the PieceAConviction, which fails.
        PieceAConvictionDTO pieceAConvictionDTO = pieceAConvictionMapper.toDto(pieceAConviction);

        restPieceAConvictionMockMvc.perform(post("/api/piece-a-convictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceAConvictionDTO)))
            .andExpect(status().isBadRequest());

        List<PieceAConviction> pieceAConvictionList = pieceAConvictionRepository.findAll();
        assertThat(pieceAConvictionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPieceAConvictions() throws Exception {
        // Initialize the database
        pieceAConvictionRepository.saveAndFlush(pieceAConviction);

        // Get all the pieceAConvictionList
        restPieceAConvictionMockMvc.perform(get("/api/piece-a-convictions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pieceAConviction.getId().intValue())))
            .andExpect(jsonPath("$.[*].libellePieceAconviction").value(hasItem(DEFAULT_LIBELLE_PIECE_ACONVICTION.toString())));
    }
    
    @Test
    @Transactional
    public void getPieceAConviction() throws Exception {
        // Initialize the database
        pieceAConvictionRepository.saveAndFlush(pieceAConviction);

        // Get the pieceAConviction
        restPieceAConvictionMockMvc.perform(get("/api/piece-a-convictions/{id}", pieceAConviction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pieceAConviction.getId().intValue()))
            .andExpect(jsonPath("$.libellePieceAconviction").value(DEFAULT_LIBELLE_PIECE_ACONVICTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPieceAConviction() throws Exception {
        // Get the pieceAConviction
        restPieceAConvictionMockMvc.perform(get("/api/piece-a-convictions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePieceAConviction() throws Exception {
        // Initialize the database
        pieceAConvictionRepository.saveAndFlush(pieceAConviction);

        int databaseSizeBeforeUpdate = pieceAConvictionRepository.findAll().size();

        // Update the pieceAConviction
        PieceAConviction updatedPieceAConviction = pieceAConvictionRepository.findById(pieceAConviction.getId()).get();
        // Disconnect from session so that the updates on updatedPieceAConviction are not directly saved in db
        em.detach(updatedPieceAConviction);
        updatedPieceAConviction
            .libellePieceAconviction(UPDATED_LIBELLE_PIECE_ACONVICTION);
        PieceAConvictionDTO pieceAConvictionDTO = pieceAConvictionMapper.toDto(updatedPieceAConviction);

        restPieceAConvictionMockMvc.perform(put("/api/piece-a-convictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceAConvictionDTO)))
            .andExpect(status().isOk());

        // Validate the PieceAConviction in the database
        List<PieceAConviction> pieceAConvictionList = pieceAConvictionRepository.findAll();
        assertThat(pieceAConvictionList).hasSize(databaseSizeBeforeUpdate);
        PieceAConviction testPieceAConviction = pieceAConvictionList.get(pieceAConvictionList.size() - 1);
        assertThat(testPieceAConviction.getLibellePieceAconviction()).isEqualTo(UPDATED_LIBELLE_PIECE_ACONVICTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPieceAConviction() throws Exception {
        int databaseSizeBeforeUpdate = pieceAConvictionRepository.findAll().size();

        // Create the PieceAConviction
        PieceAConvictionDTO pieceAConvictionDTO = pieceAConvictionMapper.toDto(pieceAConviction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPieceAConvictionMockMvc.perform(put("/api/piece-a-convictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pieceAConvictionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PieceAConviction in the database
        List<PieceAConviction> pieceAConvictionList = pieceAConvictionRepository.findAll();
        assertThat(pieceAConvictionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePieceAConviction() throws Exception {
        // Initialize the database
        pieceAConvictionRepository.saveAndFlush(pieceAConviction);

        int databaseSizeBeforeDelete = pieceAConvictionRepository.findAll().size();

        // Delete the pieceAConviction
        restPieceAConvictionMockMvc.perform(delete("/api/piece-a-convictions/{id}", pieceAConviction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PieceAConviction> pieceAConvictionList = pieceAConvictionRepository.findAll();
        assertThat(pieceAConvictionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceAConviction.class);
        PieceAConviction pieceAConviction1 = new PieceAConviction();
        pieceAConviction1.setId(1L);
        PieceAConviction pieceAConviction2 = new PieceAConviction();
        pieceAConviction2.setId(pieceAConviction1.getId());
        assertThat(pieceAConviction1).isEqualTo(pieceAConviction2);
        pieceAConviction2.setId(2L);
        assertThat(pieceAConviction1).isNotEqualTo(pieceAConviction2);
        pieceAConviction1.setId(null);
        assertThat(pieceAConviction1).isNotEqualTo(pieceAConviction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceAConvictionDTO.class);
        PieceAConvictionDTO pieceAConvictionDTO1 = new PieceAConvictionDTO();
        pieceAConvictionDTO1.setId(1L);
        PieceAConvictionDTO pieceAConvictionDTO2 = new PieceAConvictionDTO();
        assertThat(pieceAConvictionDTO1).isNotEqualTo(pieceAConvictionDTO2);
        pieceAConvictionDTO2.setId(pieceAConvictionDTO1.getId());
        assertThat(pieceAConvictionDTO1).isEqualTo(pieceAConvictionDTO2);
        pieceAConvictionDTO2.setId(2L);
        assertThat(pieceAConvictionDTO1).isNotEqualTo(pieceAConvictionDTO2);
        pieceAConvictionDTO1.setId(null);
        assertThat(pieceAConvictionDTO1).isNotEqualTo(pieceAConvictionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pieceAConvictionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pieceAConvictionMapper.fromId(null)).isNull();
    }
}
