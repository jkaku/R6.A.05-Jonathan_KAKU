package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import java.util.List;

public class AnnonceRepository {

    public Annonce findById(EntityManager manager, Long id) {
        return manager.find(Annonce.class, id);
    }

    public List<Annonce> findAll(EntityManager manager) {
        return manager.createQuery("SELECT a FROM Annonce a ORDER BY a.date DESC", Annonce.class)
                .getResultList();
    }
    public List<Annonce> findAllPaginated(EntityManager manager, int page, int limit) {
        int offset = (Math.max(page, 1) - 1) * limit;
        TypedQuery<Annonce> q = manager.createQuery("SELECT a FROM Annonce a ORDER BY a.date DESC", Annonce.class);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return q.getResultList();
    }

    public void save(EntityManager manager, Annonce item) {
        manager.persist(item); // "create" renommé en "save"
    }
    public Annonce update(EntityManager manager, Annonce item) {
        return manager.merge(item);
    }
    public void delete(EntityManager manager, Annonce item) {
        manager.remove(item);
    }
}