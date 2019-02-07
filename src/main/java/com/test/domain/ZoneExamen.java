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
 * A ZoneExamen.
 */
@Entity
@Table(name = "zone_examen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ZoneExamen implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_zone_examen")
    private String codeZoneExamen;

    @NotNull
    @Column(name = "libelle_zone_examen", nullable = false)
    private String libelleZoneExamen;

    @OneToMany(mappedBy = "zoneExamen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CentreComposition> centreCompositions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("zoneExamen")
    private CentreExamen centreExamen;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeZoneExamen() {
        return codeZoneExamen;
    }

    public ZoneExamen codeZoneExamen(String codeZoneExamen) {
        this.codeZoneExamen = codeZoneExamen;
        return this;
    }

    public void setCodeZoneExamen(String codeZoneExamen) {
        this.codeZoneExamen = codeZoneExamen;
    }

    public String getLibelleZoneExamen() {
        return libelleZoneExamen;
    }

    public ZoneExamen libelleZoneExamen(String libelleZoneExamen) {
        this.libelleZoneExamen = libelleZoneExamen;
        return this;
    }

    public void setLibelleZoneExamen(String libelleZoneExamen) {
        this.libelleZoneExamen = libelleZoneExamen;
    }

    public Set<CentreComposition> getCentreCompositions() {
        return centreCompositions;
    }

    public ZoneExamen centreCompositions(Set<CentreComposition> centreCompositions) {
        this.centreCompositions = centreCompositions;
        return this;
    }

    public ZoneExamen addCentreComposition(CentreComposition centreComposition) {
        this.centreCompositions.add(centreComposition);
        centreComposition.setZoneExamen(this);
        return this;
    }

    public ZoneExamen removeCentreComposition(CentreComposition centreComposition) {
        this.centreCompositions.remove(centreComposition);
        centreComposition.setZoneExamen(null);
        return this;
    }

    public void setCentreCompositions(Set<CentreComposition> centreCompositions) {
        this.centreCompositions = centreCompositions;
    }

    public CentreExamen getCentreExamen() {
        return centreExamen;
    }

    public ZoneExamen centreExamen(CentreExamen centreExamen) {
        this.centreExamen = centreExamen;
        return this;
    }

    public void setCentreExamen(CentreExamen centreExamen) {
        this.centreExamen = centreExamen;
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
        ZoneExamen zoneExamen = (ZoneExamen) o;
        if (zoneExamen.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zoneExamen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZoneExamen{" +
            "id=" + getId() +
            ", codeZoneExamen='" + getCodeZoneExamen() + "'" +
            ", libelleZoneExamen='" + getLibelleZoneExamen() + "'" +
            "}";
    }
}
