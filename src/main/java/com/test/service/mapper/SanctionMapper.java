package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.SanctionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sanction and its DTO SanctionDTO.
 */
@Mapper(componentModel = "spring", uses = {FraudeMapper.class})
public interface SanctionMapper extends EntityMapper<SanctionDTO, Sanction> {

    @Mapping(source = "fraude.id", target = "fraudeId")
    SanctionDTO toDto(Sanction sanction);

    @Mapping(source = "fraudeId", target = "fraude")
    Sanction toEntity(SanctionDTO sanctionDTO);

    default Sanction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sanction sanction = new Sanction();
        sanction.setId(id);
        return sanction;
    }
}
