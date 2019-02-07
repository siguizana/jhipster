package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.EpreuveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Epreuve and its DTO EpreuveDTO.
 */
@Mapper(componentModel = "spring", uses = {TypeEpreuveMapper.class})
public interface EpreuveMapper extends EntityMapper<EpreuveDTO, Epreuve> {

    @Mapping(source = "typeEpreuve.id", target = "typeEpreuveId")
    EpreuveDTO toDto(Epreuve epreuve);

    @Mapping(target = "epreuveSpecialiteOptions", ignore = true)
    @Mapping(source = "typeEpreuveId", target = "typeEpreuve")
    Epreuve toEntity(EpreuveDTO epreuveDTO);

    default Epreuve fromId(Long id) {
        if (id == null) {
            return null;
        }
        Epreuve epreuve = new Epreuve();
        epreuve.setId(id);
        return epreuve;
    }
}
