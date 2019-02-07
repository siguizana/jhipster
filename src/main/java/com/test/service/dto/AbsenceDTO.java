package com.test.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Absence entity.
 */
public class AbsenceDTO implements Serializable {

    private Long id;

    private String motifAbsence;

    private LocalDate dateAbsence;

    private Long inscriptionId;

    private Long epreuveSpecialiteOptionId;

    private Long etapeExamenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotifAbsence() {
        return motifAbsence;
    }

    public void setMotifAbsence(String motifAbsence) {
        this.motifAbsence = motifAbsence;
    }

    public LocalDate getDateAbsence() {
        return dateAbsence;
    }

    public void setDateAbsence(LocalDate dateAbsence) {
        this.dateAbsence = dateAbsence;
    }

    public Long getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public Long getEpreuveSpecialiteOptionId() {
        return epreuveSpecialiteOptionId;
    }

    public void setEpreuveSpecialiteOptionId(Long epreuveSpecialiteOptionId) {
        this.epreuveSpecialiteOptionId = epreuveSpecialiteOptionId;
    }

    public Long getEtapeExamenId() {
        return etapeExamenId;
    }

    public void setEtapeExamenId(Long etapeExamenId) {
        this.etapeExamenId = etapeExamenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbsenceDTO absenceDTO = (AbsenceDTO) o;
        if (absenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), absenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AbsenceDTO{" +
            "id=" + getId() +
            ", motifAbsence='" + getMotifAbsence() + "'" +
            ", dateAbsence='" + getDateAbsence() + "'" +
            ", inscription=" + getInscriptionId() +
            ", epreuveSpecialiteOption=" + getEpreuveSpecialiteOptionId() +
            ", etapeExamen=" + getEtapeExamenId() +
            "}";
    }
}
