package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.CentreComposition;
import com.test.repository.CentreCompositionRepository;
import com.test.service.CentreCompositionService;
import com.test.service.dto.CentreCompositionDTO;
import com.test.service.mapper.CentreCompositionMapper;
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
 * Test class for the CentreCompositionResource REST controller.
 *
 * @see CentreCompositionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class CentreCompositionResourceIntTest {

    private static final String DEFAULT_LIBELLE_CENTRE_COMPOSITION = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CENTRE_COMPOSITION = "BBBBBBBBBB";

    @Autowired
    private CentreCompositionRepository centreCompositionRepository;

    @Autowired
    private CentreCompositionMapper centreCompositionMapper;

    @Autowired
    private CentreCompositionService centreCompositionService;

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

    private MockMvc restCentreCompositionMockMvc;

    private CentreComposition centreComposition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CentreCompositionResource centreCompositionResource = new CentreCompositionResource(centreCompositionService);
        this.restCentreCompositionMockMvc = MockMvcBuilders.standaloneSetup(centreCompositionResource)
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
    public static CentreComposition createEntity(EntityManager em) {
        CentreComposition centreComposition = new CentreComposition()
            .libelleCentreComposition(DEFAULT_LIBELLE_CENTRE_COMPOSITION);
        return centreComposition;
    }

    @Before
    public void initTest() {
        centreComposition = createEntity(em);
    }

    @Test
    @Transactional
    public void createCentreComposition() throws Exception {
        int databaseSizeBeforeCreate = centreCompositionRepository.findAll().size();

        // Create the CentreComposition
        CentreCompositionDTO centreCompositionDTO = centreCompositionMapper.toDto(centreComposition);
        restCentreCompositionMockMvc.perform(post("/api/centre-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreCompositionDTO)))
            .andExpect(status().isCreated());

        // Validate the CentreComposition in the database
        List<CentreComposition> centreCompositionList = centreCompositionRepository.findAll();
        assertThat(centreCompositionList).hasSize(databaseSizeBeforeCreate + 1);
        CentreComposition testCentreComposition = centreCompositionList.get(centreCompositionList.size() - 1);
        assertThat(testCentreComposition.getLibelleCentreComposition()).isEqualTo(DEFAULT_LIBELLE_CENTRE_COMPOSITION);
    }

    @Test
    @Transactional
    public void createCentreCompositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centreCompositionRepository.findAll().size();

        // Create the CentreComposition with an existing ID
        centreComposition.setId(1L);
        CentreCompositionDTO centreCompositionDTO = centreCompositionMapper.toDto(centreComposition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentreCompositionMockMvc.perform(post("/api/centre-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreCompositionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CentreComposition in the database
        List<CentreComposition> centreCompositionList = centreCompositionRepository.findAll();
        assertThat(centreCompositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleCentreCompositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = centreCompositionRepository.findAll().size();
        // set the field null
        centreComposition.setLibelleCentreComposition(null);

        // Create the CentreComposition, which fails.
        CentreCompositionDTO centreCompositionDTO = centreCompositionMapper.toDto(centreComposition);

        restCentreCompositionMockMvc.perform(post("/api/centre-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreCompositionDTO)))
            .andExpect(status().isBadRequest());

        List<CentreComposition> centreCompositionList = centreCompositionRepository.findAll();
        assertThat(centreCompositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCentreCompositions() throws Exception {
        // Initialize the database
        centreCompositionRepository.saveAndFlush(centreComposition);

        // Get all the centreCompositionList
        restCentreCompositionMockMvc.perform(get("/api/centre-compositions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centreComposition.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleCentreComposition").value(hasItem(DEFAULT_LIBELLE_CENTRE_COMPOSITION.toString())));
    }
    
    @Test
    @Transactional
    public void getCentreComposition() throws Exception {
        // Initialize the database
        centreCompositionRepository.saveAndFlush(centreComposition);

        // Get the centreComposition
        restCentreCompositionMockMvc.perform(get("/api/centre-compositions/{id}", centreComposition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(centreComposition.getId().intValue()))
            .andExpect(jsonPath("$.libelleCentreComposition").value(DEFAULT_LIBELLE_CENTRE_COMPOSITION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCentreComposition() throws Exception {
        // Get the centreComposition
        restCentreCompositionMockMvc.perform(get("/api/centre-compositions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCentreComposition() throws Exception {
        // Initialize the database
        centreCompositionRepository.saveAndFlush(centreComposition);

        int databaseSizeBeforeUpdate = centreCompositionRepository.findAll().size();

        // Update the centreComposition
        CentreComposition updatedCentreComposition = centreCompositionRepository.findById(centreComposition.getId()).get();
        // Disconnect from session so that the updates on updatedCentreComposition are not directly saved in db
        em.detach(updatedCentreComposition);
        updatedCentreComposition
            .libelleCentreComposition(UPDATED_LIBELLE_CENTRE_COMPOSITION);
        CentreCompositionDTO centreCompositionDTO = centreCompositionMapper.toDto(updatedCentreComposition);

        restCentreCompositionMockMvc.perform(put("/api/centre-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreCompositionDTO)))
            .andExpect(status().isOk());

        // Validate the CentreComposition in the database
        List<CentreComposition> centreCompositionList = centreCompositionRepository.findAll();
        assertThat(centreCompositionList).hasSize(databaseSizeBeforeUpdate);
        CentreComposition testCentreComposition = centreCompositionList.get(centreCompositionList.size() - 1);
        assertThat(testCentreComposition.getLibelleCentreComposition()).isEqualTo(UPDATED_LIBELLE_CENTRE_COMPOSITION);
    }

    @Test
    @Transactional
    public void updateNonExistingCentreComposition() throws Exception {
        int databaseSizeBeforeUpdate = centreCompositionRepository.findAll().size();

        // Create the CentreComposition
        CentreCompositionDTO centreCompositionDTO = centreCompositionMapper.toDto(centreComposition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentreCompositionMockMvc.perform(put("/api/centre-compositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreCompositionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CentreComposition in the database
        List<CentreComposition> centreCompositionList = centreCompositionRepository.findAll();
        assertThat(centreCompositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCentreComposition() throws Exception {
        // Initialize the database
        centreCompositionRepository.saveAndFlush(centreComposition);

        int databaseSizeBeforeDelete = centreCompositionRepository.findAll().size();

        // Delete the centreComposition
        restCentreCompositionMockMvc.perform(delete("/api/centre-compositions/{id}", centreComposition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CentreComposition> centreCompositionList = centreCompositionRepository.findAll();
        assertThat(centreCompositionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentreComposition.class);
        CentreComposition centreComposition1 = new CentreComposition();
        centreComposition1.setId(1L);
        CentreComposition centreComposition2 = new CentreComposition();
        centreComposition2.setId(centreComposition1.getId());
        assertThat(centreComposition1).isEqualTo(centreComposition2);
        centreComposition2.setId(2L);
        assertThat(centreComposition1).isNotEqualTo(centreComposition2);
        centreComposition1.setId(null);
        assertThat(centreComposition1).isNotEqualTo(centreComposition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentreCompositionDTO.class);
        CentreCompositionDTO centreCompositionDTO1 = new CentreCompositionDTO();
        centreCompositionDTO1.setId(1L);
        CentreCompositionDTO centreCompositionDTO2 = new CentreCompositionDTO();
        assertThat(centreCompositionDTO1).isNotEqualTo(centreCompositionDTO2);
        centreCompositionDTO2.setId(centreCompositionDTO1.getId());
        assertThat(centreCompositionDTO1).isEqualTo(centreCompositionDTO2);
        centreCompositionDTO2.setId(2L);
        assertThat(centreCompositionDTO1).isNotEqualTo(centreCompositionDTO2);
        centreCompositionDTO1.setId(null);
        assertThat(centreCompositionDTO1).isNotEqualTo(centreCompositionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(centreCompositionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(centreCompositionMapper.fromId(null)).isNull();
    }
}
