package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Dispense entity.
 */
public class DispenseDTO implements Serializable {

    private Long id;

    @NotNull
    private String motitDispense;

    @NotNull
    private String nomPrenomMedecin;

    private Set<EpreuveSpecialiteOptionDTO> epreuveSpecialiteOptions = new HashSet<>();

    private Long inscriptionId;

    private Long handicapeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotitDispense() {
        return motitDispense;
    }

    public void setMotitDispense(String motitDispense) {
        this.motitDispense = motitDispense;
    }

    public String getNomPrenomMedecin() {
        return nomPrenomMedecin;
    }

    public void setNomPrenomMedecin(String nomPrenomMedecin) {
        this.nomPrenomMedecin = nomPrenomMedecin;
    }

    public Set<EpreuveSpecialiteOptionDTO> getEpreuveSpecialiteOptions() {
        return epreuveSpecialiteOptions;
    }

    public void setEpreuveSpecialiteOptions(Set<EpreuveSpecialiteOptionDTO> epreuveSpecialiteOptions) {
        this.epreuveSpecialiteOptions = epreuveSpecialiteOptions;
    }

    public Long getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public Long getHandicapeId() {
        return handicapeId;
    }

    public void setHandicapeId(Long handicapeId) {
        this.handicapeId = handicapeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DispenseDTO dispenseDTO = (DispenseDTO) o;
        if (dispenseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dispenseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DispenseDTO{" +
            "id=" + getId() +
            ", motitDispense='" + getMotitDispense() + "'" +
            ", nomPrenomMedecin='" + getNomPrenomMedecin() + "'" +
            ", inscription=" + getInscriptionId() +
            ", handicape=" + getHandicapeId() +
            "}";
    }
}
