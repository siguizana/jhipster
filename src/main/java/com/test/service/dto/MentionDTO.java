package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Mention entity.
 */
public class MentionDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelleMention;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleMention() {
        return libelleMention;
    }

    public void setLibelleMention(String libelleMention) {
        this.libelleMention = libelleMention;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MentionDTO mentionDTO = (MentionDTO) o;
        if (mentionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mentionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MentionDTO{" +
            "id=" + getId() +
            ", libelleMention='" + getLibelleMention() + "'" +
            "}";
    }
}
