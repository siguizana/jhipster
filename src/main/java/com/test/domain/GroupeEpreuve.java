package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GroupeEpreuve.
 */
@Entity
@Table(name = "groupe_epreuve")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GroupeEpreuve implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "groupeEpreuve")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions = new HashSet<>();
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

    public GroupeEpreuve libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<EpreuveSpecialiteOption> getEpreuveSpecialiteOptions() {
        return epreuveSpecialiteOptions;
    }

    public GroupeEpreuve epreuveSpecialiteOptions(Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions) {
        this.epreuveSpecialiteOptions = epreuveSpecialiteOptions;
        return this;
    }

    public GroupeEpreuve addEpreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOptions.add(epreuveSpecialiteOption);
        epreuveSpecialiteOption.setGroupeEpreuve(this);
        return this;
    }

    public GroupeEpreuve removeEpreuveSpecialiteOption(EpreuveSpecialiteOption epreuveSpecialiteOption) {
        this.epreuveSpecialiteOptions.remove(epreuveSpecialiteOption);
        epreuveSpecialiteOption.setGroupeEpreuve(null);
        return this;
    }

    public void setEpreuveSpecialiteOptions(Set<EpreuveSpecialiteOption> epreuveSpecialiteOptions) {
        this.epreuveSpecialiteOptions = epreuveSpecialiteOptions;
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
        GroupeEpreuve groupeEpreuve = (GroupeEpreuve) o;
        if (groupeEpreuve.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), groupeEpreuve.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GroupeEpreuve{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
