<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inscription - MasterAnnonce</title>
    <style>
        body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: #f0f2f5; }
        .form-container { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 350px; }
        h2 { text-align: center; color: #1c1e21; }
        input { width: 100%; padding: 12px; margin: 8px 0; border: 1px solid #ddd; border-radius: 6px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background-color: #42b72a; color: white; border: none; border-radius: 6px; cursor: pointer; font-weight: bold; }
        button:hover { background-color: #36a420; }
        .footer-link { text-align: center; margin-top: 15px; font-size: 0.9em; }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Créer un compte</h2>
    <form action="register" method="post">
        <input type="text" name="username" placeholder="Nom d'utilisateur" required>
        <input type="email" name="email" placeholder="Adresse e-mail" required>
        <input type="password" name="password" placeholder="Mot de passe" required>
        <button type="submit">S'inscrire</button>
    </form>
    <div class="footer-link">
        Déjà inscrit ? <a href="login">Se connecter</a>
    </div>
</div>
</body>
</html>