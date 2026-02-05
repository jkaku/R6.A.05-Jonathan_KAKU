package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories;

import org.hibernate.engine.spi.Status;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.utils.JPAUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AnnonceRepository {

    public void create(Annonce a) {
        EntityManager em = JPAUtils.getEntityManager();
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        em.close();
    }

    public Annonce findById(Long id) {
        EntityManager em = JPAUtils.getEntityManager();
        Annonce a = em.find(Annonce.class, id);
        em.close();
        return a;
    }

    public void update(Annonce a) {
        EntityManager em = JPAUtils.getEntityManager();
        em.getTransaction().begin();
        em.merge(a);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Long id) {
        EntityManager em = JPAUtils.getEntityManager();
        em.getTransaction().begin();
        Annonce a = em.find(Annonce.class, id);
        if (a != null) em.remove(a);
        em.getTransaction().commit();
        em.close();
    }

    public List<Annonce> searchByKeyword(String keyword) {
        EntityManager em = JPAUtils.getEntityManager();
        String jpql = "SELECT a FROM Annonce a WHERE a.title LIKE :kw OR a.description LIKE :kw";
        TypedQuery<Annonce> query = em.createQuery(jpql, Annonce.class);
        query.setParameter("kw", "%" + keyword + "%");
        List<Annonce> results = query.getResultList();
        em.close();
        return results;
    }

    public List<Annonce> findByCategoryAndStatus(Long catId, Status status, int page, int size) {
        EntityManager em = JPAUtils.getEntityManager();
        String jpql = "SELECT a FROM Annonce a WHERE a.category.id = :catId AND a.status = :status";

        TypedQuery<Annonce> query = em.createQuery(jpql, Annonce.class);
        query.setParameter("catId", catId);
        query.setParameter("status", status);

        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        List<Annonce> results = query.getResultList();
        em.close();
        return results;
    }

    public List<Annonce> findAllPaginated(int page, int size) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Annonce a ORDER BY a.date DESC", Annonce.class)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
