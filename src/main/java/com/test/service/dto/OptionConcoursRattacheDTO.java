package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OptionConcoursRattache entity.
 */
public class OptionConcoursRattacheDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleOptionConcoursRattache;

    private Long concoursRattacheId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleOptionConcoursRattache() {
        return libelleOptionConcoursRattache;
    }

    public void setLibelleOptionConcoursRattache(String libelleOptionConcoursRattache) {
        this.libelleOptionConcoursRattache = libelleOptionConcoursRattache;
    }

    public Long getConcoursRattacheId() {
        return concoursRattacheId;
    }

    public void setConcoursRattacheId(Long concoursRattacheId) {
        this.concoursRattacheId = concoursRattacheId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OptionConcoursRattacheDTO optionConcoursRattacheDTO = (OptionConcoursRattacheDTO) o;
        if (optionConcoursRattacheDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), optionConcoursRattacheDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OptionConcoursRattacheDTO{" +
            "id=" + getId() +
            ", libelleOptionConcoursRattache='" + getLibelleOptionConcoursRattache() + "'" +
            ", concoursRattache=" + getConcoursRattacheId() +
            "}";
    }
}
