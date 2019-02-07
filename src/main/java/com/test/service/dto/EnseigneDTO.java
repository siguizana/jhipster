package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Enseigne entity.
 */
public class EnseigneDTO implements Serializable {

    private Long id;

    @NotNull
    private String classeEnseigne;

    private Long etablissementId;

    private Long enseignantId;

    private Long sessionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClasseEnseigne() {
        return classeEnseigne;
    }

    public void setClasseEnseigne(String classeEnseigne) {
        this.classeEnseigne = classeEnseigne;
    }

    public Long getEtablissementId() {
        return etablissementId;
    }

    public void setEtablissementId(Long etablissementId) {
        this.etablissementId = etablissementId;
    }

    public Long getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(Long enseignantId) {
        this.enseignantId = enseignantId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnseigneDTO enseigneDTO = (EnseigneDTO) o;
        if (enseigneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enseigneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnseigneDTO{" +
            "id=" + getId() +
            ", classeEnseigne='" + getClasseEnseigne() + "'" +
            ", etablissement=" + getEtablissementId() +
            ", enseignant=" + getEnseignantId() +
            ", session=" + getSessionId() +
            "}";
    }
}
