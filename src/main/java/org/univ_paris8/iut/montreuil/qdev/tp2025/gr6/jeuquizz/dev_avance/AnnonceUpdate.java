package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.CategoryRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AnnonceUpdate")
public class AnnonceUpdate extends HttpServlet {
    private AnnonceService service = new AnnonceService();
    private CategoryRepository catRepo = new CategoryRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        request.setAttribute("annonce", service.getAnnonceById(id));
        request.setAttribute("categories", catRepo.findAll());

        this.getServletContext().getRequestDispatcher("/AnnonceUpdate.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Annonce a = service.getAnnonceById(id);

        a.setTitle(request.getParameter("title"));
        a.setDescription(request.getParameter("description"));
        a.setAdress(request.getParameter("adress"));

        Long catId = Long.parseLong(request.getParameter("categoryId"));
        a.setCategory(catRepo.findById(catId));

        service.updateAnnonce(a);
        response.sendRedirect("AnnonceList");
    }
}
