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
 * A Etablissement.
 */
@Entity
@Table(name = "etablissement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Etablissement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_etablissement")
    private String codeEtablissement;

    @NotNull
    @Column(name = "libelle_etablissement", nullable = false)
    private String libelleEtablissement;

    @NotNull
    @Column(name = "nom_responsable_etablissement", nullable = false)
    private String nomResponsableEtablissement;

    @NotNull
    @Column(name = "prenom_responsable_etablissement", nullable = false)
    private String prenomResponsableEtablissement;

    @NotNull
    @Column(name = "contactact_etablissement", nullable = false)
    private String contactactEtablissement;

    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Enseigne> enseignes = new HashSet<>();
    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inscription> inscriptions = new HashSet<>();
    @OneToMany(mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChoixEtablissement> choixEtablissements = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("etablissements")
    private Ceb ceb;

    @ManyToOne
    @JsonIgnoreProperties("etablissements")
    private TypeEtablissement typeEtablissement;

    @ManyToOne
    @JsonIgnoreProperties("etablissements")
    private VillageSecteur villageSecteur;

    @ManyToOne
    @JsonIgnoreProperties("etablissements")
    private CentreComposition centreComposition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeEtablissement() {
        return codeEtablissement;
    }

    public Etablissement codeEtablissement(String codeEtablissement) {
        this.codeEtablissement = codeEtablissement;
        return this;
    }

    public void setCodeEtablissement(String codeEtablissement) {
        this.codeEtablissement = codeEtablissement;
    }

    public String getLibelleEtablissement() {
        return libelleEtablissement;
    }

    public Etablissement libelleEtablissement(String libelleEtablissement) {
        this.libelleEtablissement = libelleEtablissement;
        return this;
    }

    public void setLibelleEtablissement(String libelleEtablissement) {
        this.libelleEtablissement = libelleEtablissement;
    }

    public String getNomResponsableEtablissement() {
        return nomResponsableEtablissement;
    }

    public Etablissement nomResponsableEtablissement(String nomResponsableEtablissement) {
        this.nomResponsableEtablissement = nomResponsableEtablissement;
        return this;
    }

    public void setNomResponsableEtablissement(String nomResponsableEtablissement) {
        this.nomResponsableEtablissement = nomResponsableEtablissement;
    }

    public String getPrenomResponsableEtablissement() {
        return prenomResponsableEtablissement;
    }

    public Etablissement prenomResponsableEtablissement(String prenomResponsableEtablissement) {
        this.prenomResponsableEtablissement = prenomResponsableEtablissement;
        return this;
    }

    public void setPrenomResponsableEtablissement(String prenomResponsableEtablissement) {
        this.prenomResponsableEtablissement = prenomResponsableEtablissement;
    }

    public String getContactactEtablissement() {
        return contactactEtablissement;
    }

    public Etablissement contactactEtablissement(String contactactEtablissement) {
        this.contactactEtablissement = contactactEtablissement;
        return this;
    }

    public void setContactactEtablissement(String contactactEtablissement) {
        this.contactactEtablissement = contactactEtablissement;
    }

    public Set<Enseigne> getEnseignes() {
        return enseignes;
    }

    public Etablissement enseignes(Set<Enseigne> enseignes) {
        this.enseignes = enseignes;
        return this;
    }

    public Etablissement addEnseigne(Enseigne enseigne) {
        this.enseignes.add(enseigne);
        enseigne.setEtablissement(this);
        return this;
    }

    public Etablissement removeEnseigne(Enseigne enseigne) {
        this.enseignes.remove(enseigne);
        enseigne.setEtablissement(null);
        return this;
    }

    public void setEnseignes(Set<Enseigne> enseignes) {
        this.enseignes = enseignes;
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public Etablissement inscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
        return this;
    }

    public Etablissement addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setEtablissement(this);
        return this;
    }

    public Etablissement removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setEtablissement(null);
        return this;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public Set<ChoixEtablissement> getChoixEtablissements() {
        return choixEtablissements;
    }

    public Etablissement choixEtablissements(Set<ChoixEtablissement> choixEtablissements) {
        this.choixEtablissements = choixEtablissements;
        return this;
    }

    public Etablissement addChoixEtablissement(ChoixEtablissement choixEtablissement) {
        this.choixEtablissements.add(choixEtablissement);
        choixEtablissement.setEtablissement(this);
        return this;
    }

    public Etablissement removeChoixEtablissement(ChoixEtablissement choixEtablissement) {
        this.choixEtablissements.remove(choixEtablissement);
        choixEtablissement.setEtablissement(null);
        return this;
    }

    public void setChoixEtablissements(Set<ChoixEtablissement> choixEtablissements) {
        this.choixEtablissements = choixEtablissements;
    }

    public Ceb getCeb() {
        return ceb;
    }

    public Etablissement ceb(Ceb ceb) {
        this.ceb = ceb;
        return this;
    }

    public void setCeb(Ceb ceb) {
        this.ceb = ceb;
    }

    public TypeEtablissement getTypeEtablissement() {
        return typeEtablissement;
    }

    public Etablissement typeEtablissement(TypeEtablissement typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
        return this;
    }

    public void setTypeEtablissement(TypeEtablissement typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
    }

    public VillageSecteur getVillageSecteur() {
        return villageSecteur;
    }

    public Etablissement villageSecteur(VillageSecteur villageSecteur) {
        this.villageSecteur = villageSecteur;
        return this;
    }

    public void setVillageSecteur(VillageSecteur villageSecteur) {
        this.villageSecteur = villageSecteur;
    }

    public CentreComposition getCentreComposition() {
        return centreComposition;
    }

    public Etablissement centreComposition(CentreComposition centreComposition) {
        this.centreComposition = centreComposition;
        return this;
    }

    public void setCentreComposition(CentreComposition centreComposition) {
        this.centreComposition = centreComposition;
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
        Etablissement etablissement = (Etablissement) o;
        if (etablissement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etablissement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Etablissement{" +
            "id=" + getId() +
            ", codeEtablissement='" + getCodeEtablissement() + "'" +
            ", libelleEtablissement='" + getLibelleEtablissement() + "'" +
            ", nomResponsableEtablissement='" + getNomResponsableEtablissement() + "'" +
            ", prenomResponsableEtablissement='" + getPrenomResponsableEtablissement() + "'" +
            ", contactactEtablissement='" + getContactactEtablissement() + "'" +
            "}";
    }
}
