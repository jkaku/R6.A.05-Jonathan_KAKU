<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Ajouter une annonce</title>
    <style>
        .error { color: #d9534f; font-size: 0.8em; display: block; margin-top: 5px; }
        .form-group { margin-bottom: 15px; }
        input, select, textarea { display: block; width: 300px; padding: 8px; }
    </style>
</head>
<body>
<h1>Publier une annonce</h1>

<form method="post" action="AnnonceAdd">

    <div class="form-group">
        <label>Titre :</label>
        <input type="text" name="title" value="${annonce.title}">
        <c:if test="${not empty errors.title}">
            <span class="error">${errors.title}</span>
        </c:if>
    </div>

    <div class="form-group">
        <label>Catégorie :</label>
        <select name="categoryId">
            <option value="">-- Choisir --</option>
            <c:forEach items="${categories}" var="c">
                <option value="${c.id}" ${c.id == annonce.category.id ? 'selected' : ''}>
                        ${c.label}
                </option>
            </c:forEach>
        </select>
        <c:if test="${not empty errors.category}">
            <span class="error">La catégorie est obligatoire</span>
        </c:if>
    </div>

    <div class="form-group">
        <label>Description :</label>
        <textarea name="description">${annonce.description}</textarea>
        <c:if test="${not empty errors.description}">
            <span class="error">${errors.description}</span>
        </c:if>
    </div>

    <div class="form-group">
        <label>Adresse :</label>
        <input type="text" name="adress" value="${annonce.adress}">
    </div>

    <div class="form-group">
        <label>Mail de contact :</label>
        <input type="email" name="mail" value="${annonce.mail}">
        <c:if test="${not empty errors.mail}">
            <span class="error">${errors.mail}</span>
        </c:if>
    </div>

    <button type="submit">Créer l'annonce</button>
    <a href="AnnonceList">Retour</a>
</form>
</body>
</html>