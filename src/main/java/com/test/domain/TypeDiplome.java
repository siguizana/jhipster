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
 * A TypeDiplome.
 */
@Entity
@Table(name = "type_diplome")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeDiplome implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_type_diplome", nullable = false)
    private String libelleTypeDiplome;

    @OneToMany(mappedBy = "typeDiplome")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RetraitDiplome> retraitDiplomes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleTypeDiplome() {
        return libelleTypeDiplome;
    }

    public TypeDiplome libelleTypeDiplome(String libelleTypeDiplome) {
        this.libelleTypeDiplome = libelleTypeDiplome;
        return this;
    }

    public void setLibelleTypeDiplome(String libelleTypeDiplome) {
        this.libelleTypeDiplome = libelleTypeDiplome;
    }

    public Set<RetraitDiplome> getRetraitDiplomes() {
        return retraitDiplomes;
    }

    public TypeDiplome retraitDiplomes(Set<RetraitDiplome> retraitDiplomes) {
        this.retraitDiplomes = retraitDiplomes;
        return this;
    }

    public TypeDiplome addRetraitDiplome(RetraitDiplome retraitDiplome) {
        this.retraitDiplomes.add(retraitDiplome);
        retraitDiplome.setTypeDiplome(this);
        return this;
    }

    public TypeDiplome removeRetraitDiplome(RetraitDiplome retraitDiplome) {
        this.retraitDiplomes.remove(retraitDiplome);
        retraitDiplome.setTypeDiplome(null);
        return this;
    }

    public void setRetraitDiplomes(Set<RetraitDiplome> retraitDiplomes) {
        this.retraitDiplomes = retraitDiplomes;
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
        TypeDiplome typeDiplome = (TypeDiplome) o;
        if (typeDiplome.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeDiplome.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeDiplome{" +
            "id=" + getId() +
            ", libelleTypeDiplome='" + getLibelleTypeDiplome() + "'" +
            "}";
    }
}
