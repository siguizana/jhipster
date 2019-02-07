package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.TypeDiplome;
import com.test.repository.TypeDiplomeRepository;
import com.test.service.TypeDiplomeService;
import com.test.service.dto.TypeDiplomeDTO;
import com.test.service.mapper.TypeDiplomeMapper;
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
 * Test class for the TypeDiplomeResource REST controller.
 *
 * @see TypeDiplomeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class TypeDiplomeResourceIntTest {

    private static final String DEFAULT_LIBELLE_TYPE_DIPLOME = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_DIPLOME = "BBBBBBBBBB";

    @Autowired
    private TypeDiplomeRepository typeDiplomeRepository;

    @Autowired
    private TypeDiplomeMapper typeDiplomeMapper;

    @Autowired
    private TypeDiplomeService typeDiplomeService;

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

    private MockMvc restTypeDiplomeMockMvc;

    private TypeDiplome typeDiplome;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeDiplomeResource typeDiplomeResource = new TypeDiplomeResource(typeDiplomeService);
        this.restTypeDiplomeMockMvc = MockMvcBuilders.standaloneSetup(typeDiplomeResource)
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
    public static TypeDiplome createEntity(EntityManager em) {
        TypeDiplome typeDiplome = new TypeDiplome()
            .libelleTypeDiplome(DEFAULT_LIBELLE_TYPE_DIPLOME);
        return typeDiplome;
    }

    @Before
    public void initTest() {
        typeDiplome = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeDiplome() throws Exception {
        int databaseSizeBeforeCreate = typeDiplomeRepository.findAll().size();

        // Create the TypeDiplome
        TypeDiplomeDTO typeDiplomeDTO = typeDiplomeMapper.toDto(typeDiplome);
        restTypeDiplomeMockMvc.perform(post("/api/type-diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDiplomeDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeDiplome in the database
        List<TypeDiplome> typeDiplomeList = typeDiplomeRepository.findAll();
        assertThat(typeDiplomeList).hasSize(databaseSizeBeforeCreate + 1);
        TypeDiplome testTypeDiplome = typeDiplomeList.get(typeDiplomeList.size() - 1);
        assertThat(testTypeDiplome.getLibelleTypeDiplome()).isEqualTo(DEFAULT_LIBELLE_TYPE_DIPLOME);
    }

    @Test
    @Transactional
    public void createTypeDiplomeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeDiplomeRepository.findAll().size();

        // Create the TypeDiplome with an existing ID
        typeDiplome.setId(1L);
        TypeDiplomeDTO typeDiplomeDTO = typeDiplomeMapper.toDto(typeDiplome);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeDiplomeMockMvc.perform(post("/api/type-diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDiplomeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeDiplome in the database
        List<TypeDiplome> typeDiplomeList = typeDiplomeRepository.findAll();
        assertThat(typeDiplomeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleTypeDiplomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeDiplomeRepository.findAll().size();
        // set the field null
        typeDiplome.setLibelleTypeDiplome(null);

        // Create the TypeDiplome, which fails.
        TypeDiplomeDTO typeDiplomeDTO = typeDiplomeMapper.toDto(typeDiplome);

        restTypeDiplomeMockMvc.perform(post("/api/type-diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDiplomeDTO)))
            .andExpect(status().isBadRequest());

        List<TypeDiplome> typeDiplomeList = typeDiplomeRepository.findAll();
        assertThat(typeDiplomeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeDiplomes() throws Exception {
        // Initialize the database
        typeDiplomeRepository.saveAndFlush(typeDiplome);

        // Get all the typeDiplomeList
        restTypeDiplomeMockMvc.perform(get("/api/type-diplomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeDiplome.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleTypeDiplome").value(hasItem(DEFAULT_LIBELLE_TYPE_DIPLOME.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeDiplome() throws Exception {
        // Initialize the database
        typeDiplomeRepository.saveAndFlush(typeDiplome);

        // Get the typeDiplome
        restTypeDiplomeMockMvc.perform(get("/api/type-diplomes/{id}", typeDiplome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeDiplome.getId().intValue()))
            .andExpect(jsonPath("$.libelleTypeDiplome").value(DEFAULT_LIBELLE_TYPE_DIPLOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeDiplome() throws Exception {
        // Get the typeDiplome
        restTypeDiplomeMockMvc.perform(get("/api/type-diplomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeDiplome() throws Exception {
        // Initialize the database
        typeDiplomeRepository.saveAndFlush(typeDiplome);

        int databaseSizeBeforeUpdate = typeDiplomeRepository.findAll().size();

        // Update the typeDiplome
        TypeDiplome updatedTypeDiplome = typeDiplomeRepository.findById(typeDiplome.getId()).get();
        // Disconnect from session so that the updates on updatedTypeDiplome are not directly saved in db
        em.detach(updatedTypeDiplome);
        updatedTypeDiplome
            .libelleTypeDiplome(UPDATED_LIBELLE_TYPE_DIPLOME);
        TypeDiplomeDTO typeDiplomeDTO = typeDiplomeMapper.toDto(updatedTypeDiplome);

        restTypeDiplomeMockMvc.perform(put("/api/type-diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDiplomeDTO)))
            .andExpect(status().isOk());

        // Validate the TypeDiplome in the database
        List<TypeDiplome> typeDiplomeList = typeDiplomeRepository.findAll();
        assertThat(typeDiplomeList).hasSize(databaseSizeBeforeUpdate);
        TypeDiplome testTypeDiplome = typeDiplomeList.get(typeDiplomeList.size() - 1);
        assertThat(testTypeDiplome.getLibelleTypeDiplome()).isEqualTo(UPDATED_LIBELLE_TYPE_DIPLOME);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeDiplome() throws Exception {
        int databaseSizeBeforeUpdate = typeDiplomeRepository.findAll().size();

        // Create the TypeDiplome
        TypeDiplomeDTO typeDiplomeDTO = typeDiplomeMapper.toDto(typeDiplome);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeDiplomeMockMvc.perform(put("/api/type-diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeDiplomeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeDiplome in the database
        List<TypeDiplome> typeDiplomeList = typeDiplomeRepository.findAll();
        assertThat(typeDiplomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeDiplome() throws Exception {
        // Initialize the database
        typeDiplomeRepository.saveAndFlush(typeDiplome);

        int databaseSizeBeforeDelete = typeDiplomeRepository.findAll().size();

        // Delete the typeDiplome
        restTypeDiplomeMockMvc.perform(delete("/api/type-diplomes/{id}", typeDiplome.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeDiplome> typeDiplomeList = typeDiplomeRepository.findAll();
        assertThat(typeDiplomeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDiplome.class);
        TypeDiplome typeDiplome1 = new TypeDiplome();
        typeDiplome1.setId(1L);
        TypeDiplome typeDiplome2 = new TypeDiplome();
        typeDiplome2.setId(typeDiplome1.getId());
        assertThat(typeDiplome1).isEqualTo(typeDiplome2);
        typeDiplome2.setId(2L);
        assertThat(typeDiplome1).isNotEqualTo(typeDiplome2);
        typeDiplome1.setId(null);
        assertThat(typeDiplome1).isNotEqualTo(typeDiplome2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDiplomeDTO.class);
        TypeDiplomeDTO typeDiplomeDTO1 = new TypeDiplomeDTO();
        typeDiplomeDTO1.setId(1L);
        TypeDiplomeDTO typeDiplomeDTO2 = new TypeDiplomeDTO();
        assertThat(typeDiplomeDTO1).isNotEqualTo(typeDiplomeDTO2);
        typeDiplomeDTO2.setId(typeDiplomeDTO1.getId());
        assertThat(typeDiplomeDTO1).isEqualTo(typeDiplomeDTO2);
        typeDiplomeDTO2.setId(2L);
        assertThat(typeDiplomeDTO1).isNotEqualTo(typeDiplomeDTO2);
        typeDiplomeDTO1.setId(null);
        assertThat(typeDiplomeDTO1).isNotEqualTo(typeDiplomeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeDiplomeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeDiplomeMapper.fromId(null)).isNull();
    }
}
