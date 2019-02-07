package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.TypeEpreuve;
import com.test.repository.TypeEpreuveRepository;
import com.test.service.TypeEpreuveService;
import com.test.service.dto.TypeEpreuveDTO;
import com.test.service.mapper.TypeEpreuveMapper;
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
 * Test class for the TypeEpreuveResource REST controller.
 *
 * @see TypeEpreuveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class TypeEpreuveResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeEpreuveRepository typeEpreuveRepository;

    @Autowired
    private TypeEpreuveMapper typeEpreuveMapper;

    @Autowired
    private TypeEpreuveService typeEpreuveService;

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

    private MockMvc restTypeEpreuveMockMvc;

    private TypeEpreuve typeEpreuve;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeEpreuveResource typeEpreuveResource = new TypeEpreuveResource(typeEpreuveService);
        this.restTypeEpreuveMockMvc = MockMvcBuilders.standaloneSetup(typeEpreuveResource)
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
    public static TypeEpreuve createEntity(EntityManager em) {
        TypeEpreuve typeEpreuve = new TypeEpreuve()
            .libelle(DEFAULT_LIBELLE);
        return typeEpreuve;
    }

    @Before
    public void initTest() {
        typeEpreuve = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeEpreuve() throws Exception {
        int databaseSizeBeforeCreate = typeEpreuveRepository.findAll().size();

        // Create the TypeEpreuve
        TypeEpreuveDTO typeEpreuveDTO = typeEpreuveMapper.toDto(typeEpreuve);
        restTypeEpreuveMockMvc.perform(post("/api/type-epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeEpreuveDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeEpreuve in the database
        List<TypeEpreuve> typeEpreuveList = typeEpreuveRepository.findAll();
        assertThat(typeEpreuveList).hasSize(databaseSizeBeforeCreate + 1);
        TypeEpreuve testTypeEpreuve = typeEpreuveList.get(typeEpreuveList.size() - 1);
        assertThat(testTypeEpreuve.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypeEpreuveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeEpreuveRepository.findAll().size();

        // Create the TypeEpreuve with an existing ID
        typeEpreuve.setId(1L);
        TypeEpreuveDTO typeEpreuveDTO = typeEpreuveMapper.toDto(typeEpreuve);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeEpreuveMockMvc.perform(post("/api/type-epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeEpreuveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeEpreuve in the database
        List<TypeEpreuve> typeEpreuveList = typeEpreuveRepository.findAll();
        assertThat(typeEpreuveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeEpreuveRepository.findAll().size();
        // set the field null
        typeEpreuve.setLibelle(null);

        // Create the TypeEpreuve, which fails.
        TypeEpreuveDTO typeEpreuveDTO = typeEpreuveMapper.toDto(typeEpreuve);

        restTypeEpreuveMockMvc.perform(post("/api/type-epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeEpreuveDTO)))
            .andExpect(status().isBadRequest());

        List<TypeEpreuve> typeEpreuveList = typeEpreuveRepository.findAll();
        assertThat(typeEpreuveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeEpreuves() throws Exception {
        // Initialize the database
        typeEpreuveRepository.saveAndFlush(typeEpreuve);

        // Get all the typeEpreuveList
        restTypeEpreuveMockMvc.perform(get("/api/type-epreuves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeEpreuve.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeEpreuve() throws Exception {
        // Initialize the database
        typeEpreuveRepository.saveAndFlush(typeEpreuve);

        // Get the typeEpreuve
        restTypeEpreuveMockMvc.perform(get("/api/type-epreuves/{id}", typeEpreuve.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeEpreuve.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeEpreuve() throws Exception {
        // Get the typeEpreuve
        restTypeEpreuveMockMvc.perform(get("/api/type-epreuves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeEpreuve() throws Exception {
        // Initialize the database
        typeEpreuveRepository.saveAndFlush(typeEpreuve);

        int databaseSizeBeforeUpdate = typeEpreuveRepository.findAll().size();

        // Update the typeEpreuve
        TypeEpreuve updatedTypeEpreuve = typeEpreuveRepository.findById(typeEpreuve.getId()).get();
        // Disconnect from session so that the updates on updatedTypeEpreuve are not directly saved in db
        em.detach(updatedTypeEpreuve);
        updatedTypeEpreuve
            .libelle(UPDATED_LIBELLE);
        TypeEpreuveDTO typeEpreuveDTO = typeEpreuveMapper.toDto(updatedTypeEpreuve);

        restTypeEpreuveMockMvc.perform(put("/api/type-epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeEpreuveDTO)))
            .andExpect(status().isOk());

        // Validate the TypeEpreuve in the database
        List<TypeEpreuve> typeEpreuveList = typeEpreuveRepository.findAll();
        assertThat(typeEpreuveList).hasSize(databaseSizeBeforeUpdate);
        TypeEpreuve testTypeEpreuve = typeEpreuveList.get(typeEpreuveList.size() - 1);
        assertThat(testTypeEpreuve.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeEpreuve() throws Exception {
        int databaseSizeBeforeUpdate = typeEpreuveRepository.findAll().size();

        // Create the TypeEpreuve
        TypeEpreuveDTO typeEpreuveDTO = typeEpreuveMapper.toDto(typeEpreuve);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeEpreuveMockMvc.perform(put("/api/type-epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeEpreuveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeEpreuve in the database
        List<TypeEpreuve> typeEpreuveList = typeEpreuveRepository.findAll();
        assertThat(typeEpreuveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeEpreuve() throws Exception {
        // Initialize the database
        typeEpreuveRepository.saveAndFlush(typeEpreuve);

        int databaseSizeBeforeDelete = typeEpreuveRepository.findAll().size();

        // Delete the typeEpreuve
        restTypeEpreuveMockMvc.perform(delete("/api/type-epreuves/{id}", typeEpreuve.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeEpreuve> typeEpreuveList = typeEpreuveRepository.findAll();
        assertThat(typeEpreuveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeEpreuve.class);
        TypeEpreuve typeEpreuve1 = new TypeEpreuve();
        typeEpreuve1.setId(1L);
        TypeEpreuve typeEpreuve2 = new TypeEpreuve();
        typeEpreuve2.setId(typeEpreuve1.getId());
        assertThat(typeEpreuve1).isEqualTo(typeEpreuve2);
        typeEpreuve2.setId(2L);
        assertThat(typeEpreuve1).isNotEqualTo(typeEpreuve2);
        typeEpreuve1.setId(null);
        assertThat(typeEpreuve1).isNotEqualTo(typeEpreuve2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeEpreuveDTO.class);
        TypeEpreuveDTO typeEpreuveDTO1 = new TypeEpreuveDTO();
        typeEpreuveDTO1.setId(1L);
        TypeEpreuveDTO typeEpreuveDTO2 = new TypeEpreuveDTO();
        assertThat(typeEpreuveDTO1).isNotEqualTo(typeEpreuveDTO2);
        typeEpreuveDTO2.setId(typeEpreuveDTO1.getId());
        assertThat(typeEpreuveDTO1).isEqualTo(typeEpreuveDTO2);
        typeEpreuveDTO2.setId(2L);
        assertThat(typeEpreuveDTO1).isNotEqualTo(typeEpreuveDTO2);
        typeEpreuveDTO1.setId(null);
        assertThat(typeEpreuveDTO1).isNotEqualTo(typeEpreuveDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeEpreuveMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeEpreuveMapper.fromId(null)).isNull();
    }
}
