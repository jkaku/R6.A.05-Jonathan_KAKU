<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Connexion - MasterAnnonce</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f0f2f5; margin: 0; }
        .login-container { background: white; padding: 2.5rem; border-radius: 12px; box-shadow: 0 8px 24px rgba(0,0,0,0.1); width: 350px; }
        h2 { text-align: center; color: #1c1e21; margin-bottom: 1.5rem; }
        .error-msg { color: #d93025; background: #fce8e6; padding: 12px; border-radius: 6px; margin-bottom: 1.5rem; font-size: 0.85rem; border: 1px solid #f5c2c7; }
        label { display: block; margin-bottom: 5px; font-weight: 600; color: #4b4b4b; }
        input { width: 100%; padding: 12px; margin-bottom: 1.2rem; border: 1px solid #ddd; border-radius: 6px; box-sizing: border-box; transition: border-color 0.2s; }
        input:focus { border-color: #007bff; outline: none; }
        button { width: 100%; padding: 12px; background-color: #007bff; color: white; border: none; border-radius: 6px; cursor: pointer; font-size: 1rem; font-weight: bold; }
        button:hover { background-color: #0056b3; }
        .register-link { text-align: center; margin-top: 1.5rem; font-size: 0.9rem; color: #65676b; }
        .register-link a { color: #007bff; text-decoration: none; font-weight: 600; }
    </style>
</head>
<body>

<div class="login-container">
    <h2>Connexion</h2>

    <c:if test="${not empty error}">
        <div class="error-msg">
                ${error}
        </div>
    </c:if>

    <form action="login" method="post">
        <label for="username">Nom d'utilisateur</label>
        <input type="text" id="username" name="username" value="${lastUsername}" required>

        <label for="password">Mot de passe</label>
        <input type="password" id="password" name="password" required>

        <button type="submit">Se connecter</button>
    </form>

    <div class="register-link">
        Pas encore de compte ? <a href="register">S'inscrire</a>
    </div>

    <p style="font-size: 0.75rem; text-align: center; color: #999; margin-top: 1rem;">
        Support : jkaku / smiloudi
    </p>
</div>

</body>
</html>