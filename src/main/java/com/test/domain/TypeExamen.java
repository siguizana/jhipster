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
 * A TypeExamen.
 */
@Entity
@Table(name = "type_examen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeExamen implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "typeExamen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EtapeExamen> etapeExamen = new HashSet<>();
    @OneToMany(mappedBy = "typeExamen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConcoursRattache> concoursRattaches = new HashSet<>();
    @OneToMany(mappedBy = "typeExamen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CritereExamen> critereExamen = new HashSet<>();
    @OneToMany(mappedBy = "typeExamen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SpecialiteOption> specialiteOptions = new HashSet<>();
    @OneToMany(mappedBy = "typeExamen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inscription> inscriptions = new HashSet<>();
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

    public TypeExamen libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<EtapeExamen> getEtapeExamen() {
        return etapeExamen;
    }

    public TypeExamen etapeExamen(Set<EtapeExamen> etapeExamen) {
        this.etapeExamen = etapeExamen;
        return this;
    }

    public TypeExamen addEtapeExamen(EtapeExamen etapeExamen) {
        this.etapeExamen.add(etapeExamen);
        etapeExamen.setTypeExamen(this);
        return this;
    }

    public TypeExamen removeEtapeExamen(EtapeExamen etapeExamen) {
        this.etapeExamen.remove(etapeExamen);
        etapeExamen.setTypeExamen(null);
        return this;
    }

    public void setEtapeExamen(Set<EtapeExamen> etapeExamen) {
        this.etapeExamen = etapeExamen;
    }

    public Set<ConcoursRattache> getConcoursRattaches() {
        return concoursRattaches;
    }

    public TypeExamen concoursRattaches(Set<ConcoursRattache> concoursRattaches) {
        this.concoursRattaches = concoursRattaches;
        return this;
    }

    public TypeExamen addConcoursRattache(ConcoursRattache concoursRattache) {
        this.concoursRattaches.add(concoursRattache);
        concoursRattache.setTypeExamen(this);
        return this;
    }

    public TypeExamen removeConcoursRattache(ConcoursRattache concoursRattache) {
        this.concoursRattaches.remove(concoursRattache);
        concoursRattache.setTypeExamen(null);
        return this;
    }

    public void setConcoursRattaches(Set<ConcoursRattache> concoursRattaches) {
        this.concoursRattaches = concoursRattaches;
    }

    public Set<CritereExamen> getCritereExamen() {
        return critereExamen;
    }

    public TypeExamen critereExamen(Set<CritereExamen> critereExamen) {
        this.critereExamen = critereExamen;
        return this;
    }

    public TypeExamen addCritereExamen(CritereExamen critereExamen) {
        this.critereExamen.add(critereExamen);
        critereExamen.setTypeExamen(this);
        return this;
    }

    public TypeExamen removeCritereExamen(CritereExamen critereExamen) {
        this.critereExamen.remove(critereExamen);
        critereExamen.setTypeExamen(null);
        return this;
    }

    public void setCritereExamen(Set<CritereExamen> critereExamen) {
        this.critereExamen = critereExamen;
    }

    public Set<SpecialiteOption> getSpecialiteOptions() {
        return specialiteOptions;
    }

    public TypeExamen specialiteOptions(Set<SpecialiteOption> specialiteOptions) {
        this.specialiteOptions = specialiteOptions;
        return this;
    }

    public TypeExamen addSpecialiteOption(SpecialiteOption specialiteOption) {
        this.specialiteOptions.add(specialiteOption);
        specialiteOption.setTypeExamen(this);
        return this;
    }

    public TypeExamen removeSpecialiteOption(SpecialiteOption specialiteOption) {
        this.specialiteOptions.remove(specialiteOption);
        specialiteOption.setTypeExamen(null);
        return this;
    }

    public void setSpecialiteOptions(Set<SpecialiteOption> specialiteOptions) {
        this.specialiteOptions = specialiteOptions;
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public TypeExamen inscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
        return this;
    }

    public TypeExamen addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setTypeExamen(this);
        return this;
    }

    public TypeExamen removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setTypeExamen(null);
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
        TypeExamen typeExamen = (TypeExamen) o;
        if (typeExamen.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeExamen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeExamen{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
