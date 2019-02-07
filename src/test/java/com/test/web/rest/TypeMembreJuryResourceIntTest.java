package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.TypeMembreJury;
import com.test.repository.TypeMembreJuryRepository;
import com.test.service.TypeMembreJuryService;
import com.test.service.dto.TypeMembreJuryDTO;
import com.test.service.mapper.TypeMembreJuryMapper;
import com.test.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.test.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TypeMembreJuryResource REST controller.
 *
 * @see TypeMembreJuryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class TypeMembreJuryResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TypeMembreJuryRepository typeMembreJuryRepository;

    @Mock
    private TypeMembreJuryRepository typeMembreJuryRepositoryMock;

    @Autowired
    private TypeMembreJuryMapper typeMembreJuryMapper;

    @Mock
    private TypeMembreJuryService typeMembreJuryServiceMock;

    @Autowired
    private TypeMembreJuryService typeMembreJuryService;

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

    private MockMvc restTypeMembreJuryMockMvc;

    private TypeMembreJury typeMembreJury;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeMembreJuryResource typeMembreJuryResource = new TypeMembreJuryResource(typeMembreJuryService);
        this.restTypeMembreJuryMockMvc = MockMvcBuilders.standaloneSetup(typeMembreJuryResource)
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
    public static TypeMembreJury createEntity(EntityManager em) {
        TypeMembreJury typeMembreJury = new TypeMembreJury()
            .libelle(DEFAULT_LIBELLE);
        return typeMembreJury;
    }

    @Before
    public void initTest() {
        typeMembreJury = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeMembreJury() throws Exception {
        int databaseSizeBeforeCreate = typeMembreJuryRepository.findAll().size();

        // Create the TypeMembreJury
        TypeMembreJuryDTO typeMembreJuryDTO = typeMembreJuryMapper.toDto(typeMembreJury);
        restTypeMembreJuryMockMvc.perform(post("/api/type-membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeMembreJuryDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeMembreJury in the database
        List<TypeMembreJury> typeMembreJuryList = typeMembreJuryRepository.findAll();
        assertThat(typeMembreJuryList).hasSize(databaseSizeBeforeCreate + 1);
        TypeMembreJury testTypeMembreJury = typeMembreJuryList.get(typeMembreJuryList.size() - 1);
        assertThat(testTypeMembreJury.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTypeMembreJuryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeMembreJuryRepository.findAll().size();

        // Create the TypeMembreJury with an existing ID
        typeMembreJury.setId(1L);
        TypeMembreJuryDTO typeMembreJuryDTO = typeMembreJuryMapper.toDto(typeMembreJury);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeMembreJuryMockMvc.perform(post("/api/type-membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeMembreJuryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeMembreJury in the database
        List<TypeMembreJury> typeMembreJuryList = typeMembreJuryRepository.findAll();
        assertThat(typeMembreJuryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeMembreJuryRepository.findAll().size();
        // set the field null
        typeMembreJury.setLibelle(null);

        // Create the TypeMembreJury, which fails.
        TypeMembreJuryDTO typeMembreJuryDTO = typeMembreJuryMapper.toDto(typeMembreJury);

        restTypeMembreJuryMockMvc.perform(post("/api/type-membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeMembreJuryDTO)))
            .andExpect(status().isBadRequest());

        List<TypeMembreJury> typeMembreJuryList = typeMembreJuryRepository.findAll();
        assertThat(typeMembreJuryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeMembreJuries() throws Exception {
        // Initialize the database
        typeMembreJuryRepository.saveAndFlush(typeMembreJury);

        // Get all the typeMembreJuryList
        restTypeMembreJuryMockMvc.perform(get("/api/type-membre-juries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeMembreJury.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTypeMembreJuriesWithEagerRelationshipsIsEnabled() throws Exception {
        TypeMembreJuryResource typeMembreJuryResource = new TypeMembreJuryResource(typeMembreJuryServiceMock);
        when(typeMembreJuryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTypeMembreJuryMockMvc = MockMvcBuilders.standaloneSetup(typeMembreJuryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTypeMembreJuryMockMvc.perform(get("/api/type-membre-juries?eagerload=true"))
        .andExpect(status().isOk());

        verify(typeMembreJuryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTypeMembreJuriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        TypeMembreJuryResource typeMembreJuryResource = new TypeMembreJuryResource(typeMembreJuryServiceMock);
            when(typeMembreJuryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTypeMembreJuryMockMvc = MockMvcBuilders.standaloneSetup(typeMembreJuryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTypeMembreJuryMockMvc.perform(get("/api/type-membre-juries?eagerload=true"))
        .andExpect(status().isOk());

            verify(typeMembreJuryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTypeMembreJury() throws Exception {
        // Initialize the database
        typeMembreJuryRepository.saveAndFlush(typeMembreJury);

        // Get the typeMembreJury
        restTypeMembreJuryMockMvc.perform(get("/api/type-membre-juries/{id}", typeMembreJury.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeMembreJury.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeMembreJury() throws Exception {
        // Get the typeMembreJury
        restTypeMembreJuryMockMvc.perform(get("/api/type-membre-juries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeMembreJury() throws Exception {
        // Initialize the database
        typeMembreJuryRepository.saveAndFlush(typeMembreJury);

        int databaseSizeBeforeUpdate = typeMembreJuryRepository.findAll().size();

        // Update the typeMembreJury
        TypeMembreJury updatedTypeMembreJury = typeMembreJuryRepository.findById(typeMembreJury.getId()).get();
        // Disconnect from session so that the updates on updatedTypeMembreJury are not directly saved in db
        em.detach(updatedTypeMembreJury);
        updatedTypeMembreJury
            .libelle(UPDATED_LIBELLE);
        TypeMembreJuryDTO typeMembreJuryDTO = typeMembreJuryMapper.toDto(updatedTypeMembreJury);

        restTypeMembreJuryMockMvc.perform(put("/api/type-membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeMembreJuryDTO)))
            .andExpect(status().isOk());

        // Validate the TypeMembreJury in the database
        List<TypeMembreJury> typeMembreJuryList = typeMembreJuryRepository.findAll();
        assertThat(typeMembreJuryList).hasSize(databaseSizeBeforeUpdate);
        TypeMembreJury testTypeMembreJury = typeMembreJuryList.get(typeMembreJuryList.size() - 1);
        assertThat(testTypeMembreJury.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeMembreJury() throws Exception {
        int databaseSizeBeforeUpdate = typeMembreJuryRepository.findAll().size();

        // Create the TypeMembreJury
        TypeMembreJuryDTO typeMembreJuryDTO = typeMembreJuryMapper.toDto(typeMembreJury);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeMembreJuryMockMvc.perform(put("/api/type-membre-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeMembreJuryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeMembreJury in the database
        List<TypeMembreJury> typeMembreJuryList = typeMembreJuryRepository.findAll();
        assertThat(typeMembreJuryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeMembreJury() throws Exception {
        // Initialize the database
        typeMembreJuryRepository.saveAndFlush(typeMembreJury);

        int databaseSizeBeforeDelete = typeMembreJuryRepository.findAll().size();

        // Delete the typeMembreJury
        restTypeMembreJuryMockMvc.perform(delete("/api/type-membre-juries/{id}", typeMembreJury.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeMembreJury> typeMembreJuryList = typeMembreJuryRepository.findAll();
        assertThat(typeMembreJuryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeMembreJury.class);
        TypeMembreJury typeMembreJury1 = new TypeMembreJury();
        typeMembreJury1.setId(1L);
        TypeMembreJury typeMembreJury2 = new TypeMembreJury();
        typeMembreJury2.setId(typeMembreJury1.getId());
        assertThat(typeMembreJury1).isEqualTo(typeMembreJury2);
        typeMembreJury2.setId(2L);
        assertThat(typeMembreJury1).isNotEqualTo(typeMembreJury2);
        typeMembreJury1.setId(null);
        assertThat(typeMembreJury1).isNotEqualTo(typeMembreJury2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeMembreJuryDTO.class);
        TypeMembreJuryDTO typeMembreJuryDTO1 = new TypeMembreJuryDTO();
        typeMembreJuryDTO1.setId(1L);
        TypeMembreJuryDTO typeMembreJuryDTO2 = new TypeMembreJuryDTO();
        assertThat(typeMembreJuryDTO1).isNotEqualTo(typeMembreJuryDTO2);
        typeMembreJuryDTO2.setId(typeMembreJuryDTO1.getId());
        assertThat(typeMembreJuryDTO1).isEqualTo(typeMembreJuryDTO2);
        typeMembreJuryDTO2.setId(2L);
        assertThat(typeMembreJuryDTO1).isNotEqualTo(typeMembreJuryDTO2);
        typeMembreJuryDTO1.setId(null);
        assertThat(typeMembreJuryDTO1).isNotEqualTo(typeMembreJuryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeMembreJuryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeMembreJuryMapper.fromId(null)).isNull();
    }
}
