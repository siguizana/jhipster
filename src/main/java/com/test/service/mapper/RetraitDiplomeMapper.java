package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.RetraitDiplomeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RetraitDiplome and its DTO RetraitDiplomeDTO.
 */
@Mapper(componentModel = "spring", uses = {ResultatMapper.class, TypeDiplomeMapper.class})
public interface RetraitDiplomeMapper extends EntityMapper<RetraitDiplomeDTO, RetraitDiplome> {

    @Mapping(source = "resultat.id", target = "resultatId")
    @Mapping(source = "typeDiplome.id", target = "typeDiplomeId")
    RetraitDiplomeDTO toDto(RetraitDiplome retraitDiplome);

    @Mapping(source = "resultatId", target = "resultat")
    @Mapping(source = "typeDiplomeId", target = "typeDiplome")
    RetraitDiplome toEntity(RetraitDiplomeDTO retraitDiplomeDTO);

    default RetraitDiplome fromId(Long id) {
        if (id == null) {
            return null;
        }
        RetraitDiplome retraitDiplome = new RetraitDiplome();
        retraitDiplome.setId(id);
        return retraitDiplome;
    }
}
