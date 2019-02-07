package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Surveille.
 */
@Entity
@Table(name = "surveille")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Surveille implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_debut_surveillance")
    private Instant dateDebutSurveillance;

    @Column(name = "date_fin_surveillance")
    private Instant dateFinSurveillance;

    @ManyToOne
    @JsonIgnoreProperties("surveilles")
    private MembreJury membreJury;

    @ManyToOne
    @JsonIgnoreProperties("surveilles")
    private SalleComposition salleComposition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateDebutSurveillance() {
        return dateDebutSurveillance;
    }

    public Surveille dateDebutSurveillance(Instant dateDebutSurveillance) {
        this.dateDebutSurveillance = dateDebutSurveillance;
        return this;
    }

    public void setDateDebutSurveillance(Instant dateDebutSurveillance) {
        this.dateDebutSurveillance = dateDebutSurveillance;
    }

    public Instant getDateFinSurveillance() {
        return dateFinSurveillance;
    }

    public Surveille dateFinSurveillance(Instant dateFinSurveillance) {
        this.dateFinSurveillance = dateFinSurveillance;
        return this;
    }

    public void setDateFinSurveillance(Instant dateFinSurveillance) {
        this.dateFinSurveillance = dateFinSurveillance;
    }

    public MembreJury getMembreJury() {
        return membreJury;
    }

    public Surveille membreJury(MembreJury membreJury) {
        this.membreJury = membreJury;
        return this;
    }

    public void setMembreJury(MembreJury membreJury) {
        this.membreJury = membreJury;
    }

    public SalleComposition getSalleComposition() {
        return salleComposition;
    }

    public Surveille salleComposition(SalleComposition salleComposition) {
        this.salleComposition = salleComposition;
        return this;
    }

    public void setSalleComposition(SalleComposition salleComposition) {
        this.salleComposition = salleComposition;
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
        Surveille surveille = (Surveille) o;
        if (surveille.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), surveille.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Surveille{" +
            "id=" + getId() +
            ", dateDebutSurveillance='" + getDateDebutSurveillance() + "'" +
            ", dateFinSurveillance='" + getDateFinSurveillance() + "'" +
            "}";
    }
}
