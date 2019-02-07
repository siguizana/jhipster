package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CentreCompositioJury.
 */
@Entity
@Table(name = "centre_compositio_jury")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CentreCompositioJury implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "centreCompositioJury")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SalleComposition> salleCompositions = new HashSet<>();
    @OneToMany(mappedBy = "centreCompositioJury")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inscription> inscriptions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("centreCompositioJuries")
    private CentreComposition centreComposition;

    @ManyToOne
    @JsonIgnoreProperties("centreCompositioJuries")
    private Jury jury;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<SalleComposition> getSalleCompositions() {
        return salleCompositions;
    }

    public CentreCompositioJury salleCompositions(Set<SalleComposition> salleCompositions) {
        this.salleCompositions = salleCompositions;
        return this;
    }

    public CentreCompositioJury addSalleComposition(SalleComposition salleComposition) {
        this.salleCompositions.add(salleComposition);
        salleComposition.setCentreCompositioJury(this);
        return this;
    }

    public CentreCompositioJury removeSalleComposition(SalleComposition salleComposition) {
        this.salleCompositions.remove(salleComposition);
        salleComposition.setCentreCompositioJury(null);
        return this;
    }

    public void setSalleCompositions(Set<SalleComposition> salleCompositions) {
        this.salleCompositions = salleCompositions;
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public CentreCompositioJury inscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
        return this;
    }

    public CentreCompositioJury addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setCentreCompositioJury(this);
        return this;
    }

    public CentreCompositioJury removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setCentreCompositioJury(null);
        return this;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public CentreComposition getCentreComposition() {
        return centreComposition;
    }

    public CentreCompositioJury centreComposition(CentreComposition centreComposition) {
        this.centreComposition = centreComposition;
        return this;
    }

    public void setCentreComposition(CentreComposition centreComposition) {
        this.centreComposition = centreComposition;
    }

    public Jury getJury() {
        return jury;
    }

    public CentreCompositioJury jury(Jury jury) {
        this.jury = jury;
        return this;
    }

    public void setJury(Jury jury) {
        this.jury = jury;
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
        CentreCompositioJury centreCompositioJury = (CentreCompositioJury) o;
        if (centreCompositioJury.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), centreCompositioJury.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CentreCompositioJury{" +
            "id=" + getId() +
            "}";
    }
}
