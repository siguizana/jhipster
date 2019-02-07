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
 * A OptionConcoursRattache.
 */
@Entity
@Table(name = "option_concours_rattache")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OptionConcoursRattache implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_option_concours_rattache", nullable = false)
    private String libelleOptionConcoursRattache;

    @OneToMany(mappedBy = "optionConcoursRattache")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Resultat> resultats = new HashSet<>();
    @OneToMany(mappedBy = "optionConcoursRattache")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CritereExamen> critereExamen = new HashSet<>();
    @OneToMany(mappedBy = "optionConcoursRattache")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inscription> inscriptions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("optionConcoursRattaches")
    private ConcoursRattache concoursRattache;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleOptionConcoursRattache() {
        return libelleOptionConcoursRattache;
    }

    public OptionConcoursRattache libelleOptionConcoursRattache(String libelleOptionConcoursRattache) {
        this.libelleOptionConcoursRattache = libelleOptionConcoursRattache;
        return this;
    }

    public void setLibelleOptionConcoursRattache(String libelleOptionConcoursRattache) {
        this.libelleOptionConcoursRattache = libelleOptionConcoursRattache;
    }

    public Set<Resultat> getResultats() {
        return resultats;
    }

    public OptionConcoursRattache resultats(Set<Resultat> resultats) {
        this.resultats = resultats;
        return this;
    }

    public OptionConcoursRattache addResultat(Resultat resultat) {
        this.resultats.add(resultat);
        resultat.setOptionConcoursRattache(this);
        return this;
    }

    public OptionConcoursRattache removeResultat(Resultat resultat) {
        this.resultats.remove(resultat);
        resultat.setOptionConcoursRattache(null);
        return this;
    }

    public void setResultats(Set<Resultat> resultats) {
        this.resultats = resultats;
    }

    public Set<CritereExamen> getCritereExamen() {
        return critereExamen;
    }

    public OptionConcoursRattache critereExamen(Set<CritereExamen> critereExamen) {
        this.critereExamen = critereExamen;
        return this;
    }

    public OptionConcoursRattache addCritereExamen(CritereExamen critereExamen) {
        this.critereExamen.add(critereExamen);
        critereExamen.setOptionConcoursRattache(this);
        return this;
    }

    public OptionConcoursRattache removeCritereExamen(CritereExamen critereExamen) {
        this.critereExamen.remove(critereExamen);
        critereExamen.setOptionConcoursRattache(null);
        return this;
    }

    public void setCritereExamen(Set<CritereExamen> critereExamen) {
        this.critereExamen = critereExamen;
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public OptionConcoursRattache inscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
        return this;
    }

    public OptionConcoursRattache addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setOptionConcoursRattache(this);
        return this;
    }

    public OptionConcoursRattache removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setOptionConcoursRattache(null);
        return this;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public ConcoursRattache getConcoursRattache() {
        return concoursRattache;
    }

    public OptionConcoursRattache concoursRattache(ConcoursRattache concoursRattache) {
        this.concoursRattache = concoursRattache;
        return this;
    }

    public void setConcoursRattache(ConcoursRattache concoursRattache) {
        this.concoursRattache = concoursRattache;
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
        OptionConcoursRattache optionConcoursRattache = (OptionConcoursRattache) o;
        if (optionConcoursRattache.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), optionConcoursRattache.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OptionConcoursRattache{" +
            "id=" + getId() +
            ", libelleOptionConcoursRattache='" + getLibelleOptionConcoursRattache() + "'" +
            "}";
    }
}
