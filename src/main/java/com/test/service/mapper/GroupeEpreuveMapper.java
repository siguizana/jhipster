package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.GroupeEpreuveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GroupeEpreuve and its DTO GroupeEpreuveDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GroupeEpreuveMapper extends EntityMapper<GroupeEpreuveDTO, GroupeEpreuve> {


    @Mapping(target = "epreuveSpecialiteOptions", ignore = true)
    GroupeEpreuve toEntity(GroupeEpreuveDTO groupeEpreuveDTO);

    default GroupeEpreuve fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupeEpreuve groupeEpreuve = new GroupeEpreuve();
        groupeEpreuve.setId(id);
        return groupeEpreuve;
    }
}
