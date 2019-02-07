package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TypeDiplome entity.
 */
public class TypeDiplomeDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleTypeDiplome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleTypeDiplome() {
        return libelleTypeDiplome;
    }

    public void setLibelleTypeDiplome(String libelleTypeDiplome) {
        this.libelleTypeDiplome = libelleTypeDiplome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeDiplomeDTO typeDiplomeDTO = (TypeDiplomeDTO) o;
        if (typeDiplomeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeDiplomeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeDiplomeDTO{" +
            "id=" + getId() +
            ", libelleTypeDiplome='" + getLibelleTypeDiplome() + "'" +
            "}";
    }
}
