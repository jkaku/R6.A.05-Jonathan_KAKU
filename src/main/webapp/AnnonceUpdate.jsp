<form method="post" action="AnnonceUpdate">
    <input type="hidden" name="id" value="${annonce.id}">

    Titre : <label>
    <input type="text" name="title" value="${annonce.title}">
</label><br>
    Description : <label>
    <textarea name="description">${annonce.description}</textarea>
</label><br>
    Adresse : <label>
    <input type="text" name="adress" value="${annonce.adress}">
</label><br>
    Mail : <label>
    <input type="email" name="mail" value="${annonce.mail}">
</label><br>

    <button type="submit">Enregistrer les modifications</button>
</form>