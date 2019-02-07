package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.ZoneExamen;
import com.test.repository.ZoneExamenRepository;
import com.test.service.ZoneExamenService;
import com.test.service.dto.ZoneExamenDTO;
import com.test.service.mapper.ZoneExamenMapper;
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
 * Test class for the ZoneExamenResource REST controller.
 *
 * @see ZoneExamenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class ZoneExamenResourceIntTest {

    private static final String DEFAULT_CODE_ZONE_EXAMEN = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ZONE_EXAMEN = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_ZONE_EXAMEN = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_ZONE_EXAMEN = "BBBBBBBBBB";

    @Autowired
    private ZoneExamenRepository zoneExamenRepository;

    @Autowired
    private ZoneExamenMapper zoneExamenMapper;

    @Autowired
    private ZoneExamenService zoneExamenService;

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

    private MockMvc restZoneExamenMockMvc;

    private ZoneExamen zoneExamen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZoneExamenResource zoneExamenResource = new ZoneExamenResource(zoneExamenService);
        this.restZoneExamenMockMvc = MockMvcBuilders.standaloneSetup(zoneExamenResource)
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
    public static ZoneExamen createEntity(EntityManager em) {
        ZoneExamen zoneExamen = new ZoneExamen()
            .codeZoneExamen(DEFAULT_CODE_ZONE_EXAMEN)
            .libelleZoneExamen(DEFAULT_LIBELLE_ZONE_EXAMEN);
        return zoneExamen;
    }

    @Before
    public void initTest() {
        zoneExamen = createEntity(em);
    }

    @Test
    @Transactional
    public void createZoneExamen() throws Exception {
        int databaseSizeBeforeCreate = zoneExamenRepository.findAll().size();

        // Create the ZoneExamen
        ZoneExamenDTO zoneExamenDTO = zoneExamenMapper.toDto(zoneExamen);
        restZoneExamenMockMvc.perform(post("/api/zone-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zoneExamenDTO)))
            .andExpect(status().isCreated());

        // Validate the ZoneExamen in the database
        List<ZoneExamen> zoneExamenList = zoneExamenRepository.findAll();
        assertThat(zoneExamenList).hasSize(databaseSizeBeforeCreate + 1);
        ZoneExamen testZoneExamen = zoneExamenList.get(zoneExamenList.size() - 1);
        assertThat(testZoneExamen.getCodeZoneExamen()).isEqualTo(DEFAULT_CODE_ZONE_EXAMEN);
        assertThat(testZoneExamen.getLibelleZoneExamen()).isEqualTo(DEFAULT_LIBELLE_ZONE_EXAMEN);
    }

    @Test
    @Transactional
    public void createZoneExamenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zoneExamenRepository.findAll().size();

        // Create the ZoneExamen with an existing ID
        zoneExamen.setId(1L);
        ZoneExamenDTO zoneExamenDTO = zoneExamenMapper.toDto(zoneExamen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZoneExamenMockMvc.perform(post("/api/zone-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zoneExamenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ZoneExamen in the database
        List<ZoneExamen> zoneExamenList = zoneExamenRepository.findAll();
        assertThat(zoneExamenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleZoneExamenIsRequired() throws Exception {
        int databaseSizeBeforeTest = zoneExamenRepository.findAll().size();
        // set the field null
        zoneExamen.setLibelleZoneExamen(null);

        // Create the ZoneExamen, which fails.
        ZoneExamenDTO zoneExamenDTO = zoneExamenMapper.toDto(zoneExamen);

        restZoneExamenMockMvc.perform(post("/api/zone-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zoneExamenDTO)))
            .andExpect(status().isBadRequest());

        List<ZoneExamen> zoneExamenList = zoneExamenRepository.findAll();
        assertThat(zoneExamenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZoneExamen() throws Exception {
        // Initialize the database
        zoneExamenRepository.saveAndFlush(zoneExamen);

        // Get all the zoneExamenList
        restZoneExamenMockMvc.perform(get("/api/zone-examen?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zoneExamen.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeZoneExamen").value(hasItem(DEFAULT_CODE_ZONE_EXAMEN.toString())))
            .andExpect(jsonPath("$.[*].libelleZoneExamen").value(hasItem(DEFAULT_LIBELLE_ZONE_EXAMEN.toString())));
    }
    
    @Test
    @Transactional
    public void getZoneExamen() throws Exception {
        // Initialize the database
        zoneExamenRepository.saveAndFlush(zoneExamen);

        // Get the zoneExamen
        restZoneExamenMockMvc.perform(get("/api/zone-examen/{id}", zoneExamen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zoneExamen.getId().intValue()))
            .andExpect(jsonPath("$.codeZoneExamen").value(DEFAULT_CODE_ZONE_EXAMEN.toString()))
            .andExpect(jsonPath("$.libelleZoneExamen").value(DEFAULT_LIBELLE_ZONE_EXAMEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingZoneExamen() throws Exception {
        // Get the zoneExamen
        restZoneExamenMockMvc.perform(get("/api/zone-examen/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZoneExamen() throws Exception {
        // Initialize the database
        zoneExamenRepository.saveAndFlush(zoneExamen);

        int databaseSizeBeforeUpdate = zoneExamenRepository.findAll().size();

        // Update the zoneExamen
        ZoneExamen updatedZoneExamen = zoneExamenRepository.findById(zoneExamen.getId()).get();
        // Disconnect from session so that the updates on updatedZoneExamen are not directly saved in db
        em.detach(updatedZoneExamen);
        updatedZoneExamen
            .codeZoneExamen(UPDATED_CODE_ZONE_EXAMEN)
            .libelleZoneExamen(UPDATED_LIBELLE_ZONE_EXAMEN);
        ZoneExamenDTO zoneExamenDTO = zoneExamenMapper.toDto(updatedZoneExamen);

        restZoneExamenMockMvc.perform(put("/api/zone-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zoneExamenDTO)))
            .andExpect(status().isOk());

        // Validate the ZoneExamen in the database
        List<ZoneExamen> zoneExamenList = zoneExamenRepository.findAll();
        assertThat(zoneExamenList).hasSize(databaseSizeBeforeUpdate);
        ZoneExamen testZoneExamen = zoneExamenList.get(zoneExamenList.size() - 1);
        assertThat(testZoneExamen.getCodeZoneExamen()).isEqualTo(UPDATED_CODE_ZONE_EXAMEN);
        assertThat(testZoneExamen.getLibelleZoneExamen()).isEqualTo(UPDATED_LIBELLE_ZONE_EXAMEN);
    }

    @Test
    @Transactional
    public void updateNonExistingZoneExamen() throws Exception {
        int databaseSizeBeforeUpdate = zoneExamenRepository.findAll().size();

        // Create the ZoneExamen
        ZoneExamenDTO zoneExamenDTO = zoneExamenMapper.toDto(zoneExamen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZoneExamenMockMvc.perform(put("/api/zone-examen")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zoneExamenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ZoneExamen in the database
        List<ZoneExamen> zoneExamenList = zoneExamenRepository.findAll();
        assertThat(zoneExamenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZoneExamen() throws Exception {
        // Initialize the database
        zoneExamenRepository.saveAndFlush(zoneExamen);

        int databaseSizeBeforeDelete = zoneExamenRepository.findAll().size();

        // Delete the zoneExamen
        restZoneExamenMockMvc.perform(delete("/api/zone-examen/{id}", zoneExamen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ZoneExamen> zoneExamenList = zoneExamenRepository.findAll();
        assertThat(zoneExamenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZoneExamen.class);
        ZoneExamen zoneExamen1 = new ZoneExamen();
        zoneExamen1.setId(1L);
        ZoneExamen zoneExamen2 = new ZoneExamen();
        zoneExamen2.setId(zoneExamen1.getId());
        assertThat(zoneExamen1).isEqualTo(zoneExamen2);
        zoneExamen2.setId(2L);
        assertThat(zoneExamen1).isNotEqualTo(zoneExamen2);
        zoneExamen1.setId(null);
        assertThat(zoneExamen1).isNotEqualTo(zoneExamen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZoneExamenDTO.class);
        ZoneExamenDTO zoneExamenDTO1 = new ZoneExamenDTO();
        zoneExamenDTO1.setId(1L);
        ZoneExamenDTO zoneExamenDTO2 = new ZoneExamenDTO();
        assertThat(zoneExamenDTO1).isNotEqualTo(zoneExamenDTO2);
        zoneExamenDTO2.setId(zoneExamenDTO1.getId());
        assertThat(zoneExamenDTO1).isEqualTo(zoneExamenDTO2);
        zoneExamenDTO2.setId(2L);
        assertThat(zoneExamenDTO1).isNotEqualTo(zoneExamenDTO2);
        zoneExamenDTO1.setId(null);
        assertThat(zoneExamenDTO1).isNotEqualTo(zoneExamenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(zoneExamenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(zoneExamenMapper.fromId(null)).isNull();
    }
}
