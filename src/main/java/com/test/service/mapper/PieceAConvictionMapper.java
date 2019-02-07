package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.PieceAConvictionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PieceAConviction and its DTO PieceAConvictionDTO.
 */
@Mapper(componentModel = "spring", uses = {FraudeMapper.class})
public interface PieceAConvictionMapper extends EntityMapper<PieceAConvictionDTO, PieceAConviction> {

    @Mapping(source = "fraude.id", target = "fraudeId")
    PieceAConvictionDTO toDto(PieceAConviction pieceAConviction);

    @Mapping(source = "fraudeId", target = "fraude")
    PieceAConviction toEntity(PieceAConvictionDTO pieceAConvictionDTO);

    default PieceAConviction fromId(Long id) {
        if (id == null) {
            return null;
        }
        PieceAConviction pieceAConviction = new PieceAConviction();
        pieceAConviction.setId(id);
        return pieceAConviction;
    }
}
