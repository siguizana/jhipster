package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.CompositionCopie;
import com.test.repository.CompositionCopieRepository;
import com.test.service.CompositionCopieService;
import com.test.service.dto.CompositionCopieDTO;
import com.test.service.mapper.CompositionCopieMapper;
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
 * Test class for the CompositionCopieResource REST controller.
 *
 * @see CompositionCopieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class CompositionCopieResourceIntTest {

    private static final Float DEFAULT_NOTE = 1F;
    private static final Float UPDATED_NOTE = 2F;

    private static final LocalDate DEFAULT_DATE_COMPOSITION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_COMPOSITION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMERO_ANONYMAT = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_ANONYMAT = "BBBBBBBBBB";

    @Autowired
    private CompositionCopieRepository compositionCopieRepository;

    @Autowired
    private CompositionCopieMapper compositionCopieMapper;

    @Autowired
    private CompositionCopieService compositionCopieService;

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

    private MockMvc restCompositionCopieMockMvc;

    private CompositionCopie compositionCopie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompositionCopieResource compositionCopieResource = new CompositionCopieResource(compositionCopieService);
        this.restCompositionCopieMockMvc = MockMvcBuilders.standaloneSetup(compositionCopieResource)
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
    public static CompositionCopie createEntity(EntityManager em) {
        CompositionCopie compositionCopie = new CompositionCopie()
            .note(DEFAULT_NOTE)
            .dateComposition(DEFAULT_DATE_COMPOSITION)
            .numeroAnonymat(DEFAULT_NUMERO_ANONYMAT);
        return compositionCopie;
    }

    @Before
    public void initTest() {
        compositionCopie = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompositionCopie() throws Exception {
        int databaseSizeBeforeCreate = compositionCopieRepository.findAll().size();

        // Create the CompositionCopie
        CompositionCopieDTO compositionCopieDTO = compositionCopieMapper.toDto(compositionCopie);
        restCompositionCopieMockMvc.perform(post("/api/composition-copies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compositionCopieDTO)))
            .andExpect(status().isCreated());

        // Validate the CompositionCopie in the database
        List<CompositionCopie> compositionCopieList = compositionCopieRepository.findAll();
        assertThat(compositionCopieList).hasSize(databaseSizeBeforeCreate + 1);
        CompositionCopie testCompositionCopie = compositionCopieList.get(compositionCopieList.size() - 1);
        assertThat(testCompositionCopie.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testCompositionCopie.getDateComposition()).isEqualTo(DEFAULT_DATE_COMPOSITION);
        assertThat(testCompositionCopie.getNumeroAnonymat()).isEqualTo(DEFAULT_NUMERO_ANONYMAT);
    }

    @Test
    @Transactional
    public void createCompositionCopieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compositionCopieRepository.findAll().size();

        // Create the CompositionCopie with an existing ID
        compositionCopie.setId(1L);
        CompositionCopieDTO compositionCopieDTO = compositionCopieMapper.toDto(compositionCopie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompositionCopieMockMvc.perform(post("/api/composition-copies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compositionCopieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompositionCopie in the database
        List<CompositionCopie> compositionCopieList = compositionCopieRepository.findAll();
        assertThat(compositionCopieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroAnonymatIsRequired() throws Exception {
        int databaseSizeBeforeTest = compositionCopieRepository.findAll().size();
        // set the field null
        compositionCopie.setNumeroAnonymat(null);

        // Create the CompositionCopie, which fails.
        CompositionCopieDTO compositionCopieDTO = compositionCopieMapper.toDto(compositionCopie);

        restCompositionCopieMockMvc.perform(post("/api/composition-copies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compositionCopieDTO)))
            .andExpect(status().isBadRequest());

        List<CompositionCopie> compositionCopieList = compositionCopieRepository.findAll();
        assertThat(compositionCopieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompositionCopies() throws Exception {
        // Initialize the database
        compositionCopieRepository.saveAndFlush(compositionCopie);

        // Get all the compositionCopieList
        restCompositionCopieMockMvc.perform(get("/api/composition-copies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compositionCopie.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateComposition").value(hasItem(DEFAULT_DATE_COMPOSITION.toString())))
            .andExpect(jsonPath("$.[*].numeroAnonymat").value(hasItem(DEFAULT_NUMERO_ANONYMAT.toString())));
    }
    
    @Test
    @Transactional
    public void getCompositionCopie() throws Exception {
        // Initialize the database
        compositionCopieRepository.saveAndFlush(compositionCopie);

        // Get the compositionCopie
        restCompositionCopieMockMvc.perform(get("/api/composition-copies/{id}", compositionCopie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compositionCopie.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.doubleValue()))
            .andExpect(jsonPath("$.dateComposition").value(DEFAULT_DATE_COMPOSITION.toString()))
            .andExpect(jsonPath("$.numeroAnonymat").value(DEFAULT_NUMERO_ANONYMAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompositionCopie() throws Exception {
        // Get the compositionCopie
        restCompositionCopieMockMvc.perform(get("/api/composition-copies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompositionCopie() throws Exception {
        // Initialize the database
        compositionCopieRepository.saveAndFlush(compositionCopie);

        int databaseSizeBeforeUpdate = compositionCopieRepository.findAll().size();

        // Update the compositionCopie
        CompositionCopie updatedCompositionCopie = compositionCopieRepository.findById(compositionCopie.getId()).get();
        // Disconnect from session so that the updates on updatedCompositionCopie are not directly saved in db
        em.detach(updatedCompositionCopie);
        updatedCompositionCopie
            .note(UPDATED_NOTE)
            .dateComposition(UPDATED_DATE_COMPOSITION)
            .numeroAnonymat(UPDATED_NUMERO_ANONYMAT);
        CompositionCopieDTO compositionCopieDTO = compositionCopieMapper.toDto(updatedCompositionCopie);

        restCompositionCopieMockMvc.perform(put("/api/composition-copies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compositionCopieDTO)))
            .andExpect(status().isOk());

        // Validate the CompositionCopie in the database
        List<CompositionCopie> compositionCopieList = compositionCopieRepository.findAll();
        assertThat(compositionCopieList).hasSize(databaseSizeBeforeUpdate);
        CompositionCopie testCompositionCopie = compositionCopieList.get(compositionCopieList.size() - 1);
        assertThat(testCompositionCopie.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testCompositionCopie.getDateComposition()).isEqualTo(UPDATED_DATE_COMPOSITION);
        assertThat(testCompositionCopie.getNumeroAnonymat()).isEqualTo(UPDATED_NUMERO_ANONYMAT);
    }

    @Test
    @Transactional
    public void updateNonExistingCompositionCopie() throws Exception {
        int databaseSizeBeforeUpdate = compositionCopieRepository.findAll().size();

        // Create the CompositionCopie
        CompositionCopieDTO compositionCopieDTO = compositionCopieMapper.toDto(compositionCopie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompositionCopieMockMvc.perform(put("/api/composition-copies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compositionCopieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompositionCopie in the database
        List<CompositionCopie> compositionCopieList = compositionCopieRepository.findAll();
        assertThat(compositionCopieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompositionCopie() throws Exception {
        // Initialize the database
        compositionCopieRepository.saveAndFlush(compositionCopie);

        int databaseSizeBeforeDelete = compositionCopieRepository.findAll().size();

        // Delete the compositionCopie
        restCompositionCopieMockMvc.perform(delete("/api/composition-copies/{id}", compositionCopie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompositionCopie> compositionCopieList = compositionCopieRepository.findAll();
        assertThat(compositionCopieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompositionCopie.class);
        CompositionCopie compositionCopie1 = new CompositionCopie();
        compositionCopie1.setId(1L);
        CompositionCopie compositionCopie2 = new CompositionCopie();
        compositionCopie2.setId(compositionCopie1.getId());
        assertThat(compositionCopie1).isEqualTo(compositionCopie2);
        compositionCopie2.setId(2L);
        assertThat(compositionCopie1).isNotEqualTo(compositionCopie2);
        compositionCopie1.setId(null);
        assertThat(compositionCopie1).isNotEqualTo(compositionCopie2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompositionCopieDTO.class);
        CompositionCopieDTO compositionCopieDTO1 = new CompositionCopieDTO();
        compositionCopieDTO1.setId(1L);
        CompositionCopieDTO compositionCopieDTO2 = new CompositionCopieDTO();
        assertThat(compositionCopieDTO1).isNotEqualTo(compositionCopieDTO2);
        compositionCopieDTO2.setId(compositionCopieDTO1.getId());
        assertThat(compositionCopieDTO1).isEqualTo(compositionCopieDTO2);
        compositionCopieDTO2.setId(2L);
        assertThat(compositionCopieDTO1).isNotEqualTo(compositionCopieDTO2);
        compositionCopieDTO1.setId(null);
        assertThat(compositionCopieDTO1).isNotEqualTo(compositionCopieDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(compositionCopieMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(compositionCopieMapper.fromId(null)).isNull();
    }
}
