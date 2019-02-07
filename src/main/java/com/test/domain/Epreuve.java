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
 * A Epreuve.
 */
@Entity
@Table(name = "epreuve")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Epreuve implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "epreuve")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("epreuves")
    private TypeEpreuve typeEpreuve;

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

    public Epreuve libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<EpreuveSpecialiteOption> getEpreuveSpecialiteOptions() {
        return epreuveSpecialiteOptions;
    }

    public Epreuve epreuveSpecialiteOptions(Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions) {
        this.epreuveSpecialiteOptions = epreuveSpecialiteOptions;
        return this;
    }

    public Epreuve addEpreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOptions.add(epreuveSpecialiteOption);
        epreuveSpecialiteOption.setEpreuve(this);
        return this;
    }

    public Epreuve removeEpreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOptions.remove(epreuveSpecialiteOption);
        epreuveSpecialiteOption.setEpreuve(null);
        return this;
    }

    public void setEpreuveSpecialiteOptions(Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions) {
        this.epreuveSpecialiteOptions = epreuveSpecialiteOptions;
    }

    public TypeEpreuve getTypeEpreuve() {
        return typeEpreuve;
    }

    public Epreuve typeEpreuve(TypeEpreuve typeEpreuve) {
        this.typeEpreuve = typeEpreuve;
        return this;
    }

    public void setTypeEpreuve(TypeEpreuve typeEpreuve) {
        this.typeEpreuve = typeEpreuve;
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
        Epreuve epreuve = (Epreuve) o;
        if (epreuve.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), epreuve.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Epreuve{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
