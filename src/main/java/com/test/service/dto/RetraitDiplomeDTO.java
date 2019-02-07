package com.test.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the RetraitDiplome entity.
 */
public class RetraitDiplomeDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateRetrait;

    private Long resultatId;

    private Long typeDiplomeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateRetrait() {
        return dateRetrait;
    }

    public void setDateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    public Long getResultatId() {
        return resultatId;
    }

    public void setResultatId(Long resultatId) {
        this.resultatId = resultatId;
    }

    public Long getTypeDiplomeId() {
        return typeDiplomeId;
    }

    public void setTypeDiplomeId(Long typeDiplomeId) {
        this.typeDiplomeId = typeDiplomeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RetraitDiplomeDTO retraitDiplomeDTO = (RetraitDiplomeDTO) o;
        if (retraitDiplomeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), retraitDiplomeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RetraitDiplomeDTO{" +
            "id=" + getId() +
            ", dateRetrait='" + getDateRetrait() + "'" +
            ", resultat=" + getResultatId() +
            ", typeDiplome=" + getTypeDiplomeId() +
            "}";
    }
}
