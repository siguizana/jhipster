package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.CentreExamenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CentreExamen and its DTO CentreExamenDTO.
 */
@Mapper(componentModel = "spring", uses = {RegionMapper.class})
public interface CentreExamenMapper extends EntityMapper<CentreExamenDTO, CentreExamen> {

    @Mapping(source = "region.id", target = "regionId")
    CentreExamenDTO toDto(CentreExamen centreExamen);

    @Mapping(target = "zoneExamen", ignore = true)
    @Mapping(source = "regionId", target = "region")
    CentreExamen toEntity(CentreExamenDTO centreExamenDTO);

    default CentreExamen fromId(Long id) {
        if (id == null) {
            return null;
        }
        CentreExamen centreExamen = new CentreExamen();
        centreExamen.setId(id);
        return centreExamen;
    }
}
