package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.FraudeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Fraude and its DTO FraudeDTO.
 */
@Mapper(componentModel = "spring", uses = {TypeFraudeMapper.class, InscriptionMapper.class})
public interface FraudeMapper extends EntityMapper<FraudeDTO, Fraude> {

    @Mapping(source = "typeFraude.id", target = "typeFraudeId")
    @Mapping(source = "inscription.id", target = "inscriptionId")
    FraudeDTO toDto(Fraude fraude);

    @Mapping(target = "sanctions", ignore = true)
    @Mapping(target = "pieceAConvictions", ignore = true)
    @Mapping(source = "typeFraudeId", target = "typeFraude")
    @Mapping(source = "inscriptionId", target = "inscription")
    @Mapping(target = "membreJuries", ignore = true)
    Fraude toEntity(FraudeDTO fraudeDTO);

    default Fraude fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fraude fraude = new Fraude();
        fraude.setId(id);
        return fraude;
    }
}
