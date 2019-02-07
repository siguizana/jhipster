package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.DeroulementScolariteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DeroulementScolarite and its DTO DeroulementScolariteDTO.
 */
@Mapper(componentModel = "spring", uses = {CandidatMapper.class})
public interface DeroulementScolariteMapper extends EntityMapper<DeroulementScolariteDTO, DeroulementScolarite> {

    @Mapping(source = "candidat.id", target = "candidatId")
    DeroulementScolariteDTO toDto(DeroulementScolarite deroulementScolarite);

    @Mapping(source = "candidatId", target = "candidat")
    DeroulementScolarite toEntity(DeroulementScolariteDTO deroulementScolariteDTO);

    default DeroulementScolarite fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeroulementScolarite deroulementScolarite = new DeroulementScolarite();
        deroulementScolarite.setId(id);
        return deroulementScolarite;
    }
}
