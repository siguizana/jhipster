package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.TypeEpreuveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeEpreuve and its DTO TypeEpreuveDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeEpreuveMapper extends EntityMapper<TypeEpreuveDTO, TypeEpreuve> {


    @Mapping(target = "epreuves", ignore = true)
    TypeEpreuve toEntity(TypeEpreuveDTO typeEpreuveDTO);

    default TypeEpreuve fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeEpreuve typeEpreuve = new TypeEpreuve();
        typeEpreuve.setId(id);
        return typeEpreuve;
    }
}
