package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.utils;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto.AnnonceDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Category;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.User;

public class DTOMapper {

    public static AnnonceDTO toAnnonceDTO(Annonce entity) {
        if (entity == null) return null;
        AnnonceDTO dto = new AnnonceDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setAdress(entity.getAdress());
        dto.setMail(entity.getMail());
        dto.setDate(entity.getDate());
        dto.setStatus(entity.getStatus());
        if (entity.getAuthor() != null) {
            dto.setAuthorName(entity.getAuthor().getUsername());
        }
        if (entity.getCategory() != null) {
            dto.setCategoryId(entity.getCategory().getId());
            dto.setCategoryLabel(entity.getCategory().getLabel());
        }
        return dto;
    }

    public static Annonce toAnnonceEntity(AnnonceDTO dto) {
        if (dto == null) return null;
        Annonce entity = new Annonce();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setAdress(dto.getAdress());
        entity.setMail(dto.getMail());
        return entity;
    }
}