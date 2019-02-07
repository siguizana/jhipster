package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.ConcoursRattacheDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConcoursRattache and its DTO ConcoursRattacheDTO.
 */
@Mapper(componentModel = "spring", uses = {TypeExamenMapper.class})
public interface ConcoursRattacheMapper extends EntityMapper<ConcoursRattacheDTO, ConcoursRattache> {

    @Mapping(source = "typeExamen.id", target = "typeExamenId")
    ConcoursRattacheDTO toDto(ConcoursRattache concoursRattache);

    @Mapping(target = "optionConcoursRattaches", ignore = true)
    @Mapping(source = "typeExamenId", target = "typeExamen")
    ConcoursRattache toEntity(ConcoursRattacheDTO concoursRattacheDTO);

    default ConcoursRattache fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConcoursRattache concoursRattache = new ConcoursRattache();
        concoursRattache.setId(id);
        return concoursRattache;
    }
}
