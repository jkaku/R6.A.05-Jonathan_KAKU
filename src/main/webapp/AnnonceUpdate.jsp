<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Modifier l'annonce</title>
    <style>
        .error { color: #d9534f; font-size: 0.85em; display: block; margin-top: 4px; }
        .form-group { margin-bottom: 20px; font-family: sans-serif; }
        label { display: block; font-weight: bold; margin-bottom: 5px; }
        input, select, textarea { width: 100%; max-width: 400px; padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        .btn-submit { background-color: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; }
    </style>
</head>
<body>

<div style="padding: 20px;">
    <h1>✏️ Modifier l'annonce</h1>
    <hr>

    <form method="post" action="AnnonceUpdate">
        <input type="hidden" name="id" value="${annonce.id}">

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
                <c:forEach items="${categories}" var="c">

                    <option value="${c.id}" ${c.id == annonce.category.id ? 'selected' : ''}>
                            ${c.label}
                    </option>
                </c:forEach>
            </select>
            <c:if test="${not empty errors.category}">
                <span class="error">Veuillez sélectionner une catégorie</span>
            </c:if>
        </div>

        <div class="form-group">
            <label>Description :</label>
            <textarea name="description" rows="4">${annonce.description}</textarea>
            <c:if test="${not empty errors.description}">
                <span class="error">${errors.description}</span>
            </c:if>
        </div>

        <div class="form-group">
            <label>Adresse :</label>
            <input type="text" name="adress" value="${annonce.adress}">
        </div>

        <div class="form-group">
            <label>Email de contact :</label>
            <input type="email" name="mail" value="${annonce.mail}">
            <c:if test="${not empty errors.mail}">
                <span class="error">${errors.mail}</span>
            </c:if>
        </div>

        <div style="margin-top: 30px;">
            <button type="submit" class="btn-submit">Enregistrer les modifications</button>
            <a href="AnnonceList" style="margin-left: 15px;">Annuler</a>
        </div>
    </form>
</div>

</body>
</html>