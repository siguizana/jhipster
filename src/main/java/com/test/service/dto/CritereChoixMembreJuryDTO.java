package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CritereChoixMembreJury entity.
 */
public class CritereChoixMembreJuryDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    @NotNull
    private Float noteParDefaut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Float getNoteParDefaut() {
        return noteParDefaut;
    }

    public void setNoteParDefaut(Float noteParDefaut) {
        this.noteParDefaut = noteParDefaut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CritereChoixMembreJuryDTO critereChoixMembreJuryDTO = (CritereChoixMembreJuryDTO) o;
        if (critereChoixMembreJuryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), critereChoixMembreJuryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CritereChoixMembreJuryDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", noteParDefaut=" + getNoteParDefaut() +
            "}";
    }
}
