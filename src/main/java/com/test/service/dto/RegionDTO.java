package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Region entity.
 */
public class RegionDTO implements Serializable {

    private Long id;

    private String codeRegion;

    @NotNull
    private String libelleRegion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeRegion() {
        return codeRegion;
    }

    public void setCodeRegion(String codeRegion) {
        this.codeRegion = codeRegion;
    }

    public String getLibelleRegion() {
        return libelleRegion;
    }

    public void setLibelleRegion(String libelleRegion) {
        this.libelleRegion = libelleRegion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegionDTO regionDTO = (RegionDTO) o;
        if (regionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegionDTO{" +
            "id=" + getId() +
            ", codeRegion='" + getCodeRegion() + "'" +
            ", libelleRegion='" + getLibelleRegion() + "'" +
            "}";
    }
}
