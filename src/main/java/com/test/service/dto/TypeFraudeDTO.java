package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TypeFraude entity.
 */
public class TypeFraudeDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleTypeFraude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleTypeFraude() {
        return libelleTypeFraude;
    }

    public void setLibelleTypeFraude(String libelleTypeFraude) {
        this.libelleTypeFraude = libelleTypeFraude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeFraudeDTO typeFraudeDTO = (TypeFraudeDTO) o;
        if (typeFraudeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeFraudeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeFraudeDTO{" +
            "id=" + getId() +
            ", libelleTypeFraude='" + getLibelleTypeFraude() + "'" +
            "}";
    }
}
