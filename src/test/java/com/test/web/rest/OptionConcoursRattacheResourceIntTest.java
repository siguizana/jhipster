package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.OptionConcoursRattache;
import com.test.repository.OptionConcoursRattacheRepository;
import com.test.service.OptionConcoursRattacheService;
import com.test.service.dto.OptionConcoursRattacheDTO;
import com.test.service.mapper.OptionConcoursRattacheMapper;
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
 * Test class for the OptionConcoursRattacheResource REST controller.
 *
 * @see OptionConcoursRattacheResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class OptionConcoursRattacheResourceIntTest {

    private static final String DEFAULT_LIBELLE_OPTION_CONCOURS_RATTACHE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_OPTION_CONCOURS_RATTACHE = "BBBBBBBBBB";

    @Autowired
    private OptionConcoursRattacheRepository optionConcoursRattacheRepository;

    @Autowired
    private OptionConcoursRattacheMapper optionConcoursRattacheMapper;

    @Autowired
    private OptionConcoursRattacheService optionConcoursRattacheService;

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

    private MockMvc restOptionConcoursRattacheMockMvc;

    private OptionConcoursRattache optionConcoursRattache;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OptionConcoursRattacheResource optionConcoursRattacheResource = new OptionConcoursRattacheResource(optionConcoursRattacheService);
        this.restOptionConcoursRattacheMockMvc = MockMvcBuilders.standaloneSetup(optionConcoursRattacheResource)
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
    public static OptionConcoursRattache createEntity(EntityManager em) {
        OptionConcoursRattache optionConcoursRattache = new OptionConcoursRattache()
            .libelleOptionConcoursRattache(DEFAULT_LIBELLE_OPTION_CONCOURS_RATTACHE);
        return optionConcoursRattache;
    }

    @Before
    public void initTest() {
        optionConcoursRattache = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptionConcoursRattache() throws Exception {
        int databaseSizeBeforeCreate = optionConcoursRattacheRepository.findAll().size();

        // Create the OptionConcoursRattache
        OptionConcoursRattacheDTO optionConcoursRattacheDTO = optionConcoursRattacheMapper.toDto(optionConcoursRattache);
        restOptionConcoursRattacheMockMvc.perform(post("/api/option-concours-rattaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionConcoursRattacheDTO)))
            .andExpect(status().isCreated());

        // Validate the OptionConcoursRattache in the database
        List<OptionConcoursRattache> optionConcoursRattacheList = optionConcoursRattacheRepository.findAll();
        assertThat(optionConcoursRattacheList).hasSize(databaseSizeBeforeCreate + 1);
        OptionConcoursRattache testOptionConcoursRattache = optionConcoursRattacheList.get(optionConcoursRattacheList.size() - 1);
        assertThat(testOptionConcoursRattache.getLibelleOptionConcoursRattache()).isEqualTo(DEFAULT_LIBELLE_OPTION_CONCOURS_RATTACHE);
    }

    @Test
    @Transactional
    public void createOptionConcoursRattacheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optionConcoursRattacheRepository.findAll().size();

        // Create the OptionConcoursRattache with an existing ID
        optionConcoursRattache.setId(1L);
        OptionConcoursRattacheDTO optionConcoursRattacheDTO = optionConcoursRattacheMapper.toDto(optionConcoursRattache);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionConcoursRattacheMockMvc.perform(post("/api/option-concours-rattaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionConcoursRattacheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OptionConcoursRattache in the database
        List<OptionConcoursRattache> optionConcoursRattacheList = optionConcoursRattacheRepository.findAll();
        assertThat(optionConcoursRattacheList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleOptionConcoursRattacheIsRequired() throws Exception {
        int databaseSizeBeforeTest = optionConcoursRattacheRepository.findAll().size();
        // set the field null
        optionConcoursRattache.setLibelleOptionConcoursRattache(null);

        // Create the OptionConcoursRattache, which fails.
        OptionConcoursRattacheDTO optionConcoursRattacheDTO = optionConcoursRattacheMapper.toDto(optionConcoursRattache);

        restOptionConcoursRattacheMockMvc.perform(post("/api/option-concours-rattaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionConcoursRattacheDTO)))
            .andExpect(status().isBadRequest());

        List<OptionConcoursRattache> optionConcoursRattacheList = optionConcoursRattacheRepository.findAll();
        assertThat(optionConcoursRattacheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOptionConcoursRattaches() throws Exception {
        // Initialize the database
        optionConcoursRattacheRepository.saveAndFlush(optionConcoursRattache);

        // Get all the optionConcoursRattacheList
        restOptionConcoursRattacheMockMvc.perform(get("/api/option-concours-rattaches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionConcoursRattache.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleOptionConcoursRattache").value(hasItem(DEFAULT_LIBELLE_OPTION_CONCOURS_RATTACHE.toString())));
    }
    
    @Test
    @Transactional
    public void getOptionConcoursRattache() throws Exception {
        // Initialize the database
        optionConcoursRattacheRepository.saveAndFlush(optionConcoursRattache);

        // Get the optionConcoursRattache
        restOptionConcoursRattacheMockMvc.perform(get("/api/option-concours-rattaches/{id}", optionConcoursRattache.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(optionConcoursRattache.getId().intValue()))
            .andExpect(jsonPath("$.libelleOptionConcoursRattache").value(DEFAULT_LIBELLE_OPTION_CONCOURS_RATTACHE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOptionConcoursRattache() throws Exception {
        // Get the optionConcoursRattache
        restOptionConcoursRattacheMockMvc.perform(get("/api/option-concours-rattaches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptionConcoursRattache() throws Exception {
        // Initialize the database
        optionConcoursRattacheRepository.saveAndFlush(optionConcoursRattache);

        int databaseSizeBeforeUpdate = optionConcoursRattacheRepository.findAll().size();

        // Update the optionConcoursRattache
        OptionConcoursRattache updatedOptionConcoursRattache = optionConcoursRattacheRepository.findById(optionConcoursRattache.getId()).get();
        // Disconnect from session so that the updates on updatedOptionConcoursRattache are not directly saved in db
        em.detach(updatedOptionConcoursRattache);
        updatedOptionConcoursRattache
            .libelleOptionConcoursRattache(UPDATED_LIBELLE_OPTION_CONCOURS_RATTACHE);
        OptionConcoursRattacheDTO optionConcoursRattacheDTO = optionConcoursRattacheMapper.toDto(updatedOptionConcoursRattache);

        restOptionConcoursRattacheMockMvc.perform(put("/api/option-concours-rattaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionConcoursRattacheDTO)))
            .andExpect(status().isOk());

        // Validate the OptionConcoursRattache in the database
        List<OptionConcoursRattache> optionConcoursRattacheList = optionConcoursRattacheRepository.findAll();
        assertThat(optionConcoursRattacheList).hasSize(databaseSizeBeforeUpdate);
        OptionConcoursRattache testOptionConcoursRattache = optionConcoursRattacheList.get(optionConcoursRattacheList.size() - 1);
        assertThat(testOptionConcoursRattache.getLibelleOptionConcoursRattache()).isEqualTo(UPDATED_LIBELLE_OPTION_CONCOURS_RATTACHE);
    }

    @Test
    @Transactional
    public void updateNonExistingOptionConcoursRattache() throws Exception {
        int databaseSizeBeforeUpdate = optionConcoursRattacheRepository.findAll().size();

        // Create the OptionConcoursRattache
        OptionConcoursRattacheDTO optionConcoursRattacheDTO = optionConcoursRattacheMapper.toDto(optionConcoursRattache);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionConcoursRattacheMockMvc.perform(put("/api/option-concours-rattaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optionConcoursRattacheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OptionConcoursRattache in the database
        List<OptionConcoursRattache> optionConcoursRattacheList = optionConcoursRattacheRepository.findAll();
        assertThat(optionConcoursRattacheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOptionConcoursRattache() throws Exception {
        // Initialize the database
        optionConcoursRattacheRepository.saveAndFlush(optionConcoursRattache);

        int databaseSizeBeforeDelete = optionConcoursRattacheRepository.findAll().size();

        // Delete the optionConcoursRattache
        restOptionConcoursRattacheMockMvc.perform(delete("/api/option-concours-rattaches/{id}", optionConcoursRattache.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OptionConcoursRattache> optionConcoursRattacheList = optionConcoursRattacheRepository.findAll();
        assertThat(optionConcoursRattacheList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionConcoursRattache.class);
        OptionConcoursRattache optionConcoursRattache1 = new OptionConcoursRattache();
        optionConcoursRattache1.setId(1L);
        OptionConcoursRattache optionConcoursRattache2 = new OptionConcoursRattache();
        optionConcoursRattache2.setId(optionConcoursRattache1.getId());
        assertThat(optionConcoursRattache1).isEqualTo(optionConcoursRattache2);
        optionConcoursRattache2.setId(2L);
        assertThat(optionConcoursRattache1).isNotEqualTo(optionConcoursRattache2);
        optionConcoursRattache1.setId(null);
        assertThat(optionConcoursRattache1).isNotEqualTo(optionConcoursRattache2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionConcoursRattacheDTO.class);
        OptionConcoursRattacheDTO optionConcoursRattacheDTO1 = new OptionConcoursRattacheDTO();
        optionConcoursRattacheDTO1.setId(1L);
        OptionConcoursRattacheDTO optionConcoursRattacheDTO2 = new OptionConcoursRattacheDTO();
        assertThat(optionConcoursRattacheDTO1).isNotEqualTo(optionConcoursRattacheDTO2);
        optionConcoursRattacheDTO2.setId(optionConcoursRattacheDTO1.getId());
        assertThat(optionConcoursRattacheDTO1).isEqualTo(optionConcoursRattacheDTO2);
        optionConcoursRattacheDTO2.setId(2L);
        assertThat(optionConcoursRattacheDTO1).isNotEqualTo(optionConcoursRattacheDTO2);
        optionConcoursRattacheDTO1.setId(null);
        assertThat(optionConcoursRattacheDTO1).isNotEqualTo(optionConcoursRattacheDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(optionConcoursRattacheMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(optionConcoursRattacheMapper.fromId(null)).isNull();
    }
}
