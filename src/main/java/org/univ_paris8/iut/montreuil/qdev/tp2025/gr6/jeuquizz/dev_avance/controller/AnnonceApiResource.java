package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto.AnnonceCreateDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto.AnnonceResponseDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dto.StatusPatchDTO;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.service.AnnonceManagementService;

import java.net.URI;

@RestController
@RequestMapping("/api/annonces")
@Tag(name = "Annonces", description = "Endpoints de gestion du catalogue d'annonces")
public class AnnonceApiResource {

    private final AnnonceManagementService annonceService;

    public AnnonceApiResource(AnnonceManagementService annonceService) {
        this.annonceService = annonceService;
    }

    @GetMapping
    @Operation(summary = "Lister les annonces", description = "Retourne une liste paginée (Endpoint public)")
    public ResponseEntity<Page<AnnonceResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(annonceService.listAllPaginated(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détail d'une annonce", description = "Retourne une annonce via son ID (Endpoint public)")
    public ResponseEntity<AnnonceResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(annonceService.getAnnonceById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une annonce", description = "Crée une annonce en statut DRAFT (Nécessite un Token JWT)")
    public ResponseEntity<AnnonceResponseDTO> create(@Valid @RequestBody AnnonceCreateDTO request) {
        AnnonceResponseDTO created = annonceService.createAnnonce(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une annonce", description = "Mise à jour d'une annonce (Nécessite d'être l'auteur)")
    public ResponseEntity<AnnonceResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AnnonceCreateDTO request) {
        return ResponseEntity.ok(annonceService.modifyAnnonce(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une annonce", description = "Supprime une annonce archivée (Nécessite d'être l'auteur)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        annonceService.deleteAnnonce(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Modifier le statut", description = "Change le statut d'une annonce (Admin pour archiver, Auteur pour publier)")
    public ResponseEntity<AnnonceResponseDTO> updateStatus(@PathVariable Long id, @Valid @RequestBody StatusPatchDTO request) {
        return ResponseEntity.ok(annonceService.changeStatus(id, request));
    }
}