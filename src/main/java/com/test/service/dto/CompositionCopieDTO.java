package com.test.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CompositionCopie entity.
 */
public class CompositionCopieDTO implements Serializable {

    private Long id;

    private Float note;

    private LocalDate dateComposition;

    @NotNull
    private String numeroAnonymat;

    private Long inscriptionId;

    private Long epreuveSpecialiteOptionId;

    private Long etapeExamenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getNote() {
        return note;
    }

    public void setNote(Float note) {
        this.note = note;
    }

    public LocalDate getDateComposition() {
        return dateComposition;
    }

    public void setDateComposition(LocalDate dateComposition) {
        this.dateComposition = dateComposition;
    }

    public String getNumeroAnonymat() {
        return numeroAnonymat;
    }

    public void setNumeroAnonymat(String numeroAnonymat) {
        this.numeroAnonymat = numeroAnonymat;
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

        CompositionCopieDTO compositionCopieDTO = (CompositionCopieDTO) o;
        if (compositionCopieDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compositionCopieDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompositionCopieDTO{" +
            "id=" + getId() +
            ", note=" + getNote() +
            ", dateComposition='" + getDateComposition() + "'" +
            ", numeroAnonymat='" + getNumeroAnonymat() + "'" +
            ", inscription=" + getInscriptionId() +
            ", epreuveSpecialiteOption=" + getEpreuveSpecialiteOptionId() +
            ", etapeExamen=" + getEtapeExamenId() +
            "}";
    }
}
