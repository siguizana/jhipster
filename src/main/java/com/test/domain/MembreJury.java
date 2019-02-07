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
 * A MembreJury.
 */
@Entity
@Table(name = "membre_jury")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MembreJury implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom_membre", nullable = false)
    private String nomMembre;

    @NotNull
    @Column(name = "prenom_membre", nullable = false)
    private String prenomMembre;

    @NotNull
    @Column(name = "numero_cnib", nullable = false)
    private String numeroCNIB;

    @Column(name = "matricule")
    private String matricule;

    @OneToMany(mappedBy = "membreJury")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Surveille> surveilles = new HashSet<>();
    @OneToMany(mappedBy = "membreJury")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NoteMembreCritere> noteMembreCriteres = new HashSet<>();
    @OneToMany(mappedBy = "membreJury")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MembreJuryJury> membreJuryJuries = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "membre_jury_fraude",
               joinColumns = @JoinColumn(name = "membre_jury_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "fraude_id", referencedColumnName = "id"))
    private Set<Fraude> fraudes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "membre_jury_composition_copie",
               joinColumns = @JoinColumn(name = "membre_jury_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "composition_copie_id", referencedColumnName = "id"))
    private Set<CompositionCopie> compositionCopies = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("membreJuries")
    private TypeMembreJury typeMembreJury;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomMembre() {
        return nomMembre;
    }

    public MembreJury nomMembre(String nomMembre) {
        this.nomMembre = nomMembre;
        return this;
    }

    public void setNomMembre(String nomMembre) {
        this.nomMembre = nomMembre;
    }

    public String getPrenomMembre() {
        return prenomMembre;
    }

    public MembreJury prenomMembre(String prenomMembre) {
        this.prenomMembre = prenomMembre;
        return this;
    }

    public void setPrenomMembre(String prenomMembre) {
        this.prenomMembre = prenomMembre;
    }

    public String getNumeroCNIB() {
        return numeroCNIB;
    }

    public MembreJury numeroCNIB(String numeroCNIB) {
        this.numeroCNIB = numeroCNIB;
        return this;
    }

    public void setNumeroCNIB(String numeroCNIB) {
        this.numeroCNIB = numeroCNIB;
    }

    public String getMatricule() {
        return matricule;
    }

    public MembreJury matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Set<Surveille> getSurveilles() {
        return surveilles;
    }

    public MembreJury surveilles(Set<Surveille> surveilles) {
        this.surveilles = surveilles;
        return this;
    }

    public MembreJury addSurveille(Surveille surveille) {
        this.surveilles.add(surveille);
        surveille.setMembreJury(this);
        return this;
    }

    public MembreJury removeSurveille(Surveille surveille) {
        this.surveilles.remove(surveille);
        surveille.setMembreJury(null);
        return this;
    }

    public void setSurveilles(Set<Surveille> surveilles) {
        this.surveilles = surveilles;
    }

    public Set<NoteMembreCritere> getNoteMembreCriteres() {
        return noteMembreCriteres;
    }

    public MembreJury noteMembreCriteres(Set<NoteMembreCritere> noteMembreCriteres) {
        this.noteMembreCriteres = noteMembreCriteres;
        return this;
    }

    public MembreJury addNoteMembreCritere(NoteMembreCritere noteMembreCritere) {
        this.noteMembreCriteres.add(noteMembreCritere);
        noteMembreCritere.setMembreJury(this);
        return this;
    }

    public MembreJury removeNoteMembreCritere(NoteMembreCritere noteMembreCritere) {
        this.noteMembreCriteres.remove(noteMembreCritere);
        noteMembreCritere.setMembreJury(null);
        return this;
    }

    public void setNoteMembreCriteres(Set<NoteMembreCritere> noteMembreCriteres) {
        this.noteMembreCriteres = noteMembreCriteres;
    }

    public Set<MembreJuryJury> getMembreJuryJuries() {
        return membreJuryJuries;
    }

    public MembreJury membreJuryJuries(Set<MembreJuryJury> membreJuryJuries) {
        this.membreJuryJuries = membreJuryJuries;
        return this;
    }

    public MembreJury addMembreJuryJury(MembreJuryJury membreJuryJury) {
        this.membreJuryJuries.add(membreJuryJury);
        membreJuryJury.setMembreJury(this);
        return this;
    }

    public MembreJury removeMembreJuryJury(MembreJuryJury membreJuryJury) {
        this.membreJuryJuries.remove(membreJuryJury);
        membreJuryJury.setMembreJury(null);
        return this;
    }

    public void setMembreJuryJuries(Set<MembreJuryJury> membreJuryJuries) {
        this.membreJuryJuries = membreJuryJuries;
    }

    public Set<Fraude> getFraudes() {
        return fraudes;
    }

    public MembreJury fraudes(Set<Fraude> fraudes) {
        this.fraudes = fraudes;
        return this;
    }

    public MembreJury addFraude(Fraude fraude) {
        this.fraudes.add(fraude);
        fraude.getMembreJuries().add(this);
        return this;
    }

    public MembreJury removeFraude(Fraude fraude) {
        this.fraudes.remove(fraude);
        fraude.getMembreJuries().remove(this);
        return this;
    }

    public void setFraudes(Set<Fraude> fraudes) {
        this.fraudes = fraudes;
    }

    public Set<CompositionCopie> getCompositionCopies() {
        return compositionCopies;
    }

    public MembreJury compositionCopies(Set<CompositionCopie> compositionCopies) {
        this.compositionCopies = compositionCopies;
        return this;
    }

    public MembreJury addCompositionCopie(CompositionCopie compositionCopie) {
        this.compositionCopies.add(compositionCopie);
        compositionCopie.getMembreJuries().add(this);
        return this;
    }

    public MembreJury removeCompositionCopie(CompositionCopie compositionCopie) {
        this.compositionCopies.remove(compositionCopie);
        compositionCopie.getMembreJuries().remove(this);
        return this;
    }

    public void setCompositionCopies(Set<CompositionCopie> compositionCopies) {
        this.compositionCopies = compositionCopies;
    }

    public TypeMembreJury getTypeMembreJury() {
        return typeMembreJury;
    }

    public MembreJury typeMembreJury(TypeMembreJury typeMembreJury) {
        this.typeMembreJury = typeMembreJury;
        return this;
    }

    public void setTypeMembreJury(TypeMembreJury typeMembreJury) {
        this.typeMembreJury = typeMembreJury;
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
        MembreJury membreJury = (MembreJury) o;
        if (membreJury.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), membreJury.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MembreJury{" +
            "id=" + getId() +
            ", nomMembre='" + getNomMembre() + "'" +
            ", prenomMembre='" + getPrenomMembre() + "'" +
            ", numeroCNIB='" + getNumeroCNIB() + "'" +
            ", matricule='" + getMatricule() + "'" +
            "}";
    }
}
