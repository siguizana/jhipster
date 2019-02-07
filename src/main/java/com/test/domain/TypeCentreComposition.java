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
 * A TypeCentreComposition.
 */
@Entity
@Table(name = "type_centre_composition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeCentreComposition implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_type_centre_composition", nullable = false)
    private String libelleTypeCentreComposition;

    @OneToMany(mappedBy = "typeCentreComposition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CentreComposition> centreCompositions = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleTypeCentreComposition() {
        return libelleTypeCentreComposition;
    }

    public TypeCentreComposition libelleTypeCentreComposition(String libelleTypeCentreComposition) {
        this.libelleTypeCentreComposition = libelleTypeCentreComposition;
        return this;
    }

    public void setLibelleTypeCentreComposition(String libelleTypeCentreComposition) {
        this.libelleTypeCentreComposition = libelleTypeCentreComposition;
    }

    public Set<CentreComposition> getCentreCompositions() {
        return centreCompositions;
    }

    public TypeCentreComposition centreCompositions(Set<CentreComposition> centreCompositions) {
        this.centreCompositions = centreCompositions;
        return this;
    }

    public TypeCentreComposition addCentreComposition(CentreComposition centreComposition) {
        this.centreCompositions.add(centreComposition);
        centreComposition.setTypeCentreComposition(this);
        return this;
    }

    public TypeCentreComposition removeCentreComposition(CentreComposition centreComposition) {
        this.centreCompositions.remove(centreComposition);
        centreComposition.setTypeCentreComposition(null);
        return this;
    }

    public void setCentreCompositions(Set<CentreComposition> centreCompositions) {
        this.centreCompositions = centreCompositions;
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
        TypeCentreComposition typeCentreComposition = (TypeCentreComposition) o;
        if (typeCentreComposition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeCentreComposition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeCentreComposition{" +
            "id=" + getId() +
            ", libelleTypeCentreComposition='" + getLibelleTypeCentreComposition() + "'" +
            "}";
    }
}
