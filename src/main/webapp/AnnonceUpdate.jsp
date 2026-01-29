<form method="post" action="AnnonceUpdate">
    <input type="hidden" name="id" value="${annonce.id}">

    Titre : <input type="text" name="title" value="${annonce.title}"><br>
    Description : <textarea name="description">${annonce.description}</textarea><br>
    Adresse : <input type="text" name="adress" value="${annonce.adress}"><br>
    Mail : <input type="email" name="mail" value="${annonce.mail}"><br>

    <button type="submit">Enregistrer les modifications</button>
</form>