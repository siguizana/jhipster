package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.TypeExamen;
import com.test.repository.TypeExamenRepository;
import com.test.service.TypeExamenService;
import com.test.service.dto.TypeExamenDTO;
import com.test.service.mapper.TypeExamenMapper;
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
 * Test class for the TypeExamenResource REST controller.
 *
 * @see TypeExamenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class TypeExamenResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeExamenRepository typeExamenRepository;

    @Autowired
    private TypeExamenMapper typeExamenMapper;

    @Autowired
    private TypeExamenService typeExamenService;

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

    private MockMvc restTypeExamenMockMvc;

    private TypeExamen typeExamen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeExamenResource typeExamenResource = new TypeExamenResource(typeExamenService);
        this.restTypeExamenMockMvc = MockMvcBuilders.standaloneSetup(typeExamenResource)
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
    public static TypeExamen createEntity(EntityManager em) {
        TypeExamen typeExamen = new TypeExamen()
            .libelle(DEFAULT_LIBELLE);
        return typeExamen;
    }

    @Before
    public void initTest() {
        typeExamen = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeExamen() throws Exception {
        int databaseSizeBeforeCreate = typeExamenRepository.findAll().size();

        // Create the TypeExamen
        TypeExamenDTO typeExamenDTO = typeExamenMapper.toDto(typeExamen);
        restTypeExamenMockMvc.perform(post("/api/type-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeExamenDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeExamen in the database
        List<TypeExamen> typeExamenList = typeExamenRepository.findAll();
        assertThat(typeExamenList).hasSize(databaseSizeBeforeCreate + 1);
        TypeExamen testTypeExamen = typeExamenList.get(typeExamenList.size() - 1);
        assertThat(testTypeExamen.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypeExamenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeExamenRepository.findAll().size();

        // Create the TypeExamen with an existing ID
        typeExamen.setId(1L);
        TypeExamenDTO typeExamenDTO = typeExamenMapper.toDto(typeExamen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeExamenMockMvc.perform(post("/api/type-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeExamenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeExamen in the database
        List<TypeExamen> typeExamenList = typeExamenRepository.findAll();
        assertThat(typeExamenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeExamenRepository.findAll().size();
        // set the field null
        typeExamen.setLibelle(null);

        // Create the TypeExamen, which fails.
        TypeExamenDTO typeExamenDTO = typeExamenMapper.toDto(typeExamen);

        restTypeExamenMockMvc.perform(post("/api/type-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeExamenDTO)))
            .andExpect(status().isBadRequest());

        List<TypeExamen> typeExamenList = typeExamenRepository.findAll();
        assertThat(typeExamenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeExamen() throws Exception {
        // Initialize the database
        typeExamenRepository.saveAndFlush(typeExamen);

        // Get all the typeExamenList
        restTypeExamenMockMvc.perform(get("/api/type-examen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeExamen.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeExamen() throws Exception {
        // Initialize the database
        typeExamenRepository.saveAndFlush(typeExamen);

        // Get the typeExamen
        restTypeExamenMockMvc.perform(get("/api/type-examen/{id}", typeExamen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeExamen.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeExamen() throws Exception {
        // Get the typeExamen
        restTypeExamenMockMvc.perform(get("/api/type-examen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeExamen() throws Exception {
        // Initialize the database
        typeExamenRepository.saveAndFlush(typeExamen);

        int databaseSizeBeforeUpdate = typeExamenRepository.findAll().size();

        // Update the typeExamen
        TypeExamen updatedTypeExamen = typeExamenRepository.findById(typeExamen.getId()).get();
        // Disconnect from session so that the updates on updatedTypeExamen are not directly saved in db
        em.detach(updatedTypeExamen);
        updatedTypeExamen
            .libelle(UPDATED_LIBELLE);
        TypeExamenDTO typeExamenDTO = typeExamenMapper.toDto(updatedTypeExamen);

        restTypeExamenMockMvc.perform(put("/api/type-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeExamenDTO)))
            .andExpect(status().isOk());

        // Validate the TypeExamen in the database
        List<TypeExamen> typeExamenList = typeExamenRepository.findAll();
        assertThat(typeExamenList).hasSize(databaseSizeBeforeUpdate);
        TypeExamen testTypeExamen = typeExamenList.get(typeExamenList.size() - 1);
        assertThat(testTypeExamen.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeExamen() throws Exception {
        int databaseSizeBeforeUpdate = typeExamenRepository.findAll().size();

        // Create the TypeExamen
        TypeExamenDTO typeExamenDTO = typeExamenMapper.toDto(typeExamen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeExamenMockMvc.perform(put("/api/type-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeExamenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeExamen in the database
        List<TypeExamen> typeExamenList = typeExamenRepository.findAll();
        assertThat(typeExamenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeExamen() throws Exception {
        // Initialize the database
        typeExamenRepository.saveAndFlush(typeExamen);

        int databaseSizeBeforeDelete = typeExamenRepository.findAll().size();

        // Delete the typeExamen
        restTypeExamenMockMvc.perform(delete("/api/type-examen/{id}", typeExamen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeExamen> typeExamenList = typeExamenRepository.findAll();
        assertThat(typeExamenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeExamen.class);
        TypeExamen typeExamen1 = new TypeExamen();
        typeExamen1.setId(1L);
        TypeExamen typeExamen2 = new TypeExamen();
        typeExamen2.setId(typeExamen1.getId());
        assertThat(typeExamen1).isEqualTo(typeExamen2);
        typeExamen2.setId(2L);
        assertThat(typeExamen1).isNotEqualTo(typeExamen2);
        typeExamen1.setId(null);
        assertThat(typeExamen1).isNotEqualTo(typeExamen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeExamenDTO.class);
        TypeExamenDTO typeExamenDTO1 = new TypeExamenDTO();
        typeExamenDTO1.setId(1L);
        TypeExamenDTO typeExamenDTO2 = new TypeExamenDTO();
        assertThat(typeExamenDTO1).isNotEqualTo(typeExamenDTO2);
        typeExamenDTO2.setId(typeExamenDTO1.getId());
        assertThat(typeExamenDTO1).isEqualTo(typeExamenDTO2);
        typeExamenDTO2.setId(2L);
        assertThat(typeExamenDTO1).isNotEqualTo(typeExamenDTO2);
        typeExamenDTO1.setId(null);
        assertThat(typeExamenDTO1).isNotEqualTo(typeExamenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeExamenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeExamenMapper.fromId(null)).isNull();
    }
}
