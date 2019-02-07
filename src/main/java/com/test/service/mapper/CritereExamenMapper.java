package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.CritereExamenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CritereExamen and its DTO CritereExamenDTO.
 */
@Mapper(componentModel = "spring", uses = {RegionMapper.class, OptionConcoursRattacheMapper.class, SessionMapper.class, TypeExamenMapper.class, TypeCritereMapper.class})
public interface CritereExamenMapper extends EntityMapper<CritereExamenDTO, CritereExamen> {

    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "optionConcoursRattache.id", target = "optionConcoursRattacheId")
    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "typeExamen.id", target = "typeExamenId")
    @Mapping(source = "typeCritere.id", target = "typeCritereId")
    CritereExamenDTO toDto(CritereExamen critereExamen);

    @Mapping(source = "regionId", target = "region")
    @Mapping(source = "optionConcoursRattacheId", target = "optionConcoursRattache")
    @Mapping(source = "sessionId", target = "session")
    @Mapping(source = "typeExamenId", target = "typeExamen")
    @Mapping(source = "typeCritereId", target = "typeCritere")
    CritereExamen toEntity(CritereExamenDTO critereExamenDTO);

    default CritereExamen fromId(Long id) {
        if (id == null) {
            return null;
        }
        CritereExamen critereExamen = new CritereExamen();
        critereExamen.setId(id);
        return critereExamen;
    }
}
