# Rapport de Projet - Application MasterAnnonce (TP Dév Avancé)

## 1. Architecture du Projet
L'application adopte une architecture en couches (**N-Tier**) s'appuyant sur le design pattern **MVC** (Modèle-Vue-Contrôleur). Cette structure permet une maintenance facilitée et une séparation claire des responsabilités.



### Détail des composants :
* **Modèle** : Localisé dans le package `models`. Les entités incluent `Annonce`, `User` et `Category`.
* **Repository** : Situé dans `repositories`. Utilise l' `EntityManager` pour effectuer les opérations CRUD.
* **Service** : Couche intermédiaire gérant la logique métier et les transactions JPA (via `JPAUtils`).
* **Contrôleur (Servlets & Filters)** : Les servlets (`AnnonceAdd`, `Login`, etc.) pilotent le flux de l'application. Le `AuthFilter` sécurise les accès.
* **Vue (JSP & JSTL)** : Utilisation de JSTL (`c:forEach`, `c:if`) et d'Expression Language (EL) pour un affichage dynamique sans code Java dans les vues.

---

## 2. Problèmes rencontrés & Solutions

| Problème rencontré | Cause identifiée | Solution apportée |
| :--- | :--- | :--- |
| **FATAL: PAM authentication failed** | Tentative de connexion avec l'utilisateur `postgres` restreint sur l'environnement IUT. | Utilisation des identifiants personnels (`jkaku`) configurés dans le `persistence.xml`. |
| **Erreur 404 sur les Servlets** | Incompatibilité entre Tomcat 10 (Jakarta) et les librairies `javax.servlet` du projet. | Downgrade vers **Tomcat 9** pour conserver la compatibilité avec le standard Java EE utilisé en cours. |
| **NullPointerException (JPAUtils)** | Fichier `persistence.xml` introuvable ou mal nommé au déploiement. | Relocalisation stricte dans `src/main/resources/META-INF/` et renommage de la Persistence Unit. |
| **LazyInitializationException** | Tentative d'accès à une `Category` alors que l'EntityManager était fermé. | Utilisation de `JOIN FETCH` ou chargement de l'objet avant la fermeture de la session dans le Service. |



---

## 3. Gestion de la Validation et des Erreurs (Exercice 6)

L'application intègre une gestion robuste des entrées utilisateur :
1.  **Validation Serveur** : Utilisation de **Hibernate Validator** au sein des servlets. Si un objet `Annonce` ou `User` ne respecte pas les contraintes (`@NotBlank`, `@Email`, `@Size`), l'enregistrement est stoppé.
2.  **Conservation des Saisies** : En cas d'erreur, l'utilisateur est renvoyé vers le formulaire via un `forward`. Les données déjà saisies sont réinjectées dans les champs (attribut `value="${...}"`) pour éviter une nouvelle saisie intégrale.
3.  **Messages d'Erreur** : Les messages renvoyés par le validateur sont stockés dans une `Map` et affichés dynamiquement sous chaque champ concerné dans la JSP.

---

## 4. Sécurité
Un filtre de sécurité (`AuthFilter`) a été mis en place. Il intercepte toutes les routes de modification (`/AnnonceAdd`, `/AnnonceUpdate`, `/AnnonceDelete`, `/AnnonceAction`).
* Si la session est vide (objet `user` absent), l'utilisateur est redirigé vers `/login`.
* Les pages `/register` et `/login` sont explicitement exclues pour éviter les boucles de redirection.

---

## 5. Guide de déploiement

### Pré-requis base de données
Utiliser votre BD postgres à disposition  :
```sql
INSERT INTO categories (label) VALUES ('Immobilier'), ('Emploi'), ('Véhicules'), ('Divers');
-- Utilisateur par défaut
INSERT INTO users (username, email, password) VALUES ('jkaku', 'jkaku@univ-paris8.fr', 'pass123');
```

Evidement j'ai demandé à l'IA de remixer le ReadMe hein...