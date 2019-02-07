package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.CentreCompositioJury;
import com.test.repository.CentreCompositioJuryRepository;
import com.test.service.CentreCompositioJuryService;
import com.test.service.dto.CentreCompositioJuryDTO;
import com.test.service.mapper.CentreCompositioJuryMapper;
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
 * Test class for the CentreCompositioJuryResource REST controller.
 *
 * @see CentreCompositioJuryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class CentreCompositioJuryResourceIntTest {

    @Autowired
    private CentreCompositioJuryRepository centreCompositioJuryRepository;

    @Autowired
    private CentreCompositioJuryMapper centreCompositioJuryMapper;

    @Autowired
    private CentreCompositioJuryService centreCompositioJuryService;

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

    private MockMvc restCentreCompositioJuryMockMvc;

    private CentreCompositioJury centreCompositioJury;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CentreCompositioJuryResource centreCompositioJuryResource = new CentreCompositioJuryResource(centreCompositioJuryService);
        this.restCentreCompositioJuryMockMvc = MockMvcBuilders.standaloneSetup(centreCompositioJuryResource)
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
    public static CentreCompositioJury createEntity(EntityManager em) {
        CentreCompositioJury centreCompositioJury = new CentreCompositioJury();
        return centreCompositioJury;
    }

    @Before
    public void initTest() {
        centreCompositioJury = createEntity(em);
    }

    @Test
    @Transactional
    public void createCentreCompositioJury() throws Exception {
        int databaseSizeBeforeCreate = centreCompositioJuryRepository.findAll().size();

        // Create the CentreCompositioJury
        CentreCompositioJuryDTO centreCompositioJuryDTO = centreCompositioJuryMapper.toDto(centreCompositioJury);
        restCentreCompositioJuryMockMvc.perform(post("/api/centre-compositio-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreCompositioJuryDTO)))
            .andExpect(status().isCreated());

        // Validate the CentreCompositioJury in the database
        List<CentreCompositioJury> centreCompositioJuryList = centreCompositioJuryRepository.findAll();
        assertThat(centreCompositioJuryList).hasSize(databaseSizeBeforeCreate + 1);
        CentreCompositioJury testCentreCompositioJury = centreCompositioJuryList.get(centreCompositioJuryList.size() - 1);
    }

    @Test
    @Transactional
    public void createCentreCompositioJuryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centreCompositioJuryRepository.findAll().size();

        // Create the CentreCompositioJury with an existing ID
        centreCompositioJury.setId(1L);
        CentreCompositioJuryDTO centreCompositioJuryDTO = centreCompositioJuryMapper.toDto(centreCompositioJury);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentreCompositioJuryMockMvc.perform(post("/api/centre-compositio-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreCompositioJuryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CentreCompositioJury in the database
        List<CentreCompositioJury> centreCompositioJuryList = centreCompositioJuryRepository.findAll();
        assertThat(centreCompositioJuryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCentreCompositioJuries() throws Exception {
        // Initialize the database
        centreCompositioJuryRepository.saveAndFlush(centreCompositioJury);

        // Get all the centreCompositioJuryList
        restCentreCompositioJuryMockMvc.perform(get("/api/centre-compositio-juries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centreCompositioJury.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCentreCompositioJury() throws Exception {
        // Initialize the database
        centreCompositioJuryRepository.saveAndFlush(centreCompositioJury);

        // Get the centreCompositioJury
        restCentreCompositioJuryMockMvc.perform(get("/api/centre-compositio-juries/{id}", centreCompositioJury.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(centreCompositioJury.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCentreCompositioJury() throws Exception {
        // Get the centreCompositioJury
        restCentreCompositioJuryMockMvc.perform(get("/api/centre-compositio-juries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCentreCompositioJury() throws Exception {
        // Initialize the database
        centreCompositioJuryRepository.saveAndFlush(centreCompositioJury);

        int databaseSizeBeforeUpdate = centreCompositioJuryRepository.findAll().size();

        // Update the centreCompositioJury
        CentreCompositioJury updatedCentreCompositioJury = centreCompositioJuryRepository.findById(centreCompositioJury.getId()).get();
        // Disconnect from session so that the updates on updatedCentreCompositioJury are not directly saved in db
        em.detach(updatedCentreCompositioJury);
        CentreCompositioJuryDTO centreCompositioJuryDTO = centreCompositioJuryMapper.toDto(updatedCentreCompositioJury);

        restCentreCompositioJuryMockMvc.perform(put("/api/centre-compositio-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreCompositioJuryDTO)))
            .andExpect(status().isOk());

        // Validate the CentreCompositioJury in the database
        List<CentreCompositioJury> centreCompositioJuryList = centreCompositioJuryRepository.findAll();
        assertThat(centreCompositioJuryList).hasSize(databaseSizeBeforeUpdate);
        CentreCompositioJury testCentreCompositioJury = centreCompositioJuryList.get(centreCompositioJuryList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCentreCompositioJury() throws Exception {
        int databaseSizeBeforeUpdate = centreCompositioJuryRepository.findAll().size();

        // Create the CentreCompositioJury
        CentreCompositioJuryDTO centreCompositioJuryDTO = centreCompositioJuryMapper.toDto(centreCompositioJury);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentreCompositioJuryMockMvc.perform(put("/api/centre-compositio-juries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centreCompositioJuryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CentreCompositioJury in the database
        List<CentreCompositioJury> centreCompositioJuryList = centreCompositioJuryRepository.findAll();
        assertThat(centreCompositioJuryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCentreCompositioJury() throws Exception {
        // Initialize the database
        centreCompositioJuryRepository.saveAndFlush(centreCompositioJury);

        int databaseSizeBeforeDelete = centreCompositioJuryRepository.findAll().size();

        // Delete the centreCompositioJury
        restCentreCompositioJuryMockMvc.perform(delete("/api/centre-compositio-juries/{id}", centreCompositioJury.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CentreCompositioJury> centreCompositioJuryList = centreCompositioJuryRepository.findAll();
        assertThat(centreCompositioJuryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentreCompositioJury.class);
        CentreCompositioJury centreCompositioJury1 = new CentreCompositioJury();
        centreCompositioJury1.setId(1L);
        CentreCompositioJury centreCompositioJury2 = new CentreCompositioJury();
        centreCompositioJury2.setId(centreCompositioJury1.getId());
        assertThat(centreCompositioJury1).isEqualTo(centreCompositioJury2);
        centreCompositioJury2.setId(2L);
        assertThat(centreCompositioJury1).isNotEqualTo(centreCompositioJury2);
        centreCompositioJury1.setId(null);
        assertThat(centreCompositioJury1).isNotEqualTo(centreCompositioJury2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentreCompositioJuryDTO.class);
        CentreCompositioJuryDTO centreCompositioJuryDTO1 = new CentreCompositioJuryDTO();
        centreCompositioJuryDTO1.setId(1L);
        CentreCompositioJuryDTO centreCompositioJuryDTO2 = new CentreCompositioJuryDTO();
        assertThat(centreCompositioJuryDTO1).isNotEqualTo(centreCompositioJuryDTO2);
        centreCompositioJuryDTO2.setId(centreCompositioJuryDTO1.getId());
        assertThat(centreCompositioJuryDTO1).isEqualTo(centreCompositioJuryDTO2);
        centreCompositioJuryDTO2.setId(2L);
        assertThat(centreCompositioJuryDTO1).isNotEqualTo(centreCompositioJuryDTO2);
        centreCompositioJuryDTO1.setId(null);
        assertThat(centreCompositioJuryDTO1).isNotEqualTo(centreCompositioJuryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(centreCompositioJuryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(centreCompositioJuryMapper.fromId(null)).isNull();
    }
}
