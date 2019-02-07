package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.RegionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Region and its DTO RegionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {


    @Mapping(target = "provinces", ignore = true)
    @Mapping(target = "critereExamen", ignore = true)
    @Mapping(target = "centreExamen", ignore = true)
    Region toEntity(RegionDTO regionDTO);

    default Region fromId(Long id) {
        if (id == null) {
            return null;
        }
        Region region = new Region();
        region.setId(id);
        return region;
    }
}
