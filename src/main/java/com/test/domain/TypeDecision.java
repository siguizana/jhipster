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
 * A TypeDecision.
 */
@Entity
@Table(name = "type_decision")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeDecision implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_type_decision", nullable = false)
    private String libelleTypeDecision;

    @OneToMany(mappedBy = "typeDecision")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Resultat> resultats = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleTypeDecision() {
        return libelleTypeDecision;
    }

    public TypeDecision libelleTypeDecision(String libelleTypeDecision) {
        this.libelleTypeDecision = libelleTypeDecision;
        return this;
    }

    public void setLibelleTypeDecision(String libelleTypeDecision) {
        this.libelleTypeDecision = libelleTypeDecision;
    }

    public Set<Resultat> getResultats() {
        return resultats;
    }

    public TypeDecision resultats(Set<Resultat> resultats) {
        this.resultats = resultats;
        return this;
    }

    public TypeDecision addResultat(Resultat resultat) {
        this.resultats.add(resultat);
        resultat.setTypeDecision(this);
        return this;
    }

    public TypeDecision removeResultat(Resultat resultat) {
        this.resultats.remove(resultat);
        resultat.setTypeDecision(null);
        return this;
    }

    public void setResultats(Set<Resultat> resultats) {
        this.resultats = resultats;
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
        TypeDecision typeDecision = (TypeDecision) o;
        if (typeDecision.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeDecision.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeDecision{" +
            "id=" + getId() +
            ", libelleTypeDecision='" + getLibelleTypeDecision() + "'" +
            "}";
    }
}
