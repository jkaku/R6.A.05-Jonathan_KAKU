package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.AnnonceRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnnonceDAOIntegrationTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private AnnonceRepository dao;

    @BeforeAll
    static void setupFactory() {
        emf = Persistence.createEntityManagerFactory("dev_avance_test");
    }

    @AfterAll
    static void closeFactory() {
        if (emf != null) emf.close();
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        dao = new AnnonceRepository();
    }

    @AfterEach
    void tearDown() {
        if (em != null) em.close();
    }

    @Test
    void testCreateAndFind() {
        em.getTransaction().begin();
        Annonce a = new Annonce();
        a.setTitle("Vélo Rouge");
        a.setDescription("Un beau vélo");
        a.setAdress("Paris");
        a.setMail("test@test.com");
        dao.save(em, a);
        em.getTransaction().commit();
        assertNotNull(a.getId());
        em.clear();
        Annonce found = dao.findById(em, a.getId());
        assertEquals("Vélo Rouge", found.getTitle());
    }

    @Test
    void testPagination() {
        em.getTransaction().begin();
        for (int i = 0; i < 15; i++) {
            Annonce a = new Annonce();
            a.setTitle("Annonce " + i);
            a.setDescription("Desc");
            a.setAdress("Loc");
            a.setMail("mail@test.com");
            dao.save(em, a);
        }
        em.getTransaction().commit();
        List<Annonce> page1 = dao.findAllPaginated(em, 1, 10);
        assertEquals(10, page1.size());
    }
}