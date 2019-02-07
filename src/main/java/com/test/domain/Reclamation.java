package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Reclamation.
 */
@Entity
@Table(name = "reclamation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reclamation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "motif_reclamation", nullable = false)
    private String motifReclamation;

    @Column(name = "date_reclamation")
    private LocalDate dateReclamation;

    @Column(name = "note_reclamation")
    private Float noteReclamation;

    @Column(name = "statut_reclamation")
    private Boolean statutReclamation;

    @ManyToOne
    @JsonIgnoreProperties("reclamations")
    private CompositionCopie compositionCopie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotifReclamation() {
        return motifReclamation;
    }

    public Reclamation motifReclamation(String motifReclamation) {
        this.motifReclamation = motifReclamation;
        return this;
    }

    public void setMotifReclamation(String motifReclamation) {
        this.motifReclamation = motifReclamation;
    }

    public LocalDate getDateReclamation() {
        return dateReclamation;
    }

    public Reclamation dateReclamation(LocalDate dateReclamation) {
        this.dateReclamation = dateReclamation;
        return this;
    }

    public void setDateReclamation(LocalDate dateReclamation) {
        this.dateReclamation = dateReclamation;
    }

    public Float getNoteReclamation() {
        return noteReclamation;
    }

    public Reclamation noteReclamation(Float noteReclamation) {
        this.noteReclamation = noteReclamation;
        return this;
    }

    public void setNoteReclamation(Float noteReclamation) {
        this.noteReclamation = noteReclamation;
    }

    public Boolean isStatutReclamation() {
        return statutReclamation;
    }

    public Reclamation statutReclamation(Boolean statutReclamation) {
        this.statutReclamation = statutReclamation;
        return this;
    }

    public void setStatutReclamation(Boolean statutReclamation) {
        this.statutReclamation = statutReclamation;
    }

    public CompositionCopie getCompositionCopie() {
        return compositionCopie;
    }

    public Reclamation compositionCopie(CompositionCopie compositionCopie) {
        this.compositionCopie = compositionCopie;
        return this;
    }

    public void setCompositionCopie(CompositionCopie compositionCopie) {
        this.compositionCopie = compositionCopie;
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
        Reclamation reclamation = (Reclamation) o;
        if (reclamation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reclamation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reclamation{" +
            "id=" + getId() +
            ", motifReclamation='" + getMotifReclamation() + "'" +
            ", dateReclamation='" + getDateReclamation() + "'" +
            ", noteReclamation=" + getNoteReclamation() +
            ", statutReclamation='" + isStatutReclamation() + "'" +
            "}";
    }
}
