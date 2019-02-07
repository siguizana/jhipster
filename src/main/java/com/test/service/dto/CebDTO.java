package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.test.domain.enumeration.TypeCeb;

/**
 * A DTO for the Ceb entity.
 */
public class CebDTO implements Serializable {

    private Long id;

    @NotNull
    private String codeCeb;

    @NotNull
    private String libelleCeb;

    @NotNull
    private TypeCeb typeCeb;

    private Long communeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCeb() {
        return codeCeb;
    }

    public void setCodeCeb(String codeCeb) {
        this.codeCeb = codeCeb;
    }

    public String getLibelleCeb() {
        return libelleCeb;
    }

    public void setLibelleCeb(String libelleCeb) {
        this.libelleCeb = libelleCeb;
    }

    public TypeCeb getTypeCeb() {
        return typeCeb;
    }

    public void setTypeCeb(TypeCeb typeCeb) {
        this.typeCeb = typeCeb;
    }

    public Long getCommuneId() {
        return communeId;
    }

    public void setCommuneId(Long communeId) {
        this.communeId = communeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CebDTO cebDTO = (CebDTO) o;
        if (cebDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cebDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CebDTO{" +
            "id=" + getId() +
            ", codeCeb='" + getCodeCeb() + "'" +
            ", libelleCeb='" + getLibelleCeb() + "'" +
            ", typeCeb='" + getTypeCeb() + "'" +
            ", commune=" + getCommuneId() +
            "}";
    }
}
