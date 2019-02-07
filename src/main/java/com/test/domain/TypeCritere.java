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
 * A TypeCritere.
 */
@Entity
@Table(name = "type_critere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeCritere implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "typeCritere")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CritereExamen> critereExamen = new HashSet<>();
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

    public TypeCritere libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<CritereExamen> getCritereExamen() {
        return critereExamen;
    }

    public TypeCritere critereExamen(Set<CritereExamen> critereExamen) {
        this.critereExamen = critereExamen;
        return this;
    }

    public TypeCritere addCritereExamen(CritereExamen critereExamen) {
        this.critereExamen.add(critereExamen);
        critereExamen.setTypeCritere(this);
        return this;
    }

    public TypeCritere removeCritereExamen(CritereExamen critereExamen) {
        this.critereExamen.remove(critereExamen);
        critereExamen.setTypeCritere(null);
        return this;
    }

    public void setCritereExamen(Set<CritereExamen> critereExamen) {
        this.critereExamen = critereExamen;
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
        TypeCritere typeCritere = (TypeCritere) o;
        if (typeCritere.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeCritere.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeCritere{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
