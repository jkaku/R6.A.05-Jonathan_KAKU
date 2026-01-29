package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.Annonce;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AnnonceAdd")
public class AnnonceAdd extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/AnnonceAdd.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String desc = request.getParameter("description");
        String addr = request.getParameter("adress");
        String mail = request.getParameter("mail");

        if (title != null && desc != null && addr != null && mail != null) {
            try {
                Annonce a = new Annonce();
                a.setTitle(title);
                a.setDescription(desc);
                a.setAdress(addr);
                a.setMail(mail);

                new AnnonceDAO().create(a);
                response.sendRedirect("AnnonceList"); // On redirige vers la liste
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
