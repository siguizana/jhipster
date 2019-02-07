package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TypeCentreComposition entity.
 */
public class TypeCentreCompositionDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleTypeCentreComposition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleTypeCentreComposition() {
        return libelleTypeCentreComposition;
    }

    public void setLibelleTypeCentreComposition(String libelleTypeCentreComposition) {
        this.libelleTypeCentreComposition = libelleTypeCentreComposition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeCentreCompositionDTO typeCentreCompositionDTO = (TypeCentreCompositionDTO) o;
        if (typeCentreCompositionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeCentreCompositionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeCentreCompositionDTO{" +
            "id=" + getId() +
            ", libelleTypeCentreComposition='" + getLibelleTypeCentreComposition() + "'" +
            "}";
    }
}
