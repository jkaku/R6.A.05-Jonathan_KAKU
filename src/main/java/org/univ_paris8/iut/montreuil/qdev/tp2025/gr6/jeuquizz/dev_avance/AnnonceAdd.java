package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Category;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Status;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.User;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.CategoryRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AnnonceAdd")
public class AnnonceAdd extends HttpServlet {
    private AnnonceService annonceService = new AnnonceService();
    private CategoryRepository catRepo = new CategoryRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categories", catRepo.findAll());
        this.getServletContext().getRequestDispatcher("/AnnonceAdd.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        Annonce a = new Annonce();
        a.setTitle(request.getParameter("title"));
        a.setDescription(request.getParameter("description"));
        a.setAdress(request.getParameter("adress"));
        a.setMail(request.getParameter("mail"));
        a.setStatus(Status.DRAFT);
        a.setAuthor(currentUser);
        Long catId = Long.parseLong(request.getParameter("categoryId"));
        Category cat = catRepo.findById(catId);
        a.setCategory(cat);
        annonceService.createAnnonce(a);

        response.sendRedirect("AnnonceList");
    }
}
