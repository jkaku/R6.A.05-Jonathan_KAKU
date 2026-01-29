<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table>
    <tr><th>Titre</th><th>Mail</th><th>Actions</th></tr>
    <c:forEach items="${annonces}" var="a">
        <tr>
            <td>${a.title}</td>
            <td>${a.mail}</td>
            <td>
                <a href="AnnonceUpdate?id=${a.id}">Modifier</a>
                <a href="AnnonceDelete?id=${a.id}">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
</table>