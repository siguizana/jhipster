package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A VillageSecteur.
 */
@Entity
@Table(name = "village_secteur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VillageSecteur implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_village_secteur")
    private String codeVillageSecteur;

    @NotNull
    @Column(name = "libelle_village_secteur", nullable = false)
    private String libelleVillageSecteur;

    @OneToMany(mappedBy = "villageSecteur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Etablissement> etablissements = new HashSet<>();
    @OneToMany(mappedBy = "villageSecteur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inscription> inscriptions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("villageSecteurs")
    private Commune commune;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeVillageSecteur() {
        return codeVillageSecteur;
    }

    public VillageSecteur codeVillageSecteur(String codeVillageSecteur) {
        this.codeVillageSecteur = codeVillageSecteur;
        return this;
    }

    public void setCodeVillageSecteur(String codeVillageSecteur) {
        this.codeVillageSecteur = codeVillageSecteur;
    }

    public String getLibelleVillageSecteur() {
        return libelleVillageSecteur;
    }

    public VillageSecteur libelleVillageSecteur(String libelleVillageSecteur) {
        this.libelleVillageSecteur = libelleVillageSecteur;
        return this;
    }

    public void setLibelleVillageSecteur(String libelleVillageSecteur) {
        this.libelleVillageSecteur = libelleVillageSecteur;
    }

    public Set<Etablissement> getEtablissements() {
        return etablissements;
    }

    public VillageSecteur etablissements(Set<Etablissement> etablissements) {
        this.etablissements = etablissements;
        return this;
    }

    public VillageSecteur addEtablissement(Etablissement etablissement) {
        this.etablissements.add(etablissement);
        etablissement.setVillageSecteur(this);
        return this;
    }

    public VillageSecteur removeEtablissement(Etablissement etablissement) {
        this.etablissements.remove(etablissement);
        etablissement.setVillageSecteur(null);
        return this;
    }

    public void setEtablissements(Set<Etablissement> etablissements) {
        this.etablissements = etablissements;
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public VillageSecteur inscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
        return this;
    }

    public VillageSecteur addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setVillageSecteur(this);
        return this;
    }

    public VillageSecteur removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setVillageSecteur(null);
        return this;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public Commune getCommune() {
        return commune;
    }

    public VillageSecteur commune(Commune commune) {
        this.commune = commune;
        return this;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VillageSecteur villageSecteur = (VillageSecteur) o;
        if (villageSecteur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), villageSecteur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VillageSecteur{" +
            "id=" + getId() +
            ", codeVillageSecteur='" + getCodeVillageSecteur() + "'" +
            ", libelleVillageSecteur='" + getLibelleVillageSecteur() + "'" +
            "}";
    }
}
