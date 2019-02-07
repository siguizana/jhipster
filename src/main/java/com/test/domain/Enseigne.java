package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Enseigne.
 */
@Entity
@Table(name = "enseigne")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Enseigne implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "classe_enseigne", nullable = false)
    private String classeEnseigne;

    @ManyToOne
    @JsonIgnoreProperties("enseignes")
    private Etablissement etablissement;

    @ManyToOne
    @JsonIgnoreProperties("enseignes")
    private Enseignant enseignant;

    @ManyToOne
    @JsonIgnoreProperties("enseignes")
    private Session session;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClasseEnseigne() {
        return classeEnseigne;
    }

    public Enseigne classeEnseigne(String classeEnseigne) {
        this.classeEnseigne = classeEnseigne;
        return this;
    }

    public void setClasseEnseigne(String classeEnseigne) {
        this.classeEnseigne = classeEnseigne;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public Enseigne etablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
        return this;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public Enseigne enseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        return this;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Session getSession() {
        return session;
    }

    public Enseigne session(Session session) {
        this.session = session;
        return this;
    }

    public void setSession(Session session) {
        this.session = session;
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
        Enseigne enseigne = (Enseigne) o;
        if (enseigne.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enseigne.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Enseigne{" +
            "id=" + getId() +
            ", classeEnseigne='" + getClasseEnseigne() + "'" +
            "}";
    }
}
