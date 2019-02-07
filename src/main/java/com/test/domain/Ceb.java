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

import com.test.domain.enumeration.TypeCeb;

/**
 * A Ceb.
 */
@Entity
@Table(name = "ceb")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ceb implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code_ceb", nullable = false)
    private String codeCeb;

    @NotNull
    @Column(name = "libelle_ceb", nullable = false)
    private String libelleCeb;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_ceb", nullable = false)
    private TypeCeb typeCeb;

    @OneToMany(mappedBy = "ceb")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Etablissement> etablissements = new HashSet<>();
    @OneToMany(mappedBy = "ceb")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CentreComposition> centreCompositions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("cebs")
    private Commune commune;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCeb() {
        return codeCeb;
    }

    public Ceb codeCeb(String codeCeb) {
        this.codeCeb = codeCeb;
        return this;
    }

    public void setCodeCeb(String codeCeb) {
        this.codeCeb = codeCeb;
    }

    public String getLibelleCeb() {
        return libelleCeb;
    }

    public Ceb libelleCeb(String libelleCeb) {
        this.libelleCeb = libelleCeb;
        return this;
    }

    public void setLibelleCeb(String libelleCeb) {
        this.libelleCeb = libelleCeb;
    }

    public TypeCeb getTypeCeb() {
        return typeCeb;
    }

    public Ceb typeCeb(TypeCeb typeCeb) {
        this.typeCeb = typeCeb;
        return this;
    }

    public void setTypeCeb(TypeCeb typeCeb) {
        this.typeCeb = typeCeb;
    }

    public Set<Etablissement> getEtablissements() {
        return etablissements;
    }

    public Ceb etablissements(Set<Etablissement> etablissements) {
        this.etablissements = etablissements;
        return this;
    }

    public Ceb addEtablissement(Etablissement etablissement) {
        this.etablissements.add(etablissement);
        etablissement.setCeb(this);
        return this;
    }

    public Ceb removeEtablissement(Etablissement etablissement) {
        this.etablissements.remove(etablissement);
        etablissement.setCeb(null);
        return this;
    }

    public void setEtablissements(Set<Etablissement> etablissements) {
        this.etablissements = etablissements;
    }

    public Set<CentreComposition> getCentreCompositions() {
        return centreCompositions;
    }

    public Ceb centreCompositions(Set<CentreComposition> centreCompositions) {
        this.centreCompositions = centreCompositions;
        return this;
    }

    public Ceb addCentreComposition(CentreComposition centreComposition) {
        this.centreCompositions.add(centreComposition);
        centreComposition.setCeb(this);
        return this;
    }

    public Ceb removeCentreComposition(CentreComposition centreComposition) {
        this.centreCompositions.remove(centreComposition);
        centreComposition.setCeb(null);
        return this;
    }

    public void setCentreCompositions(Set<CentreComposition> centreCompositions) {
        this.centreCompositions = centreCompositions;
    }

    public Commune getCommune() {
        return commune;
    }

    public Ceb commune(Commune commune) {
        this.commune = commune;
        return this;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
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
        Ceb ceb = (Ceb) o;
        if (ceb.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ceb.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ceb{" +
            "id=" + getId() +
            ", codeCeb='" + getCodeCeb() + "'" +
            ", libelleCeb='" + getLibelleCeb() + "'" +
            ", typeCeb='" + getTypeCeb() + "'" +
            "}";
    }
}
