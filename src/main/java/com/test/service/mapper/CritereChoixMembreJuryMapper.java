package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.CritereChoixMembreJuryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CritereChoixMembreJury and its DTO CritereChoixMembreJuryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CritereChoixMembreJuryMapper extends EntityMapper<CritereChoixMembreJuryDTO, CritereChoixMembreJury> {


    @Mapping(target = "noteMembreCriteres", ignore = true)
    @Mapping(target = "typeMembreJuries", ignore = true)
    CritereChoixMembreJury toEntity(CritereChoixMembreJuryDTO critereChoixMembreJuryDTO);

    default CritereChoixMembreJury fromId(Long id) {
        if (id == null) {
            return null;
        }
        CritereChoixMembreJury critereChoixMembreJury = new CritereChoixMembreJury();
        critereChoixMembreJury.setId(id);
        return critereChoixMembreJury;
    }
}
