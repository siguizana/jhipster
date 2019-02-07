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
 * A SalleComposition.
 */
@Entity
@Table(name = "salle_composition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalleComposition implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "capacite_salle")
    private String capaciteSalle;

    @OneToMany(mappedBy = "salleComposition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Surveille> surveilles = new HashSet<>();
    @OneToMany(mappedBy = "salleComposition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inscription> inscriptions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("salleCompositions")
    private CentreCompositioJury centreCompositioJury;

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

    public SalleComposition libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCapaciteSalle() {
        return capaciteSalle;
    }

    public SalleComposition capaciteSalle(String capaciteSalle) {
        this.capaciteSalle = capaciteSalle;
        return this;
    }

    public void setCapaciteSalle(String capaciteSalle) {
        this.capaciteSalle = capaciteSalle;
    }

    public Set<Surveille> getSurveilles() {
        return surveilles;
    }

    public SalleComposition surveilles(Set<Surveille> surveilles) {
        this.surveilles = surveilles;
        return this;
    }

    public SalleComposition addSurveille(Surveille surveille) {
        this.surveilles.add(surveille);
        surveille.setSalleComposition(this);
        return this;
    }

    public SalleComposition removeSurveille(Surveille surveille) {
        this.surveilles.remove(surveille);
        surveille.setSalleComposition(null);
        return this;
    }

    public void setSurveilles(Set<Surveille> surveilles) {
        this.surveilles = surveilles;
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public SalleComposition inscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
        return this;
    }

    public SalleComposition addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setSalleComposition(this);
        return this;
    }

    public SalleComposition removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setSalleComposition(null);
        return this;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public CentreCompositioJury getCentreCompositioJury() {
        return centreCompositioJury;
    }

    public SalleComposition centreCompositioJury(CentreCompositioJury centreCompositioJury) {
        this.centreCompositioJury = centreCompositioJury;
        return this;
    }

    public void setCentreCompositioJury(CentreCompositioJury centreCompositioJury) {
        this.centreCompositioJury = centreCompositioJury;
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
        SalleComposition salleComposition = (SalleComposition) o;
        if (salleComposition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salleComposition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalleComposition{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", capaciteSalle='" + getCapaciteSalle() + "'" +
            "}";
    }
}
