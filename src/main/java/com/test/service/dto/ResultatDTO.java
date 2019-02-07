package com.test.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Resultat entity.
 */
public class ResultatDTO implements Serializable {

    private Long id;

    @NotNull
    private Float notePondere;

    private Long etapeExamenId;

    private Long mentionId;

    private Long typeDecisionId;

    private Long optionConcoursRattacheId;

    private Long inscriptionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getNotePondere() {
        return notePondere;
    }

    public void setNotePondere(Float notePondere) {
        this.notePondere = notePondere;
    }

    public Long getEtapeExamenId() {
        return etapeExamenId;
    }

    public void setEtapeExamenId(Long etapeExamenId) {
        this.etapeExamenId = etapeExamenId;
    }

    public Long getMentionId() {
        return mentionId;
    }

    public void setMentionId(Long mentionId) {
        this.mentionId = mentionId;
    }

    public Long getTypeDecisionId() {
        return typeDecisionId;
    }

    public void setTypeDecisionId(Long typeDecisionId) {
        this.typeDecisionId = typeDecisionId;
    }

    public Long getOptionConcoursRattacheId() {
        return optionConcoursRattacheId;
    }

    public void setOptionConcoursRattacheId(Long optionConcoursRattacheId) {
        this.optionConcoursRattacheId = optionConcoursRattacheId;
    }

    public Long getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResultatDTO resultatDTO = (ResultatDTO) o;
        if (resultatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResultatDTO{" +
            "id=" + getId() +
            ", notePondere=" + getNotePondere() +
            ", etapeExamen=" + getEtapeExamenId() +
            ", mention=" + getMentionId() +
            ", typeDecision=" + getTypeDecisionId() +
            ", optionConcoursRattache=" + getOptionConcoursRattacheId() +
            ", inscription=" + getInscriptionId() +
            "}";
    }
}
