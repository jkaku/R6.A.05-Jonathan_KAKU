package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.servlet;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.AnnonceService;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AnnonceDelete")
public class AnnonceDelete extends HttpServlet {
    private AnnonceService service = new AnnonceService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                Long id = Long.parseLong(idParam);
                Annonce a = service.getAnnonceById(id);

                if (a != null) {
                    request.setAttribute("annonce", a);
                    this.getServletContext().getRequestDispatcher("/AnnonceDelete.jsp").forward(request, response);
                    return;
                }
            }
        } catch (NumberFormatException e) {
        }
        response.sendRedirect("AnnonceList");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            service.deleteAnnonce(id);
            response.sendRedirect("AnnonceList?msg=deleted");
        } catch (Exception e) {
            request.setAttribute("error", "Impossible de supprimer cette annonce : " + e.getMessage());
            doGet(request, response);
        }
    }
}