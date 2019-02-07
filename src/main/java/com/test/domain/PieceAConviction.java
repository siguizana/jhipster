package com.test.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PieceAConviction.
 */
@Entity
@Table(name = "piece_a_conviction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PieceAConviction implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_piece_aconviction", nullable = false)
    private String libellePieceAconviction;

    @ManyToOne
    @JsonIgnoreProperties("pieceAConvictions")
    private Fraude fraude;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibellePieceAconviction() {
        return libellePieceAconviction;
    }

    public PieceAConviction libellePieceAconviction(String libellePieceAconviction) {
        this.libellePieceAconviction = libellePieceAconviction;
        return this;
    }

    public void setLibellePieceAconviction(String libellePieceAconviction) {
        this.libellePieceAconviction = libellePieceAconviction;
    }

    public Fraude getFraude() {
        return fraude;
    }

    public PieceAConviction fraude(Fraude fraude) {
        this.fraude = fraude;
        return this;
    }

    public void setFraude(Fraude fraude) {
        this.fraude = fraude;
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
        PieceAConviction pieceAConviction = (PieceAConviction) o;
        if (pieceAConviction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pieceAConviction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PieceAConviction{" +
            "id=" + getId() +
            ", libellePieceAconviction='" + getLibellePieceAconviction() + "'" +
            "}";
    }
}
