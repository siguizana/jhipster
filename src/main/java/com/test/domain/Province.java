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
 * A Province.
 */
@Entity
@Table(name = "province")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Province implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_province")
    private String codeProvince;

    @NotNull
    @Column(name = "libelle_province", nullable = false)
    private String libelleProvince;

    @OneToMany(mappedBy = "province")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commune> communes = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("provinces")
    private Region region;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeProvince() {
        return codeProvince;
    }

    public Province codeProvince(String codeProvince) {
        this.codeProvince = codeProvince;
        return this;
    }

    public void setCodeProvince(String codeProvince) {
        this.codeProvince = codeProvince;
    }

    public String getLibelleProvince() {
        return libelleProvince;
    }

    public Province libelleProvince(String libelleProvince) {
        this.libelleProvince = libelleProvince;
        return this;
    }

    public void setLibelleProvince(String libelleProvince) {
        this.libelleProvince = libelleProvince;
    }

    public Set<Commune> getCommunes() {
        return communes;
    }

    public Province communes(Set<Commune> communes) {
        this.communes = communes;
        return this;
    }

    public Province addCommune(Commune commune) {
        this.communes.add(commune);
        commune.setProvince(this);
        return this;
    }

    public Province removeCommune(Commune commune) {
        this.communes.remove(commune);
        commune.setProvince(null);
        return this;
    }

    public void setCommunes(Set<Commune> communes) {
        this.communes = communes;
    }

    public Region getRegion() {
        return region;
    }

    public Province region(Region region) {
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
        Province province = (Province) o;
        if (province.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), province.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Province{" +
            "id=" + getId() +
            ", codeProvince='" + getCodeProvince() + "'" +
            ", libelleProvince='" + getLibelleProvince() + "'" +
            "}";
    }
}
