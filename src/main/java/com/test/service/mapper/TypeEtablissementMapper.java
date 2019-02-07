package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.TypeEtablissementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeEtablissement and its DTO TypeEtablissementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeEtablissementMapper extends EntityMapper<TypeEtablissementDTO, TypeEtablissement> {


    @Mapping(target = "etablissements", ignore = true)
    TypeEtablissement toEntity(TypeEtablissementDTO typeEtablissementDTO);

    default TypeEtablissement fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeEtablissement typeEtablissement = new TypeEtablissement();
        typeEtablissement.setId(id);
        return typeEtablissement;
    }
}
