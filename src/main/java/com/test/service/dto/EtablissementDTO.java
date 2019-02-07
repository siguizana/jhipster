package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Etablissement entity.
 */
public class EtablissementDTO implements Serializable {

    private Long id;

    private String codeEtablissement;

    @NotNull
    private String libelleEtablissement;

    @NotNull
    private String nomResponsableEtablissement;

    @NotNull
    private String prenomResponsableEtablissement;

    @NotNull
    private String contactactEtablissement;

    private Long cebId;

    private Long typeEtablissementId;

    private Long villageSecteurId;

    private Long centreCompositionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeEtablissement() {
        return codeEtablissement;
    }

    public void setCodeEtablissement(String codeEtablissement) {
        this.codeEtablissement = codeEtablissement;
    }

    public String getLibelleEtablissement() {
        return libelleEtablissement;
    }

    public void setLibelleEtablissement(String libelleEtablissement) {
        this.libelleEtablissement = libelleEtablissement;
    }

    public String getNomResponsableEtablissement() {
        return nomResponsableEtablissement;
    }

    public void setNomResponsableEtablissement(String nomResponsableEtablissement) {
        this.nomResponsableEtablissement = nomResponsableEtablissement;
    }

    public String getPrenomResponsableEtablissement() {
        return prenomResponsableEtablissement;
    }

    public void setPrenomResponsableEtablissement(String prenomResponsableEtablissement) {
        this.prenomResponsableEtablissement = prenomResponsableEtablissement;
    }

    public String getContactactEtablissement() {
        return contactactEtablissement;
    }

    public void setContactactEtablissement(String contactactEtablissement) {
        this.contactactEtablissement = contactactEtablissement;
    }

    public Long getCebId() {
        return cebId;
    }

    public void setCebId(Long cebId) {
        this.cebId = cebId;
    }

    public Long getTypeEtablissementId() {
        return typeEtablissementId;
    }

    public void setTypeEtablissementId(Long typeEtablissementId) {
        this.typeEtablissementId = typeEtablissementId;
    }

    public Long getVillageSecteurId() {
        return villageSecteurId;
    }

    public void setVillageSecteurId(Long villageSecteurId) {
        this.villageSecteurId = villageSecteurId;
    }

    public Long getCentreCompositionId() {
        return centreCompositionId;
    }

    public void setCentreCompositionId(Long centreCompositionId) {
        this.centreCompositionId = centreCompositionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EtablissementDTO etablissementDTO = (EtablissementDTO) o;
        if (etablissementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etablissementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtablissementDTO{" +
            "id=" + getId() +
            ", codeEtablissement='" + getCodeEtablissement() + "'" +
            ", libelleEtablissement='" + getLibelleEtablissement() + "'" +
            ", nomResponsableEtablissement='" + getNomResponsableEtablissement() + "'" +
            ", prenomResponsableEtablissement='" + getPrenomResponsableEtablissement() + "'" +
            ", contactactEtablissement='" + getContactactEtablissement() + "'" +
            ", ceb=" + getCebId() +
            ", typeEtablissement=" + getTypeEtablissementId() +
            ", villageSecteur=" + getVillageSecteurId() +
            ", centreComposition=" + getCentreCompositionId() +
            "}";
    }
}
