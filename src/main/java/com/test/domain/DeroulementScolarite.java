package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DeroulementScolarite.
 */
@Entity
@Table(name = "deroulement_scolarite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeroulementScolarite implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "anne_scolarite", nullable = false)
    private String anneScolarite;

    @NotNull
    @Column(name = "classe", nullable = false)
    private String classe;

    @ManyToOne
    @JsonIgnoreProperties("deroulementScolarites")
    private Candidat candidat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnneScolarite() {
        return anneScolarite;
    }

    public DeroulementScolarite anneScolarite(String anneScolarite) {
        this.anneScolarite = anneScolarite;
        return this;
    }

    public void setAnneScolarite(String anneScolarite) {
        this.anneScolarite = anneScolarite;
    }

    public String getClasse() {
        return classe;
    }

    public DeroulementScolarite classe(String classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public DeroulementScolarite candidat(Candidat candidat) {
        this.candidat = candidat;
        return this;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
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
        DeroulementScolarite deroulementScolarite = (DeroulementScolarite) o;
        if (deroulementScolarite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deroulementScolarite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeroulementScolarite{" +
            "id=" + getId() +
            ", anneScolarite='" + getAnneScolarite() + "'" +
            ", classe='" + getClasse() + "'" +
            "}";
    }
}
