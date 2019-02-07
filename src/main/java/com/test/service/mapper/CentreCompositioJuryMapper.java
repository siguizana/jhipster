package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.CentreCompositioJuryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CentreCompositioJury and its DTO CentreCompositioJuryDTO.
 */
@Mapper(componentModel = "spring", uses = {CentreCompositionMapper.class, JuryMapper.class})
public interface CentreCompositioJuryMapper extends EntityMapper<CentreCompositioJuryDTO, CentreCompositioJury> {

    @Mapping(source = "centreComposition.id", target = "centreCompositionId")
    @Mapping(source = "jury.id", target = "juryId")
    CentreCompositioJuryDTO toDto(CentreCompositioJury centreCompositioJury);

    @Mapping(target = "salleCompositions", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(source = "centreCompositionId", target = "centreComposition")
    @Mapping(source = "juryId", target = "jury")
    CentreCompositioJury toEntity(CentreCompositioJuryDTO centreCompositioJuryDTO);

    default CentreCompositioJury fromId(Long id) {
        if (id == null) {
            return null;
        }
        CentreCompositioJury centreCompositioJury = new CentreCompositioJury();
        centreCompositioJury.setId(id);
        return centreCompositioJury;
    }
}
