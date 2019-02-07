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
 * A EpreuveSpecialiteOption.
 */
@Entity
@Table(name = "epreuve_specialite_option")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EpreuveSpecialiteOption implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "epreuve_rachat")
    private Boolean epreuveRachat;

    @Column(name = "epreuve_admissibilite")
    private Boolean epreuveAdmissibilite;

    @Column(name = "epreuve_facultative")
    private Boolean epreuveFacultative;

    @Column(name = "note_eliminatoire")
    private Float noteEliminatoire;

    @NotNull
    @Column(name = "coefficient", nullable = false)
    private Integer coefficient;

    @OneToMany(mappedBy = "epreuveSpecialiteOption")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompositionCopie> compositionCopies = new HashSet<>();
    @OneToMany(mappedBy = "epreuveSpecialiteOption")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Absence> absences = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("epreuveSpecialiteOptions")
    private Inscription inscription;

    @ManyToOne
    @JsonIgnoreProperties("epreuveSpecialiteOptions")
    private Epreuve epreuve;

    @ManyToOne
    @JsonIgnoreProperties("epreuveSpecialiteOptions")
    private SpecialiteOption specialiteOption;

    @ManyToOne
    @JsonIgnoreProperties("epreuveSpecialiteOptions")
    private GroupeEpreuve groupeEpreuve;

    @ManyToMany(mappedBy = "epreuveSpecialiteOptions")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Dispense> dispenses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEpreuveRachat() {
        return epreuveRachat;
    }

    public EpreuveSpecialiteOption epreuveRachat(Boolean epreuveRachat) {
        this.epreuveRachat = epreuveRachat;
        return this;
    }

    public void setEpreuveRachat(Boolean epreuveRachat) {
        this.epreuveRachat = epreuveRachat;
    }

    public Boolean isEpreuveAdmissibilite() {
        return epreuveAdmissibilite;
    }

    public EpreuveSpecialiteOption epreuveAdmissibilite(Boolean epreuveAdmissibilite) {
        this.epreuveAdmissibilite = epreuveAdmissibilite;
        return this;
    }

    public void setEpreuveAdmissibilite(Boolean epreuveAdmissibilite) {
        this.epreuveAdmissibilite = epreuveAdmissibilite;
    }

    public Boolean isEpreuveFacultative() {
        return epreuveFacultative;
    }

    public EpreuveSpecialiteOption epreuveFacultative(Boolean epreuveFacultative) {
        this.epreuveFacultative = epreuveFacultative;
        return this;
    }

    public void setEpreuveFacultative(Boolean epreuveFacultative) {
        this.epreuveFacultative = epreuveFacultative;
    }

    public Float getNoteEliminatoire() {
        return noteEliminatoire;
    }

    public EpreuveSpecialiteOption noteEliminatoire(Float noteEliminatoire) {
        this.noteEliminatoire = noteEliminatoire;
        return this;
    }

    public void setNoteEliminatoire(Float noteEliminatoire) {
        this.noteEliminatoire = noteEliminatoire;
    }

    public Integer getCoefficient() {
        return coefficient;
    }

    public EpreuveSpecialiteOption coefficient(Integer coefficient) {
        this.coefficient = coefficient;
        return this;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    public Set<CompositionCopie> getCompositionCopies() {
        return compositionCopies;
    }

    public EpreuveSpecialiteOption compositionCopies(Set<CompositionCopie> compositionCopies) {
        this.compositionCopies = compositionCopies;
        return this;
    }

    public EpreuveSpecialiteOption addCompositionCopie(CompositionCopie compositionCopie) {
        this.compositionCopies.add(compositionCopie);
        compositionCopie.setEpreuveSpecialiteOption(this);
        return this;
    }

    public EpreuveSpecialiteOption removeCompositionCopie(CompositionCopie compositionCopie) {
        this.compositionCopies.remove(compositionCopie);
        compositionCopie.setEpreuveSpecialiteOption(null);
        return this;
    }

    public void setCompositionCopies(Set<CompositionCopie> compositionCopies) {
        this.compositionCopies = compositionCopies;
    }

    public Set<Absence> getAbsences() {
        return absences;
    }

    public EpreuveSpecialiteOption absences(Set<Absence> absences) {
        this.absences = absences;
        return this;
    }

    public EpreuveSpecialiteOption addAbsence(Absence absence) {
        this.absences.add(absence);
        absence.setEpreuveSpecialiteOption(this);
        return this;
    }

    public EpreuveSpecialiteOption removeAbsence(Absence absence) {
        this.absences.remove(absence);
        absence.setEpreuveSpecialiteOption(null);
        return this;
    }

    public void setAbsences(Set<Absence> absences) {
        this.absences = absences;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public EpreuveSpecialiteOption inscription(Inscription inscription) {
        this.inscription = inscription;
        return this;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Epreuve getEpreuve() {
        return epreuve;
    }

    public EpreuveSpecialiteOption epreuve(Epreuve epreuve) {
        this.epreuve = epreuve;
        return this;
    }

    public void setEpreuve(Epreuve epreuve) {
        this.epreuve = epreuve;
    }

    public SpecialiteOption getSpecialiteOption() {
        return specialiteOption;
    }

    public EpreuveSpecialiteOption specialiteOption(SpecialiteOption specialiteOption) {
        this.specialiteOption = specialiteOption;
        return this;
    }

    public void setSpecialiteOption(SpecialiteOption specialiteOption) {
        this.specialiteOption = specialiteOption;
    }

    public GroupeEpreuve getGroupeEpreuve() {
        return groupeEpreuve;
    }

    public EpreuveSpecialiteOption groupeEpreuve(GroupeEpreuve groupeEpreuve) {
        this.groupeEpreuve = groupeEpreuve;
        return this;
    }

    public void setGroupeEpreuve(GroupeEpreuve groupeEpreuve) {
        this.groupeEpreuve = groupeEpreuve;
    }

    public Set<Dispense> getDispenses() {
        return dispenses;
    }

    public EpreuveSpecialiteOption dispenses(Set<Dispense> dispenses) {
        this.dispenses = dispenses;
        return this;
    }

    public EpreuveSpecialiteOption addDispense(Dispense dispense) {
        this.dispenses.add(dispense);
        dispense.getEpreuveSpecialiteOptions().add(this);
        return this;
    }

    public EpreuveSpecialiteOption removeDispense(Dispense dispense) {
        this.dispenses.remove(dispense);
        dispense.getEpreuveSpecialiteOptions().remove(this);
        return this;
    }

    public void setDispenses(Set<Dispense> dispenses) {
        this.dispenses = dispenses;
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
        EpreuveSpecialiteOption epreuveSpecialiteOption = (EpreuveSpecialiteOption) o;
        if (epreuveSpecialiteOption.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), epreuveSpecialiteOption.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EpreuveSpecialiteOption{" +
            "id=" + getId() +
            ", epreuveRachat='" + isEpreuveRachat() + "'" +
            ", epreuveAdmissibilite='" + isEpreuveAdmissibilite() + "'" +
            ", epreuveFacultative='" + isEpreuveFacultative() + "'" +
            ", noteEliminatoire=" + getNoteEliminatoire() +
            ", coefficient=" + getCoefficient() +
            "}";
    }
}
