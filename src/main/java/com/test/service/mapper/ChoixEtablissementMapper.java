package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.ChoixEtablissementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ChoixEtablissement and its DTO ChoixEtablissementDTO.
 */
@Mapper(componentModel = "spring", uses = {InscriptionMapper.class, EtablissementMapper.class, SpecialiteOptionMapper.class})
public interface ChoixEtablissementMapper extends EntityMapper<ChoixEtablissementDTO, ChoixEtablissement> {

    @Mapping(source = "inscription.id", target = "inscriptionId")
    @Mapping(source = "etablissement.id", target = "etablissementId")
    @Mapping(source = "specialiteOption.id", target = "specialiteOptionId")
    ChoixEtablissementDTO toDto(ChoixEtablissement choixEtablissement);

    @Mapping(source = "inscriptionId", target = "inscription")
    @Mapping(source = "etablissementId", target = "etablissement")
    @Mapping(source = "specialiteOptionId", target = "specialiteOption")
    ChoixEtablissement toEntity(ChoixEtablissementDTO choixEtablissementDTO);

    default ChoixEtablissement fromId(Long id) {
        if (id == null) {
            return null;
        }
        ChoixEtablissement choixEtablissement = new ChoixEtablissement();
        choixEtablissement.setId(id);
        return choixEtablissement;
    }
}
