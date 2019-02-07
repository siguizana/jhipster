package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.SurveilleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Surveille and its DTO SurveilleDTO.
 */
@Mapper(componentModel = "spring", uses = {MembreJuryMapper.class, SalleCompositionMapper.class})
public interface SurveilleMapper extends EntityMapper<SurveilleDTO, Surveille> {

    @Mapping(source = "membreJury.id", target = "membreJuryId")
    @Mapping(source = "salleComposition.id", target = "salleCompositionId")
    SurveilleDTO toDto(Surveille surveille);

    @Mapping(source = "membreJuryId", target = "membreJury")
    @Mapping(source = "salleCompositionId", target = "salleComposition")
    Surveille toEntity(SurveilleDTO surveilleDTO);

    default Surveille fromId(Long id) {
        if (id == null) {
            return null;
        }
        Surveille surveille = new Surveille();
        surveille.setId(id);
        return surveille;
    }
}
