package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.CompositionCopieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CompositionCopie and its DTO CompositionCopieDTO.
 */
@Mapper(componentModel = "spring", uses = {InscriptionMapper.class, EpreuveSpecialiteOptionMapper.class, EtapeExamenMapper.class})
public interface CompositionCopieMapper extends EntityMapper<CompositionCopieDTO, CompositionCopie> {

    @Mapping(source = "inscription.id", target = "inscriptionId")
    @Mapping(source = "epreuveSpecialiteOption.id", target = "epreuveSpecialiteOptionId")
    @Mapping(source = "etapeExamen.id", target = "etapeExamenId")
    CompositionCopieDTO toDto(CompositionCopie compositionCopie);

    @Mapping(target = "reclamations", ignore = true)
    @Mapping(source = "inscriptionId", target = "inscription")
    @Mapping(source = "epreuveSpecialiteOptionId", target = "epreuveSpecialiteOption")
    @Mapping(source = "etapeExamenId", target = "etapeExamen")
    @Mapping(target = "membreJuries", ignore = true)
    CompositionCopie toEntity(CompositionCopieDTO compositionCopieDTO);

    default CompositionCopie fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompositionCopie compositionCopie = new CompositionCopie();
        compositionCopie.setId(id);
        return compositionCopie;
    }
}
