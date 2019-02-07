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
 * A Handicape.
 */
@Entity
@Table(name = "handicape")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Handicape implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "handicape")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dispense> dispenses = new HashSet<>();
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

    public Handicape libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Dispense> getDispenses() {
        return dispenses;
    }

    public Handicape dispenses(Set<Dispense> dispenses) {
        this.dispenses = dispenses;
        return this;
    }

    public Handicape addDispense(Dispense dispense) {
        this.dispenses.add(dispense);
        dispense.setHandicape(this);
        return this;
    }

    public Handicape removeDispense(Dispense dispense) {
        this.dispenses.remove(dispense);
        dispense.setHandicape(null);
        return this;
    }

    public void setDispenses(Set<Dispense> dispenses) {
        this.dispenses = dispenses;
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
        Handicape handicape = (Handicape) o;
        if (handicape.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), handicape.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Handicape{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
