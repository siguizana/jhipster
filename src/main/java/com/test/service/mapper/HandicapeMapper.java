package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.HandicapeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Handicape and its DTO HandicapeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HandicapeMapper extends EntityMapper<HandicapeDTO, Handicape> {


    @Mapping(target = "dispenses", ignore = true)
    Handicape toEntity(HandicapeDTO handicapeDTO);

    default Handicape fromId(Long id) {
        if (id == null) {
            return null;
        }
        Handicape handicape = new Handicape();
        handicape.setId(id);
        return handicape;
    }
}
