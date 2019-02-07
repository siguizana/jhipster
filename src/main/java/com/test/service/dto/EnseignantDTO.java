package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Enseignant entity.
 */
public class EnseignantDTO implements Serializable {

    private Long id;

    private String matricule;

    @NotNull
    private String nomEnseignant;

    @NotNull
    private String prenomEnseignant;

    private String contactEnseignant;

    private String gradeEnseignant;

    private String echelonEnseignant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomEnseignant() {
        return nomEnseignant;
    }

    public void setNomEnseignant(String nomEnseignant) {
        this.nomEnseignant = nomEnseignant;
    }

    public String getPrenomEnseignant() {
        return prenomEnseignant;
    }

    public void setPrenomEnseignant(String prenomEnseignant) {
        this.prenomEnseignant = prenomEnseignant;
    }

    public String getContactEnseignant() {
        return contactEnseignant;
    }

    public void setContactEnseignant(String contactEnseignant) {
        this.contactEnseignant = contactEnseignant;
    }

    public String getGradeEnseignant() {
        return gradeEnseignant;
    }

    public void setGradeEnseignant(String gradeEnseignant) {
        this.gradeEnseignant = gradeEnseignant;
    }

    public String getEchelonEnseignant() {
        return echelonEnseignant;
    }

    public void setEchelonEnseignant(String echelonEnseignant) {
        this.echelonEnseignant = echelonEnseignant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnseignantDTO enseignantDTO = (EnseignantDTO) o;
        if (enseignantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enseignantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnseignantDTO{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", nomEnseignant='" + getNomEnseignant() + "'" +
            ", prenomEnseignant='" + getPrenomEnseignant() + "'" +
            ", contactEnseignant='" + getContactEnseignant() + "'" +
            ", gradeEnseignant='" + getGradeEnseignant() + "'" +
            ", echelonEnseignant='" + getEchelonEnseignant() + "'" +
            "}";
    }
}
