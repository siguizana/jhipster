package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.EpreuveSpecialiteOptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EpreuveSpecialiteOption and its DTO EpreuveSpecialiteOptionDTO.
 */
@Mapper(componentModel = "spring", uses = {InscriptionMapper.class, EpreuveMapper.class, SpecialiteOptionMapper.class, GroupeEpreuveMapper.class})
public interface EpreuveSpecialiteOptionMapper extends EntityMapper<EpreuveSpecialiteOptionDTO, EpreuveSpecialiteOption> {

    @Mapping(source = "inscription.id", target = "inscriptionId")
    @Mapping(source = "epreuve.id", target = "epreuveId")
    @Mapping(source = "specialiteOption.id", target = "specialiteOptionId")
    @Mapping(source = "groupeEpreuve.id", target = "groupeEpreuveId")
    EpreuveSpecialiteOptionDTO toDto(EpreuveSpecialiteOption epreuveSpecialiteOption);

    @Mapping(target = "compositionCopies", ignore = true)
    @Mapping(target = "absences", ignore = true)
    @Mapping(source = "inscriptionId", target = "inscription")
    @Mapping(source = "epreuveId", target = "epreuve")
    @Mapping(source = "specialiteOptionId", target = "specialiteOption")
    @Mapping(source = "groupeEpreuveId", target = "groupeEpreuve")
    @Mapping(target = "dispenses", ignore = true)
    EpreuveSpecialiteOption toEntity(EpreuveSpecialiteOptionDTO epreuveSpecialiteOptionDTO);

    default EpreuveSpecialiteOption fromId(Long id) {
        if (id == null) {
            return null;
        }
        EpreuveSpecialiteOption epreuveSpecialiteOption = new EpreuveSpecialiteOption();
        epreuveSpecialiteOption.setId(id);
        return epreuveSpecialiteOption;
    }
}
