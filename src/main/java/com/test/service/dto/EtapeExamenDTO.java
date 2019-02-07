package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EtapeExamen entity.
 */
public class EtapeExamenDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleEtapeExamen;

    private Long typeExamenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleEtapeExamen() {
        return libelleEtapeExamen;
    }

    public void setLibelleEtapeExamen(String libelleEtapeExamen) {
        this.libelleEtapeExamen = libelleEtapeExamen;
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

        EtapeExamenDTO etapeExamenDTO = (EtapeExamenDTO) o;
        if (etapeExamenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etapeExamenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtapeExamenDTO{" +
            "id=" + getId() +
            ", libelleEtapeExamen='" + getLibelleEtapeExamen() + "'" +
            ", typeExamen=" + getTypeExamenId() +
            "}";
    }
}
