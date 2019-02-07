package com.test.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CentreCompositioJury entity.
 */
public class CentreCompositioJuryDTO implements Serializable {

    private Long id;

    private Long centreCompositionId;

    private Long juryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCentreCompositionId() {
        return centreCompositionId;
    }

    public void setCentreCompositionId(Long centreCompositionId) {
        this.centreCompositionId = centreCompositionId;
    }

    public Long getJuryId() {
        return juryId;
    }

    public void setJuryId(Long juryId) {
        this.juryId = juryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CentreCompositioJuryDTO centreCompositioJuryDTO = (CentreCompositioJuryDTO) o;
        if (centreCompositioJuryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), centreCompositioJuryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CentreCompositioJuryDTO{" +
            "id=" + getId() +
            ", centreComposition=" + getCentreCompositionId() +
            ", jury=" + getJuryId() +
            "}";
    }
}
