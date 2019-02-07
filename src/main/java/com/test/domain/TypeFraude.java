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
 * A TypeFraude.
 */
@Entity
@Table(name = "type_fraude")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeFraude implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_type_fraude", nullable = false)
    private String libelleTypeFraude;

    @OneToMany(mappedBy = "typeFraude")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Fraude> fraudes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleTypeFraude() {
        return libelleTypeFraude;
    }

    public TypeFraude libelleTypeFraude(String libelleTypeFraude) {
        this.libelleTypeFraude = libelleTypeFraude;
        return this;
    }

    public void setLibelleTypeFraude(String libelleTypeFraude) {
        this.libelleTypeFraude = libelleTypeFraude;
    }

    public Set<Fraude> getFraudes() {
        return fraudes;
    }

    public TypeFraude fraudes(Set<Fraude> fraudes) {
        this.fraudes = fraudes;
        return this;
    }

    public TypeFraude addFraude(Fraude fraude) {
        this.fraudes.add(fraude);
        fraude.setTypeFraude(this);
        return this;
    }

    public TypeFraude removeFraude(Fraude fraude) {
        this.fraudes.remove(fraude);
        fraude.setTypeFraude(null);
        return this;
    }

    public void setFraudes(Set<Fraude> fraudes) {
        this.fraudes = fraudes;
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
        TypeFraude typeFraude = (TypeFraude) o;
        if (typeFraude.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeFraude.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeFraude{" +
            "id=" + getId() +
            ", libelleTypeFraude='" + getLibelleTypeFraude() + "'" +
            "}";
    }
}
