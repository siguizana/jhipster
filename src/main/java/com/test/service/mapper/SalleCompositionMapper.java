package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.SalleCompositionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SalleComposition and its DTO SalleCompositionDTO.
 */
@Mapper(componentModel = "spring", uses = {CentreCompositioJuryMapper.class})
public interface SalleCompositionMapper extends EntityMapper<SalleCompositionDTO, SalleComposition> {

    @Mapping(source = "centreCompositioJury.id", target = "centreCompositioJuryId")
    SalleCompositionDTO toDto(SalleComposition salleComposition);

    @Mapping(target = "surveilles", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(source = "centreCompositioJuryId", target = "centreCompositioJury")
    SalleComposition toEntity(SalleCompositionDTO salleCompositionDTO);

    default SalleComposition fromId(Long id) {
        if (id == null) {
            return null;
        }
        SalleComposition salleComposition = new SalleComposition();
        salleComposition.setId(id);
        return salleComposition;
    }
}
