package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.JuryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Jury and its DTO JuryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JuryMapper extends EntityMapper<JuryDTO, Jury> {


    @Mapping(target = "centreCompositioJuries", ignore = true)
    @Mapping(target = "membreJuryJuries", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    Jury toEntity(JuryDTO juryDTO);

    default Jury fromId(Long id) {
        if (id == null) {
            return null;
        }
        Jury jury = new Jury();
        jury.setId(id);
        return jury;
    }
}
