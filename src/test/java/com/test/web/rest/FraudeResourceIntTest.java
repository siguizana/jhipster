package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Fraude;
import com.test.repository.FraudeRepository;
import com.test.service.FraudeService;
import com.test.service.dto.FraudeDTO;
import com.test.service.mapper.FraudeMapper;
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
 * Test class for the FraudeResource REST controller.
 *
 * @see FraudeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class FraudeResourceIntTest {

    private static final String DEFAULT_LIBELLE_FRAUDE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_FRAUDE = "BBBBBBBBBB";

    @Autowired
    private FraudeRepository fraudeRepository;

    @Autowired
    private FraudeMapper fraudeMapper;

    @Autowired
    private FraudeService fraudeService;

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

    private MockMvc restFraudeMockMvc;

    private Fraude fraude;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FraudeResource fraudeResource = new FraudeResource(fraudeService);
        this.restFraudeMockMvc = MockMvcBuilders.standaloneSetup(fraudeResource)
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
    public static Fraude createEntity(EntityManager em) {
        Fraude fraude = new Fraude()
            .libelleFraude(DEFAULT_LIBELLE_FRAUDE);
        return fraude;
    }

    @Before
    public void initTest() {
        fraude = createEntity(em);
    }

    @Test
    @Transactional
    public void createFraude() throws Exception {
        int databaseSizeBeforeCreate = fraudeRepository.findAll().size();

        // Create the Fraude
        FraudeDTO fraudeDTO = fraudeMapper.toDto(fraude);
        restFraudeMockMvc.perform(post("/api/fraudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraudeDTO)))
            .andExpect(status().isCreated());

        // Validate the Fraude in the database
        List<Fraude> fraudeList = fraudeRepository.findAll();
        assertThat(fraudeList).hasSize(databaseSizeBeforeCreate + 1);
        Fraude testFraude = fraudeList.get(fraudeList.size() - 1);
        assertThat(testFraude.getLibelleFraude()).isEqualTo(DEFAULT_LIBELLE_FRAUDE);
    }

    @Test
    @Transactional
    public void createFraudeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fraudeRepository.findAll().size();

        // Create the Fraude with an existing ID
        fraude.setId(1L);
        FraudeDTO fraudeDTO = fraudeMapper.toDto(fraude);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraudeMockMvc.perform(post("/api/fraudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraudeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fraude in the database
        List<Fraude> fraudeList = fraudeRepository.findAll();
        assertThat(fraudeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleFraudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fraudeRepository.findAll().size();
        // set the field null
        fraude.setLibelleFraude(null);

        // Create the Fraude, which fails.
        FraudeDTO fraudeDTO = fraudeMapper.toDto(fraude);

        restFraudeMockMvc.perform(post("/api/fraudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraudeDTO)))
            .andExpect(status().isBadRequest());

        List<Fraude> fraudeList = fraudeRepository.findAll();
        assertThat(fraudeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFraudes() throws Exception {
        // Initialize the database
        fraudeRepository.saveAndFlush(fraude);

        // Get all the fraudeList
        restFraudeMockMvc.perform(get("/api/fraudes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fraude.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleFraude").value(hasItem(DEFAULT_LIBELLE_FRAUDE.toString())));
    }
    
    @Test
    @Transactional
    public void getFraude() throws Exception {
        // Initialize the database
        fraudeRepository.saveAndFlush(fraude);

        // Get the fraude
        restFraudeMockMvc.perform(get("/api/fraudes/{id}", fraude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fraude.getId().intValue()))
            .andExpect(jsonPath("$.libelleFraude").value(DEFAULT_LIBELLE_FRAUDE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFraude() throws Exception {
        // Get the fraude
        restFraudeMockMvc.perform(get("/api/fraudes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFraude() throws Exception {
        // Initialize the database
        fraudeRepository.saveAndFlush(fraude);

        int databaseSizeBeforeUpdate = fraudeRepository.findAll().size();

        // Update the fraude
        Fraude updatedFraude = fraudeRepository.findById(fraude.getId()).get();
        // Disconnect from session so that the updates on updatedFraude are not directly saved in db
        em.detach(updatedFraude);
        updatedFraude
            .libelleFraude(UPDATED_LIBELLE_FRAUDE);
        FraudeDTO fraudeDTO = fraudeMapper.toDto(updatedFraude);

        restFraudeMockMvc.perform(put("/api/fraudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraudeDTO)))
            .andExpect(status().isOk());

        // Validate the Fraude in the database
        List<Fraude> fraudeList = fraudeRepository.findAll();
        assertThat(fraudeList).hasSize(databaseSizeBeforeUpdate);
        Fraude testFraude = fraudeList.get(fraudeList.size() - 1);
        assertThat(testFraude.getLibelleFraude()).isEqualTo(UPDATED_LIBELLE_FRAUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingFraude() throws Exception {
        int databaseSizeBeforeUpdate = fraudeRepository.findAll().size();

        // Create the Fraude
        FraudeDTO fraudeDTO = fraudeMapper.toDto(fraude);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraudeMockMvc.perform(put("/api/fraudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraudeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fraude in the database
        List<Fraude> fraudeList = fraudeRepository.findAll();
        assertThat(fraudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFraude() throws Exception {
        // Initialize the database
        fraudeRepository.saveAndFlush(fraude);

        int databaseSizeBeforeDelete = fraudeRepository.findAll().size();

        // Delete the fraude
        restFraudeMockMvc.perform(delete("/api/fraudes/{id}", fraude.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fraude> fraudeList = fraudeRepository.findAll();
        assertThat(fraudeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fraude.class);
        Fraude fraude1 = new Fraude();
        fraude1.setId(1L);
        Fraude fraude2 = new Fraude();
        fraude2.setId(fraude1.getId());
        assertThat(fraude1).isEqualTo(fraude2);
        fraude2.setId(2L);
        assertThat(fraude1).isNotEqualTo(fraude2);
        fraude1.setId(null);
        assertThat(fraude1).isNotEqualTo(fraude2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FraudeDTO.class);
        FraudeDTO fraudeDTO1 = new FraudeDTO();
        fraudeDTO1.setId(1L);
        FraudeDTO fraudeDTO2 = new FraudeDTO();
        assertThat(fraudeDTO1).isNotEqualTo(fraudeDTO2);
        fraudeDTO2.setId(fraudeDTO1.getId());
        assertThat(fraudeDTO1).isEqualTo(fraudeDTO2);
        fraudeDTO2.setId(2L);
        assertThat(fraudeDTO1).isNotEqualTo(fraudeDTO2);
        fraudeDTO1.setId(null);
        assertThat(fraudeDTO1).isNotEqualTo(fraudeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fraudeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fraudeMapper.fromId(null)).isNull();
    }
}
