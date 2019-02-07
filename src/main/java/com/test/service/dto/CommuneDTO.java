package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.test.domain.enumeration.TypeCommune;

/**
 * A DTO for the Commune entity.
 */
public class CommuneDTO implements Serializable {

    private Long id;

    private String codeCommune;

    @NotNull
    private String libelleCommune;

    @NotNull
    private TypeCommune typeCommune;

    private Long provinceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCommune() {
        return codeCommune;
    }

    public void setCodeCommune(String codeCommune) {
        this.codeCommune = codeCommune;
    }

    public String getLibelleCommune() {
        return libelleCommune;
    }

    public void setLibelleCommune(String libelleCommune) {
        this.libelleCommune = libelleCommune;
    }

    public TypeCommune getTypeCommune() {
        return typeCommune;
    }

    public void setTypeCommune(TypeCommune typeCommune) {
        this.typeCommune = typeCommune;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommuneDTO communeDTO = (CommuneDTO) o;
        if (communeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), communeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommuneDTO{" +
            "id=" + getId() +
            ", codeCommune='" + getCodeCommune() + "'" +
            ", libelleCommune='" + getLibelleCommune() + "'" +
            ", typeCommune='" + getTypeCommune() + "'" +
            ", province=" + getProvinceId() +
            "}";
    }
}
