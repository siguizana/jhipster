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
 * A TypeMembreJury.
 */
@Entity
@Table(name = "type_membre_jury")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeMembreJury implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "typeMembreJury")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MembreJury> membreJuries = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "type_membre_jury_critere_choix_membre_jury",
               joinColumns = @JoinColumn(name = "type_membre_jury_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "critere_choix_membre_jury_id", referencedColumnName = "id"))
    private Set<CritereChoixMembreJury> critereChoixMembreJuries = new HashSet<>();

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

    public TypeMembreJury libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<MembreJury> getMembreJuries() {
        return membreJuries;
    }

    public TypeMembreJury membreJuries(Set<MembreJury> membreJuries) {
        this.membreJuries = membreJuries;
        return this;
    }

    public TypeMembreJury addMembreJury(MembreJury membreJury) {
        this.membreJuries.add(membreJury);
        membreJury.setTypeMembreJury(this);
        return this;
    }

    public TypeMembreJury removeMembreJury(MembreJury membreJury) {
        this.membreJuries.remove(membreJury);
        membreJury.setTypeMembreJury(null);
        return this;
    }

    public void setMembreJuries(Set<MembreJury> membreJuries) {
        this.membreJuries = membreJuries;
    }

    public Set<CritereChoixMembreJury> getCritereChoixMembreJuries() {
        return critereChoixMembreJuries;
    }

    public TypeMembreJury critereChoixMembreJuries(Set<CritereChoixMembreJury> critereChoixMembreJuries) {
        this.critereChoixMembreJuries = critereChoixMembreJuries;
        return this;
    }

    public TypeMembreJury addCritereChoixMembreJury(CritereChoixMembreJury critereChoixMembreJury) {
        this.critereChoixMembreJuries.add(critereChoixMembreJury);
        critereChoixMembreJury.getTypeMembreJuries().add(this);
        return this;
    }

    public TypeMembreJury removeCritereChoixMembreJury(CritereChoixMembreJury critereChoixMembreJury) {
        this.critereChoixMembreJuries.remove(critereChoixMembreJury);
        critereChoixMembreJury.getTypeMembreJuries().remove(this);
        return this;
    }

    public void setCritereChoixMembreJuries(Set<CritereChoixMembreJury> critereChoixMembreJuries) {
        this.critereChoixMembreJuries = critereChoixMembreJuries;
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
        TypeMembreJury typeMembreJury = (TypeMembreJury) o;
        if (typeMembreJury.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeMembreJury.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeMembreJury{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
