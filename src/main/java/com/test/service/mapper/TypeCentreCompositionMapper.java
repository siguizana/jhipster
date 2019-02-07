package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.TypeCentreCompositionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TypeCentreComposition and its DTO TypeCentreCompositionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeCentreCompositionMapper extends EntityMapper<TypeCentreCompositionDTO, TypeCentreComposition> {


    @Mapping(target = "centreCompositions", ignore = true)
    TypeCentreComposition toEntity(TypeCentreCompositionDTO typeCentreCompositionDTO);

    default TypeCentreComposition fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeCentreComposition typeCentreComposition = new TypeCentreComposition();
        typeCentreComposition.setId(id);
        return typeCentreComposition;
    }
}
