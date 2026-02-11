package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.servlet;

import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.models.User;
import org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.validation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@WebServlet("/register")
public class Register extends HttpServlet {
    private UserRepository userRepo = new UserRepository();

    private static Validator validator;
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User newUser = new User();
        newUser.setUsername(request.getParameter("username"));
        newUser.setEmail(request.getParameter("email"));
        newUser.setPassword(request.getParameter("password"));
        newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        Set<ConstraintViolation<User>> violations = validator.validate(newUser);

        if (violations.isEmpty()) {
            try {
                userRepo.create(newUser);
                response.sendRedirect("login");
            } catch (Exception e) {
                request.setAttribute("error", "Ce compte ne peut pas être créé (pseudo ou email déjà utilisé).");
                request.setAttribute("user", newUser);
                this.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } else {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<User> v : violations) {
                errors.put(v.getPropertyPath().toString(), v.getMessage());
            }

            request.setAttribute("errors", errors);
            request.setAttribute("user", newUser);
            this.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}