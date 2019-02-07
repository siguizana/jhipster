package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.TypeDiplomeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeDiplome and its DTO TypeDiplomeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeDiplomeMapper extends EntityMapper<TypeDiplomeDTO, TypeDiplome> {


    @Mapping(target = "retraitDiplomes", ignore = true)
    TypeDiplome toEntity(TypeDiplomeDTO typeDiplomeDTO);

    default TypeDiplome fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeDiplome typeDiplome = new TypeDiplome();
        typeDiplome.setId(id);
        return typeDiplome;
    }
}
