package com.test.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ChoixEtablissement entity.
 */
public class ChoixEtablissementDTO implements Serializable {

    private Long id;

    private String priorite;

    private Long inscriptionId;

    private Long etablissementId;

    private Long specialiteOptionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public Long getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public Long getEtablissementId() {
        return etablissementId;
    }

    public void setEtablissementId(Long etablissementId) {
        this.etablissementId = etablissementId;
    }

    public Long getSpecialiteOptionId() {
        return specialiteOptionId;
    }

    public void setSpecialiteOptionId(Long specialiteOptionId) {
        this.specialiteOptionId = specialiteOptionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChoixEtablissementDTO choixEtablissementDTO = (ChoixEtablissementDTO) o;
        if (choixEtablissementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), choixEtablissementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChoixEtablissementDTO{" +
            "id=" + getId() +
            ", priorite='" + getPriorite() + "'" +
            ", inscription=" + getInscriptionId() +
            ", etablissement=" + getEtablissementId() +
            ", specialiteOption=" + getSpecialiteOptionId() +
            "}";
    }
}
