package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ChoixEtablissement.
 */
@Entity
@Table(name = "choix_etablissement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChoixEtablissement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "priorite")
    private String priorite;

    @ManyToOne
    @JsonIgnoreProperties("choixEtablissements")
    private Inscription inscription;

    @ManyToOne
    @JsonIgnoreProperties("choixEtablissements")
    private Etablissement etablissement;

    @ManyToOne
    @JsonIgnoreProperties("choixEtablissements")
    private SpecialiteOption specialiteOption;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriorite() {
        return priorite;
    }

    public ChoixEtablissement priorite(String priorite) {
        this.priorite = priorite;
        return this;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public ChoixEtablissement inscription(Inscription inscription) {
        this.inscription = inscription;
        return this;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public ChoixEtablissement etablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
        return this;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public SpecialiteOption getSpecialiteOption() {
        return specialiteOption;
    }

    public ChoixEtablissement specialiteOption(SpecialiteOption specialiteOption) {
        this.specialiteOption = specialiteOption;
        return this;
    }

    public void setSpecialiteOption(SpecialiteOption specialiteOption) {
        this.specialiteOption = specialiteOption;
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
        ChoixEtablissement choixEtablissement = (ChoixEtablissement) o;
        if (choixEtablissement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), choixEtablissement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChoixEtablissement{" +
            "id=" + getId() +
            ", priorite='" + getPriorite() + "'" +
            "}";
    }
}
