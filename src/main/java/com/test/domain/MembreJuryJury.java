package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MembreJuryJury.
 */
@Entity
@Table(name = "membre_jury_jury")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MembreJuryJury implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fonction")
    private String fonction;

    @Column(name = "status")
    private String status;

    @Column(name = "experience")
    private String experience;

    @Column(name = "secteur")
    private String secteur;

    @Column(name = "quartier")
    private String quartier;

    @Column(name = "diplome_academique")
    private String diplomeAcademique;

    @Column(name = "diplome_professionnel")
    private String diplomeProfessionnel;

    @Column(name = "nom_chef_etablissement")
    private String nomChefEtablissement;

    @Column(name = "prenom_chef_etabissement")
    private String prenomChefEtabissement;

    @Column(name = "avis_chef_etablissement")
    private String avisChefEtablissement;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "membre_jury_jury_commission",
               joinColumns = @JoinColumn(name = "membre_jury_jury_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "commission_id", referencedColumnName = "id"))
    private Set<Commission> commissions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("membreJuryJuries")
    private Jury jury;

    @ManyToOne
    @JsonIgnoreProperties("membreJuryJuries")
    private MembreJury membreJury;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFonction() {
        return fonction;
    }

    public MembreJuryJury fonction(String fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getStatus() {
        return status;
    }

    public MembreJuryJury status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExperience() {
        return experience;
    }

    public MembreJuryJury experience(String experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSecteur() {
        return secteur;
    }

    public MembreJuryJury secteur(String secteur) {
        this.secteur = secteur;
        return this;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getQuartier() {
        return quartier;
    }

    public MembreJuryJury quartier(String quartier) {
        this.quartier = quartier;
        return this;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getDiplomeAcademique() {
        return diplomeAcademique;
    }

    public MembreJuryJury diplomeAcademique(String diplomeAcademique) {
        this.diplomeAcademique = diplomeAcademique;
        return this;
    }

    public void setDiplomeAcademique(String diplomeAcademique) {
        this.diplomeAcademique = diplomeAcademique;
    }

    public String getDiplomeProfessionnel() {
        return diplomeProfessionnel;
    }

    public MembreJuryJury diplomeProfessionnel(String diplomeProfessionnel) {
        this.diplomeProfessionnel = diplomeProfessionnel;
        return this;
    }

    public void setDiplomeProfessionnel(String diplomeProfessionnel) {
        this.diplomeProfessionnel = diplomeProfessionnel;
    }

    public String getNomChefEtablissement() {
        return nomChefEtablissement;
    }

    public MembreJuryJury nomChefEtablissement(String nomChefEtablissement) {
        this.nomChefEtablissement = nomChefEtablissement;
        return this;
    }

    public void setNomChefEtablissement(String nomChefEtablissement) {
        this.nomChefEtablissement = nomChefEtablissement;
    }

    public String getPrenomChefEtabissement() {
        return prenomChefEtabissement;
    }

    public MembreJuryJury prenomChefEtabissement(String prenomChefEtabissement) {
        this.prenomChefEtabissement = prenomChefEtabissement;
        return this;
    }

    public void setPrenomChefEtabissement(String prenomChefEtabissement) {
        this.prenomChefEtabissement = prenomChefEtabissement;
    }

    public String getAvisChefEtablissement() {
        return avisChefEtablissement;
    }

    public MembreJuryJury avisChefEtablissement(String avisChefEtablissement) {
        this.avisChefEtablissement = avisChefEtablissement;
        return this;
    }

    public void setAvisChefEtablissement(String avisChefEtablissement) {
        this.avisChefEtablissement = avisChefEtablissement;
    }

    public Set<Commission> getCommissions() {
        return commissions;
    }

    public MembreJuryJury commissions(Set<Commission> commissions) {
        this.commissions = commissions;
        return this;
    }

    public MembreJuryJury addCommission(Commission commission) {
        this.commissions.add(commission);
        commission.getMembreJuryJuries().add(this);
        return this;
    }

    public MembreJuryJury removeCommission(Commission commission) {
        this.commissions.remove(commission);
        commission.getMembreJuryJuries().remove(this);
        return this;
    }

    public void setCommissions(Set<Commission> commissions) {
        this.commissions = commissions;
    }

    public Jury getJury() {
        return jury;
    }

    public MembreJuryJury jury(Jury jury) {
        this.jury = jury;
        return this;
    }

    public void setJury(Jury jury) {
        this.jury = jury;
    }

    public MembreJury getMembreJury() {
        return membreJury;
    }

    public MembreJuryJury membreJury(MembreJury membreJury) {
        this.membreJury = membreJury;
        return this;
    }

    public void setMembreJury(MembreJury membreJury) {
        this.membreJury = membreJury;
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
        MembreJuryJury membreJuryJury = (MembreJuryJury) o;
        if (membreJuryJury.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), membreJuryJury.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MembreJuryJury{" +
            "id=" + getId() +
            ", fonction='" + getFonction() + "'" +
            ", status='" + getStatus() + "'" +
            ", experience='" + getExperience() + "'" +
            ", secteur='" + getSecteur() + "'" +
            ", quartier='" + getQuartier() + "'" +
            ", diplomeAcademique='" + getDiplomeAcademique() + "'" +
            ", diplomeProfessionnel='" + getDiplomeProfessionnel() + "'" +
            ", nomChefEtablissement='" + getNomChefEtablissement() + "'" +
            ", prenomChefEtabissement='" + getPrenomChefEtabissement() + "'" +
            ", avisChefEtablissement='" + getAvisChefEtablissement() + "'" +
            "}";
    }
}
