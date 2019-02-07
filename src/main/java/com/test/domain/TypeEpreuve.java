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
 * A TypeEpreuve.
 */
@Entity
@Table(name = "type_epreuve")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeEpreuve implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "typeEpreuve")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Epreuve> epreuves = new HashSet<>();
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

    public TypeEpreuve libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Epreuve> getEpreuves() {
        return epreuves;
    }

    public TypeEpreuve epreuves(Set<Epreuve> epreuves) {
        this.epreuves = epreuves;
        return this;
    }

    public TypeEpreuve addEpreuve(Epreuve epreuve) {
        this.epreuves.add(epreuve);
        epreuve.setTypeEpreuve(this);
        return this;
    }

    public TypeEpreuve removeEpreuve(Epreuve epreuve) {
        this.epreuves.remove(epreuve);
        epreuve.setTypeEpreuve(null);
        return this;
    }

    public void setEpreuves(Set<Epreuve> epreuves) {
        this.epreuves = epreuves;
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
        TypeEpreuve typeEpreuve = (TypeEpreuve) o;
        if (typeEpreuve.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeEpreuve.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeEpreuve{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
