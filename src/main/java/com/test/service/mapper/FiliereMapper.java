package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.FiliereDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Filiere and its DTO FiliereDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FiliereMapper extends EntityMapper<FiliereDTO, Filiere> {


    @Mapping(target = "specialiteOptions", ignore = true)
    Filiere toEntity(FiliereDTO filiereDTO);

    default Filiere fromId(Long id) {
        if (id == null) {
            return null;
        }
        Filiere filiere = new Filiere();
        filiere.setId(id);
        return filiere;
    }
}
