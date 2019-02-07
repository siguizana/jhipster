package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.MembreJuryJuryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MembreJuryJury and its DTO MembreJuryJuryDTO.
 */
@Mapper(componentModel = "spring", uses = {CommissionMapper.class, JuryMapper.class, MembreJuryMapper.class})
public interface MembreJuryJuryMapper extends EntityMapper<MembreJuryJuryDTO, MembreJuryJury> {

    @Mapping(source = "jury.id", target = "juryId")
    @Mapping(source = "membreJury.id", target = "membreJuryId")
    MembreJuryJuryDTO toDto(MembreJuryJury membreJuryJury);

    @Mapping(source = "juryId", target = "jury")
    @Mapping(source = "membreJuryId", target = "membreJury")
    MembreJuryJury toEntity(MembreJuryJuryDTO membreJuryJuryDTO);

    default MembreJuryJury fromId(Long id) {
        if (id == null) {
            return null;
        }
        MembreJuryJury membreJuryJury = new MembreJuryJury();
        membreJuryJury.setId(id);
        return membreJuryJury;
    }
}
