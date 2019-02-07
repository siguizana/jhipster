package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.CebDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ceb and its DTO CebDTO.
 */
@Mapper(componentModel = "spring", uses = {CommuneMapper.class})
public interface CebMapper extends EntityMapper<CebDTO, Ceb> {

    @Mapping(source = "commune.id", target = "communeId")
    CebDTO toDto(Ceb ceb);

    @Mapping(target = "etablissements", ignore = true)
    @Mapping(target = "centreCompositions", ignore = true)
    @Mapping(source = "communeId", target = "commune")
    Ceb toEntity(CebDTO cebDTO);

    default Ceb fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ceb ceb = new Ceb();
        ceb.setId(id);
        return ceb;
    }
}
