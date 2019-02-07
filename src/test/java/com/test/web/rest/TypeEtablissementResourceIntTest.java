package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.TypeEtablissement;
import com.test.repository.TypeEtablissementRepository;
import com.test.service.TypeEtablissementService;
import com.test.service.dto.TypeEtablissementDTO;
import com.test.service.mapper.TypeEtablissementMapper;
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
 * Test class for the TypeEtablissementResource REST controller.
 *
 * @see TypeEtablissementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class TypeEtablissementResourceIntTest {

    private static final String DEFAULT_LIBELLE_TYPE_ETABLISSEMENT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_ETABLISSEMENT = "BBBBBBBBBB";

    @Autowired
    private TypeEtablissementRepository typeEtablissementRepository;

    @Autowired
    private TypeEtablissementMapper typeEtablissementMapper;

    @Autowired
    private TypeEtablissementService typeEtablissementService;

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

    private MockMvc restTypeEtablissementMockMvc;

    private TypeEtablissement typeEtablissement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeEtablissementResource typeEtablissementResource = new TypeEtablissementResource(typeEtablissementService);
        this.restTypeEtablissementMockMvc = MockMvcBuilders.standaloneSetup(typeEtablissementResource)
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
    public static TypeEtablissement createEntity(EntityManager em) {
        TypeEtablissement typeEtablissement = new TypeEtablissement()
            .libelleTypeEtablissement(DEFAULT_LIBELLE_TYPE_ETABLISSEMENT);
        return typeEtablissement;
    }

    @Before
    public void initTest() {
        typeEtablissement = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeEtablissement() throws Exception {
        int databaseSizeBeforeCreate = typeEtablissementRepository.findAll().size();

        // Create the TypeEtablissement
        TypeEtablissementDTO typeEtablissementDTO = typeEtablissementMapper.toDto(typeEtablissement);
        restTypeEtablissementMockMvc.perform(post("/api/type-etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeEtablissementDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeEtablissement in the database
        List<TypeEtablissement> typeEtablissementList = typeEtablissementRepository.findAll();
        assertThat(typeEtablissementList).hasSize(databaseSizeBeforeCreate + 1);
        TypeEtablissement testTypeEtablissement = typeEtablissementList.get(typeEtablissementList.size() - 1);
        assertThat(testTypeEtablissement.getLibelleTypeEtablissement()).isEqualTo(DEFAULT_LIBELLE_TYPE_ETABLISSEMENT);
    }

    @Test
    @Transactional
    public void createTypeEtablissementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeEtablissementRepository.findAll().size();

        // Create the TypeEtablissement with an existing ID
        typeEtablissement.setId(1L);
        TypeEtablissementDTO typeEtablissementDTO = typeEtablissementMapper.toDto(typeEtablissement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeEtablissementMockMvc.perform(post("/api/type-etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeEtablissementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeEtablissement in the database
        List<TypeEtablissement> typeEtablissementList = typeEtablissementRepository.findAll();
        assertThat(typeEtablissementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleTypeEtablissementIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeEtablissementRepository.findAll().size();
        // set the field null
        typeEtablissement.setLibelleTypeEtablissement(null);

        // Create the TypeEtablissement, which fails.
        TypeEtablissementDTO typeEtablissementDTO = typeEtablissementMapper.toDto(typeEtablissement);

        restTypeEtablissementMockMvc.perform(post("/api/type-etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeEtablissementDTO)))
            .andExpect(status().isBadRequest());

        List<TypeEtablissement> typeEtablissementList = typeEtablissementRepository.findAll();
        assertThat(typeEtablissementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeEtablissements() throws Exception {
        // Initialize the database
        typeEtablissementRepository.saveAndFlush(typeEtablissement);

        // Get all the typeEtablissementList
        restTypeEtablissementMockMvc.perform(get("/api/type-etablissements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeEtablissement.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleTypeEtablissement").value(hasItem(DEFAULT_LIBELLE_TYPE_ETABLISSEMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeEtablissement() throws Exception {
        // Initialize the database
        typeEtablissementRepository.saveAndFlush(typeEtablissement);

        // Get the typeEtablissement
        restTypeEtablissementMockMvc.perform(get("/api/type-etablissements/{id}", typeEtablissement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeEtablissement.getId().intValue()))
            .andExpect(jsonPath("$.libelleTypeEtablissement").value(DEFAULT_LIBELLE_TYPE_ETABLISSEMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeEtablissement() throws Exception {
        // Get the typeEtablissement
        restTypeEtablissementMockMvc.perform(get("/api/type-etablissements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeEtablissement() throws Exception {
        // Initialize the database
        typeEtablissementRepository.saveAndFlush(typeEtablissement);

        int databaseSizeBeforeUpdate = typeEtablissementRepository.findAll().size();

        // Update the typeEtablissement
        TypeEtablissement updatedTypeEtablissement = typeEtablissementRepository.findById(typeEtablissement.getId()).get();
        // Disconnect from session so that the updates on updatedTypeEtablissement are not directly saved in db
        em.detach(updatedTypeEtablissement);
        updatedTypeEtablissement
            .libelleTypeEtablissement(UPDATED_LIBELLE_TYPE_ETABLISSEMENT);
        TypeEtablissementDTO typeEtablissementDTO = typeEtablissementMapper.toDto(updatedTypeEtablissement);

        restTypeEtablissementMockMvc.perform(put("/api/type-etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeEtablissementDTO)))
            .andExpect(status().isOk());

        // Validate the TypeEtablissement in the database
        List<TypeEtablissement> typeEtablissementList = typeEtablissementRepository.findAll();
        assertThat(typeEtablissementList).hasSize(databaseSizeBeforeUpdate);
        TypeEtablissement testTypeEtablissement = typeEtablissementList.get(typeEtablissementList.size() - 1);
        assertThat(testTypeEtablissement.getLibelleTypeEtablissement()).isEqualTo(UPDATED_LIBELLE_TYPE_ETABLISSEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeEtablissement() throws Exception {
        int databaseSizeBeforeUpdate = typeEtablissementRepository.findAll().size();

        // Create the TypeEtablissement
        TypeEtablissementDTO typeEtablissementDTO = typeEtablissementMapper.toDto(typeEtablissement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeEtablissementMockMvc.perform(put("/api/type-etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeEtablissementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeEtablissement in the database
        List<TypeEtablissement> typeEtablissementList = typeEtablissementRepository.findAll();
        assertThat(typeEtablissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeEtablissement() throws Exception {
        // Initialize the database
        typeEtablissementRepository.saveAndFlush(typeEtablissement);

        int databaseSizeBeforeDelete = typeEtablissementRepository.findAll().size();

        // Delete the typeEtablissement
        restTypeEtablissementMockMvc.perform(delete("/api/type-etablissements/{id}", typeEtablissement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeEtablissement> typeEtablissementList = typeEtablissementRepository.findAll();
        assertThat(typeEtablissementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeEtablissement.class);
        TypeEtablissement typeEtablissement1 = new TypeEtablissement();
        typeEtablissement1.setId(1L);
        TypeEtablissement typeEtablissement2 = new TypeEtablissement();
        typeEtablissement2.setId(typeEtablissement1.getId());
        assertThat(typeEtablissement1).isEqualTo(typeEtablissement2);
        typeEtablissement2.setId(2L);
        assertThat(typeEtablissement1).isNotEqualTo(typeEtablissement2);
        typeEtablissement1.setId(null);
        assertThat(typeEtablissement1).isNotEqualTo(typeEtablissement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeEtablissementDTO.class);
        TypeEtablissementDTO typeEtablissementDTO1 = new TypeEtablissementDTO();
        typeEtablissementDTO1.setId(1L);
        TypeEtablissementDTO typeEtablissementDTO2 = new TypeEtablissementDTO();
        assertThat(typeEtablissementDTO1).isNotEqualTo(typeEtablissementDTO2);
        typeEtablissementDTO2.setId(typeEtablissementDTO1.getId());
        assertThat(typeEtablissementDTO1).isEqualTo(typeEtablissementDTO2);
        typeEtablissementDTO2.setId(2L);
        assertThat(typeEtablissementDTO1).isNotEqualTo(typeEtablissementDTO2);
        typeEtablissementDTO1.setId(null);
        assertThat(typeEtablissementDTO1).isNotEqualTo(typeEtablissementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeEtablissementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeEtablissementMapper.fromId(null)).isNull();
    }
}
