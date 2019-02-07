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
 * A SpecialiteOption.
 */
@Entity
@Table(name = "specialite_option")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SpecialiteOption implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "specialiteOption")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChoixEtablissement> choixEtablissements = new HashSet<>();
    @OneToMany(mappedBy = "specialiteOption")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("specialiteOptions")
    private TypeExamen typeExamen;

    @ManyToOne
    @JsonIgnoreProperties("specialiteOptions")
    private Filiere filiere;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public SpecialiteOption libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<ChoixEtablissement> getChoixEtablissements() {
        return choixEtablissements;
    }

    public SpecialiteOption choixEtablissements(Set<ChoixEtablissement> choixEtablissements) {
        this.choixEtablissements = choixEtablissements;
        return this;
    }

    public SpecialiteOption addChoixEtablissement(ChoixEtablissement choixEtablissement) {
        this.choixEtablissements.add(choixEtablissement);
        choixEtablissement.setSpecialiteOption(this);
        return this;
    }

    public SpecialiteOption removeChoixEtablissement(ChoixEtablissement choixEtablissement) {
        this.choixEtablissements.remove(choixEtablissement);
        choixEtablissement.setSpecialiteOption(null);
        return this;
    }

    public void setChoixEtablissements(Set<ChoixEtablissement> choixEtablissements) {
        this.choixEtablissements = choixEtablissements;
    }

    public Set<EpreuveSpecialiteOption> getEpreuveSpecialiteOptions() {
        return epreuveSpecialiteOptions;
    }

    public SpecialiteOption epreuveSpecialiteOptions(Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions) {
        this.epreuveSpecialiteOptions = epreuveSpecialiteOptions;
        return this;
    }

    public SpecialiteOption addEpreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOptions.add(epreuveSpecialiteOption);
        epreuveSpecialiteOption.setSpecialiteOption(this);
        return this;
    }

    public SpecialiteOption removeEpreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOptions.remove(epreuveSpecialiteOption);
        epreuveSpecialiteOption.setSpecialiteOption(null);
        return this;
    }

    public void setEpreuveSpecialiteOptions(Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions) {
        this.epreuveSpecialiteOptions = epreuveSpecialiteOptions;
    }

    public TypeExamen getTypeExamen() {
        return typeExamen;
    }

    public SpecialiteOption typeExamen(TypeExamen typeExamen) {
        this.typeExamen = typeExamen;
        return this;
    }

    public void setTypeExamen(TypeExamen typeExamen) {
        this.typeExamen = typeExamen;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public SpecialiteOption filiere(Filiere filiere) {
        this.filiere = filiere;
        return this;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
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
        SpecialiteOption specialiteOption = (SpecialiteOption) o;
        if (specialiteOption.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), specialiteOption.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpecialiteOption{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
