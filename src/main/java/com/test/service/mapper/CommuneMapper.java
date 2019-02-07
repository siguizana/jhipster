package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.CommuneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Commune and its DTO CommuneDTO.
 */
@Mapper(componentModel = "spring", uses = {ProvinceMapper.class})
public interface CommuneMapper extends EntityMapper<CommuneDTO, Commune> {

    @Mapping(source = "province.id", target = "provinceId")
    CommuneDTO toDto(Commune commune);

    @Mapping(target = "villageSecteurs", ignore = true)
    @Mapping(target = "cebs", ignore = true)
    @Mapping(source = "provinceId", target = "province")
    Commune toEntity(CommuneDTO communeDTO);

    default Commune fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commune commune = new Commune();
        commune.setId(id);
        return commune;
    }
}
