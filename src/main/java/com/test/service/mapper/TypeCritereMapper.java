package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.TypeCritereDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeCritere and its DTO TypeCritereDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeCritereMapper extends EntityMapper<TypeCritereDTO, TypeCritere> {


    @Mapping(target = "critereExamen", ignore = true)
    TypeCritere toEntity(TypeCritereDTO typeCritereDTO);

    default TypeCritere fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeCritere typeCritere = new TypeCritere();
        typeCritere.setId(id);
        return typeCritere;
    }
}
