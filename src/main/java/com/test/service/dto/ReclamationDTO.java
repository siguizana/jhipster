package com.test.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Reclamation entity.
 */
public class ReclamationDTO implements Serializable {

    private Long id;

    @NotNull
    private String motifReclamation;

    private LocalDate dateReclamation;

    private Float noteReclamation;

    private Boolean statutReclamation;

    private Long compositionCopieId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotifReclamation() {
        return motifReclamation;
    }

    public void setMotifReclamation(String motifReclamation) {
        this.motifReclamation = motifReclamation;
    }

    public LocalDate getDateReclamation() {
        return dateReclamation;
    }

    public void setDateReclamation(LocalDate dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public Float getNoteReclamation() {
        return noteReclamation;
    }

    public void setNoteReclamation(Float noteReclamation) {
        this.noteReclamation = noteReclamation;
    }

    public Boolean isStatutReclamation() {
        return statutReclamation;
    }

    public void setStatutReclamation(Boolean statutReclamation) {
        this.statutReclamation = statutReclamation;
    }

    public Long getCompositionCopieId() {
        return compositionCopieId;
    }

    public void setCompositionCopieId(Long compositionCopieId) {
        this.compositionCopieId = compositionCopieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReclamationDTO reclamationDTO = (ReclamationDTO) o;
        if (reclamationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reclamationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReclamationDTO{" +
            "id=" + getId() +
            ", motifReclamation='" + getMotifReclamation() + "'" +
            ", dateReclamation='" + getDateReclamation() + "'" +
            ", noteReclamation=" + getNoteReclamation() +
            ", statutReclamation='" + isStatutReclamation() + "'" +
            ", compositionCopie=" + getCompositionCopieId() +
            "}";
    }
}
