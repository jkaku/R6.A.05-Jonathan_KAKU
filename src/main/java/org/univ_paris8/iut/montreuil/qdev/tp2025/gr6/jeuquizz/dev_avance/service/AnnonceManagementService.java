package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto.AnnonceCreateDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto.AnnonceResponseDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto.StatusPatchDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.exception.BusinessRuleException;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.exception.ResourceNotFoundException;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.exception.UnauthorizedActionException;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.mapper.AnnonceEntityMapper;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Category;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Status;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.User;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repository.AccountDataRepo;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repository.CatalogRepo;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repository.ItemDataRepo;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.security.AppUserDetails;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
public class AnnonceManagementService {

    private final ItemDataRepo annonceRepo;
    private final AccountDataRepo userRepo;
    private final CatalogRepo categoryRepo;
    private final AnnonceEntityMapper mapper;

    public AnnonceManagementService(ItemDataRepo annonceRepo, AccountDataRepo userRepo, CatalogRepo categoryRepo, AnnonceEntityMapper mapper) {
        this.annonceRepo = annonceRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.mapper = mapper;
    }

    public Page<AnnonceResponseDTO> listAllPaginated(Pageable pageable) {
        return annonceRepo.findAll(pageable).map(mapper::toDto);
    }

    public AnnonceResponseDTO getAnnonceById(Long id) {
        Annonce annonce = annonceRepo.findWithRelationsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce introuvable : " + id));
        return mapper.toDto(annonce);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public AnnonceResponseDTO createAnnonce(AnnonceCreateDTO dto) {
        AppUserDetails principal = getCurrentUser();
        User author = userRepo.findById(principal.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Auteur introuvable"));
        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable : " + dto.getCategoryId()));
        Annonce annonce = mapper.toEntity(dto);
        annonce.setAuthor(author);
        annonce.setCategory(category);
        annonce.setStatus(Status.DRAFT);
        return mapper.toDto(annonceRepo.save(annonce));
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public AnnonceResponseDTO modifyAnnonce(Long id, AnnonceCreateDTO dto) {
        Annonce annonce = annonceRepo.findWithRelationsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce introuvable"));
        checkOwnership(annonce);
        if (annonce.getStatus() == Status.PUBLISHED) {
            throw new BusinessRuleException("Impossible de modifier une annonce publiée");
        }
        if (dto.getVersion() != null && !Objects.equals(annonce.getVersion(), dto.getVersion())) {
            throw new BusinessRuleException("Conflit : L'annonce a été modifiée par quelqu'un d'autre entre temps.");
        }
        mapper.updateEntityFromDto(dto, annonce);
        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable"));
        annonce.setCategory(category);
        return mapper.toDto(annonceRepo.save(annonce));
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void deleteAnnonce(Long id) {
        Annonce annonce = annonceRepo.findWithRelationsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce introuvable"));
        checkOwnership(annonce);
        if (annonce.getStatus() != Status.ARCHIVED) {
            throw new BusinessRuleException("L'annonce doit être archivée avant suppression");
        }
        annonceRepo.delete(annonce);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public AnnonceResponseDTO changeStatus(Long id, StatusPatchDTO patchDTO) {
        Annonce annonce = annonceRepo.findWithRelationsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce introuvable"));
        Status newStatus = patchDTO.getStatus();
        if (newStatus == Status.ARCHIVED) {
            checkAdmin();
        } else {
            checkOwnership(annonce);
        }
        if (annonce.getStatus() == newStatus) {
            throw new BusinessRuleException("L'annonce a déjà le statut " + newStatus);
        }
        if (annonce.getStatus() == Status.ARCHIVED && newStatus == Status.PUBLISHED) {
            throw new BusinessRuleException("Une annonce archivée ne peut pas repasser en PUBLISHED");
        }
        annonce.setStatus(newStatus);
        return mapper.toDto(annonceRepo.save(annonce));
    }

    private AppUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof AppUserDetails)) {
            throw new UnauthorizedActionException("Non authentifié");
        }
        return (AppUserDetails) auth.getPrincipal();
    }

    private void checkOwnership(Annonce annonce) {
        AppUserDetails principal = getCurrentUser();
        boolean isAdmin = principal.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin && !Objects.equals(annonce.getAuthor().getId(), principal.getUserId())) {
            throw new UnauthorizedActionException("Seul l'auteur peut modifier cette annonce");
        }
    }

    private void checkAdmin() {
        AppUserDetails principal = getCurrentUser();
        boolean isAdmin = principal.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new UnauthorizedActionException("Seul un administrateur peut faire ça");
        }
    }
}