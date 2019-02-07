package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Mention;
import com.test.repository.MentionRepository;
import com.test.service.MentionService;
import com.test.service.dto.MentionDTO;
import com.test.service.mapper.MentionMapper;
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
 * Test class for the MentionResource REST controller.
 *
 * @see MentionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class MentionResourceIntTest {

    private static final String DEFAULT_LIBELLE_MENTION = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_MENTION = "BBBBBBBBBB";

    @Autowired
    private MentionRepository mentionRepository;

    @Autowired
    private MentionMapper mentionMapper;

    @Autowired
    private MentionService mentionService;

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

    private MockMvc restMentionMockMvc;

    private Mention mention;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MentionResource mentionResource = new MentionResource(mentionService);
        this.restMentionMockMvc = MockMvcBuilders.standaloneSetup(mentionResource)
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
    public static Mention createEntity(EntityManager em) {
        Mention mention = new Mention()
            .libelleMention(DEFAULT_LIBELLE_MENTION);
        return mention;
    }

    @Before
    public void initTest() {
        mention = createEntity(em);
    }

    @Test
    @Transactional
    public void createMention() throws Exception {
        int databaseSizeBeforeCreate = mentionRepository.findAll().size();

        // Create the Mention
        MentionDTO mentionDTO = mentionMapper.toDto(mention);
        restMentionMockMvc.perform(post("/api/mentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mentionDTO)))
            .andExpect(status().isCreated());

        // Validate the Mention in the database
        List<Mention> mentionList = mentionRepository.findAll();
        assertThat(mentionList).hasSize(databaseSizeBeforeCreate + 1);
        Mention testMention = mentionList.get(mentionList.size() - 1);
        assertThat(testMention.getLibelleMention()).isEqualTo(DEFAULT_LIBELLE_MENTION);
    }

    @Test
    @Transactional
    public void createMentionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mentionRepository.findAll().size();

        // Create the Mention with an existing ID
        mention.setId(1L);
        MentionDTO mentionDTO = mentionMapper.toDto(mention);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMentionMockMvc.perform(post("/api/mentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mentionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mention in the database
        List<Mention> mentionList = mentionRepository.findAll();
        assertThat(mentionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleMentionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mentionRepository.findAll().size();
        // set the field null
        mention.setLibelleMention(null);

        // Create the Mention, which fails.
        MentionDTO mentionDTO = mentionMapper.toDto(mention);

        restMentionMockMvc.perform(post("/api/mentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mentionDTO)))
            .andExpect(status().isBadRequest());

        List<Mention> mentionList = mentionRepository.findAll();
        assertThat(mentionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMentions() throws Exception {
        // Initialize the database
        mentionRepository.saveAndFlush(mention);

        // Get all the mentionList
        restMentionMockMvc.perform(get("/api/mentions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mention.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleMention").value(hasItem(DEFAULT_LIBELLE_MENTION.toString())));
    }
    
    @Test
    @Transactional
    public void getMention() throws Exception {
        // Initialize the database
        mentionRepository.saveAndFlush(mention);

        // Get the mention
        restMentionMockMvc.perform(get("/api/mentions/{id}", mention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mention.getId().intValue()))
            .andExpect(jsonPath("$.libelleMention").value(DEFAULT_LIBELLE_MENTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMention() throws Exception {
        // Get the mention
        restMentionMockMvc.perform(get("/api/mentions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMention() throws Exception {
        // Initialize the database
        mentionRepository.saveAndFlush(mention);

        int databaseSizeBeforeUpdate = mentionRepository.findAll().size();

        // Update the mention
        Mention updatedMention = mentionRepository.findById(mention.getId()).get();
        // Disconnect from session so that the updates on updatedMention are not directly saved in db
        em.detach(updatedMention);
        updatedMention
            .libelleMention(UPDATED_LIBELLE_MENTION);
        MentionDTO mentionDTO = mentionMapper.toDto(updatedMention);

        restMentionMockMvc.perform(put("/api/mentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mentionDTO)))
            .andExpect(status().isOk());

        // Validate the Mention in the database
        List<Mention> mentionList = mentionRepository.findAll();
        assertThat(mentionList).hasSize(databaseSizeBeforeUpdate);
        Mention testMention = mentionList.get(mentionList.size() - 1);
        assertThat(testMention.getLibelleMention()).isEqualTo(UPDATED_LIBELLE_MENTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMention() throws Exception {
        int databaseSizeBeforeUpdate = mentionRepository.findAll().size();

        // Create the Mention
        MentionDTO mentionDTO = mentionMapper.toDto(mention);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMentionMockMvc.perform(put("/api/mentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mentionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mention in the database
        List<Mention> mentionList = mentionRepository.findAll();
        assertThat(mentionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMention() throws Exception {
        // Initialize the database
        mentionRepository.saveAndFlush(mention);

        int databaseSizeBeforeDelete = mentionRepository.findAll().size();

        // Delete the mention
        restMentionMockMvc.perform(delete("/api/mentions/{id}", mention.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mention> mentionList = mentionRepository.findAll();
        assertThat(mentionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mention.class);
        Mention mention1 = new Mention();
        mention1.setId(1L);
        Mention mention2 = new Mention();
        mention2.setId(mention1.getId());
        assertThat(mention1).isEqualTo(mention2);
        mention2.setId(2L);
        assertThat(mention1).isNotEqualTo(mention2);
        mention1.setId(null);
        assertThat(mention1).isNotEqualTo(mention2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MentionDTO.class);
        MentionDTO mentionDTO1 = new MentionDTO();
        mentionDTO1.setId(1L);
        MentionDTO mentionDTO2 = new MentionDTO();
        assertThat(mentionDTO1).isNotEqualTo(mentionDTO2);
        mentionDTO2.setId(mentionDTO1.getId());
        assertThat(mentionDTO1).isEqualTo(mentionDTO2);
        mentionDTO2.setId(2L);
        assertThat(mentionDTO1).isNotEqualTo(mentionDTO2);
        mentionDTO1.setId(null);
        assertThat(mentionDTO1).isNotEqualTo(mentionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mentionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mentionMapper.fromId(null)).isNull();
    }
}
