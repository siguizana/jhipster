package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.ChoixEtablissement;
import com.test.repository.ChoixEtablissementRepository;
import com.test.service.ChoixEtablissementService;
import com.test.service.dto.ChoixEtablissementDTO;
import com.test.service.mapper.ChoixEtablissementMapper;
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
 * Test class for the ChoixEtablissementResource REST controller.
 *
 * @see ChoixEtablissementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class ChoixEtablissementResourceIntTest {

    private static final String DEFAULT_PRIORITE = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITE = "BBBBBBBBBB";

    @Autowired
    private ChoixEtablissementRepository choixEtablissementRepository;

    @Autowired
    private ChoixEtablissementMapper choixEtablissementMapper;

    @Autowired
    private ChoixEtablissementService choixEtablissementService;

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

    private MockMvc restChoixEtablissementMockMvc;

    private ChoixEtablissement choixEtablissement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChoixEtablissementResource choixEtablissementResource = new ChoixEtablissementResource(choixEtablissementService);
        this.restChoixEtablissementMockMvc = MockMvcBuilders.standaloneSetup(choixEtablissementResource)
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
    public static ChoixEtablissement createEntity(EntityManager em) {
        ChoixEtablissement choixEtablissement = new ChoixEtablissement()
            .priorite(DEFAULT_PRIORITE);
        return choixEtablissement;
    }

    @Before
    public void initTest() {
        choixEtablissement = createEntity(em);
    }

    @Test
    @Transactional
    public void createChoixEtablissement() throws Exception {
        int databaseSizeBeforeCreate = choixEtablissementRepository.findAll().size();

        // Create the ChoixEtablissement
        ChoixEtablissementDTO choixEtablissementDTO = choixEtablissementMapper.toDto(choixEtablissement);
        restChoixEtablissementMockMvc.perform(post("/api/choix-etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(choixEtablissementDTO)))
            .andExpect(status().isCreated());

        // Validate the ChoixEtablissement in the database
        List<ChoixEtablissement> choixEtablissementList = choixEtablissementRepository.findAll();
        assertThat(choixEtablissementList).hasSize(databaseSizeBeforeCreate + 1);
        ChoixEtablissement testChoixEtablissement = choixEtablissementList.get(choixEtablissementList.size() - 1);
        assertThat(testChoixEtablissement.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
    }

    @Test
    @Transactional
    public void createChoixEtablissementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = choixEtablissementRepository.findAll().size();

        // Create the ChoixEtablissement with an existing ID
        choixEtablissement.setId(1L);
        ChoixEtablissementDTO choixEtablissementDTO = choixEtablissementMapper.toDto(choixEtablissement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChoixEtablissementMockMvc.perform(post("/api/choix-etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(choixEtablissementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChoixEtablissement in the database
        List<ChoixEtablissement> choixEtablissementList = choixEtablissementRepository.findAll();
        assertThat(choixEtablissementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChoixEtablissements() throws Exception {
        // Initialize the database
        choixEtablissementRepository.saveAndFlush(choixEtablissement);

        // Get all the choixEtablissementList
        restChoixEtablissementMockMvc.perform(get("/api/choix-etablissements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(choixEtablissement.getId().intValue())))
            .andExpect(jsonPath("$.[*].priorite").value(hasItem(DEFAULT_PRIORITE.toString())));
    }
    
    @Test
    @Transactional
    public void getChoixEtablissement() throws Exception {
        // Initialize the database
        choixEtablissementRepository.saveAndFlush(choixEtablissement);

        // Get the choixEtablissement
        restChoixEtablissementMockMvc.perform(get("/api/choix-etablissements/{id}", choixEtablissement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(choixEtablissement.getId().intValue()))
            .andExpect(jsonPath("$.priorite").value(DEFAULT_PRIORITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChoixEtablissement() throws Exception {
        // Get the choixEtablissement
        restChoixEtablissementMockMvc.perform(get("/api/choix-etablissements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChoixEtablissement() throws Exception {
        // Initialize the database
        choixEtablissementRepository.saveAndFlush(choixEtablissement);

        int databaseSizeBeforeUpdate = choixEtablissementRepository.findAll().size();

        // Update the choixEtablissement
        ChoixEtablissement updatedChoixEtablissement = choixEtablissementRepository.findById(choixEtablissement.getId()).get();
        // Disconnect from session so that the updates on updatedChoixEtablissement are not directly saved in db
        em.detach(updatedChoixEtablissement);
        updatedChoixEtablissement
            .priorite(UPDATED_PRIORITE);
        ChoixEtablissementDTO choixEtablissementDTO = choixEtablissementMapper.toDto(updatedChoixEtablissement);

        restChoixEtablissementMockMvc.perform(put("/api/choix-etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(choixEtablissementDTO)))
            .andExpect(status().isOk());

        // Validate the ChoixEtablissement in the database
        List<ChoixEtablissement> choixEtablissementList = choixEtablissementRepository.findAll();
        assertThat(choixEtablissementList).hasSize(databaseSizeBeforeUpdate);
        ChoixEtablissement testChoixEtablissement = choixEtablissementList.get(choixEtablissementList.size() - 1);
        assertThat(testChoixEtablissement.getPriorite()).isEqualTo(UPDATED_PRIORITE);
    }

    @Test
    @Transactional
    public void updateNonExistingChoixEtablissement() throws Exception {
        int databaseSizeBeforeUpdate = choixEtablissementRepository.findAll().size();

        // Create the ChoixEtablissement
        ChoixEtablissementDTO choixEtablissementDTO = choixEtablissementMapper.toDto(choixEtablissement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChoixEtablissementMockMvc.perform(put("/api/choix-etablissements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(choixEtablissementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChoixEtablissement in the database
        List<ChoixEtablissement> choixEtablissementList = choixEtablissementRepository.findAll();
        assertThat(choixEtablissementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChoixEtablissement() throws Exception {
        // Initialize the database
        choixEtablissementRepository.saveAndFlush(choixEtablissement);

        int databaseSizeBeforeDelete = choixEtablissementRepository.findAll().size();

        // Delete the choixEtablissement
        restChoixEtablissementMockMvc.perform(delete("/api/choix-etablissements/{id}", choixEtablissement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChoixEtablissement> choixEtablissementList = choixEtablissementRepository.findAll();
        assertThat(choixEtablissementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChoixEtablissement.class);
        ChoixEtablissement choixEtablissement1 = new ChoixEtablissement();
        choixEtablissement1.setId(1L);
        ChoixEtablissement choixEtablissement2 = new ChoixEtablissement();
        choixEtablissement2.setId(choixEtablissement1.getId());
        assertThat(choixEtablissement1).isEqualTo(choixEtablissement2);
        choixEtablissement2.setId(2L);
        assertThat(choixEtablissement1).isNotEqualTo(choixEtablissement2);
        choixEtablissement1.setId(null);
        assertThat(choixEtablissement1).isNotEqualTo(choixEtablissement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChoixEtablissementDTO.class);
        ChoixEtablissementDTO choixEtablissementDTO1 = new ChoixEtablissementDTO();
        choixEtablissementDTO1.setId(1L);
        ChoixEtablissementDTO choixEtablissementDTO2 = new ChoixEtablissementDTO();
        assertThat(choixEtablissementDTO1).isNotEqualTo(choixEtablissementDTO2);
        choixEtablissementDTO2.setId(choixEtablissementDTO1.getId());
        assertThat(choixEtablissementDTO1).isEqualTo(choixEtablissementDTO2);
        choixEtablissementDTO2.setId(2L);
        assertThat(choixEtablissementDTO1).isNotEqualTo(choixEtablissementDTO2);
        choixEtablissementDTO1.setId(null);
        assertThat(choixEtablissementDTO1).isNotEqualTo(choixEtablissementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(choixEtablissementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(choixEtablissementMapper.fromId(null)).isNull();
    }
}
