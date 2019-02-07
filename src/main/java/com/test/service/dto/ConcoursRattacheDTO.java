package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ConcoursRattache entity.
 */
public class ConcoursRattacheDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleConcoursRattache;

    private Long typeExamenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleConcoursRattache() {
        return libelleConcoursRattache;
    }

    public void setLibelleConcoursRattache(String libelleConcoursRattache) {
        this.libelleConcoursRattache = libelleConcoursRattache;
    }

    public Long getTypeExamenId() {
        return typeExamenId;
    }

    public void setTypeExamenId(Long typeExamenId) {
        this.typeExamenId = typeExamenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConcoursRattacheDTO concoursRattacheDTO = (ConcoursRattacheDTO) o;
        if (concoursRattacheDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), concoursRattacheDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConcoursRattacheDTO{" +
            "id=" + getId() +
            ", libelleConcoursRattache='" + getLibelleConcoursRattache() + "'" +
            ", typeExamen=" + getTypeExamenId() +
            "}";
    }
}
