package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.servlet;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.AnnonceService;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.*;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.CategoryRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.validation.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/AnnonceUpdate")
public class AnnonceUpdate extends HttpServlet {
    private AnnonceService service = new AnnonceService();
    private CategoryRepository catRepo = new CategoryRepository();

    private static Validator validator;
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Annonce a = service.getAnnonceById(id);

            if (a != null) {
                request.setAttribute("annonce", a);
                request.setAttribute("categories", catRepo.findAll());
                this.getServletContext().getRequestDispatcher("/AnnonceUpdate.jsp").forward(request, response);
            } else {
                response.sendRedirect("AnnonceList");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("AnnonceList");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Annonce a = service.getAnnonceById(id);
        a.setTitle(request.getParameter("title"));
        a.setDescription(request.getParameter("description"));
        a.setAdress(request.getParameter("adress"));
        a.setMail(request.getParameter("mail"));
        Long catId = Long.parseLong(request.getParameter("categoryId"));
        a.setCategory(catRepo.findById(catId));
        Set<ConstraintViolation<Annonce>> violations = validator.validate(a);
        if (violations.isEmpty()) {
            service.updateAnnonce(a);
            response.sendRedirect("AnnonceList");
        } else {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<Annonce> v : violations) {
                errors.put(v.getPropertyPath().toString(), v.getMessage());
            }
            request.setAttribute("errors", errors);
            request.setAttribute("annonce", a);
            request.setAttribute("categories", catRepo.findAll());

            this.getServletContext().getRequestDispatcher("/AnnonceUpdate.jsp").forward(request, response);
        }
    }
}