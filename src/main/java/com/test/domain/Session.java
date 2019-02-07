package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Session.
 */
@Entity
@Table(name = "session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "date_ouverture", nullable = false)
    private LocalDate dateOuverture;

    @Column(name = "date_fermeture")
    private LocalDate dateFermeture;

    @NotNull
    @Column(name = "annee_session", nullable = false)
    private Integer anneeSession;

    @OneToMany(mappedBy = "session")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inscription> inscriptions = new HashSet<>();
    @OneToMany(mappedBy = "session")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CritereExamen> critereExamen = new HashSet<>();
    @OneToMany(mappedBy = "session")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Enseigne> enseignes = new HashSet<>();
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

    public Session libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDateOuverture() {
        return dateOuverture;
    }

    public Session dateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
        return this;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public LocalDate getDateFermeture() {
        return dateFermeture;
    }

    public Session dateFermeture(LocalDate dateFermeture) {
        this.dateFermeture = dateFermeture;
        return this;
    }

    public void setDateFermeture(LocalDate dateFermeture) {
        this.dateFermeture = dateFermeture;
    }

    public Integer getAnneeSession() {
        return anneeSession;
    }

    public Session anneeSession(Integer anneeSession) {
        this.anneeSession = anneeSession;
        return this;
    }

    public void setAnneeSession(Integer anneeSession) {
        this.anneeSession = anneeSession;
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public Session inscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
        return this;
    }

    public Session addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setSession(this);
        return this;
    }

    public Session removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setSession(null);
        return this;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public Set<CritereExamen> getCritereExamen() {
        return critereExamen;
    }

    public Session critereExamen(Set<CritereExamen> critereExamen) {
        this.critereExamen = critereExamen;
        return this;
    }

    public Session addCritereExamen(CritereExamen critereExamen) {
        this.critereExamen.add(critereExamen);
        critereExamen.setSession(this);
        return this;
    }

    public Session removeCritereExamen(CritereExamen critereExamen) {
        this.critereExamen.remove(critereExamen);
        critereExamen.setSession(null);
        return this;
    }

    public void setCritereExamen(Set<CritereExamen> critereExamen) {
        this.critereExamen = critereExamen;
    }

    public Set<Enseigne> getEnseignes() {
        return enseignes;
    }

    public Session enseignes(Set<Enseigne> enseignes) {
        this.enseignes = enseignes;
        return this;
    }

    public Session addEnseigne(Enseigne enseigne) {
        this.enseignes.add(enseigne);
        enseigne.setSession(this);
        return this;
    }

    public Session removeEnseigne(Enseigne enseigne) {
        this.enseignes.remove(enseigne);
        enseigne.setSession(null);
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
        Session session = (Session) o;
        if (session.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), session.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", dateOuverture='" + getDateOuverture() + "'" +
            ", dateFermeture='" + getDateFermeture() + "'" +
            ", anneeSession=" + getAnneeSession() +
            "}";
    }
}
