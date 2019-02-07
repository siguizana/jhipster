package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.SpecialiteOption;
import com.test.repository.SpecialiteOptionRepository;
import com.test.service.SpecialiteOptionService;
import com.test.service.dto.SpecialiteOptionDTO;
import com.test.service.mapper.SpecialiteOptionMapper;
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
 * Test class for the SpecialiteOptionResource REST controller.
 *
 * @see SpecialiteOptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class SpecialiteOptionResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private SpecialiteOptionRepository specialiteOptionRepository;

    @Autowired
    private SpecialiteOptionMapper specialiteOptionMapper;

    @Autowired
    private SpecialiteOptionService specialiteOptionService;

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

    private MockMvc restSpecialiteOptionMockMvc;

    private SpecialiteOption specialiteOption;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpecialiteOptionResource specialiteOptionResource = new SpecialiteOptionResource(specialiteOptionService);
        this.restSpecialiteOptionMockMvc = MockMvcBuilders.standaloneSetup(specialiteOptionResource)
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
    public static SpecialiteOption createEntity(EntityManager em) {
        SpecialiteOption specialiteOption = new SpecialiteOption()
            .libelle(DEFAULT_LIBELLE);
        return specialiteOption;
    }

    @Before
    public void initTest() {
        specialiteOption = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialiteOption() throws Exception {
        int databaseSizeBeforeCreate = specialiteOptionRepository.findAll().size();

        // Create the SpecialiteOption
        SpecialiteOptionDTO specialiteOptionDTO = specialiteOptionMapper.toDto(specialiteOption);
        restSpecialiteOptionMockMvc.perform(post("/api/specialite-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialiteOptionDTO)))
            .andExpect(status().isCreated());

        // Validate the SpecialiteOption in the database
        List<SpecialiteOption> specialiteOptionList = specialiteOptionRepository.findAll();
        assertThat(specialiteOptionList).hasSize(databaseSizeBeforeCreate + 1);
        SpecialiteOption testSpecialiteOption = specialiteOptionList.get(specialiteOptionList.size() - 1);
        assertThat(testSpecialiteOption.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createSpecialiteOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialiteOptionRepository.findAll().size();

        // Create the SpecialiteOption with an existing ID
        specialiteOption.setId(1L);
        SpecialiteOptionDTO specialiteOptionDTO = specialiteOptionMapper.toDto(specialiteOption);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialiteOptionMockMvc.perform(post("/api/specialite-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialiteOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpecialiteOption in the database
        List<SpecialiteOption> specialiteOptionList = specialiteOptionRepository.findAll();
        assertThat(specialiteOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialiteOptionRepository.findAll().size();
        // set the field null
        specialiteOption.setLibelle(null);

        // Create the SpecialiteOption, which fails.
        SpecialiteOptionDTO specialiteOptionDTO = specialiteOptionMapper.toDto(specialiteOption);

        restSpecialiteOptionMockMvc.perform(post("/api/specialite-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialiteOptionDTO)))
            .andExpect(status().isBadRequest());

        List<SpecialiteOption> specialiteOptionList = specialiteOptionRepository.findAll();
        assertThat(specialiteOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpecialiteOptions() throws Exception {
        // Initialize the database
        specialiteOptionRepository.saveAndFlush(specialiteOption);

        // Get all the specialiteOptionList
        restSpecialiteOptionMockMvc.perform(get("/api/specialite-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialiteOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getSpecialiteOption() throws Exception {
        // Initialize the database
        specialiteOptionRepository.saveAndFlush(specialiteOption);

        // Get the specialiteOption
        restSpecialiteOptionMockMvc.perform(get("/api/specialite-options/{id}", specialiteOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(specialiteOption.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSpecialiteOption() throws Exception {
        // Get the specialiteOption
        restSpecialiteOptionMockMvc.perform(get("/api/specialite-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialiteOption() throws Exception {
        // Initialize the database
        specialiteOptionRepository.saveAndFlush(specialiteOption);

        int databaseSizeBeforeUpdate = specialiteOptionRepository.findAll().size();

        // Update the specialiteOption
        SpecialiteOption updatedSpecialiteOption = specialiteOptionRepository.findById(specialiteOption.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialiteOption are not directly saved in db
        em.detach(updatedSpecialiteOption);
        updatedSpecialiteOption
            .libelle(UPDATED_LIBELLE);
        SpecialiteOptionDTO specialiteOptionDTO = specialiteOptionMapper.toDto(updatedSpecialiteOption);

        restSpecialiteOptionMockMvc.perform(put("/api/specialite-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialiteOptionDTO)))
            .andExpect(status().isOk());

        // Validate the SpecialiteOption in the database
        List<SpecialiteOption> specialiteOptionList = specialiteOptionRepository.findAll();
        assertThat(specialiteOptionList).hasSize(databaseSizeBeforeUpdate);
        SpecialiteOption testSpecialiteOption = specialiteOptionList.get(specialiteOptionList.size() - 1);
        assertThat(testSpecialiteOption.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialiteOption() throws Exception {
        int databaseSizeBeforeUpdate = specialiteOptionRepository.findAll().size();

        // Create the SpecialiteOption
        SpecialiteOptionDTO specialiteOptionDTO = specialiteOptionMapper.toDto(specialiteOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialiteOptionMockMvc.perform(put("/api/specialite-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialiteOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpecialiteOption in the database
        List<SpecialiteOption> specialiteOptionList = specialiteOptionRepository.findAll();
        assertThat(specialiteOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpecialiteOption() throws Exception {
        // Initialize the database
        specialiteOptionRepository.saveAndFlush(specialiteOption);

        int databaseSizeBeforeDelete = specialiteOptionRepository.findAll().size();

        // Delete the specialiteOption
        restSpecialiteOptionMockMvc.perform(delete("/api/specialite-options/{id}", specialiteOption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SpecialiteOption> specialiteOptionList = specialiteOptionRepository.findAll();
        assertThat(specialiteOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialiteOption.class);
        SpecialiteOption specialiteOption1 = new SpecialiteOption();
        specialiteOption1.setId(1L);
        SpecialiteOption specialiteOption2 = new SpecialiteOption();
        specialiteOption2.setId(specialiteOption1.getId());
        assertThat(specialiteOption1).isEqualTo(specialiteOption2);
        specialiteOption2.setId(2L);
        assertThat(specialiteOption1).isNotEqualTo(specialiteOption2);
        specialiteOption1.setId(null);
        assertThat(specialiteOption1).isNotEqualTo(specialiteOption2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialiteOptionDTO.class);
        SpecialiteOptionDTO specialiteOptionDTO1 = new SpecialiteOptionDTO();
        specialiteOptionDTO1.setId(1L);
        SpecialiteOptionDTO specialiteOptionDTO2 = new SpecialiteOptionDTO();
        assertThat(specialiteOptionDTO1).isNotEqualTo(specialiteOptionDTO2);
        specialiteOptionDTO2.setId(specialiteOptionDTO1.getId());
        assertThat(specialiteOptionDTO1).isEqualTo(specialiteOptionDTO2);
        specialiteOptionDTO2.setId(2L);
        assertThat(specialiteOptionDTO1).isNotEqualTo(specialiteOptionDTO2);
        specialiteOptionDTO1.setId(null);
        assertThat(specialiteOptionDTO1).isNotEqualTo(specialiteOptionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(specialiteOptionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(specialiteOptionMapper.fromId(null)).isNull();
    }
}
