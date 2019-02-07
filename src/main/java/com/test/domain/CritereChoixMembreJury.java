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
 * A CritereChoixMembreJury.
 */
@Entity
@Table(name = "critere_choix_membre_jury")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CritereChoixMembreJury implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "note_par_defaut", nullable = false)
    private Float noteParDefaut;

    @OneToMany(mappedBy = "critereChoixMembreJury")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NoteMembreCritere> noteMembreCriteres = new HashSet<>();
    @ManyToMany(mappedBy = "critereChoixMembreJuries")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<TypeMembreJury> typeMembreJuries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public CritereChoixMembreJury libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Float getNoteParDefaut() {
        return noteParDefaut;
    }

    public CritereChoixMembreJury noteParDefaut(Float noteParDefaut) {
        this.noteParDefaut = noteParDefaut;
        return this;
    }

    public void setNoteParDefaut(Float noteParDefaut) {
        this.noteParDefaut = noteParDefaut;
    }

    public Set<NoteMembreCritere> getNoteMembreCriteres() {
        return noteMembreCriteres;
    }

    public CritereChoixMembreJury noteMembreCriteres(Set<NoteMembreCritere> noteMembreCriteres) {
        this.noteMembreCriteres = noteMembreCriteres;
        return this;
    }

    public CritereChoixMembreJury addNoteMembreCritere(NoteMembreCritere noteMembreCritere) {
        this.noteMembreCriteres.add(noteMembreCritere);
        noteMembreCritere.setCritereChoixMembreJury(this);
        return this;
    }

    public CritereChoixMembreJury removeNoteMembreCritere(NoteMembreCritere noteMembreCritere) {
        this.noteMembreCriteres.remove(noteMembreCritere);
        noteMembreCritere.setCritereChoixMembreJury(null);
        return this;
    }

    public void setNoteMembreCriteres(Set<NoteMembreCritere> noteMembreCriteres) {
        this.noteMembreCriteres = noteMembreCriteres;
    }

    public Set<TypeMembreJury> getTypeMembreJuries() {
        return typeMembreJuries;
    }

    public CritereChoixMembreJury typeMembreJuries(Set<TypeMembreJury> typeMembreJuries) {
        this.typeMembreJuries = typeMembreJuries;
        return this;
    }

    public CritereChoixMembreJury addTypeMembreJury(TypeMembreJury typeMembreJury) {
        this.typeMembreJuries.add(typeMembreJury);
        typeMembreJury.getCritereChoixMembreJuries().add(this);
        return this;
    }

    public CritereChoixMembreJury removeTypeMembreJury(TypeMembreJury typeMembreJury) {
        this.typeMembreJuries.remove(typeMembreJury);
        typeMembreJury.getCritereChoixMembreJuries().remove(this);
        return this;
    }

    public void setTypeMembreJuries(Set<TypeMembreJury> typeMembreJuries) {
        this.typeMembreJuries = typeMembreJuries;
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
        CritereChoixMembreJury critereChoixMembreJury = (CritereChoixMembreJury) o;
        if (critereChoixMembreJury.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), critereChoixMembreJury.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CritereChoixMembreJury{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", noteParDefaut=" + getNoteParDefaut() +
            "}";
    }
}
