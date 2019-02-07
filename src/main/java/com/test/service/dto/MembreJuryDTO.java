package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MembreJury entity.
 */
public class MembreJuryDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomMembre;

    @NotNull
    private String prenomMembre;

    @NotNull
    private String numeroCNIB;

    private String matricule;

    private Set<FraudeDTO> fraudes = new HashSet<>();

    private Set<CompositionCopieDTO> compositionCopies = new HashSet<>();

    private Long typeMembreJuryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomMembre() {
        return nomMembre;
    }

    public void setNomMembre(String nomMembre) {
        this.nomMembre = nomMembre;
    }

    public String getPrenomMembre() {
        return prenomMembre;
    }

    public void setPrenomMembre(String prenomMembre) {
        this.prenomMembre = prenomMembre;
    }

    public String getNumeroCNIB() {
        return numeroCNIB;
    }

    public void setNumeroCNIB(String numeroCNIB) {
        this.numeroCNIB = numeroCNIB;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Set<FraudeDTO> getFraudes() {
        return fraudes;
    }

    public void setFraudes(Set<FraudeDTO> fraudes) {
        this.fraudes = fraudes;
    }

    public Set<CompositionCopieDTO> getCompositionCopies() {
        return compositionCopies;
    }

    public void setCompositionCopies(Set<CompositionCopieDTO> compositionCopies) {
        this.compositionCopies = compositionCopies;
    }

    public Long getTypeMembreJuryId() {
        return typeMembreJuryId;
    }

    public void setTypeMembreJuryId(Long typeMembreJuryId) {
        this.typeMembreJuryId = typeMembreJuryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MembreJuryDTO membreJuryDTO = (MembreJuryDTO) o;
        if (membreJuryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), membreJuryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MembreJuryDTO{" +
            "id=" + getId() +
            ", nomMembre='" + getNomMembre() + "'" +
            ", prenomMembre='" + getPrenomMembre() + "'" +
            ", numeroCNIB='" + getNumeroCNIB() + "'" +
            ", matricule='" + getMatricule() + "'" +
            ", typeMembreJury=" + getTypeMembreJuryId() +
            "}";
    }
}
