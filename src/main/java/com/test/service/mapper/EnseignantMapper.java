package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.EnseignantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Enseignant and its DTO EnseignantDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EnseignantMapper extends EntityMapper<EnseignantDTO, Enseignant> {


    @Mapping(target = "enseignes", ignore = true)
    Enseignant toEntity(EnseignantDTO enseignantDTO);

    default Enseignant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enseignant enseignant = new Enseignant();
        enseignant.setId(id);
        return enseignant;
    }
}
