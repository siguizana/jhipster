package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TypeMembreJury entity.
 */
public class TypeMembreJuryDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    private Set<CritereChoixMembreJuryDTO> critereChoixMembreJuries = new HashSet<>();

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

    public Set<CritereChoixMembreJuryDTO> getCritereChoixMembreJuries() {
        return critereChoixMembreJuries;
    }

    public void setCritereChoixMembreJuries(Set<CritereChoixMembreJuryDTO> critereChoixMembreJuries) {
        this.critereChoixMembreJuries = critereChoixMembreJuries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TypeMembreJuryDTO typeMembreJuryDTO = (TypeMembreJuryDTO) o;
        if (typeMembreJuryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeMembreJuryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeMembreJuryDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
