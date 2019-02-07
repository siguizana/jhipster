package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.TypeMembreJuryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeMembreJury and its DTO TypeMembreJuryDTO.
 */
@Mapper(componentModel = "spring", uses = {CritereChoixMembreJuryMapper.class})
public interface TypeMembreJuryMapper extends EntityMapper<TypeMembreJuryDTO, TypeMembreJury> {


    @Mapping(target = "membreJuries", ignore = true)
    TypeMembreJury toEntity(TypeMembreJuryDTO typeMembreJuryDTO);

    default TypeMembreJury fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeMembreJury typeMembreJury = new TypeMembreJury();
        typeMembreJury.setId(id);
        return typeMembreJury;
    }
}
