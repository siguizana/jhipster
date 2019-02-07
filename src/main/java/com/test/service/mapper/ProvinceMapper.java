package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.ProvinceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Province and its DTO ProvinceDTO.
 */
@Mapper(componentModel = "spring", uses = {RegionMapper.class})
public interface ProvinceMapper extends EntityMapper<ProvinceDTO, Province> {

    @Mapping(source = "region.id", target = "regionId")
    ProvinceDTO toDto(Province province);

    @Mapping(target = "communes", ignore = true)
    @Mapping(source = "regionId", target = "region")
    Province toEntity(ProvinceDTO provinceDTO);

    default Province fromId(Long id) {
        if (id == null) {
            return null;
        }
        Province province = new Province();
        province.setId(id);
        return province;
    }
}
