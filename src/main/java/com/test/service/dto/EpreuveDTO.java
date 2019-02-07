package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Epreuve entity.
 */
public class EpreuveDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    private Long typeEpreuveId;

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

    public Long getTypeEpreuveId() {
        return typeEpreuveId;
    }

    public void setTypeEpreuveId(Long typeEpreuveId) {
        this.typeEpreuveId = typeEpreuveId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EpreuveDTO epreuveDTO = (EpreuveDTO) o;
        if (epreuveDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), epreuveDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EpreuveDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", typeEpreuve=" + getTypeEpreuveId() +
            "}";
    }
}
