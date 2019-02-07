package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.EtablissementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Etablissement and its DTO EtablissementDTO.
 */
@Mapper(componentModel = "spring", uses = {CebMapper.class, TypeEtablissementMapper.class, VillageSecteurMapper.class, CentreCompositionMapper.class})
public interface EtablissementMapper extends EntityMapper<EtablissementDTO, Etablissement> {

    @Mapping(source = "ceb.id", target = "cebId")
    @Mapping(source = "typeEtablissement.id", target = "typeEtablissementId")
    @Mapping(source = "villageSecteur.id", target = "villageSecteurId")
    @Mapping(source = "centreComposition.id", target = "centreCompositionId")
    EtablissementDTO toDto(Etablissement etablissement);

    @Mapping(target = "enseignes", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(target = "choixEtablissements", ignore = true)
    @Mapping(source = "cebId", target = "ceb")
    @Mapping(source = "typeEtablissementId", target = "typeEtablissement")
    @Mapping(source = "villageSecteurId", target = "villageSecteur")
    @Mapping(source = "centreCompositionId", target = "centreComposition")
    Etablissement toEntity(EtablissementDTO etablissementDTO);

    default Etablissement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Etablissement etablissement = new Etablissement();
        etablissement.setId(id);
        return etablissement;
    }
}
