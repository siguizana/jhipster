package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Session;
import com.test.repository.SessionRepository;
import com.test.service.SessionService;
import com.test.service.dto.SessionDTO;
import com.test.service.mapper.SessionMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.test.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SessionResource REST controller.
 *
 * @see SessionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class SessionResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OUVERTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OUVERTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FERMETURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FERMETURE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ANNEE_SESSION = 1;
    private static final Integer UPDATED_ANNEE_SESSION = 2;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private SessionService sessionService;

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

    private MockMvc restSessionMockMvc;

    private Session session;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SessionResource sessionResource = new SessionResource(sessionService);
        this.restSessionMockMvc = MockMvcBuilders.standaloneSetup(sessionResource)
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
    public static Session createEntity(EntityManager em) {
        Session session = new Session()
            .libelle(DEFAULT_LIBELLE)
            .dateOuverture(DEFAULT_DATE_OUVERTURE)
            .dateFermeture(DEFAULT_DATE_FERMETURE)
            .anneeSession(DEFAULT_ANNEE_SESSION);
        return session;
    }

    @Before
    public void initTest() {
        session = createEntity(em);
    }

    @Test
    @Transactional
    public void createSession() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);
        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isCreated());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate + 1);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSession.getDateOuverture()).isEqualTo(DEFAULT_DATE_OUVERTURE);
        assertThat(testSession.getDateFermeture()).isEqualTo(DEFAULT_DATE_FERMETURE);
        assertThat(testSession.getAnneeSession()).isEqualTo(DEFAULT_ANNEE_SESSION);
    }

    @Test
    @Transactional
    public void createSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session with an existing ID
        session.setId(1L);
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionRepository.findAll().size();
        // set the field null
        session.setLibelle(null);

        // Create the Session, which fails.
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOuvertureIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionRepository.findAll().size();
        // set the field null
        session.setDateOuverture(null);

        // Create the Session, which fails.
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnneeSessionIsRequired() throws Exception {
        int databaseSizeBeforeTest = sessionRepository.findAll().size();
        // set the field null
        session.setAnneeSession(null);

        // Create the Session, which fails.
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSessions() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get all the sessionList
        restSessionMockMvc.perform(get("/api/sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(session.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].dateOuverture").value(hasItem(DEFAULT_DATE_OUVERTURE.toString())))
            .andExpect(jsonPath("$.[*].dateFermeture").value(hasItem(DEFAULT_DATE_FERMETURE.toString())))
            .andExpect(jsonPath("$.[*].anneeSession").value(hasItem(DEFAULT_ANNEE_SESSION)));
    }
    
    @Test
    @Transactional
    public void getSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", session.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(session.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.dateOuverture").value(DEFAULT_DATE_OUVERTURE.toString()))
            .andExpect(jsonPath("$.dateFermeture").value(DEFAULT_DATE_FERMETURE.toString()))
            .andExpect(jsonPath("$.anneeSession").value(DEFAULT_ANNEE_SESSION));
    }

    @Test
    @Transactional
    public void getNonExistingSession() throws Exception {
        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session
        Session updatedSession = sessionRepository.findById(session.getId()).get();
        // Disconnect from session so that the updates on updatedSession are not directly saved in db
        em.detach(updatedSession);
        updatedSession
            .libelle(UPDATED_LIBELLE)
            .dateOuverture(UPDATED_DATE_OUVERTURE)
            .dateFermeture(UPDATED_DATE_FERMETURE)
            .anneeSession(UPDATED_ANNEE_SESSION);
        SessionDTO sessionDTO = sessionMapper.toDto(updatedSession);

        restSessionMockMvc.perform(put("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSession.getDateOuverture()).isEqualTo(UPDATED_DATE_OUVERTURE);
        assertThat(testSession.getDateFermeture()).isEqualTo(UPDATED_DATE_FERMETURE);
        assertThat(testSession.getAnneeSession()).isEqualTo(UPDATED_ANNEE_SESSION);
    }

    @Test
    @Transactional
    public void updateNonExistingSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Create the Session
        SessionDTO sessionDTO = sessionMapper.toDto(session);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionMockMvc.perform(put("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sessionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeDelete = sessionRepository.findAll().size();

        // Delete the session
        restSessionMockMvc.perform(delete("/api/sessions/{id}", session.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Session.class);
        Session session1 = new Session();
        session1.setId(1L);
        Session session2 = new Session();
        session2.setId(session1.getId());
        assertThat(session1).isEqualTo(session2);
        session2.setId(2L);
        assertThat(session1).isNotEqualTo(session2);
        session1.setId(null);
        assertThat(session1).isNotEqualTo(session2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionDTO.class);
        SessionDTO sessionDTO1 = new SessionDTO();
        sessionDTO1.setId(1L);
        SessionDTO sessionDTO2 = new SessionDTO();
        assertThat(sessionDTO1).isNotEqualTo(sessionDTO2);
        sessionDTO2.setId(sessionDTO1.getId());
        assertThat(sessionDTO1).isEqualTo(sessionDTO2);
        sessionDTO2.setId(2L);
        assertThat(sessionDTO1).isNotEqualTo(sessionDTO2);
        sessionDTO1.setId(null);
        assertThat(sessionDTO1).isNotEqualTo(sessionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sessionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sessionMapper.fromId(null)).isNull();
    }
}
