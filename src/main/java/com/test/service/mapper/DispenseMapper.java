package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.DispenseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dispense and its DTO DispenseDTO.
 */
@Mapper(componentModel = "spring", uses = {EpreuveSpecialiteOptionMapper.class, InscriptionMapper.class, HandicapeMapper.class})
public interface DispenseMapper extends EntityMapper<DispenseDTO, Dispense> {

    @Mapping(source = "inscription.id", target = "inscriptionId")
    @Mapping(source = "handicape.id", target = "handicapeId")
    DispenseDTO toDto(Dispense dispense);

    @Mapping(source = "inscriptionId", target = "inscription")
    @Mapping(source = "handicapeId", target = "handicape")
    Dispense toEntity(DispenseDTO dispenseDTO);

    default Dispense fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dispense dispense = new Dispense();
        dispense.setId(id);
        return dispense;
    }
}
