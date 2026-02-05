package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Status;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.utils.JPAUtils;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.AnnonceRepository;
import java.sql.Timestamp;
import javax.persistence.EntityManager;
import java.util.List;

public class AnnonceService {

    private AnnonceRepository annonceRepository = new AnnonceRepository();

    public void createAnnonce(Annonce a) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();

            a.setDate(new Timestamp(System.currentTimeMillis()));
            if (a.getStatus() == null) a.setStatus(Status.DRAFT);

            em.persist(a);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void publishAnnonce(Long id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            Annonce a = em.find(Annonce.class, id);
            if (a != null) {
                a.setStatus(Status.PUBLISHED);
                em.merge(a);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void archiveAnnonce(Long id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            Annonce a = em.find(Annonce.class, id);
            if (a != null) {
                a.setStatus(Status.ARCHIVED);
                em.merge(a);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void deleteAnnonce(Long id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            Annonce a = em.find(Annonce.class, id);
            if (a != null) em.remove(a);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Annonce> getAnnoncesByPage(int page, int size) {
        return annonceRepository.findAllPaginated(page, size);
    }

    public Annonce getAnnonceById(Long id) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            return em.find(Annonce.class, id);
        } finally {
            em.close();
        }
    }

    public void updateAnnonce(Annonce a) {
        EntityManager em = JPAUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(a);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
