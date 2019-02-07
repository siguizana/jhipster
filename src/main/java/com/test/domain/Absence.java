package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Absence.
 */
@Entity
@Table(name = "absence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Absence implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "motif_absence")
    private String motifAbsence;

    @Column(name = "date_absence")
    private LocalDate dateAbsence;

    @ManyToOne
    @JsonIgnoreProperties("absences")
    private Inscription inscription;

    @ManyToOne
    @JsonIgnoreProperties("absences")
    private EpreuveSpecialiteOption epreuveSpecialiteOption;

    @ManyToOne
    @JsonIgnoreProperties("absences")
    private EtapeExamen etapeExamen;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotifAbsence() {
        return motifAbsence;
    }

    public Absence motifAbsence(String motifAbsence) {
        this.motifAbsence = motifAbsence;
        return this;
    }

    public void setMotifAbsence(String motifAbsence) {
        this.motifAbsence = motifAbsence;
    }

    public LocalDate getDateAbsence() {
        return dateAbsence;
    }

    public Absence dateAbsence(LocalDate dateAbsence) {
        this.dateAbsence = dateAbsence;
        return this;
    }

    public void setDateAbsence(LocalDate dateAbsence) {
        this.dateAbsence = dateAbsence;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public Absence inscription(Inscription inscription) {
        this.inscription = inscription;
        return this;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public EpreuveSpecialiteOption getEpreuveSpecialiteOption() {
        return epreuveSpecialiteOption;
    }

    public Absence epreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOption = epreuveSpecialiteOption;
        return this;
    }

    public void setEpreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOption = epreuveSpecialiteOption;
    }

    public EtapeExamen getEtapeExamen() {
        return etapeExamen;
    }

    public Absence etapeExamen(EtapeExamen etapeExamen) {
        this.etapeExamen = etapeExamen;
        return this;
    }

    public void setEtapeExamen(EtapeExamen etapeExamen) {
        this.etapeExamen = etapeExamen;
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
        Absence absence = (Absence) o;
        if (absence.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), absence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Absence{" +
            "id=" + getId() +
            ", motifAbsence='" + getMotifAbsence() + "'" +
            ", dateAbsence='" + getDateAbsence() + "'" +
            "}";
    }
}
