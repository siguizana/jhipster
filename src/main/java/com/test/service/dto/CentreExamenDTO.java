package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CentreExamen entity.
 */
public class CentreExamenDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleCentreExamen;

    private Long regionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleCentreExamen() {
        return libelleCentreExamen;
    }

    public void setLibelleCentreExamen(String libelleCentreExamen) {
        this.libelleCentreExamen = libelleCentreExamen;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CentreExamenDTO centreExamenDTO = (CentreExamenDTO) o;
        if (centreExamenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), centreExamenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CentreExamenDTO{" +
            "id=" + getId() +
            ", libelleCentreExamen='" + getLibelleCentreExamen() + "'" +
            ", region=" + getRegionId() +
            "}";
    }
}
