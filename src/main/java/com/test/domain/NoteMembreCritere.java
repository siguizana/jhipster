package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A NoteMembreCritere.
 */
@Entity
@Table(name = "note_membre_critere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NoteMembreCritere implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "valeur_note")
    private Float valeurNote;

    @ManyToOne
    @JsonIgnoreProperties("noteMembreCriteres")
    private MembreJury membreJury;

    @ManyToOne
    @JsonIgnoreProperties("noteMembreCriteres")
    private CritereChoixMembreJury critereChoixMembreJury;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValeurNote() {
        return valeurNote;
    }

    public NoteMembreCritere valeurNote(Float valeurNote) {
        this.valeurNote = valeurNote;
        return this;
    }

    public void setValeurNote(Float valeurNote) {
        this.valeurNote = valeurNote;
    }

    public MembreJury getMembreJury() {
        return membreJury;
    }

    public NoteMembreCritere membreJury(MembreJury membreJury) {
        this.membreJury = membreJury;
        return this;
    }

    public void setMembreJury(MembreJury membreJury) {
        this.membreJury = membreJury;
    }

    public CritereChoixMembreJury getCritereChoixMembreJury() {
        return critereChoixMembreJury;
    }

    public NoteMembreCritere critereChoixMembreJury(CritereChoixMembreJury critereChoixMembreJury) {
        this.critereChoixMembreJury = critereChoixMembreJury;
        return this;
    }

    public void setCritereChoixMembreJury(CritereChoixMembreJury critereChoixMembreJury) {
        this.critereChoixMembreJury = critereChoixMembreJury;
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
        NoteMembreCritere noteMembreCritere = (NoteMembreCritere) o;
        if (noteMembreCritere.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), noteMembreCritere.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NoteMembreCritere{" +
            "id=" + getId() +
            ", valeurNote=" + getValeurNote() +
            "}";
    }
}
