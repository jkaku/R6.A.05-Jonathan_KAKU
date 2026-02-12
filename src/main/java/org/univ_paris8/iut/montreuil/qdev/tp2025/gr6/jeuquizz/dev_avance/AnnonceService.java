package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Status;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.AnnonceRepository;

import java.sql.Timestamp;
import java.util.List;

public class AnnonceService {

    // On utilise le repository comme seule interface avec la base de données
    private AnnonceRepository annonceRepository = new AnnonceRepository();

    /**
     * Crée une annonce avec les valeurs par défaut (date et statut DRAFT)
     */
    public void createAnnonce(Annonce a) {
        a.setDate(new Timestamp(System.currentTimeMillis()));
        if (a.getStatus() == null) {
            a.setStatus(Status.DRAFT);
        }
        annonceRepository.create(a);
    }

    /**
     * Publie une annonce existante (Passage en Status.PUBLISHED)
     * C'est cette méthode que ton test Mockito vérifie.
     */
    public void publishAnnonce(Long id) {
        Annonce a = annonceRepository.findById(id);
        if (a != null) {
            a.setStatus(Status.PUBLISHED);
            annonceRepository.update(a);
        }
    }

    /**
     * Archive une annonce
     */
    public void archiveAnnonce(Long id) {
        Annonce a = annonceRepository.findById(id);
        if (a != null) {
            a.setStatus(Status.ARCHIVED);
            annonceRepository.update(a);
        }
    }

    /**
     * Supprime une annonce par son ID
     */
    public void deleteAnnonce(Long id) {
        // On vérifie d'abord si elle existe
        Annonce a = annonceRepository.findById(id);
        if (a != null) {
            annonceRepository.delete(a.getId());
        }
    }

    /**
     * Récupère les annonces avec pagination
     */
    public List<Annonce> getAnnoncesByPage(int page, int size) {
        return annonceRepository.findAllPaginated(page, size);
    }

    /**
     * Récupère une annonce spécifique par son ID
     */
    public Annonce getAnnonceById(Long id) {
        return annonceRepository.findById(id);
    }

    /**
     * Met à jour les données d'une annonce
     */
    public void updateAnnonce(Annonce a) {
        annonceRepository.update(a);
    }
}