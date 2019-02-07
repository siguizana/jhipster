package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.MembreJuryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MembreJury and its DTO MembreJuryDTO.
 */
@Mapper(componentModel = "spring", uses = {FraudeMapper.class, CompositionCopieMapper.class, TypeMembreJuryMapper.class})
public interface MembreJuryMapper extends EntityMapper<MembreJuryDTO, MembreJury> {

    @Mapping(source = "typeMembreJury.id", target = "typeMembreJuryId")
    MembreJuryDTO toDto(MembreJury membreJury);

    @Mapping(target = "surveilles", ignore = true)
    @Mapping(target = "noteMembreCriteres", ignore = true)
    @Mapping(target = "membreJuryJuries", ignore = true)
    @Mapping(source = "typeMembreJuryId", target = "typeMembreJury")
    MembreJury toEntity(MembreJuryDTO membreJuryDTO);

    default MembreJury fromId(Long id) {
        if (id == null) {
            return null;
        }
        MembreJury membreJury = new MembreJury();
        membreJury.setId(id);
        return membreJury;
    }
}
