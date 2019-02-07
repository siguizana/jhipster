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
 * A TypeEtablissement.
 */
@Entity
@Table(name = "type_etablissement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeEtablissement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_type_etablissement", nullable = false)
    private String libelleTypeEtablissement;

    @OneToMany(mappedBy = "typeEtablissement")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Etablissement> etablissements = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleTypeEtablissement() {
        return libelleTypeEtablissement;
    }

    public TypeEtablissement libelleTypeEtablissement(String libelleTypeEtablissement) {
        this.libelleTypeEtablissement = libelleTypeEtablissement;
        return this;
    }

    public void setLibelleTypeEtablissement(String libelleTypeEtablissement) {
        this.libelleTypeEtablissement = libelleTypeEtablissement;
    }

    public Set<Etablissement> getEtablissements() {
        return etablissements;
    }

    public TypeEtablissement etablissements(Set<Etablissement> etablissements) {
        this.etablissements = etablissements;
        return this;
    }

    public TypeEtablissement addEtablissement(Etablissement etablissement) {
        this.etablissements.add(etablissement);
        etablissement.setTypeEtablissement(this);
        return this;
    }

    public TypeEtablissement removeEtablissement(Etablissement etablissement) {
        this.etablissements.remove(etablissement);
        etablissement.setTypeEtablissement(null);
        return this;
    }

    public void setEtablissements(Set<Etablissement> etablissements) {
        this.etablissements = etablissements;
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
        TypeEtablissement typeEtablissement = (TypeEtablissement) o;
        if (typeEtablissement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeEtablissement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeEtablissement{" +
            "id=" + getId() +
            ", libelleTypeEtablissement='" + getLibelleTypeEtablissement() + "'" +
            "}";
    }
}
