package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.SessionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Session and its DTO SessionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SessionMapper extends EntityMapper<SessionDTO, Session> {


    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(target = "critereExamen", ignore = true)
    @Mapping(target = "enseignes", ignore = true)
    Session toEntity(SessionDTO sessionDTO);

    default Session fromId(Long id) {
        if (id == null) {
            return null;
        }
        Session session = new Session();
        session.setId(id);
        return session;
    }
}
