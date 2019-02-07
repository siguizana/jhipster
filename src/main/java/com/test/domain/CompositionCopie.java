package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CompositionCopie.
 */
@Entity
@Table(name = "composition_copie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompositionCopie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "note")
    private Float note;

    @Column(name = "date_composition")
    private LocalDate dateComposition;

    @NotNull
    @Column(name = "numero_anonymat", nullable = false)
    private String numeroAnonymat;

    @OneToMany(mappedBy = "compositionCopie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reclamation> reclamations = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("compositionCopies")
    private Inscription inscription;

    @ManyToOne
    @JsonIgnoreProperties("compositionCopies")
    private EpreuveSpecialiteOption epreuveSpecialiteOption;

    @ManyToOne
    @JsonIgnoreProperties("compositionCopies")
    private EtapeExamen etapeExamen;

    @ManyToMany(mappedBy = "compositionCopies")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<MembreJury> membreJuries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getNote() {
        return note;
    }

    public CompositionCopie note(Float note) {
        this.note = note;
        return this;
    }

    public void setNote(Float note) {
        this.note = note;
    }

    public LocalDate getDateComposition() {
        return dateComposition;
    }

    public CompositionCopie dateComposition(LocalDate dateComposition) {
        this.dateComposition = dateComposition;
        return this;
    }

    public void setDateComposition(LocalDate dateComposition) {
        this.dateComposition = dateComposition;
    }

    public String getNumeroAnonymat() {
        return numeroAnonymat;
    }

    public CompositionCopie numeroAnonymat(String numeroAnonymat) {
        this.numeroAnonymat = numeroAnonymat;
        return this;
    }

    public void setNumeroAnonymat(String numeroAnonymat) {
        this.numeroAnonymat = numeroAnonymat;
    }

    public Set<Reclamation> getReclamations() {
        return reclamations;
    }

    public CompositionCopie reclamations(Set<Reclamation> reclamations) {
        this.reclamations = reclamations;
        return this;
    }

    public CompositionCopie addReclamation(Reclamation reclamation) {
        this.reclamations.add(reclamation);
        reclamation.setCompositionCopie(this);
        return this;
    }

    public CompositionCopie removeReclamation(Reclamation reclamation) {
        this.reclamations.remove(reclamation);
        reclamation.setCompositionCopie(null);
        return this;
    }

    public void setReclamations(Set<Reclamation> reclamations) {
        this.reclamations = reclamations;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public CompositionCopie inscription(Inscription inscription) {
        this.inscription = inscription;
        return this;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public EpreuveSpecialiteOption getEpreuveSpecialiteOption() {
        return epreuveSpecialiteOption;
    }

    public CompositionCopie epreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOption = epreuveSpecialiteOption;
        return this;
    }

    public void setEpreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOption = epreuveSpecialiteOption;
    }

    public EtapeExamen getEtapeExamen() {
        return etapeExamen;
    }

    public CompositionCopie etapeExamen(EtapeExamen etapeExamen) {
        this.etapeExamen = etapeExamen;
        return this;
    }

    public void setEtapeExamen(EtapeExamen etapeExamen) {
        this.etapeExamen = etapeExamen;
    }

    public Set<MembreJury> getMembreJuries() {
        return membreJuries;
    }

    public CompositionCopie membreJuries(Set<MembreJury> membreJuries) {
        this.membreJuries = membreJuries;
        return this;
    }

    public CompositionCopie addMembreJury(MembreJury membreJury) {
        this.membreJuries.add(membreJury);
        membreJury.getCompositionCopies().add(this);
        return this;
    }

    public CompositionCopie removeMembreJury(MembreJury membreJury) {
        this.membreJuries.remove(membreJury);
        membreJury.getCompositionCopies().remove(this);
        return this;
    }

    public void setMembreJuries(Set<MembreJury> membreJuries) {
        this.membreJuries = membreJuries;
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
        CompositionCopie compositionCopie = (CompositionCopie) o;
        if (compositionCopie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compositionCopie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompositionCopie{" +
            "id=" + getId() +
            ", note=" + getNote() +
            ", dateComposition='" + getDateComposition() + "'" +
            ", numeroAnonymat='" + getNumeroAnonymat() + "'" +
            "}";
    }
}
