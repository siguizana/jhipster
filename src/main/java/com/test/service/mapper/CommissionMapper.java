package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.CommissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Commission and its DTO CommissionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommissionMapper extends EntityMapper<CommissionDTO, Commission> {


    @Mapping(target = "membreJuryJuries", ignore = true)
    Commission toEntity(CommissionDTO commissionDTO);

    default Commission fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commission commission = new Commission();
        commission.setId(id);
        return commission;
    }
}
