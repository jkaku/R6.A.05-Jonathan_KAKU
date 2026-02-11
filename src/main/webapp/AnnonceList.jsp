<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Liste des Annonces</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 20px; font-family: sans-serif; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #f8f9fa; }
        .btn-add { background-color: #28a745; color: white; padding: 10px 15px; text-decoration: none; border-radius: 4px; display: inline-block; }
        .action-link { margin-right: 8px; text-decoration: none; font-size: 0.9em; }
        .status-tag { font-size: 0.8em; padding: 3px 6px; border-radius: 3px; background: #eee; }
        .pagination { margin-top: 20px; text-align: center; }
        .page-btn { padding: 8px 12px; border: 1px solid #ddd; text-decoration: none; color: #333; }
        .disabled { color: #ccc; pointer-events: none; }
    </style>
</head>
<body>

<h1>📋 Toutes les annonces</h1>

<div style="margin-bottom: 20px;">
    <a href="AnnonceAdd" class="btn-add">+ Publier une annonce</a>
</div>

<hr>

<c:choose>
    <c:when test="${not empty annonces}">
        <table>
            <thead>
            <tr>
                <th>Titre / Statut</th>
                <th>Adresse</th>
                <th>Contact</th>
                <th>Date</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${annonces}" var="a">
                <tr>
                    <td>
                        <strong>${a.title}</strong><br>
                        <span class="status-tag">${a.status}</span>
                    </td>
                    <td>${a.adress}</td>
                    <td>${a.mail}</td>
                    <td>${a.date}</td>
                    <td>
                        <c:if test="${a.status == 'DRAFT'}">
                            <a href="AnnonceAction?action=publish&id=${a.id}" class="action-link" style="color: green;">🚀 Publier</a>
                        </c:if>
                        <c:if test="${a.status == 'PUBLISHED'}">
                            <a href="AnnonceAction?action=archive&id=${a.id}" class="action-link" style="color: orange;">📁 Archiver</a>
                        </c:if>

                        <a href="AnnonceUpdate?id=${a.id}" class="action-link" style="color: blue;">Modifier</a>
                        <a href="AnnonceDelete?id=${a.id}" class="action-link" style="color: red;">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%-- PAGINATION HORS DU TABLEAU --%>
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="AnnonceList?page=${currentPage - 1}" class="page-btn">« Précédent</a>
            </c:if>

            <span style="margin: 0 15px;">Page <strong>${currentPage}</strong></span>

            <c:if test="${hasNext}">
                <a href="AnnonceList?page=${currentPage + 1}" class="page-btn">Suivant »</a>
            </c:if>
        </div>
    </c:when>
    <c:otherwise>
        <div style="padding: 40px; text-align: center; background: #f9f9f9; border-radius: 8px;">
            <p>Aucune annonce n'a été trouvée en base de données.</p>
            <a href="AnnonceAdd">Soyez le premier à en publier une !</a>
        </div>
    </c:otherwise>
</c:choose>

</body>
</html>