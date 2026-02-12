package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.servlet;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.AnnonceService;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AnnonceList")
public class AnnonceList extends HttpServlet {
    private AnnonceService service = new AnnonceService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int size = 5;
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                page = Math.max(1, Integer.parseInt(pageParam));
            }
        } catch (NumberFormatException e) {
            page = 1;
        }
        List<Annonce> list = service.getAnnoncesByPage(page, size);
        request.setAttribute("annonces", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("hasNext", list.size() == size);
        this.getServletContext().getRequestDispatcher("/AnnonceList.jsp").forward(request, response);
    }
}