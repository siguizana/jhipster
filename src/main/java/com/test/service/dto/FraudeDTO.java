package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Fraude entity.
 */
public class FraudeDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleFraude;

    private Long typeFraudeId;

    private Long inscriptionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleFraude() {
        return libelleFraude;
    }

    public void setLibelleFraude(String libelleFraude) {
        this.libelleFraude = libelleFraude;
    }

    public Long getTypeFraudeId() {
        return typeFraudeId;
    }

    public void setTypeFraudeId(Long typeFraudeId) {
        this.typeFraudeId = typeFraudeId;
    }

    public Long getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FraudeDTO fraudeDTO = (FraudeDTO) o;
        if (fraudeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fraudeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FraudeDTO{" +
            "id=" + getId() +
            ", libelleFraude='" + getLibelleFraude() + "'" +
            ", typeFraude=" + getTypeFraudeId() +
            ", inscription=" + getInscriptionId() +
            "}";
    }
}
