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
 * A RetraitDiplome.
 */
@Entity
@Table(name = "retrait_diplome")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RetraitDiplome implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_retrait", nullable = false)
    private LocalDate dateRetrait;

    @ManyToOne
    @JsonIgnoreProperties("retraitDiplomes")
    private Resultat resultat;

    @ManyToOne
    @JsonIgnoreProperties("retraitDiplomes")
    private TypeDiplome typeDiplome;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateRetrait() {
        return dateRetrait;
    }

    public RetraitDiplome dateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
        return this;
    }

    public void setDateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    public Resultat getResultat() {
        return resultat;
    }

    public RetraitDiplome resultat(Resultat resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(Resultat resultat) {
        this.resultat = resultat;
    }

    public TypeDiplome getTypeDiplome() {
        return typeDiplome;
    }

    public RetraitDiplome typeDiplome(TypeDiplome typeDiplome) {
        this.typeDiplome = typeDiplome;
        return this;
    }

    public void setTypeDiplome(TypeDiplome typeDiplome) {
        this.typeDiplome = typeDiplome;
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
        RetraitDiplome retraitDiplome = (RetraitDiplome) o;
        if (retraitDiplome.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), retraitDiplome.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RetraitDiplome{" +
            "id=" + getId() +
            ", dateRetrait='" + getDateRetrait() + "'" +
            "}";
    }
}
