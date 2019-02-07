package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VillageSecteur entity.
 */
public class VillageSecteurDTO implements Serializable {

    private Long id;

    private String codeVillageSecteur;

    @NotNull
    private String libelleVillageSecteur;

    private Long communeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeVillageSecteur() {
        return codeVillageSecteur;
    }

    public void setCodeVillageSecteur(String codeVillageSecteur) {
        this.codeVillageSecteur = codeVillageSecteur;
    }

    public String getLibelleVillageSecteur() {
        return libelleVillageSecteur;
    }

    public void setLibelleVillageSecteur(String libelleVillageSecteur) {
        this.libelleVillageSecteur = libelleVillageSecteur;
    }

    public Long getCommuneId() {
        return communeId;
    }

    public void setCommuneId(Long communeId) {
        this.communeId = communeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VillageSecteurDTO villageSecteurDTO = (VillageSecteurDTO) o;
        if (villageSecteurDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), villageSecteurDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VillageSecteurDTO{" +
            "id=" + getId() +
            ", codeVillageSecteur='" + getCodeVillageSecteur() + "'" +
            ", libelleVillageSecteur='" + getLibelleVillageSecteur() + "'" +
            ", commune=" + getCommuneId() +
            "}";
    }
}
