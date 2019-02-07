package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Jury entity.
 */
public class JuryDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleJury;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleJury() {
        return libelleJury;
    }

    public void setLibelleJury(String libelleJury) {
        this.libelleJury = libelleJury;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JuryDTO juryDTO = (JuryDTO) o;
        if (juryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), juryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JuryDTO{" +
            "id=" + getId() +
            ", libelleJury='" + getLibelleJury() + "'" +
            "}";
    }
}
