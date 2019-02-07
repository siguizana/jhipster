package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.InscriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Inscription and its DTO InscriptionDTO.
 */
@Mapper(componentModel = "spring", uses = {VillageSecteurMapper.class, EtablissementMapper.class, SessionMapper.class, OptionConcoursRattacheMapper.class, CandidatMapper.class, TypeExamenMapper.class, CentreCompositioJuryMapper.class, SalleCompositionMapper.class, JuryMapper.class})
public interface InscriptionMapper extends EntityMapper<InscriptionDTO, Inscription> {

    @Mapping(source = "villageSecteur.id", target = "villageSecteurId")
    @Mapping(source = "etablissement.id", target = "etablissementId")
    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "optionConcoursRattache.id", target = "optionConcoursRattacheId")
    @Mapping(source = "candidat.id", target = "candidatId")
    @Mapping(source = "typeExamen.id", target = "typeExamenId")
    @Mapping(source = "centreCompositioJury.id", target = "centreCompositioJuryId")
    @Mapping(source = "salleComposition.id", target = "salleCompositionId")
    @Mapping(source = "jury.id", target = "juryId")
    InscriptionDTO toDto(Inscription inscription);

    @Mapping(target = "epreuveSpecialiteOptions", ignore = true)
    @Mapping(target = "choixEtablissements", ignore = true)
    @Mapping(target = "compositionCopies", ignore = true)
    @Mapping(target = "absences", ignore = true)
    @Mapping(target = "resultats", ignore = true)
    @Mapping(target = "dispenses", ignore = true)
    @Mapping(target = "fraudes", ignore = true)
    @Mapping(source = "villageSecteurId", target = "villageSecteur")
    @Mapping(source = "etablissementId", target = "etablissement")
    @Mapping(source = "sessionId", target = "session")
    @Mapping(source = "optionConcoursRattacheId", target = "optionConcoursRattache")
    @Mapping(source = "candidatId", target = "candidat")
    @Mapping(source = "typeExamenId", target = "typeExamen")
    @Mapping(source = "centreCompositioJuryId", target = "centreCompositioJury")
    @Mapping(source = "salleCompositionId", target = "salleComposition")
    @Mapping(source = "juryId", target = "jury")
    Inscription toEntity(InscriptionDTO inscriptionDTO);

    default Inscription fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inscription inscription = new Inscription();
        inscription.setId(id);
        return inscription;
    }
}
