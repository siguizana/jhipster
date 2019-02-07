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
 * A Commission.
 */
@Entity
@Table(name = "commission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commission implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_commission", nullable = false)
    private String libelleCommission;

    @ManyToMany(mappedBy = "commissions")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<MembreJuryJury> membreJuryJuries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleCommission() {
        return libelleCommission;
    }

    public Commission libelleCommission(String libelleCommission) {
        this.libelleCommission = libelleCommission;
        return this;
    }

    public void setLibelleCommission(String libelleCommission) {
        this.libelleCommission = libelleCommission;
    }

    public Set<MembreJuryJury> getMembreJuryJuries() {
        return membreJuryJuries;
    }

    public Commission membreJuryJuries(Set<MembreJuryJury> membreJuryJuries) {
        this.membreJuryJuries = membreJuryJuries;
        return this;
    }

    public Commission addMembreJuryJury(MembreJuryJury membreJuryJury) {
        this.membreJuryJuries.add(membreJuryJury);
        membreJuryJury.getCommissions().add(this);
        return this;
    }

    public Commission removeMembreJuryJury(MembreJuryJury membreJuryJury) {
        this.membreJuryJuries.remove(membreJuryJury);
        membreJuryJury.getCommissions().remove(this);
        return this;
    }

    public void setMembreJuryJuries(Set<MembreJuryJury> membreJuryJuries) {
        this.membreJuryJuries = membreJuryJuries;
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
        Commission commission = (Commission) o;
        if (commission.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commission.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Commission{" +
            "id=" + getId() +
            ", libelleCommission='" + getLibelleCommission() + "'" +
            "}";
    }
}
