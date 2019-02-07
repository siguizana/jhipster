package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TypeDecision entity.
 */
public class TypeDecisionDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleTypeDecision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleTypeDecision() {
        return libelleTypeDecision;
    }

    public void setLibelleTypeDecision(String libelleTypeDecision) {
        this.libelleTypeDecision = libelleTypeDecision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeDecisionDTO typeDecisionDTO = (TypeDecisionDTO) o;
        if (typeDecisionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeDecisionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeDecisionDTO{" +
            "id=" + getId() +
            ", libelleTypeDecision='" + getLibelleTypeDecision() + "'" +
            "}";
    }
}
