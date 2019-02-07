package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EpreuveSpecialiteOption entity.
 */
public class EpreuveSpecialiteOptionDTO implements Serializable {

    private Long id;

    private Boolean epreuveRachat;

    private Boolean epreuveAdmissibilite;

    private Boolean epreuveFacultative;

    private Float noteEliminatoire;

    @NotNull
    private Integer coefficient;

    private Long inscriptionId;

    private Long epreuveId;

    private Long specialiteOptionId;

    private Long groupeEpreuveId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEpreuveRachat() {
        return epreuveRachat;
    }

    public void setEpreuveRachat(Boolean epreuveRachat) {
        this.epreuveRachat = epreuveRachat;
    }

    public Boolean isEpreuveAdmissibilite() {
        return epreuveAdmissibilite;
    }

    public void setEpreuveAdmissibilite(Boolean epreuveAdmissibilite) {
        this.epreuveAdmissibilite = epreuveAdmissibilite;
    }

    public Boolean isEpreuveFacultative() {
        return epreuveFacultative;
    }

    public void setEpreuveFacultative(Boolean epreuveFacultative) {
        this.epreuveFacultative = epreuveFacultative;
    }

    public Float getNoteEliminatoire() {
        return noteEliminatoire;
    }

    public void setNoteEliminatoire(Float noteEliminatoire) {
        this.noteEliminatoire = noteEliminatoire;
    }

    public Integer getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    public Long getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public Long getEpreuveId() {
        return epreuveId;
    }

    public void setEpreuveId(Long epreuveId) {
        this.epreuveId = epreuveId;
    }

    public Long getSpecialiteOptionId() {
        return specialiteOptionId;
    }

    public void setSpecialiteOptionId(Long specialiteOptionId) {
        this.specialiteOptionId = specialiteOptionId;
    }

    public Long getGroupeEpreuveId() {
        return groupeEpreuveId;
    }

    public void setGroupeEpreuveId(Long groupeEpreuveId) {
        this.groupeEpreuveId = groupeEpreuveId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EpreuveSpecialiteOptionDTO epreuveSpecialiteOptionDTO = (EpreuveSpecialiteOptionDTO) o;
        if (epreuveSpecialiteOptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), epreuveSpecialiteOptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EpreuveSpecialiteOptionDTO{" +
            "id=" + getId() +
            ", epreuveRachat='" + isEpreuveRachat() + "'" +
            ", epreuveAdmissibilite='" + isEpreuveAdmissibilite() + "'" +
            ", epreuveFacultative='" + isEpreuveFacultative() + "'" +
            ", noteEliminatoire=" + getNoteEliminatoire() +
            ", coefficient=" + getCoefficient() +
            ", inscription=" + getInscriptionId() +
            ", epreuve=" + getEpreuveId() +
            ", specialiteOption=" + getSpecialiteOptionId() +
            ", groupeEpreuve=" + getGroupeEpreuveId() +
            "}";
    }
}
