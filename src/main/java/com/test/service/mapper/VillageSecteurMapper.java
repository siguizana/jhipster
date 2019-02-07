package com.test.service.mapper;

import com.test.domain.*;
import com.test.service.dto.VillageSecteurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VillageSecteur and its DTO VillageSecteurDTO.
 */
@Mapper(componentModel = "spring", uses = {CommuneMapper.class})
public interface VillageSecteurMapper extends EntityMapper<VillageSecteurDTO, VillageSecteur> {

    @Mapping(source = "commune.id", target = "communeId")
    VillageSecteurDTO toDto(VillageSecteur villageSecteur);

    @Mapping(target = "etablissements", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(source = "communeId", target = "commune")
    VillageSecteur toEntity(VillageSecteurDTO villageSecteurDTO);

    default VillageSecteur fromId(Long id) {
        if (id == null) {
            return null;
        }
        VillageSecteur villageSecteur = new VillageSecteur();
        villageSecteur.setId(id);
        return villageSecteur;
    }
}
