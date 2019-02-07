package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CritereExamen entity.
 */
public class CritereExamenDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleCritereExamen;

    @NotNull
    private Float valeur;

    private Long regionId;

    private Long optionConcoursRattacheId;

    private Long sessionId;

    private Long typeExamenId;

    private Long typeCritereId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleCritereExamen() {
        return libelleCritereExamen;
    }

    public void setLibelleCritereExamen(String libelleCritereExamen) {
        this.libelleCritereExamen = libelleCritereExamen;
    }

    public Float getValeur() {
        return valeur;
    }

    public void setValeur(Float valeur) {
        this.valeur = valeur;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getOptionConcoursRattacheId() {
        return optionConcoursRattacheId;
    }

    public void setOptionConcoursRattacheId(Long optionConcoursRattacheId) {
        this.optionConcoursRattacheId = optionConcoursRattacheId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getTypeExamenId() {
        return typeExamenId;
    }

    public void setTypeExamenId(Long typeExamenId) {
        this.typeExamenId = typeExamenId;
    }

    public Long getTypeCritereId() {
        return typeCritereId;
    }

    public void setTypeCritereId(Long typeCritereId) {
        this.typeCritereId = typeCritereId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CritereExamenDTO critereExamenDTO = (CritereExamenDTO) o;
        if (critereExamenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), critereExamenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CritereExamenDTO{" +
            "id=" + getId() +
            ", libelleCritereExamen='" + getLibelleCritereExamen() + "'" +
            ", valeur=" + getValeur() +
            ", region=" + getRegionId() +
            ", optionConcoursRattache=" + getOptionConcoursRattacheId() +
            ", session=" + getSessionId() +
            ", typeExamen=" + getTypeExamenId() +
            ", typeCritere=" + getTypeCritereId() +
            "}";
    }
}
