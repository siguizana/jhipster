package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Filiere.
 */
@Entity
@Table(name = "filiere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Filiere implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "filiere")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SpecialiteOption> specialiteOptions = new HashSet<>();
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

    public Filiere libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<SpecialiteOption> getSpecialiteOptions() {
        return specialiteOptions;
    }

    public Filiere specialiteOptions(Set<SpecialiteOption> specialiteOptions) {
        this.specialiteOptions = specialiteOptions;
        return this;
    }

    public Filiere addSpecialiteOption(SpecialiteOption specialiteOption) {
        this.specialiteOptions.add(specialiteOption);
        specialiteOption.setFiliere(this);
        return this;
    }

    public Filiere removeSpecialiteOption(SpecialiteOption specialiteOption) {
        this.specialiteOptions.remove(specialiteOption);
        specialiteOption.setFiliere(null);
        return this;
    }

    public void setSpecialiteOptions(Set<SpecialiteOption> specialiteOptions) {
        this.specialiteOptions = specialiteOptions;
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
        Filiere filiere = (Filiere) o;
        if (filiere.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filiere.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Filiere{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
