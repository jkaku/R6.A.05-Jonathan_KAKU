<form method="post" action="AnnonceAdd">
    Titre : <input type="text" name="title" required><br>

    Catégorie :
    <select name="categoryId">
        <c:forEach items="${categories}" var="c">
            <option value="${c.id}">${c.label}</option>
        </c:forEach>
    </select><br>

    Description : <textarea name="description"></textarea><br>
    Adresse : <input type="text" name="adress"><br>
    Mail : <input type="email" name="mail"><br>

    <button type="submit">Créer l'annonce</button>
</form>