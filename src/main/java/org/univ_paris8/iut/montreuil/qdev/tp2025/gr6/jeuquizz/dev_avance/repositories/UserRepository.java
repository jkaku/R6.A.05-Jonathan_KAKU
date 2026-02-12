package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories;

import jakarta.persistence.EntityManager;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.User;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.utils.JPAUtils;



public class UserRepository {
    public void create(User u) {
        EntityManager em = JPAUtils.getEntityManager();
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        em.close();
    }

    public User findByUsername(String username) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :user", User.class)
                    .setParameter("user", username)
                    .getSingleResult();
        } catch (Exception e) { return null; }
        finally { em.close(); }
    }
}
