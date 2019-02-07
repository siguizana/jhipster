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
 * A Jury.
 */
@Entity
@Table(name = "jury")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Jury implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_jury", nullable = false)
    private String libelleJury;

    @OneToMany(mappedBy = "jury")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CentreCompositioJury> centreCompositioJuries = new HashSet<>();
    @OneToMany(mappedBy = "jury")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MembreJuryJury> membreJuryJuries = new HashSet<>();
    @OneToMany(mappedBy = "jury")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inscription> inscriptions = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleJury() {
        return libelleJury;
    }

    public Jury libelleJury(String libelleJury) {
        this.libelleJury = libelleJury;
        return this;
    }

    public void setLibelleJury(String libelleJury) {
        this.libelleJury = libelleJury;
    }

    public Set<CentreCompositioJury> getCentreCompositioJuries() {
        return centreCompositioJuries;
    }

    public Jury centreCompositioJuries(Set<CentreCompositioJury> centreCompositioJuries) {
        this.centreCompositioJuries = centreCompositioJuries;
        return this;
    }

    public Jury addCentreCompositioJury(CentreCompositioJury centreCompositioJury) {
        this.centreCompositioJuries.add(centreCompositioJury);
        centreCompositioJury.setJury(this);
        return this;
    }

    public Jury removeCentreCompositioJury(CentreCompositioJury centreCompositioJury) {
        this.centreCompositioJuries.remove(centreCompositioJury);
        centreCompositioJury.setJury(null);
        return this;
    }

    public void setCentreCompositioJuries(Set<CentreCompositioJury> centreCompositioJuries) {
        this.centreCompositioJuries = centreCompositioJuries;
    }

    public Set<MembreJuryJury> getMembreJuryJuries() {
        return membreJuryJuries;
    }

    public Jury membreJuryJuries(Set<MembreJuryJury> membreJuryJuries) {
        this.membreJuryJuries = membreJuryJuries;
        return this;
    }

    public Jury addMembreJuryJury(MembreJuryJury membreJuryJury) {
        this.membreJuryJuries.add(membreJuryJury);
        membreJuryJury.setJury(this);
        return this;
    }

    public Jury removeMembreJuryJury(MembreJuryJury membreJuryJury) {
        this.membreJuryJuries.remove(membreJuryJury);
        membreJuryJury.setJury(null);
        return this;
    }

    public void setMembreJuryJuries(Set<MembreJuryJury> membreJuryJuries) {
        this.membreJuryJuries = membreJuryJuries;
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public Jury inscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
        return this;
    }

    public Jury addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setJury(this);
        return this;
    }

    public Jury removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setJury(null);
        return this;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
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
        Jury jury = (Jury) o;
        if (jury.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jury.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Jury{" +
            "id=" + getId() +
            ", libelleJury='" + getLibelleJury() + "'" +
            "}";
    }
}
