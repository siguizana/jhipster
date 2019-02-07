package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Sanction entity.
 */
public class SanctionDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleSanction;

    private Long fraudeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleSanction() {
        return libelleSanction;
    }

    public void setLibelleSanction(String libelleSanction) {
        this.libelleSanction = libelleSanction;
    }

    public Long getFraudeId() {
        return fraudeId;
    }

    public void setFraudeId(Long fraudeId) {
        this.fraudeId = fraudeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SanctionDTO sanctionDTO = (SanctionDTO) o;
        if (sanctionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sanctionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SanctionDTO{" +
            "id=" + getId() +
            ", libelleSanction='" + getLibelleSanction() + "'" +
            ", fraude=" + getFraudeId() +
            "}";
    }
}
