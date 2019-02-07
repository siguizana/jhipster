package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.VillageSecteur;
import com.test.repository.VillageSecteurRepository;
import com.test.service.VillageSecteurService;
import com.test.service.dto.VillageSecteurDTO;
import com.test.service.mapper.VillageSecteurMapper;
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
 * Test class for the VillageSecteurResource REST controller.
 *
 * @see VillageSecteurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class VillageSecteurResourceIntTest {

    private static final String DEFAULT_CODE_VILLAGE_SECTEUR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_VILLAGE_SECTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_VILLAGE_SECTEUR = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_VILLAGE_SECTEUR = "BBBBBBBBBB";

    @Autowired
    private VillageSecteurRepository villageSecteurRepository;

    @Autowired
    private VillageSecteurMapper villageSecteurMapper;

    @Autowired
    private VillageSecteurService villageSecteurService;

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

    private MockMvc restVillageSecteurMockMvc;

    private VillageSecteur villageSecteur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VillageSecteurResource villageSecteurResource = new VillageSecteurResource(villageSecteurService);
        this.restVillageSecteurMockMvc = MockMvcBuilders.standaloneSetup(villageSecteurResource)
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
    public static VillageSecteur createEntity(EntityManager em) {
        VillageSecteur villageSecteur = new VillageSecteur()
            .codeVillageSecteur(DEFAULT_CODE_VILLAGE_SECTEUR)
            .libelleVillageSecteur(DEFAULT_LIBELLE_VILLAGE_SECTEUR);
        return villageSecteur;
    }

    @Before
    public void initTest() {
        villageSecteur = createEntity(em);
    }

    @Test
    @Transactional
    public void createVillageSecteur() throws Exception {
        int databaseSizeBeforeCreate = villageSecteurRepository.findAll().size();

        // Create the VillageSecteur
        VillageSecteurDTO villageSecteurDTO = villageSecteurMapper.toDto(villageSecteur);
        restVillageSecteurMockMvc.perform(post("/api/village-secteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(villageSecteurDTO)))
            .andExpect(status().isCreated());

        // Validate the VillageSecteur in the database
        List<VillageSecteur> villageSecteurList = villageSecteurRepository.findAll();
        assertThat(villageSecteurList).hasSize(databaseSizeBeforeCreate + 1);
        VillageSecteur testVillageSecteur = villageSecteurList.get(villageSecteurList.size() - 1);
        assertThat(testVillageSecteur.getCodeVillageSecteur()).isEqualTo(DEFAULT_CODE_VILLAGE_SECTEUR);
        assertThat(testVillageSecteur.getLibelleVillageSecteur()).isEqualTo(DEFAULT_LIBELLE_VILLAGE_SECTEUR);
    }

    @Test
    @Transactional
    public void createVillageSecteurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = villageSecteurRepository.findAll().size();

        // Create the VillageSecteur with an existing ID
        villageSecteur.setId(1L);
        VillageSecteurDTO villageSecteurDTO = villageSecteurMapper.toDto(villageSecteur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVillageSecteurMockMvc.perform(post("/api/village-secteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(villageSecteurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VillageSecteur in the database
        List<VillageSecteur> villageSecteurList = villageSecteurRepository.findAll();
        assertThat(villageSecteurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleVillageSecteurIsRequired() throws Exception {
        int databaseSizeBeforeTest = villageSecteurRepository.findAll().size();
        // set the field null
        villageSecteur.setLibelleVillageSecteur(null);

        // Create the VillageSecteur, which fails.
        VillageSecteurDTO villageSecteurDTO = villageSecteurMapper.toDto(villageSecteur);

        restVillageSecteurMockMvc.perform(post("/api/village-secteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(villageSecteurDTO)))
            .andExpect(status().isBadRequest());

        List<VillageSecteur> villageSecteurList = villageSecteurRepository.findAll();
        assertThat(villageSecteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVillageSecteurs() throws Exception {
        // Initialize the database
        villageSecteurRepository.saveAndFlush(villageSecteur);

        // Get all the villageSecteurList
        restVillageSecteurMockMvc.perform(get("/api/village-secteurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(villageSecteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeVillageSecteur").value(hasItem(DEFAULT_CODE_VILLAGE_SECTEUR.toString())))
            .andExpect(jsonPath("$.[*].libelleVillageSecteur").value(hasItem(DEFAULT_LIBELLE_VILLAGE_SECTEUR.toString())));
    }
    
    @Test
    @Transactional
    public void getVillageSecteur() throws Exception {
        // Initialize the database
        villageSecteurRepository.saveAndFlush(villageSecteur);

        // Get the villageSecteur
        restVillageSecteurMockMvc.perform(get("/api/village-secteurs/{id}", villageSecteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(villageSecteur.getId().intValue()))
            .andExpect(jsonPath("$.codeVillageSecteur").value(DEFAULT_CODE_VILLAGE_SECTEUR.toString()))
            .andExpect(jsonPath("$.libelleVillageSecteur").value(DEFAULT_LIBELLE_VILLAGE_SECTEUR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVillageSecteur() throws Exception {
        // Get the villageSecteur
        restVillageSecteurMockMvc.perform(get("/api/village-secteurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVillageSecteur() throws Exception {
        // Initialize the database
        villageSecteurRepository.saveAndFlush(villageSecteur);

        int databaseSizeBeforeUpdate = villageSecteurRepository.findAll().size();

        // Update the villageSecteur
        VillageSecteur updatedVillageSecteur = villageSecteurRepository.findById(villageSecteur.getId()).get();
        // Disconnect from session so that the updates on updatedVillageSecteur are not directly saved in db
        em.detach(updatedVillageSecteur);
        updatedVillageSecteur
            .codeVillageSecteur(UPDATED_CODE_VILLAGE_SECTEUR)
            .libelleVillageSecteur(UPDATED_LIBELLE_VILLAGE_SECTEUR);
        VillageSecteurDTO villageSecteurDTO = villageSecteurMapper.toDto(updatedVillageSecteur);

        restVillageSecteurMockMvc.perform(put("/api/village-secteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(villageSecteurDTO)))
            .andExpect(status().isOk());

        // Validate the VillageSecteur in the database
        List<VillageSecteur> villageSecteurList = villageSecteurRepository.findAll();
        assertThat(villageSecteurList).hasSize(databaseSizeBeforeUpdate);
        VillageSecteur testVillageSecteur = villageSecteurList.get(villageSecteurList.size() - 1);
        assertThat(testVillageSecteur.getCodeVillageSecteur()).isEqualTo(UPDATED_CODE_VILLAGE_SECTEUR);
        assertThat(testVillageSecteur.getLibelleVillageSecteur()).isEqualTo(UPDATED_LIBELLE_VILLAGE_SECTEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingVillageSecteur() throws Exception {
        int databaseSizeBeforeUpdate = villageSecteurRepository.findAll().size();

        // Create the VillageSecteur
        VillageSecteurDTO villageSecteurDTO = villageSecteurMapper.toDto(villageSecteur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVillageSecteurMockMvc.perform(put("/api/village-secteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(villageSecteurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VillageSecteur in the database
        List<VillageSecteur> villageSecteurList = villageSecteurRepository.findAll();
        assertThat(villageSecteurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVillageSecteur() throws Exception {
        // Initialize the database
        villageSecteurRepository.saveAndFlush(villageSecteur);

        int databaseSizeBeforeDelete = villageSecteurRepository.findAll().size();

        // Delete the villageSecteur
        restVillageSecteurMockMvc.perform(delete("/api/village-secteurs/{id}", villageSecteur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VillageSecteur> villageSecteurList = villageSecteurRepository.findAll();
        assertThat(villageSecteurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VillageSecteur.class);
        VillageSecteur villageSecteur1 = new VillageSecteur();
        villageSecteur1.setId(1L);
        VillageSecteur villageSecteur2 = new VillageSecteur();
        villageSecteur2.setId(villageSecteur1.getId());
        assertThat(villageSecteur1).isEqualTo(villageSecteur2);
        villageSecteur2.setId(2L);
        assertThat(villageSecteur1).isNotEqualTo(villageSecteur2);
        villageSecteur1.setId(null);
        assertThat(villageSecteur1).isNotEqualTo(villageSecteur2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VillageSecteurDTO.class);
        VillageSecteurDTO villageSecteurDTO1 = new VillageSecteurDTO();
        villageSecteurDTO1.setId(1L);
        VillageSecteurDTO villageSecteurDTO2 = new VillageSecteurDTO();
        assertThat(villageSecteurDTO1).isNotEqualTo(villageSecteurDTO2);
        villageSecteurDTO2.setId(villageSecteurDTO1.getId());
        assertThat(villageSecteurDTO1).isEqualTo(villageSecteurDTO2);
        villageSecteurDTO2.setId(2L);
        assertThat(villageSecteurDTO1).isNotEqualTo(villageSecteurDTO2);
        villageSecteurDTO1.setId(null);
        assertThat(villageSecteurDTO1).isNotEqualTo(villageSecteurDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(villageSecteurMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(villageSecteurMapper.fromId(null)).isNull();
    }
}
