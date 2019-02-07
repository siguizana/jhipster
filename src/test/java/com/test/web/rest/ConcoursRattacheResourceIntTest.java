package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.ConcoursRattache;
import com.test.repository.ConcoursRattacheRepository;
import com.test.service.ConcoursRattacheService;
import com.test.service.dto.ConcoursRattacheDTO;
import com.test.service.mapper.ConcoursRattacheMapper;
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
 * Test class for the ConcoursRattacheResource REST controller.
 *
 * @see ConcoursRattacheResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class ConcoursRattacheResourceIntTest {

    private static final String DEFAULT_LIBELLE_CONCOURS_RATTACHE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CONCOURS_RATTACHE = "BBBBBBBBBB";

    @Autowired
    private ConcoursRattacheRepository concoursRattacheRepository;

    @Autowired
    private ConcoursRattacheMapper concoursRattacheMapper;

    @Autowired
    private ConcoursRattacheService concoursRattacheService;

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

    private MockMvc restConcoursRattacheMockMvc;

    private ConcoursRattache concoursRattache;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConcoursRattacheResource concoursRattacheResource = new ConcoursRattacheResource(concoursRattacheService);
        this.restConcoursRattacheMockMvc = MockMvcBuilders.standaloneSetup(concoursRattacheResource)
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
    public static ConcoursRattache createEntity(EntityManager em) {
        ConcoursRattache concoursRattache = new ConcoursRattache()
            .libelleConcoursRattache(DEFAULT_LIBELLE_CONCOURS_RATTACHE);
        return concoursRattache;
    }

    @Before
    public void initTest() {
        concoursRattache = createEntity(em);
    }

    @Test
    @Transactional
    public void createConcoursRattache() throws Exception {
        int databaseSizeBeforeCreate = concoursRattacheRepository.findAll().size();

        // Create the ConcoursRattache
        ConcoursRattacheDTO concoursRattacheDTO = concoursRattacheMapper.toDto(concoursRattache);
        restConcoursRattacheMockMvc.perform(post("/api/concours-rattaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concoursRattacheDTO)))
            .andExpect(status().isCreated());

        // Validate the ConcoursRattache in the database
        List<ConcoursRattache> concoursRattacheList = concoursRattacheRepository.findAll();
        assertThat(concoursRattacheList).hasSize(databaseSizeBeforeCreate + 1);
        ConcoursRattache testConcoursRattache = concoursRattacheList.get(concoursRattacheList.size() - 1);
        assertThat(testConcoursRattache.getLibelleConcoursRattache()).isEqualTo(DEFAULT_LIBELLE_CONCOURS_RATTACHE);
    }

    @Test
    @Transactional
    public void createConcoursRattacheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = concoursRattacheRepository.findAll().size();

        // Create the ConcoursRattache with an existing ID
        concoursRattache.setId(1L);
        ConcoursRattacheDTO concoursRattacheDTO = concoursRattacheMapper.toDto(concoursRattache);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcoursRattacheMockMvc.perform(post("/api/concours-rattaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concoursRattacheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConcoursRattache in the database
        List<ConcoursRattache> concoursRattacheList = concoursRattacheRepository.findAll();
        assertThat(concoursRattacheList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleConcoursRattacheIsRequired() throws Exception {
        int databaseSizeBeforeTest = concoursRattacheRepository.findAll().size();
        // set the field null
        concoursRattache.setLibelleConcoursRattache(null);

        // Create the ConcoursRattache, which fails.
        ConcoursRattacheDTO concoursRattacheDTO = concoursRattacheMapper.toDto(concoursRattache);

        restConcoursRattacheMockMvc.perform(post("/api/concours-rattaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concoursRattacheDTO)))
            .andExpect(status().isBadRequest());

        List<ConcoursRattache> concoursRattacheList = concoursRattacheRepository.findAll();
        assertThat(concoursRattacheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConcoursRattaches() throws Exception {
        // Initialize the database
        concoursRattacheRepository.saveAndFlush(concoursRattache);

        // Get all the concoursRattacheList
        restConcoursRattacheMockMvc.perform(get("/api/concours-rattaches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concoursRattache.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleConcoursRattache").value(hasItem(DEFAULT_LIBELLE_CONCOURS_RATTACHE.toString())));
    }
    
    @Test
    @Transactional
    public void getConcoursRattache() throws Exception {
        // Initialize the database
        concoursRattacheRepository.saveAndFlush(concoursRattache);

        // Get the concoursRattache
        restConcoursRattacheMockMvc.perform(get("/api/concours-rattaches/{id}", concoursRattache.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(concoursRattache.getId().intValue()))
            .andExpect(jsonPath("$.libelleConcoursRattache").value(DEFAULT_LIBELLE_CONCOURS_RATTACHE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConcoursRattache() throws Exception {
        // Get the concoursRattache
        restConcoursRattacheMockMvc.perform(get("/api/concours-rattaches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcoursRattache() throws Exception {
        // Initialize the database
        concoursRattacheRepository.saveAndFlush(concoursRattache);

        int databaseSizeBeforeUpdate = concoursRattacheRepository.findAll().size();

        // Update the concoursRattache
        ConcoursRattache updatedConcoursRattache = concoursRattacheRepository.findById(concoursRattache.getId()).get();
        // Disconnect from session so that the updates on updatedConcoursRattache are not directly saved in db
        em.detach(updatedConcoursRattache);
        updatedConcoursRattache
            .libelleConcoursRattache(UPDATED_LIBELLE_CONCOURS_RATTACHE);
        ConcoursRattacheDTO concoursRattacheDTO = concoursRattacheMapper.toDto(updatedConcoursRattache);

        restConcoursRattacheMockMvc.perform(put("/api/concours-rattaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concoursRattacheDTO)))
            .andExpect(status().isOk());

        // Validate the ConcoursRattache in the database
        List<ConcoursRattache> concoursRattacheList = concoursRattacheRepository.findAll();
        assertThat(concoursRattacheList).hasSize(databaseSizeBeforeUpdate);
        ConcoursRattache testConcoursRattache = concoursRattacheList.get(concoursRattacheList.size() - 1);
        assertThat(testConcoursRattache.getLibelleConcoursRattache()).isEqualTo(UPDATED_LIBELLE_CONCOURS_RATTACHE);
    }

    @Test
    @Transactional
    public void updateNonExistingConcoursRattache() throws Exception {
        int databaseSizeBeforeUpdate = concoursRattacheRepository.findAll().size();

        // Create the ConcoursRattache
        ConcoursRattacheDTO concoursRattacheDTO = concoursRattacheMapper.toDto(concoursRattache);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcoursRattacheMockMvc.perform(put("/api/concours-rattaches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concoursRattacheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConcoursRattache in the database
        List<ConcoursRattache> concoursRattacheList = concoursRattacheRepository.findAll();
        assertThat(concoursRattacheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConcoursRattache() throws Exception {
        // Initialize the database
        concoursRattacheRepository.saveAndFlush(concoursRattache);

        int databaseSizeBeforeDelete = concoursRattacheRepository.findAll().size();

        // Delete the concoursRattache
        restConcoursRattacheMockMvc.perform(delete("/api/concours-rattaches/{id}", concoursRattache.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConcoursRattache> concoursRattacheList = concoursRattacheRepository.findAll();
        assertThat(concoursRattacheList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConcoursRattache.class);
        ConcoursRattache concoursRattache1 = new ConcoursRattache();
        concoursRattache1.setId(1L);
        ConcoursRattache concoursRattache2 = new ConcoursRattache();
        concoursRattache2.setId(concoursRattache1.getId());
        assertThat(concoursRattache1).isEqualTo(concoursRattache2);
        concoursRattache2.setId(2L);
        assertThat(concoursRattache1).isNotEqualTo(concoursRattache2);
        concoursRattache1.setId(null);
        assertThat(concoursRattache1).isNotEqualTo(concoursRattache2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConcoursRattacheDTO.class);
        ConcoursRattacheDTO concoursRattacheDTO1 = new ConcoursRattacheDTO();
        concoursRattacheDTO1.setId(1L);
        ConcoursRattacheDTO concoursRattacheDTO2 = new ConcoursRattacheDTO();
        assertThat(concoursRattacheDTO1).isNotEqualTo(concoursRattacheDTO2);
        concoursRattacheDTO2.setId(concoursRattacheDTO1.getId());
        assertThat(concoursRattacheDTO1).isEqualTo(concoursRattacheDTO2);
        concoursRattacheDTO2.setId(2L);
        assertThat(concoursRattacheDTO1).isNotEqualTo(concoursRattacheDTO2);
        concoursRattacheDTO1.setId(null);
        assertThat(concoursRattacheDTO1).isNotEqualTo(concoursRattacheDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(concoursRattacheMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(concoursRattacheMapper.fromId(null)).isNull();
    }
}
