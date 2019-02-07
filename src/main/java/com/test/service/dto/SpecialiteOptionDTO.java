package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SpecialiteOption entity.
 */
public class SpecialiteOptionDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    private Long typeExamenId;

    private Long filiereId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getTypeExamenId() {
        return typeExamenId;
    }

    public void setTypeExamenId(Long typeExamenId) {
        this.typeExamenId = typeExamenId;
    }

    public Long getFiliereId() {
        return filiereId;
    }

    public void setFiliereId(Long filiereId) {
        this.filiereId = filiereId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpecialiteOptionDTO specialiteOptionDTO = (SpecialiteOptionDTO) o;
        if (specialiteOptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), specialiteOptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpecialiteOptionDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", typeExamen=" + getTypeExamenId() +
            ", filiere=" + getFiliereId() +
            "}";
    }
}
