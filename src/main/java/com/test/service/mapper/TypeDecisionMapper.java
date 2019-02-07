package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.TypeDecisionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeDecision and its DTO TypeDecisionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeDecisionMapper extends EntityMapper<TypeDecisionDTO, TypeDecision> {


    @Mapping(target = "resultats", ignore = true)
    TypeDecision toEntity(TypeDecisionDTO typeDecisionDTO);

    default TypeDecision fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeDecision typeDecision = new TypeDecision();
        typeDecision.setId(id);
        return typeDecision;
    }
}
