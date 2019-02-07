package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SalleComposition entity.
 */
public class SalleCompositionDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    private String capaciteSalle;

    private Long centreCompositioJuryId;

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

    public String getCapaciteSalle() {
        return capaciteSalle;
    }

    public void setCapaciteSalle(String capaciteSalle) {
        this.capaciteSalle = capaciteSalle;
    }

    public Long getCentreCompositioJuryId() {
        return centreCompositioJuryId;
    }

    public void setCentreCompositioJuryId(Long centreCompositioJuryId) {
        this.centreCompositioJuryId = centreCompositioJuryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalleCompositionDTO salleCompositionDTO = (SalleCompositionDTO) o;
        if (salleCompositionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salleCompositionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalleCompositionDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", capaciteSalle='" + getCapaciteSalle() + "'" +
            ", centreCompositioJury=" + getCentreCompositioJuryId() +
            "}";
    }
}
