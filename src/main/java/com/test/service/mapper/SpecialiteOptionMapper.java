package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.SpecialiteOptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SpecialiteOption and its DTO SpecialiteOptionDTO.
 */
@Mapper(componentModel = "spring", uses = {TypeExamenMapper.class, FiliereMapper.class})
public interface SpecialiteOptionMapper extends EntityMapper<SpecialiteOptionDTO, SpecialiteOption> {

    @Mapping(source = "typeExamen.id", target = "typeExamenId")
    @Mapping(source = "filiere.id", target = "filiereId")
    SpecialiteOptionDTO toDto(SpecialiteOption specialiteOption);

    @Mapping(target = "choixEtablissements", ignore = true)
    @Mapping(target = "epreuveSpecialiteOptions", ignore = true)
    @Mapping(source = "typeExamenId", target = "typeExamen")
    @Mapping(source = "filiereId", target = "filiere")
    SpecialiteOption toEntity(SpecialiteOptionDTO specialiteOptionDTO);

    default SpecialiteOption fromId(Long id) {
        if (id == null) {
            return null;
        }
        SpecialiteOption specialiteOption = new SpecialiteOption();
        specialiteOption.setId(id);
        return specialiteOption;
    }
}
