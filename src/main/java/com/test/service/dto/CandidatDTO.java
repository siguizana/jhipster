package com.test.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.test.domain.enumeration.Sexe;

/**
 * A DTO for the Candidat entity.
 */
public class CandidatDTO implements Serializable {

    private Long id;

    private String codeCandidat;

    @NotNull
    private String nomCandidat;

    @NotNull
    private String prenomCandidat;

    @NotNull
    private LocalDate dateNaissance;

    @NotNull
    private String lieuNaissance;

    @NotNull
    private String paysNaissance;

    @NotNull
    private Sexe sexe;

    private String telephoneCandidat;

    @NotNull
    private String nationaliteCandidat;

    private String matriculeCandidat;

    private LocalDate dateEnregistrementCandidat;

    private LocalDate dateDebutCariereCandidat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCandidat() {
        return codeCandidat;
    }

    public void setCodeCandidat(String codeCandidat) {
        this.codeCandidat = codeCandidat;
    }

    public String getNomCandidat() {
        return nomCandidat;
    }

    public void setNomCandidat(String nomCandidat) {
        this.nomCandidat = nomCandidat;
    }

    public String getPrenomCandidat() {
        return prenomCandidat;
    }

    public void setPrenomCandidat(String prenomCandidat) {
        this.prenomCandidat = prenomCandidat;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getPaysNaissance() {
        return paysNaissance;
    }

    public void setPaysNaissance(String paysNaissance) {
        this.paysNaissance = paysNaissance;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getTelephoneCandidat() {
        return telephoneCandidat;
    }

    public void setTelephoneCandidat(String telephoneCandidat) {
        this.telephoneCandidat = telephoneCandidat;
    }

    public String getNationaliteCandidat() {
        return nationaliteCandidat;
    }

    public void setNationaliteCandidat(String nationaliteCandidat) {
        this.nationaliteCandidat = nationaliteCandidat;
    }

    public String getMatriculeCandidat() {
        return matriculeCandidat;
    }

    public void setMatriculeCandidat(String matriculeCandidat) {
        this.matriculeCandidat = matriculeCandidat;
    }

    public LocalDate getDateEnregistrementCandidat() {
        return dateEnregistrementCandidat;
    }

    public void setDateEnregistrementCandidat(LocalDate dateEnregistrementCandidat) {
        this.dateEnregistrementCandidat = dateEnregistrementCandidat;
    }

    public LocalDate getDateDebutCariereCandidat() {
        return dateDebutCariereCandidat;
    }

    public void setDateDebutCariereCandidat(LocalDate dateDebutCariereCandidat) {
        this.dateDebutCariereCandidat = dateDebutCariereCandidat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CandidatDTO candidatDTO = (CandidatDTO) o;
        if (candidatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandidatDTO{" +
            "id=" + getId() +
            ", codeCandidat='" + getCodeCandidat() + "'" +
            ", nomCandidat='" + getNomCandidat() + "'" +
            ", prenomCandidat='" + getPrenomCandidat() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", paysNaissance='" + getPaysNaissance() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", telephoneCandidat='" + getTelephoneCandidat() + "'" +
            ", nationaliteCandidat='" + getNationaliteCandidat() + "'" +
            ", matriculeCandidat='" + getMatriculeCandidat() + "'" +
            ", dateEnregistrementCandidat='" + getDateEnregistrementCandidat() + "'" +
            ", dateDebutCariereCandidat='" + getDateDebutCariereCandidat() + "'" +
            "}";
    }
}
