package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.NoteMembreCritere;
import com.test.repository.NoteMembreCritereRepository;
import com.test.service.NoteMembreCritereService;
import com.test.service.dto.NoteMembreCritereDTO;
import com.test.service.mapper.NoteMembreCritereMapper;
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
 * Test class for the NoteMembreCritereResource REST controller.
 *
 * @see NoteMembreCritereResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class NoteMembreCritereResourceIntTest {

    private static final Float DEFAULT_VALEUR_NOTE = 1F;
    private static final Float UPDATED_VALEUR_NOTE = 2F;

    @Autowired
    private NoteMembreCritereRepository noteMembreCritereRepository;

    @Autowired
    private NoteMembreCritereMapper noteMembreCritereMapper;

    @Autowired
    private NoteMembreCritereService noteMembreCritereService;

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

    private MockMvc restNoteMembreCritereMockMvc;

    private NoteMembreCritere noteMembreCritere;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NoteMembreCritereResource noteMembreCritereResource = new NoteMembreCritereResource(noteMembreCritereService);
        this.restNoteMembreCritereMockMvc = MockMvcBuilders.standaloneSetup(noteMembreCritereResource)
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
    public static NoteMembreCritere createEntity(EntityManager em) {
        NoteMembreCritere noteMembreCritere = new NoteMembreCritere()
            .valeurNote(DEFAULT_VALEUR_NOTE);
        return noteMembreCritere;
    }

    @Before
    public void initTest() {
        noteMembreCritere = createEntity(em);
    }

    @Test
    @Transactional
    public void createNoteMembreCritere() throws Exception {
        int databaseSizeBeforeCreate = noteMembreCritereRepository.findAll().size();

        // Create the NoteMembreCritere
        NoteMembreCritereDTO noteMembreCritereDTO = noteMembreCritereMapper.toDto(noteMembreCritere);
        restNoteMembreCritereMockMvc.perform(post("/api/note-membre-criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noteMembreCritereDTO)))
            .andExpect(status().isCreated());

        // Validate the NoteMembreCritere in the database
        List<NoteMembreCritere> noteMembreCritereList = noteMembreCritereRepository.findAll();
        assertThat(noteMembreCritereList).hasSize(databaseSizeBeforeCreate + 1);
        NoteMembreCritere testNoteMembreCritere = noteMembreCritereList.get(noteMembreCritereList.size() - 1);
        assertThat(testNoteMembreCritere.getValeurNote()).isEqualTo(DEFAULT_VALEUR_NOTE);
    }

    @Test
    @Transactional
    public void createNoteMembreCritereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = noteMembreCritereRepository.findAll().size();

        // Create the NoteMembreCritere with an existing ID
        noteMembreCritere.setId(1L);
        NoteMembreCritereDTO noteMembreCritereDTO = noteMembreCritereMapper.toDto(noteMembreCritere);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoteMembreCritereMockMvc.perform(post("/api/note-membre-criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noteMembreCritereDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NoteMembreCritere in the database
        List<NoteMembreCritere> noteMembreCritereList = noteMembreCritereRepository.findAll();
        assertThat(noteMembreCritereList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNoteMembreCriteres() throws Exception {
        // Initialize the database
        noteMembreCritereRepository.saveAndFlush(noteMembreCritere);

        // Get all the noteMembreCritereList
        restNoteMembreCritereMockMvc.perform(get("/api/note-membre-criteres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noteMembreCritere.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeurNote").value(hasItem(DEFAULT_VALEUR_NOTE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getNoteMembreCritere() throws Exception {
        // Initialize the database
        noteMembreCritereRepository.saveAndFlush(noteMembreCritere);

        // Get the noteMembreCritere
        restNoteMembreCritereMockMvc.perform(get("/api/note-membre-criteres/{id}", noteMembreCritere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(noteMembreCritere.getId().intValue()))
            .andExpect(jsonPath("$.valeurNote").value(DEFAULT_VALEUR_NOTE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNoteMembreCritere() throws Exception {
        // Get the noteMembreCritere
        restNoteMembreCritereMockMvc.perform(get("/api/note-membre-criteres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoteMembreCritere() throws Exception {
        // Initialize the database
        noteMembreCritereRepository.saveAndFlush(noteMembreCritere);

        int databaseSizeBeforeUpdate = noteMembreCritereRepository.findAll().size();

        // Update the noteMembreCritere
        NoteMembreCritere updatedNoteMembreCritere = noteMembreCritereRepository.findById(noteMembreCritere.getId()).get();
        // Disconnect from session so that the updates on updatedNoteMembreCritere are not directly saved in db
        em.detach(updatedNoteMembreCritere);
        updatedNoteMembreCritere
            .valeurNote(UPDATED_VALEUR_NOTE);
        NoteMembreCritereDTO noteMembreCritereDTO = noteMembreCritereMapper.toDto(updatedNoteMembreCritere);

        restNoteMembreCritereMockMvc.perform(put("/api/note-membre-criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noteMembreCritereDTO)))
            .andExpect(status().isOk());

        // Validate the NoteMembreCritere in the database
        List<NoteMembreCritere> noteMembreCritereList = noteMembreCritereRepository.findAll();
        assertThat(noteMembreCritereList).hasSize(databaseSizeBeforeUpdate);
        NoteMembreCritere testNoteMembreCritere = noteMembreCritereList.get(noteMembreCritereList.size() - 1);
        assertThat(testNoteMembreCritere.getValeurNote()).isEqualTo(UPDATED_VALEUR_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingNoteMembreCritere() throws Exception {
        int databaseSizeBeforeUpdate = noteMembreCritereRepository.findAll().size();

        // Create the NoteMembreCritere
        NoteMembreCritereDTO noteMembreCritereDTO = noteMembreCritereMapper.toDto(noteMembreCritere);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoteMembreCritereMockMvc.perform(put("/api/note-membre-criteres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(noteMembreCritereDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NoteMembreCritere in the database
        List<NoteMembreCritere> noteMembreCritereList = noteMembreCritereRepository.findAll();
        assertThat(noteMembreCritereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNoteMembreCritere() throws Exception {
        // Initialize the database
        noteMembreCritereRepository.saveAndFlush(noteMembreCritere);

        int databaseSizeBeforeDelete = noteMembreCritereRepository.findAll().size();

        // Delete the noteMembreCritere
        restNoteMembreCritereMockMvc.perform(delete("/api/note-membre-criteres/{id}", noteMembreCritere.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NoteMembreCritere> noteMembreCritereList = noteMembreCritereRepository.findAll();
        assertThat(noteMembreCritereList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoteMembreCritere.class);
        NoteMembreCritere noteMembreCritere1 = new NoteMembreCritere();
        noteMembreCritere1.setId(1L);
        NoteMembreCritere noteMembreCritere2 = new NoteMembreCritere();
        noteMembreCritere2.setId(noteMembreCritere1.getId());
        assertThat(noteMembreCritere1).isEqualTo(noteMembreCritere2);
        noteMembreCritere2.setId(2L);
        assertThat(noteMembreCritere1).isNotEqualTo(noteMembreCritere2);
        noteMembreCritere1.setId(null);
        assertThat(noteMembreCritere1).isNotEqualTo(noteMembreCritere2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoteMembreCritereDTO.class);
        NoteMembreCritereDTO noteMembreCritereDTO1 = new NoteMembreCritereDTO();
        noteMembreCritereDTO1.setId(1L);
        NoteMembreCritereDTO noteMembreCritereDTO2 = new NoteMembreCritereDTO();
        assertThat(noteMembreCritereDTO1).isNotEqualTo(noteMembreCritereDTO2);
        noteMembreCritereDTO2.setId(noteMembreCritereDTO1.getId());
        assertThat(noteMembreCritereDTO1).isEqualTo(noteMembreCritereDTO2);
        noteMembreCritereDTO2.setId(2L);
        assertThat(noteMembreCritereDTO1).isNotEqualTo(noteMembreCritereDTO2);
        noteMembreCritereDTO1.setId(null);
        assertThat(noteMembreCritereDTO1).isNotEqualTo(noteMembreCritereDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(noteMembreCritereMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(noteMembreCritereMapper.fromId(null)).isNull();
    }
}
