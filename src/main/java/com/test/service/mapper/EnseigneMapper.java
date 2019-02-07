package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.EnseigneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Enseigne and its DTO EnseigneDTO.
 */
@Mapper(componentModel = "spring", uses = {EtablissementMapper.class, EnseignantMapper.class, SessionMapper.class})
public interface EnseigneMapper extends EntityMapper<EnseigneDTO, Enseigne> {

    @Mapping(source = "etablissement.id", target = "etablissementId")
    @Mapping(source = "enseignant.id", target = "enseignantId")
    @Mapping(source = "session.id", target = "sessionId")
    EnseigneDTO toDto(Enseigne enseigne);

    @Mapping(source = "etablissementId", target = "etablissement")
    @Mapping(source = "enseignantId", target = "enseignant")
    @Mapping(source = "sessionId", target = "session")
    Enseigne toEntity(EnseigneDTO enseigneDTO);

    default Enseigne fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enseigne enseigne = new Enseigne();
        enseigne.setId(id);
        return enseigne;
    }
}
