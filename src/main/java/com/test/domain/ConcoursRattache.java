package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A ConcoursRattache.
 */
@Entity
@Table(name = "concours_rattache")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConcoursRattache implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_concours_rattache", nullable = false)
    private String libelleConcoursRattache;

    @OneToMany(mappedBy = "concoursRattache")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OptionConcoursRattache> optionConcoursRattaches = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("concoursRattaches")
    private TypeExamen typeExamen;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleConcoursRattache() {
        return libelleConcoursRattache;
    }

    public ConcoursRattache libelleConcoursRattache(String libelleConcoursRattache) {
        this.libelleConcoursRattache = libelleConcoursRattache;
        return this;
    }

    public void setLibelleConcoursRattache(String libelleConcoursRattache) {
        this.libelleConcoursRattache = libelleConcoursRattache;
    }

    public Set<OptionConcoursRattache> getOptionConcoursRattaches() {
        return optionConcoursRattaches;
    }

    public ConcoursRattache optionConcoursRattaches(Set<OptionConcoursRattache> optionConcoursRattaches) {
        this.optionConcoursRattaches = optionConcoursRattaches;
        return this;
    }

    public ConcoursRattache addOptionConcoursRattache(OptionConcoursRattache optionConcoursRattache) {
        this.optionConcoursRattaches.add(optionConcoursRattache);
        optionConcoursRattache.setConcoursRattache(this);
        return this;
    }

    public ConcoursRattache removeOptionConcoursRattache(OptionConcoursRattache optionConcoursRattache) {
        this.optionConcoursRattaches.remove(optionConcoursRattache);
        optionConcoursRattache.setConcoursRattache(null);
        return this;
    }

    public void setOptionConcoursRattaches(Set<OptionConcoursRattache> optionConcoursRattaches) {
        this.optionConcoursRattaches = optionConcoursRattaches;
    }

    public TypeExamen getTypeExamen() {
        return typeExamen;
    }

    public ConcoursRattache typeExamen(TypeExamen typeExamen) {
        this.typeExamen = typeExamen;
        return this;
    }

    public void setTypeExamen(TypeExamen typeExamen) {
        this.typeExamen = typeExamen;
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
        ConcoursRattache concoursRattache = (ConcoursRattache) o;
        if (concoursRattache.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), concoursRattache.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConcoursRattache{" +
            "id=" + getId() +
            ", libelleConcoursRattache='" + getLibelleConcoursRattache() + "'" +
            "}";
    }
}
