package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.ResultatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Resultat and its DTO ResultatDTO.
 */
@Mapper(componentModel = "spring", uses = {EtapeExamenMapper.class, MentionMapper.class, TypeDecisionMapper.class, OptionConcoursRattacheMapper.class, InscriptionMapper.class})
public interface ResultatMapper extends EntityMapper<ResultatDTO, Resultat> {

    @Mapping(source = "etapeExamen.id", target = "etapeExamenId")
    @Mapping(source = "mention.id", target = "mentionId")
    @Mapping(source = "typeDecision.id", target = "typeDecisionId")
    @Mapping(source = "optionConcoursRattache.id", target = "optionConcoursRattacheId")
    @Mapping(source = "inscription.id", target = "inscriptionId")
    ResultatDTO toDto(Resultat resultat);

    @Mapping(target = "retraitDiplomes", ignore = true)
    @Mapping(source = "etapeExamenId", target = "etapeExamen")
    @Mapping(source = "mentionId", target = "mention")
    @Mapping(source = "typeDecisionId", target = "typeDecision")
    @Mapping(source = "optionConcoursRattacheId", target = "optionConcoursRattache")
    @Mapping(source = "inscriptionId", target = "inscription")
    Resultat toEntity(ResultatDTO resultatDTO);

    default Resultat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Resultat resultat = new Resultat();
        resultat.setId(id);
        return resultat;
    }
}
