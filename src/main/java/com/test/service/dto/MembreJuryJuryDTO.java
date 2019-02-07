package com.test.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MembreJuryJury entity.
 */
public class MembreJuryJuryDTO implements Serializable {

    private Long id;

    private String fonction;

    private String status;

    private String experience;

    private String secteur;

    private String quartier;

    private String diplomeAcademique;

    private String diplomeProfessionnel;

    private String nomChefEtablissement;

    private String prenomChefEtabissement;

    private String avisChefEtablissement;

    private Set<CommissionDTO> commissions = new HashSet<>();

    private Long juryId;

    private Long membreJuryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getDiplomeAcademique() {
        return diplomeAcademique;
    }

    public void setDiplomeAcademique(String diplomeAcademique) {
        this.diplomeAcademique = diplomeAcademique;
    }

    public String getDiplomeProfessionnel() {
        return diplomeProfessionnel;
    }

    public void setDiplomeProfessionnel(String diplomeProfessionnel) {
        this.diplomeProfessionnel = diplomeProfessionnel;
    }

    public String getNomChefEtablissement() {
        return nomChefEtablissement;
    }

    public void setNomChefEtablissement(String nomChefEtablissement) {
        this.nomChefEtablissement = nomChefEtablissement;
    }

    public String getPrenomChefEtabissement() {
        return prenomChefEtabissement;
    }

    public void setPrenomChefEtabissement(String prenomChefEtabissement) {
        this.prenomChefEtabissement = prenomChefEtabissement;
    }

    public String getAvisChefEtablissement() {
        return avisChefEtablissement;
    }

    public void setAvisChefEtablissement(String avisChefEtablissement) {
        this.avisChefEtablissement = avisChefEtablissement;
    }

    public Set<CommissionDTO> getCommissions() {
        return commissions;
    }

    public void setCommissions(Set<CommissionDTO> commissions) {
        this.commissions = commissions;
    }

    public Long getJuryId() {
        return juryId;
    }

    public void setJuryId(Long juryId) {
        this.juryId = juryId;
    }

    public Long getMembreJuryId() {
        return membreJuryId;
    }

    public void setMembreJuryId(Long membreJuryId) {
        this.membreJuryId = membreJuryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MembreJuryJuryDTO membreJuryJuryDTO = (MembreJuryJuryDTO) o;
        if (membreJuryJuryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), membreJuryJuryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MembreJuryJuryDTO{" +
            "id=" + getId() +
            ", fonction='" + getFonction() + "'" +
            ", status='" + getStatus() + "'" +
            ", experience='" + getExperience() + "'" +
            ", secteur='" + getSecteur() + "'" +
            ", quartier='" + getQuartier() + "'" +
            ", diplomeAcademique='" + getDiplomeAcademique() + "'" +
            ", diplomeProfessionnel='" + getDiplomeProfessionnel() + "'" +
            ", nomChefEtablissement='" + getNomChefEtablissement() + "'" +
            ", prenomChefEtabissement='" + getPrenomChefEtabissement() + "'" +
            ", avisChefEtablissement='" + getAvisChefEtablissement() + "'" +
            ", jury=" + getJuryId() +
            ", membreJury=" + getMembreJuryId() +
            "}";
    }
}
