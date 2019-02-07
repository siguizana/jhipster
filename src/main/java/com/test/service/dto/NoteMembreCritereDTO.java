package com.test.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the NoteMembreCritere entity.
 */
public class NoteMembreCritereDTO implements Serializable {

    private Long id;

    private Float valeurNote;

    private Long membreJuryId;

    private Long critereChoixMembreJuryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValeurNote() {
        return valeurNote;
    }

    public void setValeurNote(Float valeurNote) {
        this.valeurNote = valeurNote;
    }

    public Long getMembreJuryId() {
        return membreJuryId;
    }

    public void setMembreJuryId(Long membreJuryId) {
        this.membreJuryId = membreJuryId;
    }

    public Long getCritereChoixMembreJuryId() {
        return critereChoixMembreJuryId;
    }

    public void setCritereChoixMembreJuryId(Long critereChoixMembreJuryId) {
        this.critereChoixMembreJuryId = critereChoixMembreJuryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NoteMembreCritereDTO noteMembreCritereDTO = (NoteMembreCritereDTO) o;
        if (noteMembreCritereDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), noteMembreCritereDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NoteMembreCritereDTO{" +
            "id=" + getId() +
            ", valeurNote=" + getValeurNote() +
            ", membreJury=" + getMembreJuryId() +
            ", critereChoixMembreJury=" + getCritereChoixMembreJuryId() +
            "}";
    }
}
