package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.NoteMembreCritereDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity NoteMembreCritere and its DTO NoteMembreCritereDTO.
 */
@Mapper(componentModel = "spring", uses = {MembreJuryMapper.class, CritereChoixMembreJuryMapper.class})
public interface NoteMembreCritereMapper extends EntityMapper<NoteMembreCritereDTO, NoteMembreCritere> {

    @Mapping(source = "membreJury.id", target = "membreJuryId")
    @Mapping(source = "critereChoixMembreJury.id", target = "critereChoixMembreJuryId")
    NoteMembreCritereDTO toDto(NoteMembreCritere noteMembreCritere);

    @Mapping(source = "membreJuryId", target = "membreJury")
    @Mapping(source = "critereChoixMembreJuryId", target = "critereChoixMembreJury")
    NoteMembreCritere toEntity(NoteMembreCritereDTO noteMembreCritereDTO);

    default NoteMembreCritere fromId(Long id) {
        if (id == null) {
            return null;
        }
        NoteMembreCritere noteMembreCritere = new NoteMembreCritere();
        noteMembreCritere.setId(id);
        return noteMembreCritere;
    }
}
