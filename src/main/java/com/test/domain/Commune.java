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

import com.test.domain.enumeration.TypeCommune;

/**
 * A Commune.
 */
@Entity
@Table(name = "commune")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commune implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_commune")
    private String codeCommune;

    @NotNull
    @Column(name = "libelle_commune", nullable = false)
    private String libelleCommune;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_commune", nullable = false)
    private TypeCommune typeCommune;

    @OneToMany(mappedBy = "commune")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VillageSecteur> villageSecteurs = new HashSet<>();
    @OneToMany(mappedBy = "commune")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ceb> cebs = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("communes")
    private Province province;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCommune() {
        return codeCommune;
    }

    public Commune codeCommune(String codeCommune) {
        this.codeCommune = codeCommune;
        return this;
    }

    public void setCodeCommune(String codeCommune) {
        this.codeCommune = codeCommune;
    }

    public String getLibelleCommune() {
        return libelleCommune;
    }

    public Commune libelleCommune(String libelleCommune) {
        this.libelleCommune = libelleCommune;
        return this;
    }

    public void setLibelleCommune(String libelleCommune) {
        this.libelleCommune = libelleCommune;
    }

    public TypeCommune getTypeCommune() {
        return typeCommune;
    }

    public Commune typeCommune(TypeCommune typeCommune) {
        this.typeCommune = typeCommune;
        return this;
    }

    public void setTypeCommune(TypeCommune typeCommune) {
        this.typeCommune = typeCommune;
    }

    public Set<VillageSecteur> getVillageSecteurs() {
        return villageSecteurs;
    }

    public Commune villageSecteurs(Set<VillageSecteur> villageSecteurs) {
        this.villageSecteurs = villageSecteurs;
        return this;
    }

    public Commune addVillageSecteur(VillageSecteur villageSecteur) {
        this.villageSecteurs.add(villageSecteur);
        villageSecteur.setCommune(this);
        return this;
    }

    public Commune removeVillageSecteur(VillageSecteur villageSecteur) {
        this.villageSecteurs.remove(villageSecteur);
        villageSecteur.setCommune(null);
        return this;
    }

    public void setVillageSecteurs(Set<VillageSecteur> villageSecteurs) {
        this.villageSecteurs = villageSecteurs;
    }

    public Set<Ceb> getCebs() {
        return cebs;
    }

    public Commune cebs(Set<Ceb> cebs) {
        this.cebs = cebs;
        return this;
    }

    public Commune addCeb(Ceb ceb) {
        this.cebs.add(ceb);
        ceb.setCommune(this);
        return this;
    }

    public Commune removeCeb(Ceb ceb) {
        this.cebs.remove(ceb);
        ceb.setCommune(null);
        return this;
    }

    public void setCebs(Set<Ceb> cebs) {
        this.cebs = cebs;
    }

    public Province getProvince() {
        return province;
    }

    public Commune province(Province province) {
        this.province = province;
        return this;
    }

    public void setProvince(Province province) {
        this.province = province;
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
        Commune commune = (Commune) o;
        if (commune.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commune.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Commune{" +
            "id=" + getId() +
            ", codeCommune='" + getCodeCommune() + "'" +
            ", libelleCommune='" + getLibelleCommune() + "'" +
            ", typeCommune='" + getTypeCommune() + "'" +
            "}";
    }
}
