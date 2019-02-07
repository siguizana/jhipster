package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Commission entity.
 */
public class CommissionDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleCommission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleCommission() {
        return libelleCommission;
    }

    public void setLibelleCommission(String libelleCommission) {
        this.libelleCommission = libelleCommission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommissionDTO commissionDTO = (CommissionDTO) o;
        if (commissionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commissionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommissionDTO{" +
            "id=" + getId() +
            ", libelleCommission='" + getLibelleCommission() + "'" +
            "}";
    }
}
