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
 * A Resultat.
 */
@Entity
@Table(name = "resultat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resultat implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "note_pondere", nullable = false)
    private Float notePondere;

    @OneToMany(mappedBy = "resultat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RetraitDiplome> retraitDiplomes = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("resultats")
    private EtapeExamen etapeExamen;

    @ManyToOne
    @JsonIgnoreProperties("resultats")
    private Mention mention;

    @ManyToOne
    @JsonIgnoreProperties("resultats")
    private TypeDecision typeDecision;

    @ManyToOne
    @JsonIgnoreProperties("resultats")
    private OptionConcoursRattache optionConcoursRattache;

    @ManyToOne
    @JsonIgnoreProperties("resultats")
    private Inscription inscription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getNotePondere() {
        return notePondere;
    }

    public Resultat notePondere(Float notePondere) {
        this.notePondere = notePondere;
        return this;
    }

    public void setNotePondere(Float notePondere) {
        this.notePondere = notePondere;
    }

    public Set<RetraitDiplome> getRetraitDiplomes() {
        return retraitDiplomes;
    }

    public Resultat retraitDiplomes(Set<RetraitDiplome> retraitDiplomes) {
        this.retraitDiplomes = retraitDiplomes;
        return this;
    }

    public Resultat addRetraitDiplome(RetraitDiplome retraitDiplome) {
        this.retraitDiplomes.add(retraitDiplome);
        retraitDiplome.setResultat(this);
        return this;
    }

    public Resultat removeRetraitDiplome(RetraitDiplome retraitDiplome) {
        this.retraitDiplomes.remove(retraitDiplome);
        retraitDiplome.setResultat(null);
        return this;
    }

    public void setRetraitDiplomes(Set<RetraitDiplome> retraitDiplomes) {
        this.retraitDiplomes = retraitDiplomes;
    }

    public EtapeExamen getEtapeExamen() {
        return etapeExamen;
    }

    public Resultat etapeExamen(EtapeExamen etapeExamen) {
        this.etapeExamen = etapeExamen;
        return this;
    }

    public void setEtapeExamen(EtapeExamen etapeExamen) {
        this.etapeExamen = etapeExamen;
    }

    public Mention getMention() {
        return mention;
    }

    public Resultat mention(Mention mention) {
        this.mention = mention;
        return this;
    }

    public void setMention(Mention mention) {
        this.mention = mention;
    }

    public TypeDecision getTypeDecision() {
        return typeDecision;
    }

    public Resultat typeDecision(TypeDecision typeDecision) {
        this.typeDecision = typeDecision;
        return this;
    }

    public void setTypeDecision(TypeDecision typeDecision) {
        this.typeDecision = typeDecision;
    }

    public OptionConcoursRattache getOptionConcoursRattache() {
        return optionConcoursRattache;
    }

    public Resultat optionConcoursRattache(OptionConcoursRattache optionConcoursRattache) {
        this.optionConcoursRattache = optionConcoursRattache;
        return this;
    }

    public void setOptionConcoursRattache(OptionConcoursRattache optionConcoursRattache) {
        this.optionConcoursRattache = optionConcoursRattache;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public Resultat inscription(Inscription inscription) {
        this.inscription = inscription;
        return this;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
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
        Resultat resultat = (Resultat) o;
        if (resultat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resultat{" +
            "id=" + getId() +
            ", notePondere=" + getNotePondere() +
            "}";
    }
}
