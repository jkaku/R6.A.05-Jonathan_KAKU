<form method="post" action="AnnonceUpdate">
    <input type="hidden" name="id" value="${annonce.id}">

    Titre : <input type="text" name="title" value="${annonce.title}"><br>

    Catégorie :
    <select name="categoryId">
        <c:forEach items="${categories}" var="c">
            <option value="${c.id}" ${c.id == annonce.category.id ? 'selected' : ''}>
                    ${c.label}
            </option>
        </c:forEach>
    </select><br>

    Description : <textarea name="description">${annonce.description}</textarea><br>
    Adresse : <input type="text" name="adress" value="${annonce.adress}"><br>

    <button type="submit">Mettre à jour</button>
</form>