import org.junit.jupiter.api.Test;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.AnnonceRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnnonceRepositoryTest {
    private AnnonceRepository repo = new AnnonceRepository();

    @Test
    public void testCreateAndFind() {
        Annonce a = new Annonce();
        a.setTitle("Test CRUD");
        a.setMail("test@test.com");

        repo.create(a);

        assertNotNull(a.getId());
        Annonce found = repo.findById(a.getId());
        assertEquals("Test CRUD", found.getTitle());
    }

    @Test
    public void testPagination() {
        List<Annonce> list = repo.findAllPaginated(1, 5);
        assertTrue(list.size() <= 5);
    }
}