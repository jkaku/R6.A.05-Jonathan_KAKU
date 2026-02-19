package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.service;

import jakarta.persistence.EntityManager;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Category;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Status;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.User;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.AnnonceRepository;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.UserRepository;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.utils.JPAProvider;

import java.sql.Timestamp;
import java.util.List;

public class AnnonceService {

    private final AnnonceRepository repo;
    private final UserRepository userRepo;
    public AnnonceService() {
        this.repo = new AnnonceRepository();
        this.userRepo = new UserRepository();
    }

    public List<Annonce> getList(int page, int size) {
        EntityManager manager = JPAProvider.getEntityManager();
        try {
            return repo.findAllPaginated(manager, page, size);
        } finally {
            manager.close();
        }
    }

    public Annonce getOne(Long id) {
        EntityManager manager = JPAProvider.getEntityManager();
        try {
            return repo.findById(manager, id);
        } finally {
            manager.close();
        }
    }

    public void addAnnonce(Annonce annonce, String username, Long categoryId) {
        EntityManager manager = JPAProvider.getEntityManager();
        try {
            manager.getTransaction().begin();
            User author = userRepo.findByUsername(username);
            if (author == null) throw new ForbiddenException("Utilisateur inconnu");
            if (categoryId != null) {
                Category cat = manager.find(Category.class, categoryId);
                if (cat == null) {
                    throw new NotFoundException("Catégorie introuvable avec l'ID " + categoryId);
                }
                annonce.setCategory(cat);
            }
            annonce.setAuthor(author);
            annonce.setDate(new Timestamp(System.currentTimeMillis()));
            if (annonce.getStatus() == null) annonce.setStatus(Status.DRAFT);
            repo.save(manager, annonce);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) manager.getTransaction().rollback();
            throw e;
        } finally {
            manager.close();
        }
    }

    public void updateAnnonce(Long id, Annonce updates, String username) {
        EntityManager manager = JPAProvider.getEntityManager();
        try {
            manager.getTransaction().begin();
            Annonce existing = repo.findById(manager, id);
            if (existing == null) throw new NotFoundException("Annonce introuvable");
            if (!existing.getAuthor().getUsername().equals(username)) {
                throw new ForbiddenException("Vous n'êtes pas l'auteur de cette annonce");
            }
            if (existing.getStatus() == Status.PUBLISHED) {
                throw new ForbiddenException("Impossible de modifier une annonce publiée");
            }
            existing.setTitle(updates.getTitle());
            existing.setDescription(updates.getDescription());
            existing.setAdress(updates.getAdress());
            repo.update(manager, existing);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }

    public void deleteAnnonce(Long id, String username) {
        EntityManager manager = JPAProvider.getEntityManager();
        try {
            manager.getTransaction().begin();
            Annonce existing = repo.findById(manager, id);
            if (existing == null) throw new NotFoundException("Annonce introuvable");
            if (!existing.getAuthor().getUsername().equals(username)) {
                throw new ForbiddenException("Action non autorisée");
            }
            if (existing.getStatus() != Status.ARCHIVED) {

            }
            repo.delete(manager, existing);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }
}