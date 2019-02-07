package com.test.domain;


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
 * A Dispense.
 */
@Entity
@Table(name = "dispense")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dispense implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "motit_dispense", nullable = false)
    private String motitDispense;

    @NotNull
    @Column(name = "nom_prenom_medecin", nullable = false)
    private String nomPrenomMedecin;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "dispense_epreuve_specialite_option",
               joinColumns = @JoinColumn(name = "dispense_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "epreuve_specialite_option_id", referencedColumnName = "id"))
    private Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("dispenses")
    private Inscription inscription;

    @ManyToOne
    @JsonIgnoreProperties("dispenses")
    private Handicape handicape;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotitDispense() {
        return motitDispense;
    }

    public Dispense motitDispense(String motitDispense) {
        this.motitDispense = motitDispense;
        return this;
    }

    public void setMotitDispense(String motitDispense) {
        this.motitDispense = motitDispense;
    }

    public String getNomPrenomMedecin() {
        return nomPrenomMedecin;
    }

    public Dispense nomPrenomMedecin(String nomPrenomMedecin) {
        this.nomPrenomMedecin = nomPrenomMedecin;
        return this;
    }

    public void setNomPrenomMedecin(String nomPrenomMedecin) {
        this.nomPrenomMedecin = nomPrenomMedecin;
    }

    public Set<EpreuveSpecialiteOption> getEpreuveSpecialiteOptions() {
        return epreuveSpecialiteOptions;
    }

    public Dispense epreuveSpecialiteOptions(Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions) {
        this.epreuveSpecialiteOptions = epreuveSpecialiteOptions;
        return this;
    }

    public Dispense addEpreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOptions.add(epreuveSpecialiteOption);
        epreuveSpecialiteOption.getDispenses().add(this);
        return this;
    }

    public Dispense removeEpreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOptions.remove(epreuveSpecialiteOption);
        epreuveSpecialiteOption.getDispenses().remove(this);
        return this;
    }

    public void setEpreuveSpecialiteOptions(Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions) {
        this.epreuveSpecialiteOptions = epreuveSpecialiteOptions;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public Dispense inscription(Inscription inscription) {
        this.inscription = inscription;
        return this;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Handicape getHandicape() {
        return handicape;
    }

    public Dispense handicape(Handicape handicape) {
        this.handicape = handicape;
        return this;
    }

    public void setHandicape(Handicape handicape) {
        this.handicape = handicape;
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
        Dispense dispense = (Dispense) o;
        if (dispense.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dispense.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dispense{" +
            "id=" + getId() +
            ", motitDispense='" + getMotitDispense() + "'" +
            ", nomPrenomMedecin='" + getNomPrenomMedecin() + "'" +
            "}";
    }
}
