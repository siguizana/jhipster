package com.test.web.rest;

import com.test.SigecApp;

import com.test.domain.Inscription;
import com.test.repository.InscriptionRepository;
import com.test.service.InscriptionService;
import com.test.service.dto.InscriptionDTO;
import com.test.service.mapper.InscriptionMapper;
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
 * Test class for the InscriptionResource REST controller.
 *
 * @see InscriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SigecApp.class)
public class InscriptionResourceIntTest {

    private static final Long DEFAULT_NUMERO_PV = 1L;
    private static final Long UPDATED_NUMERO_PV = 2L;

    private static final LocalDate DEFAULT_DATE_INSCRIPTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_INSCRIPTION = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_APTITUDE = false;
    private static final Boolean UPDATED_APTITUDE = true;

    private static final String DEFAULT_RESIDENCE = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENCE = "BBBBBBBBBB";

    private static final String DEFAULT_ECHELON = "AAAAAAAAAA";
    private static final String UPDATED_ECHELON = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DERNIERE_PROMOTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DERNIERE_PROMOTION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DIPLOME = "AAAAAAAAAA";
    private static final String UPDATED_DIPLOME = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSE_TENUE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSE_TENUE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PRENOM_PERE_OU_TUTEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PRENOM_PERE_OU_TUTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PRENOM_MERE_OU_TUTRICE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PRENOM_MERE_OU_TUTRICE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_PARENT_OU_TITEUR = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_PARENT_OU_TITEUR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARTICIPE_CONCOURS_RATTACHE = false;
    private static final Boolean UPDATED_PARTICIPE_CONCOURS_RATTACHE = true;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private InscriptionMapper inscriptionMapper;

    @Autowired
    private InscriptionService inscriptionService;

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

    private MockMvc restInscriptionMockMvc;

    private Inscription inscription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InscriptionResource inscriptionResource = new InscriptionResource(inscriptionService);
        this.restInscriptionMockMvc = MockMvcBuilders.standaloneSetup(inscriptionResource)
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
    public static Inscription createEntity(EntityManager em) {
        Inscription inscription = new Inscription()
            .numeroPV(DEFAULT_NUMERO_PV)
            .dateInscription(DEFAULT_DATE_INSCRIPTION)
            .aptitude(DEFAULT_APTITUDE)
            .residence(DEFAULT_RESIDENCE)
            .echelon(DEFAULT_ECHELON)
            .grade(DEFAULT_GRADE)
            .dateDernierePromotion(DEFAULT_DATE_DERNIERE_PROMOTION)
            .diplome(DEFAULT_DIPLOME)
            .classeTenue(DEFAULT_CLASSE_TENUE)
            .nomPrenomPereOuTuteur(DEFAULT_NOM_PRENOM_PERE_OU_TUTEUR)
            .nomPrenomMereOuTutrice(DEFAULT_NOM_PRENOM_MERE_OU_TUTRICE)
            .adresseParentOuTiteur(DEFAULT_ADRESSE_PARENT_OU_TITEUR)
            .participeConcoursRattache(DEFAULT_PARTICIPE_CONCOURS_RATTACHE);
        return inscription;
    }

    @Before
    public void initTest() {
        inscription = createEntity(em);
    }

    @Test
    @Transactional
    public void createInscription() throws Exception {
        int databaseSizeBeforeCreate = inscriptionRepository.findAll().size();

        // Create the Inscription
        InscriptionDTO inscriptionDTO = inscriptionMapper.toDto(inscription);
        restInscriptionMockMvc.perform(post("/api/inscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscriptionDTO)))
            .andExpect(status().isCreated());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        Inscription testInscription = inscriptionList.get(inscriptionList.size() - 1);
        assertThat(testInscription.getNumeroPV()).isEqualTo(DEFAULT_NUMERO_PV);
        assertThat(testInscription.getDateInscription()).isEqualTo(DEFAULT_DATE_INSCRIPTION);
        assertThat(testInscription.isAptitude()).isEqualTo(DEFAULT_APTITUDE);
        assertThat(testInscription.getResidence()).isEqualTo(DEFAULT_RESIDENCE);
        assertThat(testInscription.getEchelon()).isEqualTo(DEFAULT_ECHELON);
        assertThat(testInscription.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testInscription.getDateDernierePromotion()).isEqualTo(DEFAULT_DATE_DERNIERE_PROMOTION);
        assertThat(testInscription.getDiplome()).isEqualTo(DEFAULT_DIPLOME);
        assertThat(testInscription.getClasseTenue()).isEqualTo(DEFAULT_CLASSE_TENUE);
        assertThat(testInscription.getNomPrenomPereOuTuteur()).isEqualTo(DEFAULT_NOM_PRENOM_PERE_OU_TUTEUR);
        assertThat(testInscription.getNomPrenomMereOuTutrice()).isEqualTo(DEFAULT_NOM_PRENOM_MERE_OU_TUTRICE);
        assertThat(testInscription.getAdresseParentOuTiteur()).isEqualTo(DEFAULT_ADRESSE_PARENT_OU_TITEUR);
        assertThat(testInscription.isParticipeConcoursRattache()).isEqualTo(DEFAULT_PARTICIPE_CONCOURS_RATTACHE);
    }

    @Test
    @Transactional
    public void createInscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inscriptionRepository.findAll().size();

        // Create the Inscription with an existing ID
        inscription.setId(1L);
        InscriptionDTO inscriptionDTO = inscriptionMapper.toDto(inscription);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscriptionMockMvc.perform(post("/api/inscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInscriptions() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList
        restInscriptionMockMvc.perform(get("/api/inscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroPV").value(hasItem(DEFAULT_NUMERO_PV.intValue())))
            .andExpect(jsonPath("$.[*].dateInscription").value(hasItem(DEFAULT_DATE_INSCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].aptitude").value(hasItem(DEFAULT_APTITUDE.booleanValue())))
            .andExpect(jsonPath("$.[*].residence").value(hasItem(DEFAULT_RESIDENCE.toString())))
            .andExpect(jsonPath("$.[*].echelon").value(hasItem(DEFAULT_ECHELON.toString())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
            .andExpect(jsonPath("$.[*].dateDernierePromotion").value(hasItem(DEFAULT_DATE_DERNIERE_PROMOTION.toString())))
            .andExpect(jsonPath("$.[*].diplome").value(hasItem(DEFAULT_DIPLOME.toString())))
            .andExpect(jsonPath("$.[*].classeTenue").value(hasItem(DEFAULT_CLASSE_TENUE.toString())))
            .andExpect(jsonPath("$.[*].nomPrenomPereOuTuteur").value(hasItem(DEFAULT_NOM_PRENOM_PERE_OU_TUTEUR.toString())))
            .andExpect(jsonPath("$.[*].nomPrenomMereOuTutrice").value(hasItem(DEFAULT_NOM_PRENOM_MERE_OU_TUTRICE.toString())))
            .andExpect(jsonPath("$.[*].adresseParentOuTiteur").value(hasItem(DEFAULT_ADRESSE_PARENT_OU_TITEUR.toString())))
            .andExpect(jsonPath("$.[*].participeConcoursRattache").value(hasItem(DEFAULT_PARTICIPE_CONCOURS_RATTACHE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get the inscription
        restInscriptionMockMvc.perform(get("/api/inscriptions/{id}", inscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inscription.getId().intValue()))
            .andExpect(jsonPath("$.numeroPV").value(DEFAULT_NUMERO_PV.intValue()))
            .andExpect(jsonPath("$.dateInscription").value(DEFAULT_DATE_INSCRIPTION.toString()))
            .andExpect(jsonPath("$.aptitude").value(DEFAULT_APTITUDE.booleanValue()))
            .andExpect(jsonPath("$.residence").value(DEFAULT_RESIDENCE.toString()))
            .andExpect(jsonPath("$.echelon").value(DEFAULT_ECHELON.toString()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.toString()))
            .andExpect(jsonPath("$.dateDernierePromotion").value(DEFAULT_DATE_DERNIERE_PROMOTION.toString()))
            .andExpect(jsonPath("$.diplome").value(DEFAULT_DIPLOME.toString()))
            .andExpect(jsonPath("$.classeTenue").value(DEFAULT_CLASSE_TENUE.toString()))
            .andExpect(jsonPath("$.nomPrenomPereOuTuteur").value(DEFAULT_NOM_PRENOM_PERE_OU_TUTEUR.toString()))
            .andExpect(jsonPath("$.nomPrenomMereOuTutrice").value(DEFAULT_NOM_PRENOM_MERE_OU_TUTRICE.toString()))
            .andExpect(jsonPath("$.adresseParentOuTiteur").value(DEFAULT_ADRESSE_PARENT_OU_TITEUR.toString()))
            .andExpect(jsonPath("$.participeConcoursRattache").value(DEFAULT_PARTICIPE_CONCOURS_RATTACHE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInscription() throws Exception {
        // Get the inscription
        restInscriptionMockMvc.perform(get("/api/inscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();

        // Update the inscription
        Inscription updatedInscription = inscriptionRepository.findById(inscription.getId()).get();
        // Disconnect from session so that the updates on updatedInscription are not directly saved in db
        em.detach(updatedInscription);
        updatedInscription
            .numeroPV(UPDATED_NUMERO_PV)
            .dateInscription(UPDATED_DATE_INSCRIPTION)
            .aptitude(UPDATED_APTITUDE)
            .residence(UPDATED_RESIDENCE)
            .echelon(UPDATED_ECHELON)
            .grade(UPDATED_GRADE)
            .dateDernierePromotion(UPDATED_DATE_DERNIERE_PROMOTION)
            .diplome(UPDATED_DIPLOME)
            .classeTenue(UPDATED_CLASSE_TENUE)
            .nomPrenomPereOuTuteur(UPDATED_NOM_PRENOM_PERE_OU_TUTEUR)
            .nomPrenomMereOuTutrice(UPDATED_NOM_PRENOM_MERE_OU_TUTRICE)
            .adresseParentOuTiteur(UPDATED_ADRESSE_PARENT_OU_TITEUR)
            .participeConcoursRattache(UPDATED_PARTICIPE_CONCOURS_RATTACHE);
        InscriptionDTO inscriptionDTO = inscriptionMapper.toDto(updatedInscription);

        restInscriptionMockMvc.perform(put("/api/inscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscriptionDTO)))
            .andExpect(status().isOk());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
        Inscription testInscription = inscriptionList.get(inscriptionList.size() - 1);
        assertThat(testInscription.getNumeroPV()).isEqualTo(UPDATED_NUMERO_PV);
        assertThat(testInscription.getDateInscription()).isEqualTo(UPDATED_DATE_INSCRIPTION);
        assertThat(testInscription.isAptitude()).isEqualTo(UPDATED_APTITUDE);
        assertThat(testInscription.getResidence()).isEqualTo(UPDATED_RESIDENCE);
        assertThat(testInscription.getEchelon()).isEqualTo(UPDATED_ECHELON);
        assertThat(testInscription.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testInscription.getDateDernierePromotion()).isEqualTo(UPDATED_DATE_DERNIERE_PROMOTION);
        assertThat(testInscription.getDiplome()).isEqualTo(UPDATED_DIPLOME);
        assertThat(testInscription.getClasseTenue()).isEqualTo(UPDATED_CLASSE_TENUE);
        assertThat(testInscription.getNomPrenomPereOuTuteur()).isEqualTo(UPDATED_NOM_PRENOM_PERE_OU_TUTEUR);
        assertThat(testInscription.getNomPrenomMereOuTutrice()).isEqualTo(UPDATED_NOM_PRENOM_MERE_OU_TUTRICE);
        assertThat(testInscription.getAdresseParentOuTiteur()).isEqualTo(UPDATED_ADRESSE_PARENT_OU_TITEUR);
        assertThat(testInscription.isParticipeConcoursRattache()).isEqualTo(UPDATED_PARTICIPE_CONCOURS_RATTACHE);
    }

    @Test
    @Transactional
    public void updateNonExistingInscription() throws Exception {
        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();

        // Create the Inscription
        InscriptionDTO inscriptionDTO = inscriptionMapper.toDto(inscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscriptionMockMvc.perform(put("/api/inscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        int databaseSizeBeforeDelete = inscriptionRepository.findAll().size();

        // Delete the inscription
        restInscriptionMockMvc.perform(delete("/api/inscriptions/{id}", inscription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inscription.class);
        Inscription inscription1 = new Inscription();
        inscription1.setId(1L);
        Inscription inscription2 = new Inscription();
        inscription2.setId(inscription1.getId());
        assertThat(inscription1).isEqualTo(inscription2);
        inscription2.setId(2L);
        assertThat(inscription1).isNotEqualTo(inscription2);
        inscription1.setId(null);
        assertThat(inscription1).isNotEqualTo(inscription2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InscriptionDTO.class);
        InscriptionDTO inscriptionDTO1 = new InscriptionDTO();
        inscriptionDTO1.setId(1L);
        InscriptionDTO inscriptionDTO2 = new InscriptionDTO();
        assertThat(inscriptionDTO1).isNotEqualTo(inscriptionDTO2);
        inscriptionDTO2.setId(inscriptionDTO1.getId());
        assertThat(inscriptionDTO1).isEqualTo(inscriptionDTO2);
        inscriptionDTO2.setId(2L);
        assertThat(inscriptionDTO1).isNotEqualTo(inscriptionDTO2);
        inscriptionDTO1.setId(null);
        assertThat(inscriptionDTO1).isNotEqualTo(inscriptionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inscriptionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inscriptionMapper.fromId(null)).isNull();
    }
}
