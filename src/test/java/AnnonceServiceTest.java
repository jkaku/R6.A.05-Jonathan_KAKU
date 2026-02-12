import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Status;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.AnnonceRepository;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.AnnonceService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnnonceServiceTest {

    @Mock
    private AnnonceRepository annonceRepository;

    @InjectMocks
    private AnnonceService service;

    @Test
    public void testPublishAnnonceChangesStatus() {
        Annonce a = new Annonce();
        a.setId(1L);
        a.setStatus(Status.DRAFT);

        when(annonceRepository.findById(1L)).thenReturn(a);

        service.publishAnnonce(1L);

        assertEquals(Status.PUBLISHED, a.getStatus());
        verify(annonceRepository).update(a);
    }
}