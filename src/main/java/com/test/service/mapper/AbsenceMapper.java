package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.AbsenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Absence and its DTO AbsenceDTO.
 */
@Mapper(componentModel = "spring", uses = {InscriptionMapper.class, EpreuveSpecialiteOptionMapper.class, EtapeExamenMapper.class})
public interface AbsenceMapper extends EntityMapper<AbsenceDTO, Absence> {

    @Mapping(source = "inscription.id", target = "inscriptionId")
    @Mapping(source = "epreuveSpecialiteOption.id", target = "epreuveSpecialiteOptionId")
    @Mapping(source = "etapeExamen.id", target = "etapeExamenId")
    AbsenceDTO toDto(Absence absence);

    @Mapping(source = "inscriptionId", target = "inscription")
    @Mapping(source = "epreuveSpecialiteOptionId", target = "epreuveSpecialiteOption")
    @Mapping(source = "etapeExamenId", target = "etapeExamen")
    Absence toEntity(AbsenceDTO absenceDTO);

    default Absence fromId(Long id) {
        if (id == null) {
            return null;
        }
        Absence absence = new Absence();
        absence.setId(id);
        return absence;
    }
}
