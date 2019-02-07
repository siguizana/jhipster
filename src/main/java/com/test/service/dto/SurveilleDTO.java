package com.test.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Surveille entity.
 */
public class SurveilleDTO implements Serializable {

    private Long id;

    private Instant dateDebutSurveillance;

    private Instant dateFinSurveillance;

    private Long membreJuryId;

    private Long salleCompositionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateDebutSurveillance() {
        return dateDebutSurveillance;
    }

    public void setDateDebutSurveillance(Instant dateDebutSurveillance) {
        this.dateDebutSurveillance = dateDebutSurveillance;
    }

    public Instant getDateFinSurveillance() {
        return dateFinSurveillance;
    }

    public void setDateFinSurveillance(Instant dateFinSurveillance) {
        this.dateFinSurveillance = dateFinSurveillance;
    }

    public Long getMembreJuryId() {
        return membreJuryId;
    }

    public void setMembreJuryId(Long membreJuryId) {
        this.membreJuryId = membreJuryId;
    }

    public Long getSalleCompositionId() {
        return salleCompositionId;
    }

    public void setSalleCompositionId(Long salleCompositionId) {
        this.salleCompositionId = salleCompositionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SurveilleDTO surveilleDTO = (SurveilleDTO) o;
        if (surveilleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), surveilleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SurveilleDTO{" +
            "id=" + getId() +
            ", dateDebutSurveillance='" + getDateDebutSurveillance() + "'" +
            ", dateFinSurveillance='" + getDateFinSurveillance() + "'" +
            ", membreJury=" + getMembreJuryId() +
            ", salleComposition=" + getSalleCompositionId() +
            "}";
    }
}
