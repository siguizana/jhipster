package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.TypeCritere;
import com.test.repository.TypeCritereRepository;
import com.test.service.TypeCritereService;
import com.test.service.dto.TypeCritereDTO;
import com.test.service.mapper.TypeCritereMapper;
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
 * Test class for the TypeCritereResource REST controller.
 *
 * @see TypeCritereResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class TypeCritereResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeCritereRepository typeCritereRepository;

    @Autowired
    private TypeCritereMapper typeCritereMapper;

    @Autowired
    private TypeCritereService typeCritereService;

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

    private MockMvc restTypeCritereMockMvc;

    private TypeCritere typeCritere;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeCritereResource typeCritereResource = new TypeCritereResource(typeCritereService);
        this.restTypeCritereMockMvc = MockMvcBuilders.standaloneSetup(typeCritereResource)
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
    public static TypeCritere createEntity(EntityManager em) {
        TypeCritere typeCritere = new TypeCritere()
            .libelle(DEFAULT_LIBELLE);
        return typeCritere;
    }

    @Before
    public void initTest() {
        typeCritere = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeCritere() throws Exception {
        int databaseSizeBeforeCreate = typeCritereRepository.findAll().size();

        // Create the TypeCritere
        TypeCritereDTO typeCritereDTO = typeCritereMapper.toDto(typeCritere);
        restTypeCritereMockMvc.perform(post("/api/type-criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCritereDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeCritere in the database
        List<TypeCritere> typeCritereList = typeCritereRepository.findAll();
        assertThat(typeCritereList).hasSize(databaseSizeBeforeCreate + 1);
        TypeCritere testTypeCritere = typeCritereList.get(typeCritereList.size() - 1);
        assertThat(testTypeCritere.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypeCritereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeCritereRepository.findAll().size();

        // Create the TypeCritere with an existing ID
        typeCritere.setId(1L);
        TypeCritereDTO typeCritereDTO = typeCritereMapper.toDto(typeCritere);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeCritereMockMvc.perform(post("/api/type-criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCritereDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCritere in the database
        List<TypeCritere> typeCritereList = typeCritereRepository.findAll();
        assertThat(typeCritereList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeCritereRepository.findAll().size();
        // set the field null
        typeCritere.setLibelle(null);

        // Create the TypeCritere, which fails.
        TypeCritereDTO typeCritereDTO = typeCritereMapper.toDto(typeCritere);

        restTypeCritereMockMvc.perform(post("/api/type-criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCritereDTO)))
            .andExpect(status().isBadRequest());

        List<TypeCritere> typeCritereList = typeCritereRepository.findAll();
        assertThat(typeCritereList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeCriteres() throws Exception {
        // Initialize the database
        typeCritereRepository.saveAndFlush(typeCritere);

        // Get all the typeCritereList
        restTypeCritereMockMvc.perform(get("/api/type-criteres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeCritere.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeCritere() throws Exception {
        // Initialize the database
        typeCritereRepository.saveAndFlush(typeCritere);

        // Get the typeCritere
        restTypeCritereMockMvc.perform(get("/api/type-criteres/{id}", typeCritere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeCritere.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeCritere() throws Exception {
        // Get the typeCritere
        restTypeCritereMockMvc.perform(get("/api/type-criteres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeCritere() throws Exception {
        // Initialize the database
        typeCritereRepository.saveAndFlush(typeCritere);

        int databaseSizeBeforeUpdate = typeCritereRepository.findAll().size();

        // Update the typeCritere
        TypeCritere updatedTypeCritere = typeCritereRepository.findById(typeCritere.getId()).get();
        // Disconnect from session so that the updates on updatedTypeCritere are not directly saved in db
        em.detach(updatedTypeCritere);
        updatedTypeCritere
            .libelle(UPDATED_LIBELLE);
        TypeCritereDTO typeCritereDTO = typeCritereMapper.toDto(updatedTypeCritere);

        restTypeCritereMockMvc.perform(put("/api/type-criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCritereDTO)))
            .andExpect(status().isOk());

        // Validate the TypeCritere in the database
        List<TypeCritere> typeCritereList = typeCritereRepository.findAll();
        assertThat(typeCritereList).hasSize(databaseSizeBeforeUpdate);
        TypeCritere testTypeCritere = typeCritereList.get(typeCritereList.size() - 1);
        assertThat(testTypeCritere.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeCritere() throws Exception {
        int databaseSizeBeforeUpdate = typeCritereRepository.findAll().size();

        // Create the TypeCritere
        TypeCritereDTO typeCritereDTO = typeCritereMapper.toDto(typeCritere);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeCritereMockMvc.perform(put("/api/type-criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCritereDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCritere in the database
        List<TypeCritere> typeCritereList = typeCritereRepository.findAll();
        assertThat(typeCritereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeCritere() throws Exception {
        // Initialize the database
        typeCritereRepository.saveAndFlush(typeCritere);

        int databaseSizeBeforeDelete = typeCritereRepository.findAll().size();

        // Delete the typeCritere
        restTypeCritereMockMvc.perform(delete("/api/type-criteres/{id}", typeCritere.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeCritere> typeCritereList = typeCritereRepository.findAll();
        assertThat(typeCritereList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCritere.class);
        TypeCritere typeCritere1 = new TypeCritere();
        typeCritere1.setId(1L);
        TypeCritere typeCritere2 = new TypeCritere();
        typeCritere2.setId(typeCritere1.getId());
        assertThat(typeCritere1).isEqualTo(typeCritere2);
        typeCritere2.setId(2L);
        assertThat(typeCritere1).isNotEqualTo(typeCritere2);
        typeCritere1.setId(null);
        assertThat(typeCritere1).isNotEqualTo(typeCritere2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCritereDTO.class);
        TypeCritereDTO typeCritereDTO1 = new TypeCritereDTO();
        typeCritereDTO1.setId(1L);
        TypeCritereDTO typeCritereDTO2 = new TypeCritereDTO();
        assertThat(typeCritereDTO1).isNotEqualTo(typeCritereDTO2);
        typeCritereDTO2.setId(typeCritereDTO1.getId());
        assertThat(typeCritereDTO1).isEqualTo(typeCritereDTO2);
        typeCritereDTO2.setId(2L);
        assertThat(typeCritereDTO1).isNotEqualTo(typeCritereDTO2);
        typeCritereDTO1.setId(null);
        assertThat(typeCritereDTO1).isNotEqualTo(typeCritereDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeCritereMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeCritereMapper.fromId(null)).isNull();
    }
}
