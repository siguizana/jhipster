package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Sanction;
import com.test.repository.SanctionRepository;
import com.test.service.SanctionService;
import com.test.service.dto.SanctionDTO;
import com.test.service.mapper.SanctionMapper;
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
 * Test class for the SanctionResource REST controller.
 *
 * @see SanctionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class SanctionResourceIntTest {

    private static final String DEFAULT_LIBELLE_SANCTION = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_SANCTION = "BBBBBBBBBB";

    @Autowired
    private SanctionRepository sanctionRepository;

    @Autowired
    private SanctionMapper sanctionMapper;

    @Autowired
    private SanctionService sanctionService;

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

    private MockMvc restSanctionMockMvc;

    private Sanction sanction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SanctionResource sanctionResource = new SanctionResource(sanctionService);
        this.restSanctionMockMvc = MockMvcBuilders.standaloneSetup(sanctionResource)
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
    public static Sanction createEntity(EntityManager em) {
        Sanction sanction = new Sanction()
            .libelleSanction(DEFAULT_LIBELLE_SANCTION);
        return sanction;
    }

    @Before
    public void initTest() {
        sanction = createEntity(em);
    }

    @Test
    @Transactional
    public void createSanction() throws Exception {
        int databaseSizeBeforeCreate = sanctionRepository.findAll().size();

        // Create the Sanction
        SanctionDTO sanctionDTO = sanctionMapper.toDto(sanction);
        restSanctionMockMvc.perform(post("/api/sanctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sanctionDTO)))
            .andExpect(status().isCreated());

        // Validate the Sanction in the database
        List<Sanction> sanctionList = sanctionRepository.findAll();
        assertThat(sanctionList).hasSize(databaseSizeBeforeCreate + 1);
        Sanction testSanction = sanctionList.get(sanctionList.size() - 1);
        assertThat(testSanction.getLibelleSanction()).isEqualTo(DEFAULT_LIBELLE_SANCTION);
    }

    @Test
    @Transactional
    public void createSanctionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sanctionRepository.findAll().size();

        // Create the Sanction with an existing ID
        sanction.setId(1L);
        SanctionDTO sanctionDTO = sanctionMapper.toDto(sanction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSanctionMockMvc.perform(post("/api/sanctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sanctionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sanction in the database
        List<Sanction> sanctionList = sanctionRepository.findAll();
        assertThat(sanctionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleSanctionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sanctionRepository.findAll().size();
        // set the field null
        sanction.setLibelleSanction(null);

        // Create the Sanction, which fails.
        SanctionDTO sanctionDTO = sanctionMapper.toDto(sanction);

        restSanctionMockMvc.perform(post("/api/sanctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sanctionDTO)))
            .andExpect(status().isBadRequest());

        List<Sanction> sanctionList = sanctionRepository.findAll();
        assertThat(sanctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSanctions() throws Exception {
        // Initialize the database
        sanctionRepository.saveAndFlush(sanction);

        // Get all the sanctionList
        restSanctionMockMvc.perform(get("/api/sanctions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sanction.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleSanction").value(hasItem(DEFAULT_LIBELLE_SANCTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSanction() throws Exception {
        // Initialize the database
        sanctionRepository.saveAndFlush(sanction);

        // Get the sanction
        restSanctionMockMvc.perform(get("/api/sanctions/{id}", sanction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sanction.getId().intValue()))
            .andExpect(jsonPath("$.libelleSanction").value(DEFAULT_LIBELLE_SANCTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSanction() throws Exception {
        // Get the sanction
        restSanctionMockMvc.perform(get("/api/sanctions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSanction() throws Exception {
        // Initialize the database
        sanctionRepository.saveAndFlush(sanction);

        int databaseSizeBeforeUpdate = sanctionRepository.findAll().size();

        // Update the sanction
        Sanction updatedSanction = sanctionRepository.findById(sanction.getId()).get();
        // Disconnect from session so that the updates on updatedSanction are not directly saved in db
        em.detach(updatedSanction);
        updatedSanction
            .libelleSanction(UPDATED_LIBELLE_SANCTION);
        SanctionDTO sanctionDTO = sanctionMapper.toDto(updatedSanction);

        restSanctionMockMvc.perform(put("/api/sanctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sanctionDTO)))
            .andExpect(status().isOk());

        // Validate the Sanction in the database
        List<Sanction> sanctionList = sanctionRepository.findAll();
        assertThat(sanctionList).hasSize(databaseSizeBeforeUpdate);
        Sanction testSanction = sanctionList.get(sanctionList.size() - 1);
        assertThat(testSanction.getLibelleSanction()).isEqualTo(UPDATED_LIBELLE_SANCTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSanction() throws Exception {
        int databaseSizeBeforeUpdate = sanctionRepository.findAll().size();

        // Create the Sanction
        SanctionDTO sanctionDTO = sanctionMapper.toDto(sanction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSanctionMockMvc.perform(put("/api/sanctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sanctionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sanction in the database
        List<Sanction> sanctionList = sanctionRepository.findAll();
        assertThat(sanctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSanction() throws Exception {
        // Initialize the database
        sanctionRepository.saveAndFlush(sanction);

        int databaseSizeBeforeDelete = sanctionRepository.findAll().size();

        // Delete the sanction
        restSanctionMockMvc.perform(delete("/api/sanctions/{id}", sanction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sanction> sanctionList = sanctionRepository.findAll();
        assertThat(sanctionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sanction.class);
        Sanction sanction1 = new Sanction();
        sanction1.setId(1L);
        Sanction sanction2 = new Sanction();
        sanction2.setId(sanction1.getId());
        assertThat(sanction1).isEqualTo(sanction2);
        sanction2.setId(2L);
        assertThat(sanction1).isNotEqualTo(sanction2);
        sanction1.setId(null);
        assertThat(sanction1).isNotEqualTo(sanction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SanctionDTO.class);
        SanctionDTO sanctionDTO1 = new SanctionDTO();
        sanctionDTO1.setId(1L);
        SanctionDTO sanctionDTO2 = new SanctionDTO();
        assertThat(sanctionDTO1).isNotEqualTo(sanctionDTO2);
        sanctionDTO2.setId(sanctionDTO1.getId());
        assertThat(sanctionDTO1).isEqualTo(sanctionDTO2);
        sanctionDTO2.setId(2L);
        assertThat(sanctionDTO1).isNotEqualTo(sanctionDTO2);
        sanctionDTO1.setId(null);
        assertThat(sanctionDTO1).isNotEqualTo(sanctionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sanctionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sanctionMapper.fromId(null)).isNull();
    }
}
