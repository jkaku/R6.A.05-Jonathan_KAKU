<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Inscription - MasterAnnonce</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: #f0f2f5; }
        .form-container { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 350px; }
        h2 { text-align: center; color: #1c1e21; margin-bottom: 20px; }
        .error-main { color: #d93025; background: #fce8e6; padding: 10px; border-radius: 4px; margin-bottom: 15px; font-size: 0.85rem; }
        .error-field { color: red; font-size: 0.75rem; display: block; margin-bottom: 8px; }
        input { width: 100%; padding: 12px; margin: 5px 0; border: 1px solid #ddd; border-radius: 6px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background-color: #42b72a; color: white; border: none; border-radius: 6px; cursor: pointer; font-weight: bold; margin-top: 10px; }
        button:hover { background-color: #36a420; }
        .footer-link { text-align: center; margin-top: 15px; font-size: 0.9em; }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Créer un compte</h2>

    <c:if test="${not empty error}">
        <div class="error-main">${error}</div>
    </c:if>

    <form action="register" method="post">
        <input type="text" name="username" placeholder="Nom d'utilisateur" value="${user.username}">
        <c:if test="${not empty errors.username}"><span class="error-field">${errors.username}</span></c:if>

        <input type="email" name="email" placeholder="Adresse e-mail" value="${user.email}">
        <c:if test="${not empty errors.email}"><span class="error-field">${errors.email}</span></c:if>

        <input type="password" name="password" placeholder="Mot de passe">
        <c:if test="${not empty errors.password}"><span class="error-field">${errors.password}</span></c:if>

        <button type="submit">S'inscrire</button>
    </form>

    <div class="footer-link">
        Déjà inscrit ? <a href="login">Se connecter</a>
    </div>
</div>
</body>
</html>