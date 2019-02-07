package com.test.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Session entity.
 */
public class SessionDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    @NotNull
    private LocalDate dateOuverture;

    private LocalDate dateFermeture;

    @NotNull
    private Integer anneeSession;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public LocalDate getDateFermeture() {
        return dateFermeture;
    }

    public void setDateFermeture(LocalDate dateFermeture) {
        this.dateFermeture = dateFermeture;
    }

    public Integer getAnneeSession() {
        return anneeSession;
    }

    public void setAnneeSession(Integer anneeSession) {
        this.anneeSession = anneeSession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SessionDTO sessionDTO = (SessionDTO) o;
        if (sessionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sessionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SessionDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", dateOuverture='" + getDateOuverture() + "'" +
            ", dateFermeture='" + getDateFermeture() + "'" +
            ", anneeSession=" + getAnneeSession() +
            "}";
    }
}
