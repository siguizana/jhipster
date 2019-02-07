package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Sanction.
 */
@Entity
@Table(name = "sanction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sanction implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_sanction", nullable = false)
    private String libelleSanction;

    @ManyToOne
    @JsonIgnoreProperties("sanctions")
    private Fraude fraude;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleSanction() {
        return libelleSanction;
    }

    public Sanction libelleSanction(String libelleSanction) {
        this.libelleSanction = libelleSanction;
        return this;
    }

    public void setLibelleSanction(String libelleSanction) {
        this.libelleSanction = libelleSanction;
    }

    public Fraude getFraude() {
        return fraude;
    }

    public Sanction fraude(Fraude fraude) {
        this.fraude = fraude;
        return this;
    }

    public void setFraude(Fraude fraude) {
        this.fraude = fraude;
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
        Sanction sanction = (Sanction) o;
        if (sanction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sanction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sanction{" +
            "id=" + getId() +
            ", libelleSanction='" + getLibelleSanction() + "'" +
            "}";
    }
}
