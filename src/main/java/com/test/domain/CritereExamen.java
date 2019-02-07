package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CritereExamen.
 */
@Entity
@Table(name = "critere_examen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CritereExamen implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_critere_examen", nullable = false)
    private String libelleCritereExamen;

    @NotNull
    @Column(name = "valeur", nullable = false)
    private Float valeur;

    @ManyToOne
    @JsonIgnoreProperties("critereExamen")
    private Region region;

    @ManyToOne
    @JsonIgnoreProperties("critereExamen")
    private OptionConcoursRattache optionConcoursRattache;

    @ManyToOne
    @JsonIgnoreProperties("critereExamen")
    private Session session;

    @ManyToOne
    @JsonIgnoreProperties("critereExamen")
    private TypeExamen typeExamen;

    @ManyToOne
    @JsonIgnoreProperties("critereExamen")
    private TypeCritere typeCritere;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleCritereExamen() {
        return libelleCritereExamen;
    }

    public CritereExamen libelleCritereExamen(String libelleCritereExamen) {
        this.libelleCritereExamen = libelleCritereExamen;
        return this;
    }

    public void setLibelleCritereExamen(String libelleCritereExamen) {
        this.libelleCritereExamen = libelleCritereExamen;
    }

    public Float getValeur() {
        return valeur;
    }

    public CritereExamen valeur(Float valeur) {
        this.valeur = valeur;
        return this;
    }

    public void setValeur(Float valeur) {
        this.valeur = valeur;
    }

    public Region getRegion() {
        return region;
    }

    public CritereExamen region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public OptionConcoursRattache getOptionConcoursRattache() {
        return optionConcoursRattache;
    }

    public CritereExamen optionConcoursRattache(OptionConcoursRattache optionConcoursRattache) {
        this.optionConcoursRattache = optionConcoursRattache;
        return this;
    }

    public void setOptionConcoursRattache(OptionConcoursRattache optionConcoursRattache) {
        this.optionConcoursRattache = optionConcoursRattache;
    }

    public Session getSession() {
        return session;
    }

    public CritereExamen session(Session session) {
        this.session = session;
        return this;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public TypeExamen getTypeExamen() {
        return typeExamen;
    }

    public CritereExamen typeExamen(TypeExamen typeExamen) {
        this.typeExamen = typeExamen;
        return this;
    }

    public void setTypeExamen(TypeExamen typeExamen) {
        this.typeExamen = typeExamen;
    }

    public TypeCritere getTypeCritere() {
        return typeCritere;
    }

    public CritereExamen typeCritere(TypeCritere typeCritere) {
        this.typeCritere = typeCritere;
        return this;
    }

    public void setTypeCritere(TypeCritere typeCritere) {
        this.typeCritere = typeCritere;
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
        CritereExamen critereExamen = (CritereExamen) o;
        if (critereExamen.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), critereExamen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CritereExamen{" +
            "id=" + getId() +
            ", libelleCritereExamen='" + getLibelleCritereExamen() + "'" +
            ", valeur=" + getValeur() +
            "}";
    }
}
