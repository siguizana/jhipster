package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.MentionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mention and its DTO MentionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MentionMapper extends EntityMapper<MentionDTO, Mention> {


    @Mapping(target = "resultats", ignore = true)
    Mention toEntity(MentionDTO mentionDTO);

    default Mention fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mention mention = new Mention();
        mention.setId(id);
        return mention;
    }
}
