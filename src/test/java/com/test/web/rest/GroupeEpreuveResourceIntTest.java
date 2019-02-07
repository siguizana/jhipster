package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.GroupeEpreuve;
import com.test.repository.GroupeEpreuveRepository;
import com.test.service.GroupeEpreuveService;
import com.test.service.dto.GroupeEpreuveDTO;
import com.test.service.mapper.GroupeEpreuveMapper;
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
 * Test class for the GroupeEpreuveResource REST controller.
 *
 * @see GroupeEpreuveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class GroupeEpreuveResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private GroupeEpreuveRepository groupeEpreuveRepository;

    @Autowired
    private GroupeEpreuveMapper groupeEpreuveMapper;

    @Autowired
    private GroupeEpreuveService groupeEpreuveService;

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

    private MockMvc restGroupeEpreuveMockMvc;

    private GroupeEpreuve groupeEpreuve;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupeEpreuveResource groupeEpreuveResource = new GroupeEpreuveResource(groupeEpreuveService);
        this.restGroupeEpreuveMockMvc = MockMvcBuilders.standaloneSetup(groupeEpreuveResource)
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
    public static GroupeEpreuve createEntity(EntityManager em) {
        GroupeEpreuve groupeEpreuve = new GroupeEpreuve()
            .libelle(DEFAULT_LIBELLE);
        return groupeEpreuve;
    }

    @Before
    public void initTest() {
        groupeEpreuve = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupeEpreuve() throws Exception {
        int databaseSizeBeforeCreate = groupeEpreuveRepository.findAll().size();

        // Create the GroupeEpreuve
        GroupeEpreuveDTO groupeEpreuveDTO = groupeEpreuveMapper.toDto(groupeEpreuve);
        restGroupeEpreuveMockMvc.perform(post("/api/groupe-epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupeEpreuveDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupeEpreuve in the database
        List<GroupeEpreuve> groupeEpreuveList = groupeEpreuveRepository.findAll();
        assertThat(groupeEpreuveList).hasSize(databaseSizeBeforeCreate + 1);
        GroupeEpreuve testGroupeEpreuve = groupeEpreuveList.get(groupeEpreuveList.size() - 1);
        assertThat(testGroupeEpreuve.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createGroupeEpreuveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupeEpreuveRepository.findAll().size();

        // Create the GroupeEpreuve with an existing ID
        groupeEpreuve.setId(1L);
        GroupeEpreuveDTO groupeEpreuveDTO = groupeEpreuveMapper.toDto(groupeEpreuve);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupeEpreuveMockMvc.perform(post("/api/groupe-epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupeEpreuveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupeEpreuve in the database
        List<GroupeEpreuve> groupeEpreuveList = groupeEpreuveRepository.findAll();
        assertThat(groupeEpreuveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupeEpreuveRepository.findAll().size();
        // set the field null
        groupeEpreuve.setLibelle(null);

        // Create the GroupeEpreuve, which fails.
        GroupeEpreuveDTO groupeEpreuveDTO = groupeEpreuveMapper.toDto(groupeEpreuve);

        restGroupeEpreuveMockMvc.perform(post("/api/groupe-epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupeEpreuveDTO)))
            .andExpect(status().isBadRequest());

        List<GroupeEpreuve> groupeEpreuveList = groupeEpreuveRepository.findAll();
        assertThat(groupeEpreuveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupeEpreuves() throws Exception {
        // Initialize the database
        groupeEpreuveRepository.saveAndFlush(groupeEpreuve);

        // Get all the groupeEpreuveList
        restGroupeEpreuveMockMvc.perform(get("/api/groupe-epreuves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupeEpreuve.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
    
    @Test
    @Transactional
    public void getGroupeEpreuve() throws Exception {
        // Initialize the database
        groupeEpreuveRepository.saveAndFlush(groupeEpreuve);

        // Get the groupeEpreuve
        restGroupeEpreuveMockMvc.perform(get("/api/groupe-epreuves/{id}", groupeEpreuve.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupeEpreuve.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGroupeEpreuve() throws Exception {
        // Get the groupeEpreuve
        restGroupeEpreuveMockMvc.perform(get("/api/groupe-epreuves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupeEpreuve() throws Exception {
        // Initialize the database
        groupeEpreuveRepository.saveAndFlush(groupeEpreuve);

        int databaseSizeBeforeUpdate = groupeEpreuveRepository.findAll().size();

        // Update the groupeEpreuve
        GroupeEpreuve updatedGroupeEpreuve = groupeEpreuveRepository.findById(groupeEpreuve.getId()).get();
        // Disconnect from session so that the updates on updatedGroupeEpreuve are not directly saved in db
        em.detach(updatedGroupeEpreuve);
        updatedGroupeEpreuve
            .libelle(UPDATED_LIBELLE);
        GroupeEpreuveDTO groupeEpreuveDTO = groupeEpreuveMapper.toDto(updatedGroupeEpreuve);

        restGroupeEpreuveMockMvc.perform(put("/api/groupe-epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupeEpreuveDTO)))
            .andExpect(status().isOk());

        // Validate the GroupeEpreuve in the database
        List<GroupeEpreuve> groupeEpreuveList = groupeEpreuveRepository.findAll();
        assertThat(groupeEpreuveList).hasSize(databaseSizeBeforeUpdate);
        GroupeEpreuve testGroupeEpreuve = groupeEpreuveList.get(groupeEpreuveList.size() - 1);
        assertThat(testGroupeEpreuve.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupeEpreuve() throws Exception {
        int databaseSizeBeforeUpdate = groupeEpreuveRepository.findAll().size();

        // Create the GroupeEpreuve
        GroupeEpreuveDTO groupeEpreuveDTO = groupeEpreuveMapper.toDto(groupeEpreuve);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupeEpreuveMockMvc.perform(put("/api/groupe-epreuves")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupeEpreuveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupeEpreuve in the database
        List<GroupeEpreuve> groupeEpreuveList = groupeEpreuveRepository.findAll();
        assertThat(groupeEpreuveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupeEpreuve() throws Exception {
        // Initialize the database
        groupeEpreuveRepository.saveAndFlush(groupeEpreuve);

        int databaseSizeBeforeDelete = groupeEpreuveRepository.findAll().size();

        // Delete the groupeEpreuve
        restGroupeEpreuveMockMvc.perform(delete("/api/groupe-epreuves/{id}", groupeEpreuve.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GroupeEpreuve> groupeEpreuveList = groupeEpreuveRepository.findAll();
        assertThat(groupeEpreuveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupeEpreuve.class);
        GroupeEpreuve groupeEpreuve1 = new GroupeEpreuve();
        groupeEpreuve1.setId(1L);
        GroupeEpreuve groupeEpreuve2 = new GroupeEpreuve();
        groupeEpreuve2.setId(groupeEpreuve1.getId());
        assertThat(groupeEpreuve1).isEqualTo(groupeEpreuve2);
        groupeEpreuve2.setId(2L);
        assertThat(groupeEpreuve1).isNotEqualTo(groupeEpreuve2);
        groupeEpreuve1.setId(null);
        assertThat(groupeEpreuve1).isNotEqualTo(groupeEpreuve2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupeEpreuveDTO.class);
        GroupeEpreuveDTO groupeEpreuveDTO1 = new GroupeEpreuveDTO();
        groupeEpreuveDTO1.setId(1L);
        GroupeEpreuveDTO groupeEpreuveDTO2 = new GroupeEpreuveDTO();
        assertThat(groupeEpreuveDTO1).isNotEqualTo(groupeEpreuveDTO2);
        groupeEpreuveDTO2.setId(groupeEpreuveDTO1.getId());
        assertThat(groupeEpreuveDTO1).isEqualTo(groupeEpreuveDTO2);
        groupeEpreuveDTO2.setId(2L);
        assertThat(groupeEpreuveDTO1).isNotEqualTo(groupeEpreuveDTO2);
        groupeEpreuveDTO1.setId(null);
        assertThat(groupeEpreuveDTO1).isNotEqualTo(groupeEpreuveDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(groupeEpreuveMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(groupeEpreuveMapper.fromId(null)).isNull();
    }
}
