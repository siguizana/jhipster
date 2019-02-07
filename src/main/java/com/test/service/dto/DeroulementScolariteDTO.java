package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DeroulementScolarite entity.
 */
public class DeroulementScolariteDTO implements Serializable {

    private Long id;

    @NotNull
    private String anneScolarite;

    @NotNull
    private String classe;

    private Long candidatId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnneScolarite() {
        return anneScolarite;
    }

    public void setAnneScolarite(String anneScolarite) {
        this.anneScolarite = anneScolarite;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Long getCandidatId() {
        return candidatId;
    }

    public void setCandidatId(Long candidatId) {
        this.candidatId = candidatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeroulementScolariteDTO deroulementScolariteDTO = (DeroulementScolariteDTO) o;
        if (deroulementScolariteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deroulementScolariteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeroulementScolariteDTO{" +
            "id=" + getId() +
            ", anneScolarite='" + getAnneScolarite() + "'" +
            ", classe='" + getClasse() + "'" +
            ", candidat=" + getCandidatId() +
            "}";
    }
}
