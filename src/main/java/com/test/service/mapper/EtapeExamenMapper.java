package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.EtapeExamenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EtapeExamen and its DTO EtapeExamenDTO.
 */
@Mapper(componentModel = "spring", uses = {TypeExamenMapper.class})
public interface EtapeExamenMapper extends EntityMapper<EtapeExamenDTO, EtapeExamen> {

    @Mapping(source = "typeExamen.id", target = "typeExamenId")
    EtapeExamenDTO toDto(EtapeExamen etapeExamen);

    @Mapping(target = "compositionCopies", ignore = true)
    @Mapping(target = "absences", ignore = true)
    @Mapping(target = "resultats", ignore = true)
    @Mapping(source = "typeExamenId", target = "typeExamen")
    EtapeExamen toEntity(EtapeExamenDTO etapeExamenDTO);

    default EtapeExamen fromId(Long id) {
        if (id == null) {
            return null;
        }
        EtapeExamen etapeExamen = new EtapeExamen();
        etapeExamen.setId(id);
        return etapeExamen;
    }
}
