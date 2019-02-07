package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.CentreCompositionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CentreComposition and its DTO CentreCompositionDTO.
 */
@Mapper(componentModel = "spring", uses = {CebMapper.class, ZoneExamenMapper.class, TypeCentreCompositionMapper.class})
public interface CentreCompositionMapper extends EntityMapper<CentreCompositionDTO, CentreComposition> {

    @Mapping(source = "ceb.id", target = "cebId")
    @Mapping(source = "zoneExamen.id", target = "zoneExamenId")
    @Mapping(source = "typeCentreComposition.id", target = "typeCentreCompositionId")
    CentreCompositionDTO toDto(CentreComposition centreComposition);

    @Mapping(target = "centreCompositioJuries", ignore = true)
    @Mapping(target = "etablissements", ignore = true)
    @Mapping(source = "cebId", target = "ceb")
    @Mapping(source = "zoneExamenId", target = "zoneExamen")
    @Mapping(source = "typeCentreCompositionId", target = "typeCentreComposition")
    CentreComposition toEntity(CentreCompositionDTO centreCompositionDTO);

    default CentreComposition fromId(Long id) {
        if (id == null) {
            return null;
        }
        CentreComposition centreComposition = new CentreComposition();
        centreComposition.setId(id);
        return centreComposition;
    }
}
