package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Ceb;
import com.test.repository.CebRepository;
import com.test.service.CebService;
import com.test.service.dto.CebDTO;
import com.test.service.mapper.CebMapper;
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

import com.test.domain.enumeration.TypeCeb;
/**
 * Test class for the CebResource REST controller.
 *
 * @see CebResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class CebResourceIntTest {

    private static final String DEFAULT_CODE_CEB = "AAAAAAAAAA";
    private static final String UPDATED_CODE_CEB = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_CEB = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CEB = "BBBBBBBBBB";

    private static final TypeCeb DEFAULT_TYPE_CEB = TypeCeb.CEBREFORME;
    private static final TypeCeb UPDATED_TYPE_CEB = TypeCeb.CEBNONREFORME;

    @Autowired
    private CebRepository cebRepository;

    @Autowired
    private CebMapper cebMapper;

    @Autowired
    private CebService cebService;

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

    private MockMvc restCebMockMvc;

    private Ceb ceb;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CebResource cebResource = new CebResource(cebService);
        this.restCebMockMvc = MockMvcBuilders.standaloneSetup(cebResource)
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
    public static Ceb createEntity(EntityManager em) {
        Ceb ceb = new Ceb()
            .codeCeb(DEFAULT_CODE_CEB)
            .libelleCeb(DEFAULT_LIBELLE_CEB)
            .typeCeb(DEFAULT_TYPE_CEB);
        return ceb;
    }

    @Before
    public void initTest() {
        ceb = createEntity(em);
    }

    @Test
    @Transactional
    public void createCeb() throws Exception {
        int databaseSizeBeforeCreate = cebRepository.findAll().size();

        // Create the Ceb
        CebDTO cebDTO = cebMapper.toDto(ceb);
        restCebMockMvc.perform(post("/api/cebs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cebDTO)))
            .andExpect(status().isCreated());

        // Validate the Ceb in the database
        List<Ceb> cebList = cebRepository.findAll();
        assertThat(cebList).hasSize(databaseSizeBeforeCreate + 1);
        Ceb testCeb = cebList.get(cebList.size() - 1);
        assertThat(testCeb.getCodeCeb()).isEqualTo(DEFAULT_CODE_CEB);
        assertThat(testCeb.getLibelleCeb()).isEqualTo(DEFAULT_LIBELLE_CEB);
        assertThat(testCeb.getTypeCeb()).isEqualTo(DEFAULT_TYPE_CEB);
    }

    @Test
    @Transactional
    public void createCebWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cebRepository.findAll().size();

        // Create the Ceb with an existing ID
        ceb.setId(1L);
        CebDTO cebDTO = cebMapper.toDto(ceb);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCebMockMvc.perform(post("/api/cebs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cebDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ceb in the database
        List<Ceb> cebList = cebRepository.findAll();
        assertThat(cebList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeCebIsRequired() throws Exception {
        int databaseSizeBeforeTest = cebRepository.findAll().size();
        // set the field null
        ceb.setCodeCeb(null);

        // Create the Ceb, which fails.
        CebDTO cebDTO = cebMapper.toDto(ceb);

        restCebMockMvc.perform(post("/api/cebs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cebDTO)))
            .andExpect(status().isBadRequest());

        List<Ceb> cebList = cebRepository.findAll();
        assertThat(cebList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleCebIsRequired() throws Exception {
        int databaseSizeBeforeTest = cebRepository.findAll().size();
        // set the field null
        ceb.setLibelleCeb(null);

        // Create the Ceb, which fails.
        CebDTO cebDTO = cebMapper.toDto(ceb);

        restCebMockMvc.perform(post("/api/cebs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cebDTO)))
            .andExpect(status().isBadRequest());

        List<Ceb> cebList = cebRepository.findAll();
        assertThat(cebList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeCebIsRequired() throws Exception {
        int databaseSizeBeforeTest = cebRepository.findAll().size();
        // set the field null
        ceb.setTypeCeb(null);

        // Create the Ceb, which fails.
        CebDTO cebDTO = cebMapper.toDto(ceb);

        restCebMockMvc.perform(post("/api/cebs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cebDTO)))
            .andExpect(status().isBadRequest());

        List<Ceb> cebList = cebRepository.findAll();
        assertThat(cebList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCebs() throws Exception {
        // Initialize the database
        cebRepository.saveAndFlush(ceb);

        // Get all the cebList
        restCebMockMvc.perform(get("/api/cebs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ceb.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeCeb").value(hasItem(DEFAULT_CODE_CEB.toString())))
            .andExpect(jsonPath("$.[*].libelleCeb").value(hasItem(DEFAULT_LIBELLE_CEB.toString())))
            .andExpect(jsonPath("$.[*].typeCeb").value(hasItem(DEFAULT_TYPE_CEB.toString())));
    }
    
    @Test
    @Transactional
    public void getCeb() throws Exception {
        // Initialize the database
        cebRepository.saveAndFlush(ceb);

        // Get the ceb
        restCebMockMvc.perform(get("/api/cebs/{id}", ceb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ceb.getId().intValue()))
            .andExpect(jsonPath("$.codeCeb").value(DEFAULT_CODE_CEB.toString()))
            .andExpect(jsonPath("$.libelleCeb").value(DEFAULT_LIBELLE_CEB.toString()))
            .andExpect(jsonPath("$.typeCeb").value(DEFAULT_TYPE_CEB.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCeb() throws Exception {
        // Get the ceb
        restCebMockMvc.perform(get("/api/cebs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCeb() throws Exception {
        // Initialize the database
        cebRepository.saveAndFlush(ceb);

        int databaseSizeBeforeUpdate = cebRepository.findAll().size();

        // Update the ceb
        Ceb updatedCeb = cebRepository.findById(ceb.getId()).get();
        // Disconnect from session so that the updates on updatedCeb are not directly saved in db
        em.detach(updatedCeb);
        updatedCeb
            .codeCeb(UPDATED_CODE_CEB)
            .libelleCeb(UPDATED_LIBELLE_CEB)
            .typeCeb(UPDATED_TYPE_CEB);
        CebDTO cebDTO = cebMapper.toDto(updatedCeb);

        restCebMockMvc.perform(put("/api/cebs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cebDTO)))
            .andExpect(status().isOk());

        // Validate the Ceb in the database
        List<Ceb> cebList = cebRepository.findAll();
        assertThat(cebList).hasSize(databaseSizeBeforeUpdate);
        Ceb testCeb = cebList.get(cebList.size() - 1);
        assertThat(testCeb.getCodeCeb()).isEqualTo(UPDATED_CODE_CEB);
        assertThat(testCeb.getLibelleCeb()).isEqualTo(UPDATED_LIBELLE_CEB);
        assertThat(testCeb.getTypeCeb()).isEqualTo(UPDATED_TYPE_CEB);
    }

    @Test
    @Transactional
    public void updateNonExistingCeb() throws Exception {
        int databaseSizeBeforeUpdate = cebRepository.findAll().size();

        // Create the Ceb
        CebDTO cebDTO = cebMapper.toDto(ceb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCebMockMvc.perform(put("/api/cebs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cebDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ceb in the database
        List<Ceb> cebList = cebRepository.findAll();
        assertThat(cebList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCeb() throws Exception {
        // Initialize the database
        cebRepository.saveAndFlush(ceb);

        int databaseSizeBeforeDelete = cebRepository.findAll().size();

        // Delete the ceb
        restCebMockMvc.perform(delete("/api/cebs/{id}", ceb.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ceb> cebList = cebRepository.findAll();
        assertThat(cebList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ceb.class);
        Ceb ceb1 = new Ceb();
        ceb1.setId(1L);
        Ceb ceb2 = new Ceb();
        ceb2.setId(ceb1.getId());
        assertThat(ceb1).isEqualTo(ceb2);
        ceb2.setId(2L);
        assertThat(ceb1).isNotEqualTo(ceb2);
        ceb1.setId(null);
        assertThat(ceb1).isNotEqualTo(ceb2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CebDTO.class);
        CebDTO cebDTO1 = new CebDTO();
        cebDTO1.setId(1L);
        CebDTO cebDTO2 = new CebDTO();
        assertThat(cebDTO1).isNotEqualTo(cebDTO2);
        cebDTO2.setId(cebDTO1.getId());
        assertThat(cebDTO1).isEqualTo(cebDTO2);
        cebDTO2.setId(2L);
        assertThat(cebDTO1).isNotEqualTo(cebDTO2);
        cebDTO1.setId(null);
        assertThat(cebDTO1).isNotEqualTo(cebDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cebMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cebMapper.fromId(null)).isNull();
    }
}
