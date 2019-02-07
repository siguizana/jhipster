package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.ZoneExamenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZoneExamen and its DTO ZoneExamenDTO.
 */
@Mapper(componentModel = "spring", uses = {CentreExamenMapper.class})
public interface ZoneExamenMapper extends EntityMapper<ZoneExamenDTO, ZoneExamen> {

    @Mapping(source = "centreExamen.id", target = "centreExamenId")
    ZoneExamenDTO toDto(ZoneExamen zoneExamen);

    @Mapping(target = "centreCompositions", ignore = true)
    @Mapping(source = "centreExamenId", target = "centreExamen")
    ZoneExamen toEntity(ZoneExamenDTO zoneExamenDTO);

    default ZoneExamen fromId(Long id) {
        if (id == null) {
            return null;
        }
        ZoneExamen zoneExamen = new ZoneExamen();
        zoneExamen.setId(id);
        return zoneExamen;
    }
}
