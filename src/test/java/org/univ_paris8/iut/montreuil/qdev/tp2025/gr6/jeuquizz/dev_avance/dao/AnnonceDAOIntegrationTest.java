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
        // On charge l'unité de test définie ci-dessus
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
        // GIVEN
        em.getTransaction().begin();
        Annonce a = new Annonce();
        a.setTitle("Vélo Rouge");
        a.setDescription("Un beau vélo");
        a.setAdress("Paris");
        a.setMail("test@test.com");

        // WHEN
        dao.save(em, a);
        em.getTransaction().commit();

        // THEN
        assertNotNull(a.getId());

        em.clear(); // On vide le cache pour forcer une vraie requête SELECT
        Annonce found = dao.findById(em, a.getId());
        assertEquals("Vélo Rouge", found.getTitle());
    }

    @Test
    void testPagination() {
        // GIVEN: On insère 15 annonces
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

        // WHEN: On demande la page 1 (taille 10)
        List<Annonce> page1 = dao.findAllPaginated(em, 1, 10);

        // THEN
        assertEquals(10, page1.size());
    }
}