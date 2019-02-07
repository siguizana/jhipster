package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.TypeCentreComposition;
import com.test.repository.TypeCentreCompositionRepository;
import com.test.service.TypeCentreCompositionService;
import com.test.service.dto.TypeCentreCompositionDTO;
import com.test.service.mapper.TypeCentreCompositionMapper;
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
 * Test class for the TypeCentreCompositionResource REST controller.
 *
 * @see TypeCentreCompositionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class TypeCentreCompositionResourceIntTest {

    private static final String DEFAULT_LIBELLE_TYPE_CENTRE_COMPOSITION = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_CENTRE_COMPOSITION = "BBBBBBBBBB";

    @Autowired
    private TypeCentreCompositionRepository typeCentreCompositionRepository;

    @Autowired
    private TypeCentreCompositionMapper typeCentreCompositionMapper;

    @Autowired
    private TypeCentreCompositionService typeCentreCompositionService;

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

    private MockMvc restTypeCentreCompositionMockMvc;

    private TypeCentreComposition typeCentreComposition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeCentreCompositionResource typeCentreCompositionResource = new TypeCentreCompositionResource(typeCentreCompositionService);
        this.restTypeCentreCompositionMockMvc = MockMvcBuilders.standaloneSetup(typeCentreCompositionResource)
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
    public static TypeCentreComposition createEntity(EntityManager em) {
        TypeCentreComposition typeCentreComposition = new TypeCentreComposition()
            .libelleTypeCentreComposition(DEFAULT_LIBELLE_TYPE_CENTRE_COMPOSITION);
        return typeCentreComposition;
    }

    @Before
    public void initTest() {
        typeCentreComposition = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeCentreComposition() throws Exception {
        int databaseSizeBeforeCreate = typeCentreCompositionRepository.findAll().size();

        // Create the TypeCentreComposition
        TypeCentreCompositionDTO typeCentreCompositionDTO = typeCentreCompositionMapper.toDto(typeCentreComposition);
        restTypeCentreCompositionMockMvc.perform(post("/api/type-centre-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCentreCompositionDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeCentreComposition in the database
        List<TypeCentreComposition> typeCentreCompositionList = typeCentreCompositionRepository.findAll();
        assertThat(typeCentreCompositionList).hasSize(databaseSizeBeforeCreate + 1);
        TypeCentreComposition testTypeCentreComposition = typeCentreCompositionList.get(typeCentreCompositionList.size() - 1);
        assertThat(testTypeCentreComposition.getLibelleTypeCentreComposition()).isEqualTo(DEFAULT_LIBELLE_TYPE_CENTRE_COMPOSITION);
    }

    @Test
    @Transactional
    public void createTypeCentreCompositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeCentreCompositionRepository.findAll().size();

        // Create the TypeCentreComposition with an existing ID
        typeCentreComposition.setId(1L);
        TypeCentreCompositionDTO typeCentreCompositionDTO = typeCentreCompositionMapper.toDto(typeCentreComposition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeCentreCompositionMockMvc.perform(post("/api/type-centre-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCentreCompositionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCentreComposition in the database
        List<TypeCentreComposition> typeCentreCompositionList = typeCentreCompositionRepository.findAll();
        assertThat(typeCentreCompositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleTypeCentreCompositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeCentreCompositionRepository.findAll().size();
        // set the field null
        typeCentreComposition.setLibelleTypeCentreComposition(null);

        // Create the TypeCentreComposition, which fails.
        TypeCentreCompositionDTO typeCentreCompositionDTO = typeCentreCompositionMapper.toDto(typeCentreComposition);

        restTypeCentreCompositionMockMvc.perform(post("/api/type-centre-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCentreCompositionDTO)))
            .andExpect(status().isBadRequest());

        List<TypeCentreComposition> typeCentreCompositionList = typeCentreCompositionRepository.findAll();
        assertThat(typeCentreCompositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeCentreCompositions() throws Exception {
        // Initialize the database
        typeCentreCompositionRepository.saveAndFlush(typeCentreComposition);

        // Get all the typeCentreCompositionList
        restTypeCentreCompositionMockMvc.perform(get("/api/type-centre-compositions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeCentreComposition.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleTypeCentreComposition").value(hasItem(DEFAULT_LIBELLE_TYPE_CENTRE_COMPOSITION.toString())));
    }
    
    @Test
    @Transactional
    public void getTypeCentreComposition() throws Exception {
        // Initialize the database
        typeCentreCompositionRepository.saveAndFlush(typeCentreComposition);

        // Get the typeCentreComposition
        restTypeCentreCompositionMockMvc.perform(get("/api/type-centre-compositions/{id}", typeCentreComposition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeCentreComposition.getId().intValue()))
            .andExpect(jsonPath("$.libelleTypeCentreComposition").value(DEFAULT_LIBELLE_TYPE_CENTRE_COMPOSITION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeCentreComposition() throws Exception {
        // Get the typeCentreComposition
        restTypeCentreCompositionMockMvc.perform(get("/api/type-centre-compositions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeCentreComposition() throws Exception {
        // Initialize the database
        typeCentreCompositionRepository.saveAndFlush(typeCentreComposition);

        int databaseSizeBeforeUpdate = typeCentreCompositionRepository.findAll().size();

        // Update the typeCentreComposition
        TypeCentreComposition updatedTypeCentreComposition = typeCentreCompositionRepository.findById(typeCentreComposition.getId()).get();
        // Disconnect from session so that the updates on updatedTypeCentreComposition are not directly saved in db
        em.detach(updatedTypeCentreComposition);
        updatedTypeCentreComposition
            .libelleTypeCentreComposition(UPDATED_LIBELLE_TYPE_CENTRE_COMPOSITION);
        TypeCentreCompositionDTO typeCentreCompositionDTO = typeCentreCompositionMapper.toDto(updatedTypeCentreComposition);

        restTypeCentreCompositionMockMvc.perform(put("/api/type-centre-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCentreCompositionDTO)))
            .andExpect(status().isOk());

        // Validate the TypeCentreComposition in the database
        List<TypeCentreComposition> typeCentreCompositionList = typeCentreCompositionRepository.findAll();
        assertThat(typeCentreCompositionList).hasSize(databaseSizeBeforeUpdate);
        TypeCentreComposition testTypeCentreComposition = typeCentreCompositionList.get(typeCentreCompositionList.size() - 1);
        assertThat(testTypeCentreComposition.getLibelleTypeCentreComposition()).isEqualTo(UPDATED_LIBELLE_TYPE_CENTRE_COMPOSITION);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeCentreComposition() throws Exception {
        int databaseSizeBeforeUpdate = typeCentreCompositionRepository.findAll().size();

        // Create the TypeCentreComposition
        TypeCentreCompositionDTO typeCentreCompositionDTO = typeCentreCompositionMapper.toDto(typeCentreComposition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeCentreCompositionMockMvc.perform(put("/api/type-centre-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeCentreCompositionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeCentreComposition in the database
        List<TypeCentreComposition> typeCentreCompositionList = typeCentreCompositionRepository.findAll();
        assertThat(typeCentreCompositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeCentreComposition() throws Exception {
        // Initialize the database
        typeCentreCompositionRepository.saveAndFlush(typeCentreComposition);

        int databaseSizeBeforeDelete = typeCentreCompositionRepository.findAll().size();

        // Delete the typeCentreComposition
        restTypeCentreCompositionMockMvc.perform(delete("/api/type-centre-compositions/{id}", typeCentreComposition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypeCentreComposition> typeCentreCompositionList = typeCentreCompositionRepository.findAll();
        assertThat(typeCentreCompositionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCentreComposition.class);
        TypeCentreComposition typeCentreComposition1 = new TypeCentreComposition();
        typeCentreComposition1.setId(1L);
        TypeCentreComposition typeCentreComposition2 = new TypeCentreComposition();
        typeCentreComposition2.setId(typeCentreComposition1.getId());
        assertThat(typeCentreComposition1).isEqualTo(typeCentreComposition2);
        typeCentreComposition2.setId(2L);
        assertThat(typeCentreComposition1).isNotEqualTo(typeCentreComposition2);
        typeCentreComposition1.setId(null);
        assertThat(typeCentreComposition1).isNotEqualTo(typeCentreComposition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCentreCompositionDTO.class);
        TypeCentreCompositionDTO typeCentreCompositionDTO1 = new TypeCentreCompositionDTO();
        typeCentreCompositionDTO1.setId(1L);
        TypeCentreCompositionDTO typeCentreCompositionDTO2 = new TypeCentreCompositionDTO();
        assertThat(typeCentreCompositionDTO1).isNotEqualTo(typeCentreCompositionDTO2);
        typeCentreCompositionDTO2.setId(typeCentreCompositionDTO1.getId());
        assertThat(typeCentreCompositionDTO1).isEqualTo(typeCentreCompositionDTO2);
        typeCentreCompositionDTO2.setId(2L);
        assertThat(typeCentreCompositionDTO1).isNotEqualTo(typeCentreCompositionDTO2);
        typeCentreCompositionDTO1.setId(null);
        assertThat(typeCentreCompositionDTO1).isNotEqualTo(typeCentreCompositionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(typeCentreCompositionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(typeCentreCompositionMapper.fromId(null)).isNull();
    }
}
