package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.test.domain.enumeration.Sexe;

/**
 * A Candidat.
 */
@Entity
@Table(name = "candidat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Candidat implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_candidat")
    private String codeCandidat;

    @NotNull
    @Column(name = "nom_candidat", nullable = false)
    private String nomCandidat;

    @NotNull
    @Column(name = "prenom_candidat", nullable = false)
    private String prenomCandidat;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @NotNull
    @Column(name = "lieu_naissance", nullable = false)
    private String lieuNaissance;

    @NotNull
    @Column(name = "pays_naissance", nullable = false)
    private String paysNaissance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", nullable = false)
    private Sexe sexe;

    @Column(name = "telephone_candidat")
    private String telephoneCandidat;

    @NotNull
    @Column(name = "nationalite_candidat", nullable = false)
    private String nationaliteCandidat;

    @Column(name = "matricule_candidat")
    private String matriculeCandidat;

    @Column(name = "date_enregistrement_candidat")
    private LocalDate dateEnregistrementCandidat;

    @Column(name = "date_debut_cariere_candidat")
    private LocalDate dateDebutCariereCandidat;

    @OneToMany(mappedBy = "candidat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inscription> inscriptions = new HashSet<>();
    @OneToMany(mappedBy = "candidat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeroulementScolarite> deroulementScolarites = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCandidat() {
        return codeCandidat;
    }

    public Candidat codeCandidat(String codeCandidat) {
        this.codeCandidat = codeCandidat;
        return this;
    }

    public void setCodeCandidat(String codeCandidat) {
        this.codeCandidat = codeCandidat;
    }

    public String getNomCandidat() {
        return nomCandidat;
    }

    public Candidat nomCandidat(String nomCandidat) {
        this.nomCandidat = nomCandidat;
        return this;
    }

    public void setNomCandidat(String nomCandidat) {
        this.nomCandidat = nomCandidat;
    }

    public String getPrenomCandidat() {
        return prenomCandidat;
    }

    public Candidat prenomCandidat(String prenomCandidat) {
        this.prenomCandidat = prenomCandidat;
        return this;
    }

    public void setPrenomCandidat(String prenomCandidat) {
        this.prenomCandidat = prenomCandidat;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Candidat dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public Candidat lieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getPaysNaissance() {
        return paysNaissance;
    }

    public Candidat paysNaissance(String paysNaissance) {
        this.paysNaissance = paysNaissance;
        return this;
    }

    public void setPaysNaissance(String paysNaissance) {
        this.paysNaissance = paysNaissance;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public Candidat sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getTelephoneCandidat() {
        return telephoneCandidat;
    }

    public Candidat telephoneCandidat(String telephoneCandidat) {
        this.telephoneCandidat = telephoneCandidat;
        return this;
    }

    public void setTelephoneCandidat(String telephoneCandidat) {
        this.telephoneCandidat = telephoneCandidat;
    }

    public String getNationaliteCandidat() {
        return nationaliteCandidat;
    }

    public Candidat nationaliteCandidat(String nationaliteCandidat) {
        this.nationaliteCandidat = nationaliteCandidat;
        return this;
    }

    public void setNationaliteCandidat(String nationaliteCandidat) {
        this.nationaliteCandidat = nationaliteCandidat;
    }

    public String getMatriculeCandidat() {
        return matriculeCandidat;
    }

    public Candidat matriculeCandidat(String matriculeCandidat) {
        this.matriculeCandidat = matriculeCandidat;
        return this;
    }

    public void setMatriculeCandidat(String matriculeCandidat) {
        this.matriculeCandidat = matriculeCandidat;
    }

    public LocalDate getDateEnregistrementCandidat() {
        return dateEnregistrementCandidat;
    }

    public Candidat dateEnregistrementCandidat(LocalDate dateEnregistrementCandidat) {
        this.dateEnregistrementCandidat = dateEnregistrementCandidat;
        return this;
    }

    public void setDateEnregistrementCandidat(LocalDate dateEnregistrementCandidat) {
        this.dateEnregistrementCandidat = dateEnregistrementCandidat;
    }

    public LocalDate getDateDebutCariereCandidat() {
        return dateDebutCariereCandidat;
    }

    public Candidat dateDebutCariereCandidat(LocalDate dateDebutCariereCandidat) {
        this.dateDebutCariereCandidat = dateDebutCariereCandidat;
        return this;
    }

    public void setDateDebutCariereCandidat(LocalDate dateDebutCariereCandidat) {
        this.dateDebutCariereCandidat = dateDebutCariereCandidat;
    }

    public Set<Inscription> getInscriptions() {
        return inscriptions;
    }

    public Candidat inscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
        return this;
    }

    public Candidat addInscription(Inscription inscription) {
        this.inscriptions.add(inscription);
        inscription.setCandidat(this);
        return this;
    }

    public Candidat removeInscription(Inscription inscription) {
        this.inscriptions.remove(inscription);
        inscription.setCandidat(null);
        return this;
    }

    public void setInscriptions(Set<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public Set<DeroulementScolarite> getDeroulementScolarites() {
        return deroulementScolarites;
    }

    public Candidat deroulementScolarites(Set<DeroulementScolarite> deroulementScolarites) {
        this.deroulementScolarites = deroulementScolarites;
        return this;
    }

    public Candidat addDeroulementScolarite(DeroulementScolarite deroulementScolarite) {
        this.deroulementScolarites.add(deroulementScolarite);
        deroulementScolarite.setCandidat(this);
        return this;
    }

    public Candidat removeDeroulementScolarite(DeroulementScolarite deroulementScolarite) {
        this.deroulementScolarites.remove(deroulementScolarite);
        deroulementScolarite.setCandidat(null);
        return this;
    }

    public void setDeroulementScolarites(Set<DeroulementScolarite> deroulementScolarites) {
        this.deroulementScolarites = deroulementScolarites;
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
        Candidat candidat = (Candidat) o;
        if (candidat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Candidat{" +
            "id=" + getId() +
            ", codeCandidat='" + getCodeCandidat() + "'" +
            ", nomCandidat='" + getNomCandidat() + "'" +
            ", prenomCandidat='" + getPrenomCandidat() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", paysNaissance='" + getPaysNaissance() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", telephoneCandidat='" + getTelephoneCandidat() + "'" +
            ", nationaliteCandidat='" + getNationaliteCandidat() + "'" +
            ", matriculeCandidat='" + getMatriculeCandidat() + "'" +
            ", dateEnregistrementCandidat='" + getDateEnregistrementCandidat() + "'" +
            ", dateDebutCariereCandidat='" + getDateDebutCariereCandidat() + "'" +
            "}";
    }
}
