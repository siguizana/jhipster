package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.OptionConcoursRattacheDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OptionConcoursRattache and its DTO OptionConcoursRattacheDTO.
 */
@Mapper(componentModel = "spring", uses = {ConcoursRattacheMapper.class})
public interface OptionConcoursRattacheMapper extends EntityMapper<OptionConcoursRattacheDTO, OptionConcoursRattache> {

    @Mapping(source = "concoursRattache.id", target = "concoursRattacheId")
    OptionConcoursRattacheDTO toDto(OptionConcoursRattache optionConcoursRattache);

    @Mapping(target = "resultats", ignore = true)
    @Mapping(target = "critereExamen", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(source = "concoursRattacheId", target = "concoursRattache")
    OptionConcoursRattache toEntity(OptionConcoursRattacheDTO optionConcoursRattacheDTO);

    default OptionConcoursRattache fromId(Long id) {
        if (id == null) {
            return null;
        }
        OptionConcoursRattache optionConcoursRattache = new OptionConcoursRattache();
        optionConcoursRattache.setId(id);
        return optionConcoursRattache;
    }
}
