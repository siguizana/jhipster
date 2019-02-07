package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Inscription.
 */
@Entity
@Table(name = "inscription")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inscription implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_pv")
    private Long numeroPV;

    @Column(name = "date_inscription")
    private LocalDate dateInscription;

    @Column(name = "aptitude")
    private Boolean aptitude;

    @Column(name = "residence")
    private String residence;

    @Column(name = "echelon")
    private String echelon;

    @Column(name = "grade")
    private String grade;

    @Column(name = "date_derniere_promotion")
    private LocalDate dateDernierePromotion;

    @Column(name = "diplome")
    private String diplome;

    @Column(name = "classe_tenue")
    private String classeTenue;

    @Column(name = "nom_prenom_pere_ou_tuteur")
    private String nomPrenomPereOuTuteur;

    @Column(name = "nom_prenom_mere_ou_tutrice")
    private String nomPrenomMereOuTutrice;

    @Column(name = "adresse_parent_ou_titeur")
    private String adresseParentOuTiteur;

    @Column(name = "participe_concours_rattache")
    private Boolean participeConcoursRattache;

    @OneToMany(mappedBy = "inscription")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions = new HashSet<>();
    @OneToMany(mappedBy = "inscription")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChoixEtablissement> choixEtablissements = new HashSet<>();
    @OneToMany(mappedBy = "inscription")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompositionCopie> compositionCopies = new HashSet<>();
    @OneToMany(mappedBy = "inscription")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Absence> absences = new HashSet<>();
    @OneToMany(mappedBy = "inscription")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Resultat> resultats = new HashSet<>();
    @OneToMany(mappedBy = "inscription")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dispense> dispenses = new HashSet<>();
    @OneToMany(mappedBy = "inscription")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Fraude> fraudes = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private VillageSecteur villageSecteur;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private Etablissement etablissement;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private Session session;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private OptionConcoursRattache optionConcoursRattache;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private Candidat candidat;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private TypeExamen typeExamen;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private CentreCompositioJury centreCompositioJury;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private SalleComposition salleComposition;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private Jury jury;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroPV() {
        return numeroPV;
    }

    public Inscription numeroPV(Long numeroPV) {
        this.numeroPV = numeroPV;
        return this;
    }

    public void setNumeroPV(Long numeroPV) {
        this.numeroPV = numeroPV;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public Inscription dateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
        return this;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Boolean isAptitude() {
        return aptitude;
    }

    public Inscription aptitude(Boolean aptitude) {
        this.aptitude = aptitude;
        return this;
    }

    public void setAptitude(Boolean aptitude) {
        this.aptitude = aptitude;
    }

    public String getResidence() {
        return residence;
    }

    public Inscription residence(String residence) {
        this.residence = residence;
        return this;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getEchelon() {
        return echelon;
    }

    public Inscription echelon(String echelon) {
        this.echelon = echelon;
        return this;
    }

    public void setEchelon(String echelon) {
        this.echelon = echelon;
    }

    public String getGrade() {
        return grade;
    }

    public Inscription grade(String grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getDateDernierePromotion() {
        return dateDernierePromotion;
    }

    public Inscription dateDernierePromotion(LocalDate dateDernierePromotion) {
        this.dateDernierePromotion = dateDernierePromotion;
        return this;
    }

    public void setDateDernierePromotion(LocalDate dateDernierePromotion) {
        this.dateDernierePromotion = dateDernierePromotion;
    }

    public String getDiplome() {
        return diplome;
    }

    public Inscription diplome(String diplome) {
        this.diplome = diplome;
        return this;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getClasseTenue() {
        return classeTenue;
    }

    public Inscription classeTenue(String classeTenue) {
        this.classeTenue = classeTenue;
        return this;
    }

    public void setClasseTenue(String classeTenue) {
        this.classeTenue = classeTenue;
    }

    public String getNomPrenomPereOuTuteur() {
        return nomPrenomPereOuTuteur;
    }

    public Inscription nomPrenomPereOuTuteur(String nomPrenomPereOuTuteur) {
        this.nomPrenomPereOuTuteur = nomPrenomPereOuTuteur;
        return this;
    }

    public void setNomPrenomPereOuTuteur(String nomPrenomPereOuTuteur) {
        this.nomPrenomPereOuTuteur = nomPrenomPereOuTuteur;
    }

    public String getNomPrenomMereOuTutrice() {
        return nomPrenomMereOuTutrice;
    }

    public Inscription nomPrenomMereOuTutrice(String nomPrenomMereOuTutrice) {
        this.nomPrenomMereOuTutrice = nomPrenomMereOuTutrice;
        return this;
    }

    public void setNomPrenomMereOuTutrice(String nomPrenomMereOuTutrice) {
        this.nomPrenomMereOuTutrice = nomPrenomMereOuTutrice;
    }

    public String getAdresseParentOuTiteur() {
        return adresseParentOuTiteur;
    }

    public Inscription adresseParentOuTiteur(String adresseParentOuTiteur) {
        this.adresseParentOuTiteur = adresseParentOuTiteur;
        return this;
    }

    public void setAdresseParentOuTiteur(String adresseParentOuTiteur) {
        this.adresseParentOuTiteur = adresseParentOuTiteur;
    }

    public Boolean isParticipeConcoursRattache() {
        return participeConcoursRattache;
    }

    public Inscription participeConcoursRattache(Boolean participeConcoursRattache) {
        this.participeConcoursRattache = participeConcoursRattache;
        return this;
    }

    public void setParticipeConcoursRattache(Boolean participeConcoursRattache) {
        this.participeConcoursRattache = participeConcoursRattache;
    }

    public Set<EpreuveSpecialiteOption> getEpreuveSpecialiteOptions() {
        return epreuveSpecialiteOptions;
    }

    public Inscription epreuveSpecialiteOptions(Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions) {
        this.epreuveSpecialiteOptions = epreuveSpecialiteOptions;
        return this;
    }

    public Inscription addEpreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOptions.add(epreuveSpecialiteOption);
        epreuveSpecialiteOption.setInscription(this);
        return this;
    }

    public Inscription removeEpreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOptions.remove(epreuveSpecialiteOption);
        epreuveSpecialiteOption.setInscription(null);
        return this;
    }

    public void setEpreuveSpecialiteOptions(Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions) {
        this.epreuveSpecialiteOptions = epreuveSpecialiteOptions;
    }

    public Set<ChoixEtablissement> getChoixEtablissements() {
        return choixEtablissements;
    }

    public Inscription choixEtablissements(Set<ChoixEtablissement> choixEtablissements) {
        this.choixEtablissements = choixEtablissements;
        return this;
    }

    public Inscription addChoixEtablissement(ChoixEtablissement choixEtablissement) {
        this.choixEtablissements.add(choixEtablissement);
        choixEtablissement.setInscription(this);
        return this;
    }

    public Inscription removeChoixEtablissement(ChoixEtablissement choixEtablissement) {
        this.choixEtablissements.remove(choixEtablissement);
        choixEtablissement.setInscription(null);
        return this;
    }

    public void setChoixEtablissements(Set<ChoixEtablissement> choixEtablissements) {
        this.choixEtablissements = choixEtablissements;
    }

    public Set<CompositionCopie> getCompositionCopies() {
        return compositionCopies;
    }

    public Inscription compositionCopies(Set<CompositionCopie> compositionCopies) {
        this.compositionCopies = compositionCopies;
        return this;
    }

    public Inscription addCompositionCopie(CompositionCopie compositionCopie) {
        this.compositionCopies.add(compositionCopie);
        compositionCopie.setInscription(this);
        return this;
    }

    public Inscription removeCompositionCopie(CompositionCopie compositionCopie) {
        this.compositionCopies.remove(compositionCopie);
        compositionCopie.setInscription(null);
        return this;
    }

    public void setCompositionCopies(Set<CompositionCopie> compositionCopies) {
        this.compositionCopies = compositionCopies;
    }

    public Set<Absence> getAbsences() {
        return absences;
    }

    public Inscription absences(Set<Absence> absences) {
        this.absences = absences;
        return this;
    }

    public Inscription addAbsence(Absence absence) {
        this.absences.add(absence);
        absence.setInscription(this);
        return this;
    }

    public Inscription removeAbsence(Absence absence) {
        this.absences.remove(absence);
        absence.setInscription(null);
        return this;
    }

    public void setAbsences(Set<Absence> absences) {
        this.absences = absences;
    }

    public Set<Resultat> getResultats() {
        return resultats;
    }

    public Inscription resultats(Set<Resultat> resultats) {
        this.resultats = resultats;
        return this;
    }

    public Inscription addResultat(Resultat resultat) {
        this.resultats.add(resultat);
        resultat.setInscription(this);
        return this;
    }

    public Inscription removeResultat(Resultat resultat) {
        this.resultats.remove(resultat);
        resultat.setInscription(null);
        return this;
    }

    public void setResultats(Set<Resultat> resultats) {
        this.resultats = resultats;
    }

    public Set<Dispense> getDispenses() {
        return dispenses;
    }

    public Inscription dispenses(Set<Dispense> dispenses) {
        this.dispenses = dispenses;
        return this;
    }

    public Inscription addDispense(Dispense dispense) {
        this.dispenses.add(dispense);
        dispense.setInscription(this);
        return this;
    }

    public Inscription removeDispense(Dispense dispense) {
        this.dispenses.remove(dispense);
        dispense.setInscription(null);
        return this;
    }

    public void setDispenses(Set<Dispense> dispenses) {
        this.dispenses = dispenses;
    }

    public Set<Fraude> getFraudes() {
        return fraudes;
    }

    public Inscription fraudes(Set<Fraude> fraudes) {
        this.fraudes = fraudes;
        return this;
    }

    public Inscription addFraude(Fraude fraude) {
        this.fraudes.add(fraude);
        fraude.setInscription(this);
        return this;
    }

    public Inscription removeFraude(Fraude fraude) {
        this.fraudes.remove(fraude);
        fraude.setInscription(null);
        return this;
    }

    public void setFraudes(Set<Fraude> fraudes) {
        this.fraudes = fraudes;
    }

    public VillageSecteur getVillageSecteur() {
        return villageSecteur;
    }

    public Inscription villageSecteur(VillageSecteur villageSecteur) {
        this.villageSecteur = villageSecteur;
        return this;
    }

    public void setVillageSecteur(VillageSecteur villageSecteur) {
        this.villageSecteur = villageSecteur;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public Inscription etablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
        return this;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public Session getSession() {
        return session;
    }

    public Inscription session(Session session) {
        this.session = session;
        return this;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public OptionConcoursRattache getOptionConcoursRattache() {
        return optionConcoursRattache;
    }

    public Inscription optionConcoursRattache(OptionConcoursRattache optionConcoursRattache) {
        this.optionConcoursRattache = optionConcoursRattache;
        return this;
    }

    public void setOptionConcoursRattache(OptionConcoursRattache optionConcoursRattache) {
        this.optionConcoursRattache = optionConcoursRattache;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Inscription candidat(Candidat candidat) {
        this.candidat = candidat;
        return this;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public TypeExamen getTypeExamen() {
        return typeExamen;
    }

    public Inscription typeExamen(TypeExamen typeExamen) {
        this.typeExamen = typeExamen;
        return this;
    }

    public void setTypeExamen(TypeExamen typeExamen) {
        this.typeExamen = typeExamen;
    }

    public CentreCompositioJury getCentreCompositioJury() {
        return centreCompositioJury;
    }

    public Inscription centreCompositioJury(CentreCompositioJury centreCompositioJury) {
        this.centreCompositioJury = centreCompositioJury;
        return this;
    }

    public void setCentreCompositioJury(CentreCompositioJury centreCompositioJury) {
        this.centreCompositioJury = centreCompositioJury;
    }

    public SalleComposition getSalleComposition() {
        return salleComposition;
    }

    public Inscription salleComposition(SalleComposition salleComposition) {
        this.salleComposition = salleComposition;
        return this;
    }

    public void setSalleComposition(SalleComposition salleComposition) {
        this.salleComposition = salleComposition;
    }

    public Jury getJury() {
        return jury;
    }

    public Inscription jury(Jury jury) {
        this.jury = jury;
        return this;
    }

    public void setJury(Jury jury) {
        this.jury = jury;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Inscription inscription = (Inscription) o;
        if (inscription.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inscription.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Inscription{" +
            "id=" + getId() +
            ", numeroPV=" + getNumeroPV() +
            ", dateInscription='" + getDateInscription() + "'" +
            ", aptitude='" + isAptitude() + "'" +
            ", residence='" + getResidence() + "'" +
            ", echelon='" + getEchelon() + "'" +
            ", grade='" + getGrade() + "'" +
            ", dateDernierePromotion='" + getDateDernierePromotion() + "'" +
            ", diplome='" + getDiplome() + "'" +
            ", classeTenue='" + getClasseTenue() + "'" +
            ", nomPrenomPereOuTuteur='" + getNomPrenomPereOuTuteur() + "'" +
            ", nomPrenomMereOuTutrice='" + getNomPrenomMereOuTutrice() + "'" +
            ", adresseParentOuTiteur='" + getAdresseParentOuTiteur() + "'" +
            ", participeConcoursRattache='" + isParticipeConcoursRattache() + "'" +
            "}";
    }
}
