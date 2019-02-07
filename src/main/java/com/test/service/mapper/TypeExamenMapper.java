package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.TypeExamenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeExamen and its DTO TypeExamenDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeExamenMapper extends EntityMapper<TypeExamenDTO, TypeExamen> {


    @Mapping(target = "etapeExamen", ignore = true)
    @Mapping(target = "concoursRattaches", ignore = true)
    @Mapping(target = "critereExamen", ignore = true)
    @Mapping(target = "specialiteOptions", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    TypeExamen toEntity(TypeExamenDTO typeExamenDTO);

    default TypeExamen fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeExamen typeExamen = new TypeExamen();
        typeExamen.setId(id);
        return typeExamen;
    }
}
