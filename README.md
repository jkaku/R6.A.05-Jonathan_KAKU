# TP Dev Avancé - Backend API Sécurisé

## Architecture
Ce projet respecte une architecture n-tiers stricte sans Spring :
- **API (JAX-RS)** : `AnnonceResource`, `AuthResource`. Utilise des DTOs.
- **Service** : Gestion des transactions et règles métier (immuabilité, droits).
- **DAO** : Persistance via JPA/Hibernate.
- **Sécurité** : Implémentation JAAS complète (LoginModule custom) avec authentification Stateless (Token).

## Sécurité JAAS (Bonus Exercice 5)
L'authentification se fait en deux temps :
1. **Login** : `DBLoginModule` vérifie le couple user/pass en base et génère un Token.
2. **Requêtes** : `TokenLoginModule` intercepte le token Bearer via un filtre JAX-RS pour reconstruire l'identité (Subject/Principal) à chaque requête.

## Lancement
1. Base de données PostgreSQL requise (config dans `persistence.xml`).
2. Ajouter l'option VM : `-Djava.security.auth.login.config=src/main/resources/jaas.config`