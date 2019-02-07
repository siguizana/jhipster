package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TypeEtablissement entity.
 */
public class TypeEtablissementDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleTypeEtablissement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleTypeEtablissement() {
        return libelleTypeEtablissement;
    }

    public void setLibelleTypeEtablissement(String libelleTypeEtablissement) {
        this.libelleTypeEtablissement = libelleTypeEtablissement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeEtablissementDTO typeEtablissementDTO = (TypeEtablissementDTO) o;
        if (typeEtablissementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeEtablissementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeEtablissementDTO{" +
            "id=" + getId() +
            ", libelleTypeEtablissement='" + getLibelleTypeEtablissement() + "'" +
            "}";
    }
}
