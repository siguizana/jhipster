package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.CentreExamen;
import com.test.repository.CentreExamenRepository;
import com.test.service.CentreExamenService;
import com.test.service.dto.CentreExamenDTO;
import com.test.service.mapper.CentreExamenMapper;
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
 * Test class for the CentreExamenResource REST controller.
 *
 * @see CentreExamenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class CentreExamenResourceIntTest {

    private static final String DEFAULT_LIBELLE_CENTRE_EXAMEN = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CENTRE_EXAMEN = "BBBBBBBBBB";

    @Autowired
    private CentreExamenRepository centreExamenRepository;

    @Autowired
    private CentreExamenMapper centreExamenMapper;

    @Autowired
    private CentreExamenService centreExamenService;

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

    private MockMvc restCentreExamenMockMvc;

    private CentreExamen centreExamen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CentreExamenResource centreExamenResource = new CentreExamenResource(centreExamenService);
        this.restCentreExamenMockMvc = MockMvcBuilders.standaloneSetup(centreExamenResource)
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
    public static CentreExamen createEntity(EntityManager em) {
        CentreExamen centreExamen = new CentreExamen()
            .libelleCentreExamen(DEFAULT_LIBELLE_CENTRE_EXAMEN);
        return centreExamen;
    }

    @Before
    public void initTest() {
        centreExamen = createEntity(em);
    }

    @Test
    @Transactional
    public void createCentreExamen() throws Exception {
        int databaseSizeBeforeCreate = centreExamenRepository.findAll().size();

        // Create the CentreExamen
        CentreExamenDTO centreExamenDTO = centreExamenMapper.toDto(centreExamen);
        restCentreExamenMockMvc.perform(post("/api/centre-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreExamenDTO)))
            .andExpect(status().isCreated());

        // Validate the CentreExamen in the database
        List<CentreExamen> centreExamenList = centreExamenRepository.findAll();
        assertThat(centreExamenList).hasSize(databaseSizeBeforeCreate + 1);
        CentreExamen testCentreExamen = centreExamenList.get(centreExamenList.size() - 1);
        assertThat(testCentreExamen.getLibelleCentreExamen()).isEqualTo(DEFAULT_LIBELLE_CENTRE_EXAMEN);
    }

    @Test
    @Transactional
    public void createCentreExamenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centreExamenRepository.findAll().size();

        // Create the CentreExamen with an existing ID
        centreExamen.setId(1L);
        CentreExamenDTO centreExamenDTO = centreExamenMapper.toDto(centreExamen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentreExamenMockMvc.perform(post("/api/centre-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreExamenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CentreExamen in the database
        List<CentreExamen> centreExamenList = centreExamenRepository.findAll();
        assertThat(centreExamenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleCentreExamenIsRequired() throws Exception {
        int databaseSizeBeforeTest = centreExamenRepository.findAll().size();
        // set the field null
        centreExamen.setLibelleCentreExamen(null);

        // Create the CentreExamen, which fails.
        CentreExamenDTO centreExamenDTO = centreExamenMapper.toDto(centreExamen);

        restCentreExamenMockMvc.perform(post("/api/centre-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreExamenDTO)))
            .andExpect(status().isBadRequest());

        List<CentreExamen> centreExamenList = centreExamenRepository.findAll();
        assertThat(centreExamenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCentreExamen() throws Exception {
        // Initialize the database
        centreExamenRepository.saveAndFlush(centreExamen);

        // Get all the centreExamenList
        restCentreExamenMockMvc.perform(get("/api/centre-examen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centreExamen.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleCentreExamen").value(hasItem(DEFAULT_LIBELLE_CENTRE_EXAMEN.toString())));
    }
    
    @Test
    @Transactional
    public void getCentreExamen() throws Exception {
        // Initialize the database
        centreExamenRepository.saveAndFlush(centreExamen);

        // Get the centreExamen
        restCentreExamenMockMvc.perform(get("/api/centre-examen/{id}", centreExamen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(centreExamen.getId().intValue()))
            .andExpect(jsonPath("$.libelleCentreExamen").value(DEFAULT_LIBELLE_CENTRE_EXAMEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCentreExamen() throws Exception {
        // Get the centreExamen
        restCentreExamenMockMvc.perform(get("/api/centre-examen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCentreExamen() throws Exception {
        // Initialize the database
        centreExamenRepository.saveAndFlush(centreExamen);

        int databaseSizeBeforeUpdate = centreExamenRepository.findAll().size();

        // Update the centreExamen
        CentreExamen updatedCentreExamen = centreExamenRepository.findById(centreExamen.getId()).get();
        // Disconnect from session so that the updates on updatedCentreExamen are not directly saved in db
        em.detach(updatedCentreExamen);
        updatedCentreExamen
            .libelleCentreExamen(UPDATED_LIBELLE_CENTRE_EXAMEN);
        CentreExamenDTO centreExamenDTO = centreExamenMapper.toDto(updatedCentreExamen);

        restCentreExamenMockMvc.perform(put("/api/centre-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreExamenDTO)))
            .andExpect(status().isOk());

        // Validate the CentreExamen in the database
        List<CentreExamen> centreExamenList = centreExamenRepository.findAll();
        assertThat(centreExamenList).hasSize(databaseSizeBeforeUpdate);
        CentreExamen testCentreExamen = centreExamenList.get(centreExamenList.size() - 1);
        assertThat(testCentreExamen.getLibelleCentreExamen()).isEqualTo(UPDATED_LIBELLE_CENTRE_EXAMEN);
    }

    @Test
    @Transactional
    public void updateNonExistingCentreExamen() throws Exception {
        int databaseSizeBeforeUpdate = centreExamenRepository.findAll().size();

        // Create the CentreExamen
        CentreExamenDTO centreExamenDTO = centreExamenMapper.toDto(centreExamen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentreExamenMockMvc.perform(put("/api/centre-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreExamenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CentreExamen in the database
        List<CentreExamen> centreExamenList = centreExamenRepository.findAll();
        assertThat(centreExamenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCentreExamen() throws Exception {
        // Initialize the database
        centreExamenRepository.saveAndFlush(centreExamen);

        int databaseSizeBeforeDelete = centreExamenRepository.findAll().size();

        // Delete the centreExamen
        restCentreExamenMockMvc.perform(delete("/api/centre-examen/{id}", centreExamen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CentreExamen> centreExamenList = centreExamenRepository.findAll();
        assertThat(centreExamenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentreExamen.class);
        CentreExamen centreExamen1 = new CentreExamen();
        centreExamen1.setId(1L);
        CentreExamen centreExamen2 = new CentreExamen();
        centreExamen2.setId(centreExamen1.getId());
        assertThat(centreExamen1).isEqualTo(centreExamen2);
        centreExamen2.setId(2L);
        assertThat(centreExamen1).isNotEqualTo(centreExamen2);
        centreExamen1.setId(null);
        assertThat(centreExamen1).isNotEqualTo(centreExamen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentreExamenDTO.class);
        CentreExamenDTO centreExamenDTO1 = new CentreExamenDTO();
        centreExamenDTO1.setId(1L);
        CentreExamenDTO centreExamenDTO2 = new CentreExamenDTO();
        assertThat(centreExamenDTO1).isNotEqualTo(centreExamenDTO2);
        centreExamenDTO2.setId(centreExamenDTO1.getId());
        assertThat(centreExamenDTO1).isEqualTo(centreExamenDTO2);
        centreExamenDTO2.setId(2L);
        assertThat(centreExamenDTO1).isNotEqualTo(centreExamenDTO2);
        centreExamenDTO1.setId(null);
        assertThat(centreExamenDTO1).isNotEqualTo(centreExamenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(centreExamenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(centreExamenMapper.fromId(null)).isNull();
    }
}
