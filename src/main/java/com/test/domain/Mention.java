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
 * A Mention.
 */
@Entity
@Table(name = "mention")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mention implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "libelle_mention", nullable = false)
    private String libelleMention;

    @OneToMany(mappedBy = "mention")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Resultat> resultats = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleMention() {
        return libelleMention;
    }

    public Mention libelleMention(String libelleMention) {
        this.libelleMention = libelleMention;
        return this;
    }

    public void setLibelleMention(String libelleMention) {
        this.libelleMention = libelleMention;
    }

    public Set<Resultat> getResultats() {
        return resultats;
    }

    public Mention resultats(Set<Resultat> resultats) {
        this.resultats = resultats;
        return this;
    }

    public Mention addResultat(Resultat resultat) {
        this.resultats.add(resultat);
        resultat.setMention(this);
        return this;
    }

    public Mention removeResultat(Resultat resultat) {
        this.resultats.remove(resultat);
        resultat.setMention(null);
        return this;
    }

    public void setResultats(Set<Resultat> resultats) {
        this.resultats = resultats;
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
        Mention mention = (Mention) o;
        if (mention.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mention.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mention{" +
            "id=" + getId() +
            ", libelleMention='" + getLibelleMention() + "'" +
            "}";
    }
}
