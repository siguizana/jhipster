package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CentreComposition entity.
 */
public class CentreCompositionDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleCentreComposition;

    private Long cebId;

    private Long zoneExamenId;

    private Long typeCentreCompositionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleCentreComposition() {
        return libelleCentreComposition;
    }

    public void setLibelleCentreComposition(String libelleCentreComposition) {
        this.libelleCentreComposition = libelleCentreComposition;
    }

    public Long getCebId() {
        return cebId;
    }

    public void setCebId(Long cebId) {
        this.cebId = cebId;
    }

    public Long getZoneExamenId() {
        return zoneExamenId;
    }

    public void setZoneExamenId(Long zoneExamenId) {
        this.zoneExamenId = zoneExamenId;
    }

    public Long getTypeCentreCompositionId() {
        return typeCentreCompositionId;
    }

    public void setTypeCentreCompositionId(Long typeCentreCompositionId) {
        this.typeCentreCompositionId = typeCentreCompositionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CentreCompositionDTO centreCompositionDTO = (CentreCompositionDTO) o;
        if (centreCompositionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), centreCompositionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CentreCompositionDTO{" +
            "id=" + getId() +
            ", libelleCentreComposition='" + getLibelleCentreComposition() + "'" +
            ", ceb=" + getCebId() +
            ", zoneExamen=" + getZoneExamenId() +
            ", typeCentreComposition=" + getTypeCentreCompositionId() +
            "}";
    }
}
