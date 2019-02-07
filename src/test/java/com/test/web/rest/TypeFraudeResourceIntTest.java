package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.TypeFraude;
import com.test.repository.TypeFraudeRepository;
import com.test.service.TypeFraudeService;
import com.test.service.dto.TypeFraudeDTO;
import com.test.service.mapper.TypeFraudeMapper;
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
 * Test class for the TypeFraudeResource REST controller.
 *
 * @see TypeFraudeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class TypeFraudeResourceIntTest {

    private static final String DEFAULT_LIBELLE_TYPE_FRAUDE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_FRAUDE = "BBBBBBBBBB";

    @Autowired
    private TypeFraudeRepository typeFraudeRepository;

    @Autowired
    private TypeFraudeMapper typeFraudeMapper;

    @Autowired
    private TypeFraudeService typeFraudeService;

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

    private MockMvc restTypeFraudeMockMvc;

    private TypeFraude typeFraude;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeFraudeResource typeFraudeResource = new TypeFraudeResource(typeFraudeService);
        this.restTypeFraudeMockMvc = MockMvcBuilders.standaloneSetup(typeFraudeResource)
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
    public static TypeFraude createEntity(EntityManager em) {
        TypeFraude typeFraude = new TypeFraude()
            .libelleTypeFraude(DEFAULT_LIBELLE_TYPE_FRAUDE);
        return typeFraude;
    }

    @Before
    public void initTest() {
        typeFraude = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeFraude() throws Exception {
        int databaseSizeBeforeCreate = typeFraudeRepository.findAll().size();

        // Create the TypeFraude
        TypeFraudeDTO typeFraudeDTO = typeFraudeMapper.toDto(typeFraude);
        restTypeFraudeMockMvc.perform(post("/api/type-fraudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeFraudeDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeFraude in the database
        List<TypeFraude> typeFraudeList = typeFraudeRepository.findAll();
        assertThat(typeFraudeList).hasSize(databaseSizeBeforeCreate + 1);
        TypeFraude testTypeFraude = typeFraudeList.get(typeFraudeList.size() - 1);
        assertThat(testTypeFraude.getLibelleTypeFraude()).isEqualTo(DEFAULT_LIBELLE_TYPE_FRAUDE);
    }

    @Test
    @Transactional
    public void createTypeFraudeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeFraudeRepository.findAll().size();

        // Create the TypeFraude with an existing ID
        typeFraude.setId(1L);
        TypeFraudeDTO typeFraudeDTO = typeFraudeMapper.toDto(typeFraude);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeFraudeMockMvc.perform(post("/api/type-fraudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeFraudeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeFraude in the database
        List<TypeFraude> typeFraudeList = typeFraudeRepository.findAll();
        assertThat(typeFraudeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleTypeFraudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeFraudeRepository.findAll().size();
        // set the field null
        typeFraude.setLibelleTypeFraude(null);

        // Create the TypeFraude, which fails.
        TypeFraudeDTO typeFraudeDTO = typeFraudeMapper.toDto(typeFraude);

        restTypeFraudeMockMvc.perform(post("/api/type-fraudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeFraudeDTO)))
            .andExpect(status().isBadRequest());

        List<TypeFraude> typeFraudeList = typeFraudeRepository.findAll();
        assertThat(typeFraudeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeFraudes() throws Exception {
        // Initialize the database
        typeFraudeRepository.saveAndFlush(typeFraude);

        // Get all the typeFraudeList
        restTypeFraudeMockMvc.perform(get("/api/type-fraudes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeFraude.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleTypeFraude").value(hasItem(DEFAULT_LIBELLE_TYPE_FRAUDE.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeFraude() throws Exception {
        // Initialize the database
        typeFraudeRepository.saveAndFlush(typeFraude);

        // Get the typeFraude
        restTypeFraudeMockMvc.perform(get("/api/type-fraudes/{id}", typeFraude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeFraude.getId().intValue()))
            .andExpect(jsonPath("$.libelleTypeFraude").value(DEFAULT_LIBELLE_TYPE_FRAUDE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeFraude() throws Exception {
        // Get the typeFraude
        restTypeFraudeMockMvc.perform(get("/api/type-fraudes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeFraude() throws Exception {
        // Initialize the database
        typeFraudeRepository.saveAndFlush(typeFraude);

        int databaseSizeBeforeUpdate = typeFraudeRepository.findAll().size();

        // Update the typeFraude
        TypeFraude updatedTypeFraude = typeFraudeRepository.findById(typeFraude.getId()).get();
        // Disconnect from session so that the updates on updatedTypeFraude are not directly saved in db
        em.detach(updatedTypeFraude);
        updatedTypeFraude
            .libelleTypeFraude(UPDATED_LIBELLE_TYPE_FRAUDE);
        TypeFraudeDTO typeFraudeDTO = typeFraudeMapper.toDto(updatedTypeFraude);

        restTypeFraudeMockMvc.perform(put("/api/type-fraudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeFraudeDTO)))
            .andExpect(status().isOk());

        // Validate the TypeFraude in the database
        List<TypeFraude> typeFraudeList = typeFraudeRepository.findAll();
        assertThat(typeFraudeList).hasSize(databaseSizeBeforeUpdate);
        TypeFraude testTypeFraude = typeFraudeList.get(typeFraudeList.size() - 1);
        assertThat(testTypeFraude.getLibelleTypeFraude()).isEqualTo(UPDATED_LIBELLE_TYPE_FRAUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeFraude() throws Exception {
        int databaseSizeBeforeUpdate = typeFraudeRepository.findAll().size();

        // Create the TypeFraude
        TypeFraudeDTO typeFraudeDTO = typeFraudeMapper.toDto(typeFraude);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeFraudeMockMvc.perform(put("/api/type-fraudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeFraudeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeFraude in the database
        List<TypeFraude> typeFraudeList = typeFraudeRepository.findAll();
        assertThat(typeFraudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeFraude() throws Exception {
        // Initialize the database
        typeFraudeRepository.saveAndFlush(typeFraude);

        int databaseSizeBeforeDelete = typeFraudeRepository.findAll().size();

        // Delete the typeFraude
        restTypeFraudeMockMvc.perform(delete("/api/type-fraudes/{id}", typeFraude.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeFraude> typeFraudeList = typeFraudeRepository.findAll();
        assertThat(typeFraudeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeFraude.class);
        TypeFraude typeFraude1 = new TypeFraude();
        typeFraude1.setId(1L);
        TypeFraude typeFraude2 = new TypeFraude();
        typeFraude2.setId(typeFraude1.getId());
        assertThat(typeFraude1).isEqualTo(typeFraude2);
        typeFraude2.setId(2L);
        assertThat(typeFraude1).isNotEqualTo(typeFraude2);
        typeFraude1.setId(null);
        assertThat(typeFraude1).isNotEqualTo(typeFraude2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeFraudeDTO.class);
        TypeFraudeDTO typeFraudeDTO1 = new TypeFraudeDTO();
        typeFraudeDTO1.setId(1L);
        TypeFraudeDTO typeFraudeDTO2 = new TypeFraudeDTO();
        assertThat(typeFraudeDTO1).isNotEqualTo(typeFraudeDTO2);
        typeFraudeDTO2.setId(typeFraudeDTO1.getId());
        assertThat(typeFraudeDTO1).isEqualTo(typeFraudeDTO2);
        typeFraudeDTO2.setId(2L);
        assertThat(typeFraudeDTO1).isNotEqualTo(typeFraudeDTO2);
        typeFraudeDTO1.setId(null);
        assertThat(typeFraudeDTO1).isNotEqualTo(typeFraudeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeFraudeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeFraudeMapper.fromId(null)).isNull();
    }
}
