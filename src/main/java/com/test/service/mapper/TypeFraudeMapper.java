package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.TypeFraudeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeFraude and its DTO TypeFraudeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeFraudeMapper extends EntityMapper<TypeFraudeDTO, TypeFraude> {


    @Mapping(target = "fraudes", ignore = true)
    TypeFraude toEntity(TypeFraudeDTO typeFraudeDTO);

    default TypeFraude fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeFraude typeFraude = new TypeFraude();
        typeFraude.setId(id);
        return typeFraude;
    }
}
