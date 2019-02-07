package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PieceAConviction entity.
 */
public class PieceAConvictionDTO implements Serializable {

    private Long id;

    @NotNull
    private String libellePieceAconviction;

    private Long fraudeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibellePieceAconviction() {
        return libellePieceAconviction;
    }

    public void setLibellePieceAconviction(String libellePieceAconviction) {
        this.libellePieceAconviction = libellePieceAconviction;
    }

    public Long getFraudeId() {
        return fraudeId;
    }

    public void setFraudeId(Long fraudeId) {
        this.fraudeId = fraudeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PieceAConvictionDTO pieceAConvictionDTO = (PieceAConvictionDTO) o;
        if (pieceAConvictionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pieceAConvictionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PieceAConvictionDTO{" +
            "id=" + getId() +
            ", libellePieceAconviction='" + getLibellePieceAconviction() + "'" +
            ", fraude=" + getFraudeId() +
            "}";
    }
}
