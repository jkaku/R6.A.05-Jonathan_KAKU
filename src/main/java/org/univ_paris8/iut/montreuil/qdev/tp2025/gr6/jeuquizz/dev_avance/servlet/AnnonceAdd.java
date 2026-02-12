package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.servlet;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.AnnonceService;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.*;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.CategoryRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.validation.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/AnnonceAdd")
public class AnnonceAdd extends HttpServlet {
    private AnnonceService annonceService = new AnnonceService();
    private CategoryRepository catRepo = new CategoryRepository();

    // Initialisation du validateur Hibernate
    private static Validator validator;
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categories", catRepo.findAll());
        this.getServletContext().getRequestDispatcher("/AnnonceAdd.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");

        // 1. On peuple l'objet avec les saisies
        Annonce a = new Annonce();
        a.setTitle(request.getParameter("title"));
        a.setDescription(request.getParameter("description"));
        a.setAdress(request.getParameter("adress"));
        a.setMail(request.getParameter("mail"));
        a.setStatus(Status.DRAFT);
        a.setAuthor(currentUser);

        Long catId = null;
        if (request.getParameter("categoryId") != null) {
            catId = Long.parseLong(request.getParameter("categoryId"));
            a.setCategory(catRepo.findById(catId));
        }

        // 2. Lancement de la validation
        Set<ConstraintViolation<Annonce>> violations = validator.validate(a);

        if (violations.isEmpty()) {
            // OK : On enregistre
            annonceService.createAnnonce(a);
            response.sendRedirect("AnnonceList");
        } else {
            // ERREUR : On prépare le retour
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<Annonce> v : violations) {
                errors.put(v.getPropertyPath().toString(), v.getMessage());
            }

            // On renvoie l'objet 'a' pour pré-remplir les champs (Conservation)
            request.setAttribute("annonce", a);
            request.setAttribute("errors", errors);

            // On doit recharger les catégories pour le <select>
            request.setAttribute("categories", catRepo.findAll());

            this.getServletContext().getRequestDispatcher("/AnnonceAdd.jsp").forward(request, response);
        }
    }
}