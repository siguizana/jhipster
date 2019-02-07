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
 * A CentreComposition.
 */
@Entity
@Table(name = "centre_composition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CentreComposition implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_centre_composition", nullable = false)
    private String libelleCentreComposition;

    @OneToMany(mappedBy = "centreComposition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CentreCompositioJury> centreCompositioJuries = new HashSet<>();
    @OneToMany(mappedBy = "centreComposition")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Etablissement> etablissements = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("centreCompositions")
    private Ceb ceb;

    @ManyToOne
    @JsonIgnoreProperties("centreCompositions")
    private ZoneExamen zoneExamen;

    @ManyToOne
    @JsonIgnoreProperties("centreCompositions")
    private TypeCentreComposition typeCentreComposition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleCentreComposition() {
        return libelleCentreComposition;
    }

    public CentreComposition libelleCentreComposition(String libelleCentreComposition) {
        this.libelleCentreComposition = libelleCentreComposition;
        return this;
    }

    public void setLibelleCentreComposition(String libelleCentreComposition) {
        this.libelleCentreComposition = libelleCentreComposition;
    }

    public Set<CentreCompositioJury> getCentreCompositioJuries() {
        return centreCompositioJuries;
    }

    public CentreComposition centreCompositioJuries(Set<CentreCompositioJury> centreCompositioJuries) {
        this.centreCompositioJuries = centreCompositioJuries;
        return this;
    }

    public CentreComposition addCentreCompositioJury(CentreCompositioJury centreCompositioJury) {
        this.centreCompositioJuries.add(centreCompositioJury);
        centreCompositioJury.setCentreComposition(this);
        return this;
    }

    public CentreComposition removeCentreCompositioJury(CentreCompositioJury centreCompositioJury) {
        this.centreCompositioJuries.remove(centreCompositioJury);
        centreCompositioJury.setCentreComposition(null);
        return this;
    }

    public void setCentreCompositioJuries(Set<CentreCompositioJury> centreCompositioJuries) {
        this.centreCompositioJuries = centreCompositioJuries;
    }

    public Set<Etablissement> getEtablissements() {
        return etablissements;
    }

    public CentreComposition etablissements(Set<Etablissement> etablissements) {
        this.etablissements = etablissements;
        return this;
    }

    public CentreComposition addEtablissement(Etablissement etablissement) {
        this.etablissements.add(etablissement);
        etablissement.setCentreComposition(this);
        return this;
    }

    public CentreComposition removeEtablissement(Etablissement etablissement) {
        this.etablissements.remove(etablissement);
        etablissement.setCentreComposition(null);
        return this;
    }

    public void setEtablissements(Set<Etablissement> etablissements) {
        this.etablissements = etablissements;
    }

    public Ceb getCeb() {
        return ceb;
    }

    public CentreComposition ceb(Ceb ceb) {
        this.ceb = ceb;
        return this;
    }

    public void setCeb(Ceb ceb) {
        this.ceb = ceb;
    }

    public ZoneExamen getZoneExamen() {
        return zoneExamen;
    }

    public CentreComposition zoneExamen(ZoneExamen zoneExamen) {
        this.zoneExamen = zoneExamen;
        return this;
    }

    public void setZoneExamen(ZoneExamen zoneExamen) {
        this.zoneExamen = zoneExamen;
    }

    public TypeCentreComposition getTypeCentreComposition() {
        return typeCentreComposition;
    }

    public CentreComposition typeCentreComposition(TypeCentreComposition typeCentreComposition) {
        this.typeCentreComposition = typeCentreComposition;
        return this;
    }

    public void setTypeCentreComposition(TypeCentreComposition typeCentreComposition) {
        this.typeCentreComposition = typeCentreComposition;
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
        CentreComposition centreComposition = (CentreComposition) o;
        if (centreComposition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), centreComposition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CentreComposition{" +
            "id=" + getId() +
            ", libelleCentreComposition='" + getLibelleCentreComposition() + "'" +
            "}";
    }
}
