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
 * A CentreExamen.
 */
@Entity
@Table(name = "centre_examen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CentreExamen implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_centre_examen", nullable = false)
    private String libelleCentreExamen;

    @OneToMany(mappedBy = "centreExamen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ZoneExamen> zoneExamen = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("centreExamen")
    private Region region;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleCentreExamen() {
        return libelleCentreExamen;
    }

    public CentreExamen libelleCentreExamen(String libelleCentreExamen) {
        this.libelleCentreExamen = libelleCentreExamen;
        return this;
    }

    public void setLibelleCentreExamen(String libelleCentreExamen) {
        this.libelleCentreExamen = libelleCentreExamen;
    }

    public Set<ZoneExamen> getZoneExamen() {
        return zoneExamen;
    }

    public CentreExamen zoneExamen(Set<ZoneExamen> zoneExamen) {
        this.zoneExamen = zoneExamen;
        return this;
    }

    public CentreExamen addZoneExamen(ZoneExamen zoneExamen) {
        this.zoneExamen.add(zoneExamen);
        zoneExamen.setCentreExamen(this);
        return this;
    }

    public CentreExamen removeZoneExamen(ZoneExamen zoneExamen) {
        this.zoneExamen.remove(zoneExamen);
        zoneExamen.setCentreExamen(null);
        return this;
    }

    public void setZoneExamen(Set<ZoneExamen> zoneExamen) {
        this.zoneExamen = zoneExamen;
    }

    public Region getRegion() {
        return region;
    }

    public CentreExamen region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
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
        CentreExamen centreExamen = (CentreExamen) o;
        if (centreExamen.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), centreExamen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CentreExamen{" +
            "id=" + getId() +
            ", libelleCentreExamen='" + getLibelleCentreExamen() + "'" +
            "}";
    }
}
