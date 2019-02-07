package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.CandidatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Candidat and its DTO CandidatDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CandidatMapper extends EntityMapper<CandidatDTO, Candidat> {


    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(target = "deroulementScolarites", ignore = true)
    Candidat toEntity(CandidatDTO candidatDTO);

    default Candidat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Candidat candidat = new Candidat();
        candidat.setId(id);
        return candidat;
    }
}
