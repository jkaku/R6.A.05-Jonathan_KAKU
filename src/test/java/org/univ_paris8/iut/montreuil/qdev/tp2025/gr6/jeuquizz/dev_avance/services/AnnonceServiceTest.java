package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Category;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.User;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.AnnonceRepository;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.CategoryRepository;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.UserRepository;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.service.AnnonceService;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.utils.JPAProvider;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AnnonceServiceTest {

    @Test
    void addAnnonce_ShouldCommitTransaction_WhenUserAndCategoryExist() throws Exception {
        EntityManager em = mock(EntityManager.class);
        EntityTransaction tx = mock(EntityTransaction.class);
        AnnonceRepository repoMock = mock(AnnonceRepository.class); // Renommé pour clarté
        UserRepository userRepoMock = mock(UserRepository.class);
        CategoryRepository catRepoMock = mock(CategoryRepository.class);
        when(em.getTransaction()).thenReturn(tx);
        User fakeUser = new User();
        fakeUser.setUsername("jkaku");
        when(userRepoMock.findByUsername("jkaku")).thenReturn(fakeUser);
        Category fakeCat = new Category();
        fakeCat.setId(1L);
        fakeCat.setLabel("Test Category");
        when(em.find(Category.class, 1L)).thenReturn(fakeCat);
        try (MockedStatic<JPAProvider> mockedJPA = mockStatic(JPAProvider.class)) {
            mockedJPA.when(JPAProvider::getEntityManager).thenReturn(em);
            AnnonceService business = new AnnonceService();
            injectPrivateField(business, "repo", repoMock);
            injectPrivateField(business, "userRepo", userRepoMock);
            injectPrivateField(business, "catRepo", catRepoMock);
            Annonce a = new Annonce();
            a.setTitle("Test Annonce");
            business.addAnnonce(a, "jkaku", 1L);
            verify(tx).begin();
            verify(repoMock).save(eq(em), any(Annonce.class));
            verify(tx).commit();
            verify(em).close();
        }
    }

    private void injectPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}