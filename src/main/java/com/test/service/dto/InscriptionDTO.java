package com.test.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Inscription entity.
 */
public class InscriptionDTO implements Serializable {

    private Long id;

    private Long numeroPV;

    private LocalDate dateInscription;

    private Boolean aptitude;

    private String residence;

    private String echelon;

    private String grade;

    private LocalDate dateDernierePromotion;

    private String diplome;

    private String classeTenue;

    private String nomPrenomPereOuTuteur;

    private String nomPrenomMereOuTutrice;

    private String adresseParentOuTiteur;

    private Boolean participeConcoursRattache;

    private Long villageSecteurId;

    private Long etablissementId;

    private Long sessionId;

    private Long optionConcoursRattacheId;

    private Long candidatId;

    private Long typeExamenId;

    private Long centreCompositioJuryId;

    private Long salleCompositionId;

    private Long juryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroPV() {
        return numeroPV;
    }

    public void setNumeroPV(Long numeroPV) {
        this.numeroPV = numeroPV;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Boolean isAptitude() {
        return aptitude;
    }

    public void setAptitude(Boolean aptitude) {
        this.aptitude = aptitude;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getEchelon() {
        return echelon;
    }

    public void setEchelon(String echelon) {
        this.echelon = echelon;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getDateDernierePromotion() {
        return dateDernierePromotion;
    }

    public void setDateDernierePromotion(LocalDate dateDernierePromotion) {
        this.dateDernierePromotion = dateDernierePromotion;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getClasseTenue() {
        return classeTenue;
    }

    public void setClasseTenue(String classeTenue) {
        this.classeTenue = classeTenue;
    }

    public String getNomPrenomPereOuTuteur() {
        return nomPrenomPereOuTuteur;
    }

    public void setNomPrenomPereOuTuteur(String nomPrenomPereOuTuteur) {
        this.nomPrenomPereOuTuteur = nomPrenomPereOuTuteur;
    }

    public String getNomPrenomMereOuTutrice() {
        return nomPrenomMereOuTutrice;
    }

    public void setNomPrenomMereOuTutrice(String nomPrenomMereOuTutrice) {
        this.nomPrenomMereOuTutrice = nomPrenomMereOuTutrice;
    }

    public String getAdresseParentOuTiteur() {
        return adresseParentOuTiteur;
    }

    public void setAdresseParentOuTiteur(String adresseParentOuTiteur) {
        this.adresseParentOuTiteur = adresseParentOuTiteur;
    }

    public Boolean isParticipeConcoursRattache() {
        return participeConcoursRattache;
    }

    public void setParticipeConcoursRattache(Boolean participeConcoursRattache) {
        this.participeConcoursRattache = participeConcoursRattache;
    }

    public Long getVillageSecteurId() {
        return villageSecteurId;
    }

    public void setVillageSecteurId(Long villageSecteurId) {
        this.villageSecteurId = villageSecteurId;
    }

    public Long getEtablissementId() {
        return etablissementId;
    }

    public void setEtablissementId(Long etablissementId) {
        this.etablissementId = etablissementId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getOptionConcoursRattacheId() {
        return optionConcoursRattacheId;
    }

    public void setOptionConcoursRattacheId(Long optionConcoursRattacheId) {
        this.optionConcoursRattacheId = optionConcoursRattacheId;
    }

    public Long getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(Long candidatId) {
        this.candidatId = candidatId;
    }

    public Long getTypeExamenId() {
        return typeExamenId;
    }

    public void setTypeExamenId(Long typeExamenId) {
        this.typeExamenId = typeExamenId;
    }

    public Long getCentreCompositioJuryId() {
        return centreCompositioJuryId;
    }

    public void setCentreCompositioJuryId(Long centreCompositioJuryId) {
        this.centreCompositioJuryId = centreCompositioJuryId;
    }

    public Long getSalleCompositionId() {
        return salleCompositionId;
    }

    public void setSalleCompositionId(Long salleCompositionId) {
        this.salleCompositionId = salleCompositionId;
    }

    public Long getJuryId() {
        return juryId;
    }

    public void setJuryId(Long juryId) {
        this.juryId = juryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InscriptionDTO inscriptionDTO = (InscriptionDTO) o;
        if (inscriptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inscriptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InscriptionDTO{" +
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
            ", villageSecteur=" + getVillageSecteurId() +
            ", etablissement=" + getEtablissementId() +
            ", session=" + getSessionId() +
            ", optionConcoursRattache=" + getOptionConcoursRattacheId() +
            ", candidat=" + getCandidatId() +
            ", typeExamen=" + getTypeExamenId() +
            ", centreCompositioJury=" + getCentreCompositioJuryId() +
            ", salleComposition=" + getSalleCompositionId() +
            ", jury=" + getJuryId() +
            "}";
    }
}
