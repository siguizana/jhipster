package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.TypeDecision;
import com.test.repository.TypeDecisionRepository;
import com.test.service.TypeDecisionService;
import com.test.service.dto.TypeDecisionDTO;
import com.test.service.mapper.TypeDecisionMapper;
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
 * Test class for the TypeDecisionResource REST controller.
 *
 * @see TypeDecisionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class TypeDecisionResourceIntTest {

    private static final String DEFAULT_LIBELLE_TYPE_DECISION = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_DECISION = "BBBBBBBBBB";

    @Autowired
    private TypeDecisionRepository typeDecisionRepository;

    @Autowired
    private TypeDecisionMapper typeDecisionMapper;

    @Autowired
    private TypeDecisionService typeDecisionService;

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

    private MockMvc restTypeDecisionMockMvc;

    private TypeDecision typeDecision;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeDecisionResource typeDecisionResource = new TypeDecisionResource(typeDecisionService);
        this.restTypeDecisionMockMvc = MockMvcBuilders.standaloneSetup(typeDecisionResource)
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
    public static TypeDecision createEntity(EntityManager em) {
        TypeDecision typeDecision = new TypeDecision()
            .libelleTypeDecision(DEFAULT_LIBELLE_TYPE_DECISION);
        return typeDecision;
    }

    @Before
    public void initTest() {
        typeDecision = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeDecision() throws Exception {
        int databaseSizeBeforeCreate = typeDecisionRepository.findAll().size();

        // Create the TypeDecision
        TypeDecisionDTO typeDecisionDTO = typeDecisionMapper.toDto(typeDecision);
        restTypeDecisionMockMvc.perform(post("/api/type-decisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDecisionDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeDecision in the database
        List<TypeDecision> typeDecisionList = typeDecisionRepository.findAll();
        assertThat(typeDecisionList).hasSize(databaseSizeBeforeCreate + 1);
        TypeDecision testTypeDecision = typeDecisionList.get(typeDecisionList.size() - 1);
        assertThat(testTypeDecision.getLibelleTypeDecision()).isEqualTo(DEFAULT_LIBELLE_TYPE_DECISION);
    }

    @Test
    @Transactional
    public void createTypeDecisionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeDecisionRepository.findAll().size();

        // Create the TypeDecision with an existing ID
        typeDecision.setId(1L);
        TypeDecisionDTO typeDecisionDTO = typeDecisionMapper.toDto(typeDecision);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeDecisionMockMvc.perform(post("/api/type-decisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDecisionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeDecision in the database
        List<TypeDecision> typeDecisionList = typeDecisionRepository.findAll();
        assertThat(typeDecisionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleTypeDecisionIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeDecisionRepository.findAll().size();
        // set the field null
        typeDecision.setLibelleTypeDecision(null);

        // Create the TypeDecision, which fails.
        TypeDecisionDTO typeDecisionDTO = typeDecisionMapper.toDto(typeDecision);

        restTypeDecisionMockMvc.perform(post("/api/type-decisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDecisionDTO)))
            .andExpect(status().isBadRequest());

        List<TypeDecision> typeDecisionList = typeDecisionRepository.findAll();
        assertThat(typeDecisionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeDecisions() throws Exception {
        // Initialize the database
        typeDecisionRepository.saveAndFlush(typeDecision);

        // Get all the typeDecisionList
        restTypeDecisionMockMvc.perform(get("/api/type-decisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeDecision.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleTypeDecision").value(hasItem(DEFAULT_LIBELLE_TYPE_DECISION.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeDecision() throws Exception {
        // Initialize the database
        typeDecisionRepository.saveAndFlush(typeDecision);

        // Get the typeDecision
        restTypeDecisionMockMvc.perform(get("/api/type-decisions/{id}", typeDecision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeDecision.getId().intValue()))
            .andExpect(jsonPath("$.libelleTypeDecision").value(DEFAULT_LIBELLE_TYPE_DECISION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeDecision() throws Exception {
        // Get the typeDecision
        restTypeDecisionMockMvc.perform(get("/api/type-decisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeDecision() throws Exception {
        // Initialize the database
        typeDecisionRepository.saveAndFlush(typeDecision);

        int databaseSizeBeforeUpdate = typeDecisionRepository.findAll().size();

        // Update the typeDecision
        TypeDecision updatedTypeDecision = typeDecisionRepository.findById(typeDecision.getId()).get();
        // Disconnect from session so that the updates on updatedTypeDecision are not directly saved in db
        em.detach(updatedTypeDecision);
        updatedTypeDecision
            .libelleTypeDecision(UPDATED_LIBELLE_TYPE_DECISION);
        TypeDecisionDTO typeDecisionDTO = typeDecisionMapper.toDto(updatedTypeDecision);

        restTypeDecisionMockMvc.perform(put("/api/type-decisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDecisionDTO)))
            .andExpect(status().isOk());

        // Validate the TypeDecision in the database
        List<TypeDecision> typeDecisionList = typeDecisionRepository.findAll();
        assertThat(typeDecisionList).hasSize(databaseSizeBeforeUpdate);
        TypeDecision testTypeDecision = typeDecisionList.get(typeDecisionList.size() - 1);
        assertThat(testTypeDecision.getLibelleTypeDecision()).isEqualTo(UPDATED_LIBELLE_TYPE_DECISION);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeDecision() throws Exception {
        int databaseSizeBeforeUpdate = typeDecisionRepository.findAll().size();

        // Create the TypeDecision
        TypeDecisionDTO typeDecisionDTO = typeDecisionMapper.toDto(typeDecision);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeDecisionMockMvc.perform(put("/api/type-decisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDecisionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeDecision in the database
        List<TypeDecision> typeDecisionList = typeDecisionRepository.findAll();
        assertThat(typeDecisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeDecision() throws Exception {
        // Initialize the database
        typeDecisionRepository.saveAndFlush(typeDecision);

        int databaseSizeBeforeDelete = typeDecisionRepository.findAll().size();

        // Delete the typeDecision
        restTypeDecisionMockMvc.perform(delete("/api/type-decisions/{id}", typeDecision.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeDecision> typeDecisionList = typeDecisionRepository.findAll();
        assertThat(typeDecisionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDecision.class);
        TypeDecision typeDecision1 = new TypeDecision();
        typeDecision1.setId(1L);
        TypeDecision typeDecision2 = new TypeDecision();
        typeDecision2.setId(typeDecision1.getId());
        assertThat(typeDecision1).isEqualTo(typeDecision2);
        typeDecision2.setId(2L);
        assertThat(typeDecision1).isNotEqualTo(typeDecision2);
        typeDecision1.setId(null);
        assertThat(typeDecision1).isNotEqualTo(typeDecision2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDecisionDTO.class);
        TypeDecisionDTO typeDecisionDTO1 = new TypeDecisionDTO();
        typeDecisionDTO1.setId(1L);
        TypeDecisionDTO typeDecisionDTO2 = new TypeDecisionDTO();
        assertThat(typeDecisionDTO1).isNotEqualTo(typeDecisionDTO2);
        typeDecisionDTO2.setId(typeDecisionDTO1.getId());
        assertThat(typeDecisionDTO1).isEqualTo(typeDecisionDTO2);
        typeDecisionDTO2.setId(2L);
        assertThat(typeDecisionDTO1).isNotEqualTo(typeDecisionDTO2);
        typeDecisionDTO1.setId(null);
        assertThat(typeDecisionDTO1).isNotEqualTo(typeDecisionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeDecisionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeDecisionMapper.fromId(null)).isNull();
    }
}
