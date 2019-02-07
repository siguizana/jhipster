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
 * A EtapeExamen.
 */
@Entity
@Table(name = "etape_examen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EtapeExamen implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_etape_examen", nullable = false)
    private String libelleEtapeExamen;

    @OneToMany(mappedBy = "etapeExamen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompositionCopie> compositionCopies = new HashSet<>();
    @OneToMany(mappedBy = "etapeExamen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Absence> absences = new HashSet<>();
    @OneToMany(mappedBy = "etapeExamen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Resultat> resultats = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("etapeExamen")
    private TypeExamen typeExamen;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleEtapeExamen() {
        return libelleEtapeExamen;
    }

    public EtapeExamen libelleEtapeExamen(String libelleEtapeExamen) {
        this.libelleEtapeExamen = libelleEtapeExamen;
        return this;
    }

    public void setLibelleEtapeExamen(String libelleEtapeExamen) {
        this.libelleEtapeExamen = libelleEtapeExamen;
    }

    public Set<CompositionCopie> getCompositionCopies() {
        return compositionCopies;
    }

    public EtapeExamen compositionCopies(Set<CompositionCopie> compositionCopies) {
        this.compositionCopies = compositionCopies;
        return this;
    }

    public EtapeExamen addCompositionCopie(CompositionCopie compositionCopie) {
        this.compositionCopies.add(compositionCopie);
        compositionCopie.setEtapeExamen(this);
        return this;
    }

    public EtapeExamen removeCompositionCopie(CompositionCopie compositionCopie) {
        this.compositionCopies.remove(compositionCopie);
        compositionCopie.setEtapeExamen(null);
        return this;
    }

    public void setCompositionCopies(Set<CompositionCopie> compositionCopies) {
        this.compositionCopies = compositionCopies;
    }

    public Set<Absence> getAbsences() {
        return absences;
    }

    public EtapeExamen absences(Set<Absence> absences) {
        this.absences = absences;
        return this;
    }

    public EtapeExamen addAbsence(Absence absence) {
        this.absences.add(absence);
        absence.setEtapeExamen(this);
        return this;
    }

    public EtapeExamen removeAbsence(Absence absence) {
        this.absences.remove(absence);
        absence.setEtapeExamen(null);
        return this;
    }

    public void setAbsences(Set<Absence> absences) {
        this.absences = absences;
    }

    public Set<Resultat> getResultats() {
        return resultats;
    }

    public EtapeExamen resultats(Set<Resultat> resultats) {
        this.resultats = resultats;
        return this;
    }

    public EtapeExamen addResultat(Resultat resultat) {
        this.resultats.add(resultat);
        resultat.setEtapeExamen(this);
        return this;
    }

    public EtapeExamen removeResultat(Resultat resultat) {
        this.resultats.remove(resultat);
        resultat.setEtapeExamen(null);
        return this;
    }

    public void setResultats(Set<Resultat> resultats) {
        this.resultats = resultats;
    }

    public TypeExamen getTypeExamen() {
        return typeExamen;
    }

    public EtapeExamen typeExamen(TypeExamen typeExamen) {
        this.typeExamen = typeExamen;
        return this;
    }

    public void setTypeExamen(TypeExamen typeExamen) {
        this.typeExamen = typeExamen;
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
        EtapeExamen etapeExamen = (EtapeExamen) o;
        if (etapeExamen.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etapeExamen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtapeExamen{" +
            "id=" + getId() +
            ", libelleEtapeExamen='" + getLibelleEtapeExamen() + "'" +
            "}";
    }
}
