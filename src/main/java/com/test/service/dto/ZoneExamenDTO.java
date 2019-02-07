package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ZoneExamen entity.
 */
public class ZoneExamenDTO implements Serializable {

    private Long id;

    private String codeZoneExamen;

    @NotNull
    private String libelleZoneExamen;

    private Long centreExamenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeZoneExamen() {
        return codeZoneExamen;
    }

    public void setCodeZoneExamen(String codeZoneExamen) {
        this.codeZoneExamen = codeZoneExamen;
    }

    public String getLibelleZoneExamen() {
        return libelleZoneExamen;
    }

    public void setLibelleZoneExamen(String libelleZoneExamen) {
        this.libelleZoneExamen = libelleZoneExamen;
    }

    public Long getCentreExamenId() {
        return centreExamenId;
    }

    public void setCentreExamenId(Long centreExamenId) {
        this.centreExamenId = centreExamenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZoneExamenDTO zoneExamenDTO = (ZoneExamenDTO) o;
        if (zoneExamenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zoneExamenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZoneExamenDTO{" +
            "id=" + getId() +
            ", codeZoneExamen='" + getCodeZoneExamen() + "'" +
            ", libelleZoneExamen='" + getLibelleZoneExamen() + "'" +
            ", centreExamen=" + getCentreExamenId() +
            "}";
    }
}
