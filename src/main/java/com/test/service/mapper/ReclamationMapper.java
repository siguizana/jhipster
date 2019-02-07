package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.ReclamationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Reclamation and its DTO ReclamationDTO.
 */
@Mapper(componentModel = "spring", uses = {CompositionCopieMapper.class})
public interface ReclamationMapper extends EntityMapper<ReclamationDTO, Reclamation> {

    @Mapping(source = "compositionCopie.id", target = "compositionCopieId")
    ReclamationDTO toDto(Reclamation reclamation);

    @Mapping(source = "compositionCopieId", target = "compositionCopie")
    Reclamation toEntity(ReclamationDTO reclamationDTO);

    default Reclamation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Reclamation reclamation = new Reclamation();
        reclamation.setId(id);
        return reclamation;
    }
}
