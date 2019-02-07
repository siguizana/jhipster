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
 * A Region.
 */
@Entity
@Table(name = "region")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_region")
    private String codeRegion;

    @NotNull
    @Column(name = "libelle_region", nullable = false)
    private String libelleRegion;

    @OneToMany(mappedBy = "region")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Province> provinces = new HashSet<>();
    @OneToMany(mappedBy = "region")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CritereExamen> critereExamen = new HashSet<>();
    @OneToMany(mappedBy = "region")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CentreExamen> centreExamen = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeRegion() {
        return codeRegion;
    }

    public Region codeRegion(String codeRegion) {
        this.codeRegion = codeRegion;
        return this;
    }

    public void setCodeRegion(String codeRegion) {
        this.codeRegion = codeRegion;
    }

    public String getLibelleRegion() {
        return libelleRegion;
    }

    public Region libelleRegion(String libelleRegion) {
        this.libelleRegion = libelleRegion;
        return this;
    }

    public void setLibelleRegion(String libelleRegion) {
        this.libelleRegion = libelleRegion;
    }

    public Set<Province> getProvinces() {
        return provinces;
    }

    public Region provinces(Set<Province> provinces) {
        this.provinces = provinces;
        return this;
    }

    public Region addProvince(Province province) {
        this.provinces.add(province);
        province.setRegion(this);
        return this;
    }

    public Region removeProvince(Province province) {
        this.provinces.remove(province);
        province.setRegion(null);
        return this;
    }

    public void setProvinces(Set<Province> provinces) {
        this.provinces = provinces;
    }

    public Set<CritereExamen> getCritereExamen() {
        return critereExamen;
    }

    public Region critereExamen(Set<CritereExamen> critereExamen) {
        this.critereExamen = critereExamen;
        return this;
    }

    public Region addCritereExamen(CritereExamen critereExamen) {
        this.critereExamen.add(critereExamen);
        critereExamen.setRegion(this);
        return this;
    }

    public Region removeCritereExamen(CritereExamen critereExamen) {
        this.critereExamen.remove(critereExamen);
        critereExamen.setRegion(null);
        return this;
    }

    public void setCritereExamen(Set<CritereExamen> critereExamen) {
        this.critereExamen = critereExamen;
    }

    public Set<CentreExamen> getCentreExamen() {
        return centreExamen;
    }

    public Region centreExamen(Set<CentreExamen> centreExamen) {
        this.centreExamen = centreExamen;
        return this;
    }

    public Region addCentreExamen(CentreExamen centreExamen) {
        this.centreExamen.add(centreExamen);
        centreExamen.setRegion(this);
        return this;
    }

    public Region removeCentreExamen(CentreExamen centreExamen) {
        this.centreExamen.remove(centreExamen);
        centreExamen.setRegion(null);
        return this;
    }

    public void setCentreExamen(Set<CentreExamen> centreExamen) {
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
        Region region = (Region) o;
        if (region.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), region.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", codeRegion='" + getCodeRegion() + "'" +
            ", libelleRegion='" + getLibelleRegion() + "'" +
            "}";
    }
}
