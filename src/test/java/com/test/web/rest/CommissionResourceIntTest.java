package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Commission;
import com.test.repository.CommissionRepository;
import com.test.service.CommissionService;
import com.test.service.dto.CommissionDTO;
import com.test.service.mapper.CommissionMapper;
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
 * Test class for the CommissionResource REST controller.
 *
 * @see CommissionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class CommissionResourceIntTest {

    private static final String DEFAULT_LIBELLE_COMMISSION = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_COMMISSION = "BBBBBBBBBB";

    @Autowired
    private CommissionRepository commissionRepository;

    @Autowired
    private CommissionMapper commissionMapper;

    @Autowired
    private CommissionService commissionService;

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

    private MockMvc restCommissionMockMvc;

    private Commission commission;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommissionResource commissionResource = new CommissionResource(commissionService);
        this.restCommissionMockMvc = MockMvcBuilders.standaloneSetup(commissionResource)
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
    public static Commission createEntity(EntityManager em) {
        Commission commission = new Commission()
            .libelleCommission(DEFAULT_LIBELLE_COMMISSION);
        return commission;
    }

    @Before
    public void initTest() {
        commission = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommission() throws Exception {
        int databaseSizeBeforeCreate = commissionRepository.findAll().size();

        // Create the Commission
        CommissionDTO commissionDTO = commissionMapper.toDto(commission);
        restCommissionMockMvc.perform(post("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isCreated());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeCreate + 1);
        Commission testCommission = commissionList.get(commissionList.size() - 1);
        assertThat(testCommission.getLibelleCommission()).isEqualTo(DEFAULT_LIBELLE_COMMISSION);
    }

    @Test
    @Transactional
    public void createCommissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commissionRepository.findAll().size();

        // Create the Commission with an existing ID
        commission.setId(1L);
        CommissionDTO commissionDTO = commissionMapper.toDto(commission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommissionMockMvc.perform(post("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleCommissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = commissionRepository.findAll().size();
        // set the field null
        commission.setLibelleCommission(null);

        // Create the Commission, which fails.
        CommissionDTO commissionDTO = commissionMapper.toDto(commission);

        restCommissionMockMvc.perform(post("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isBadRequest());

        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommissions() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList
        restCommissionMockMvc.perform(get("/api/commissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commission.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleCommission").value(hasItem(DEFAULT_LIBELLE_COMMISSION.toString())));
    }
    
    @Test
    @Transactional
    public void getCommission() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get the commission
        restCommissionMockMvc.perform(get("/api/commissions/{id}", commission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commission.getId().intValue()))
            .andExpect(jsonPath("$.libelleCommission").value(DEFAULT_LIBELLE_COMMISSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommission() throws Exception {
        // Get the commission
        restCommissionMockMvc.perform(get("/api/commissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommission() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();

        // Update the commission
        Commission updatedCommission = commissionRepository.findById(commission.getId()).get();
        // Disconnect from session so that the updates on updatedCommission are not directly saved in db
        em.detach(updatedCommission);
        updatedCommission
            .libelleCommission(UPDATED_LIBELLE_COMMISSION);
        CommissionDTO commissionDTO = commissionMapper.toDto(updatedCommission);

        restCommissionMockMvc.perform(put("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isOk());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
        Commission testCommission = commissionList.get(commissionList.size() - 1);
        assertThat(testCommission.getLibelleCommission()).isEqualTo(UPDATED_LIBELLE_COMMISSION);
    }

    @Test
    @Transactional
    public void updateNonExistingCommission() throws Exception {
        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();

        // Create the Commission
        CommissionDTO commissionDTO = commissionMapper.toDto(commission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionMockMvc.perform(put("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommission() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        int databaseSizeBeforeDelete = commissionRepository.findAll().size();

        // Delete the commission
        restCommissionMockMvc.perform(delete("/api/commissions/{id}", commission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commission.class);
        Commission commission1 = new Commission();
        commission1.setId(1L);
        Commission commission2 = new Commission();
        commission2.setId(commission1.getId());
        assertThat(commission1).isEqualTo(commission2);
        commission2.setId(2L);
        assertThat(commission1).isNotEqualTo(commission2);
        commission1.setId(null);
        assertThat(commission1).isNotEqualTo(commission2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommissionDTO.class);
        CommissionDTO commissionDTO1 = new CommissionDTO();
        commissionDTO1.setId(1L);
        CommissionDTO commissionDTO2 = new CommissionDTO();
        assertThat(commissionDTO1).isNotEqualTo(commissionDTO2);
        commissionDTO2.setId(commissionDTO1.getId());
        assertThat(commissionDTO1).isEqualTo(commissionDTO2);
        commissionDTO2.setId(2L);
        assertThat(commissionDTO1).isNotEqualTo(commissionDTO2);
        commissionDTO1.setId(null);
        assertThat(commissionDTO1).isNotEqualTo(commissionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commissionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commissionMapper.fromId(null)).isNull();
    }
}
