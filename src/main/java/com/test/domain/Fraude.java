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
 * A Fraude.
 */
@Entity
@Table(name = "fraude")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fraude implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_fraude", nullable = false)
    private String libelleFraude;

    @OneToMany(mappedBy = "fraude")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Sanction> sanctions = new HashSet<>();
    @OneToMany(mappedBy = "fraude")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PieceAConviction> pieceAConvictions = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("fraudes")
    private TypeFraude typeFraude;

    @ManyToOne
    @JsonIgnoreProperties("fraudes")
    private Inscription inscription;

    @ManyToMany(mappedBy = "fraudes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<MembreJury> membreJuries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleFraude() {
        return libelleFraude;
    }

    public Fraude libelleFraude(String libelleFraude) {
        this.libelleFraude = libelleFraude;
        return this;
    }

    public void setLibelleFraude(String libelleFraude) {
        this.libelleFraude = libelleFraude;
    }

    public Set<Sanction> getSanctions() {
        return sanctions;
    }

    public Fraude sanctions(Set<Sanction> sanctions) {
        this.sanctions = sanctions;
        return this;
    }

    public Fraude addSanction(Sanction sanction) {
        this.sanctions.add(sanction);
        sanction.setFraude(this);
        return this;
    }

    public Fraude removeSanction(Sanction sanction) {
        this.sanctions.remove(sanction);
        sanction.setFraude(null);
        return this;
    }

    public void setSanctions(Set<Sanction> sanctions) {
        this.sanctions = sanctions;
    }

    public Set<PieceAConviction> getPieceAConvictions() {
        return pieceAConvictions;
    }

    public Fraude pieceAConvictions(Set<PieceAConviction> pieceAConvictions) {
        this.pieceAConvictions = pieceAConvictions;
        return this;
    }

    public Fraude addPieceAConviction(PieceAConviction pieceAConviction) {
        this.pieceAConvictions.add(pieceAConviction);
        pieceAConviction.setFraude(this);
        return this;
    }

    public Fraude removePieceAConviction(PieceAConviction pieceAConviction) {
        this.pieceAConvictions.remove(pieceAConviction);
        pieceAConviction.setFraude(null);
        return this;
    }

    public void setPieceAConvictions(Set<PieceAConviction> pieceAConvictions) {
        this.pieceAConvictions = pieceAConvictions;
    }

    public TypeFraude getTypeFraude() {
        return typeFraude;
    }

    public Fraude typeFraude(TypeFraude typeFraude) {
        this.typeFraude = typeFraude;
        return this;
    }

    public void setTypeFraude(TypeFraude typeFraude) {
        this.typeFraude = typeFraude;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public Fraude inscription(Inscription inscription) {
        this.inscription = inscription;
        return this;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Set<MembreJury> getMembreJuries() {
        return membreJuries;
    }

    public Fraude membreJuries(Set<MembreJury> membreJuries) {
        this.membreJuries = membreJuries;
        return this;
    }

    public Fraude addMembreJury(MembreJury membreJury) {
        this.membreJuries.add(membreJury);
        membreJury.getFraudes().add(this);
        return this;
    }

    public Fraude removeMembreJury(MembreJury membreJury) {
        this.membreJuries.remove(membreJury);
        membreJury.getFraudes().remove(this);
        return this;
    }

    public void setMembreJuries(Set<MembreJury> membreJuries) {
        this.membreJuries = membreJuries;
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
        Fraude fraude = (Fraude) o;
        if (fraude.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fraude.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fraude{" +
            "id=" + getId() +
            ", libelleFraude='" + getLibelleFraude() + "'" +
            "}";
    }
}
