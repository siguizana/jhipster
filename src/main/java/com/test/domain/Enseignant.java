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
 * A Enseignant.
 */
@Entity
@Table(name = "enseignant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Enseignant implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "matricule")
    private String matricule;

    @NotNull
    @Column(name = "nom_enseignant", nullable = false)
    private String nomEnseignant;

    @NotNull
    @Column(name = "prenom_enseignant", nullable = false)
    private String prenomEnseignant;

    @Column(name = "contact_enseignant")
    private String contactEnseignant;

    @Column(name = "grade_enseignant")
    private String gradeEnseignant;

    @Column(name = "echelon_enseignant")
    private String echelonEnseignant;

    @OneToMany(mappedBy = "enseignant")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Enseigne> enseignes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public Enseignant matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomEnseignant() {
        return nomEnseignant;
    }

    public Enseignant nomEnseignant(String nomEnseignant) {
        this.nomEnseignant = nomEnseignant;
        return this;
    }

    public void setNomEnseignant(String nomEnseignant) {
        this.nomEnseignant = nomEnseignant;
    }

    public String getPrenomEnseignant() {
        return prenomEnseignant;
    }

    public Enseignant prenomEnseignant(String prenomEnseignant) {
        this.prenomEnseignant = prenomEnseignant;
        return this;
    }

    public void setPrenomEnseignant(String prenomEnseignant) {
        this.prenomEnseignant = prenomEnseignant;
    }

    public String getContactEnseignant() {
        return contactEnseignant;
    }

    public Enseignant contactEnseignant(String contactEnseignant) {
        this.contactEnseignant = contactEnseignant;
        return this;
    }

    public void setContactEnseignant(String contactEnseignant) {
        this.contactEnseignant = contactEnseignant;
    }

    public String getGradeEnseignant() {
        return gradeEnseignant;
    }

    public Enseignant gradeEnseignant(String gradeEnseignant) {
        this.gradeEnseignant = gradeEnseignant;
        return this;
    }

    public void setGradeEnseignant(String gradeEnseignant) {
        this.gradeEnseignant = gradeEnseignant;
    }

    public String getEchelonEnseignant() {
        return echelonEnseignant;
    }

    public Enseignant echelonEnseignant(String echelonEnseignant) {
        this.echelonEnseignant = echelonEnseignant;
        return this;
    }

    public void setEchelonEnseignant(String echelonEnseignant) {
        this.echelonEnseignant = echelonEnseignant;
    }

    public Set<Enseigne> getEnseignes() {
        return enseignes;
    }

    public Enseignant enseignes(Set<Enseigne> enseignes) {
        this.enseignes = enseignes;
        return this;
    }

    public Enseignant addEnseigne(Enseigne enseigne) {
        this.enseignes.add(enseigne);
        enseigne.setEnseignant(this);
        return this;
    }

    public Enseignant removeEnseigne(Enseigne enseigne) {
        this.enseignes.remove(enseigne);
        enseigne.setEnseignant(null);
        return this;
    }

    public void setEnseignes(Set<Enseigne> enseignes) {
        this.enseignes = enseignes;
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
        Enseignant enseignant = (Enseignant) o;
        if (enseignant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enseignant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Enseignant{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", nomEnseignant='" + getNomEnseignant() + "'" +
            ", prenomEnseignant='" + getPrenomEnseignant() + "'" +
            ", contactEnseignant='" + getContactEnseignant() + "'" +
            ", gradeEnseignant='" + getGradeEnseignant() + "'" +
            ", echelonEnseignant='" + getEchelonEnseignant() + "'" +
            "}";
    }
}
