# BackEnd – Vente aux enchères

## Description

Ce projet constitue le back-end d’une application de gestion de lots pour un site de vente aux enchères.  
Il permet notamment :
- De visualiser les lots disponibles
- De filtrer par catégorie ou sous-catégorie (hiérarchisation)
- D’obtenir le détail de chaque lot
- De gérer les enchères (placer, consulter, clôture automatique)
- De gérer les utilisateurs (inscription)

Ce projet s’adresse principalement aux professeurs dans le cadre d’un projet pédagogique.

---

## Statut du projet

**En développement**  
Les fonctionnalités de base d’affichage, de filtrage des lots et de gestion des enchères sont implémentées.

---

## Prérequis

- **Java** : 17
- **Maven** : 3.8+
- **MySQL** : 8+
- **Docker** (optionnel, pour le déploiement avec Docker Compose)

## Technologies utilisées

- **Spring Boot** : 3.2.4
- **Spring Data JPA**
- **Spring Web**
- **Spring Security**
- **MySQL Connector/J** : 9.2.0
- **JJWT (Java JWT)** : 0.11.5
- **JUnit Jupiter** : 5.10.2 (pour les tests)

---

## Installation

1. **Cloner le dépôt**
```bash
git clone <url-du-depot>
cd <nom-du-repo>
```

2. **Configurer la base de données**

- Créer la base de données `bidster` dans MySQL.
- Exécuter le script SQL fourni (`src/main/resources/DATASET.sql`) pour créer les tables et insérer un jeu de données.

3. **Configurer les variables d’environnement**

- Modifier le fichier `src/main/resources/application.properties` avec vos identifiants MySQL.

4. **Installer les dépendances et démarrer l’application**
```bash
mvn clean install
mvn spring-boot:run
```

Le serveur démarre par défaut sur le port 8080.

---

## Déploiement avec Docker Compose
1. **Cloner le dépôt**

```bash
git clone <url-du-depot>
cd <nom-du-repo>
```

2. **Lancer l’application et la base de données**

```bash
docker-compose up
```

Cette commande démarre automatiquement :

- Un conteneur MySQL avec la base bidster et l’exécution du script SQL fourni.
- Un conteneur Spring Boot exposé sur le port 8080.

3. **Accéder à l’application**

Le serveur démarre par défaut sur le port 8080 (http://localhost:8080).

4. **Arrêter l’application**

```bash
docker-compose down
```
---

## Endpoints principaux

| **Endpoint**                                             | **Méthode** | **Accès**                | **Description**                                                                                  |
|----------------------------------------------------------|-------------|--------------------------|--------------------------------------------------------------------------------------------------|
| **Authentification**                                     |             |                          |                                                                                                  |
| `/auth/login`                                            | POST        | Tout le monde            | Authentification, retourne un JWT                                                                |
| **Utilisateurs**                                         |             |                          |                                                                                                  |
| `/v1/utilisateurs`                                       | POST        | Tout le monde            | Création d’un utilisateur                                                                        |
| `/v1/utilisateurs/{nomUtilisateur}`                      | GET         | Connecté (propre compte) | Récupère les infos d’un utilisateur                                                              |
| `/v1/utilisateurs/{nomUtilisateur}/ajouter-solde`        | PATCH       | Connecté (propre compte) | Ajoute du solde à l’utilisateur                                                                  |
| `/v1/utilisateurs/{nomUtilisateur}/reduire-solde`        | PATCH       | Connecté (propre compte) | Réduit le solde de l’utilisateur                                                                 |
| `/v1/utilisateurs/{nomUtilisateur}/lots-vendus`          | GET         | Connecté (propre compte) | Récupère les lots vendus par l’utilisateur                                                       |
| `/v1/utilisateurs/{nomUtilisateur}/lots-gagnes`          | GET         | Connecté (propre compte) | Récupère les lots gagnés par l’utilisateur                                                       |
| `/v1/utilisateurs/{nomUtilisateur}`                      | PUT         | Connecté (propre compte) | Met à jour les informations de l’utilisateur                                                     |
| **Lots**                                                 |             |                          |                                                                                                  |
| `/v1/lots/{id}`                                          | GET         | Tout le monde            | Détail d’un lot                                                                                  |
| `/v1/lots/all`                                           | GET         | Tout le monde            | Liste de tous les lots                                                                           |
| `/v1/lots/categorie-principale/{nom}`                    | GET         | Tout le monde            | Lots d’une catégorie principale (et sous-catégories)                                             |
| `/v1/lots/sous-categorie/{nom}`                          | GET         | Tout le monde            | Lots d’une sous-catégorie                                                                        |
| `/v1/lots`                                               | POST        | Connecté                 | Création d’un lot (avec image optionnelle)                                                       |
| **Enchères**                                             |             |                          |                                                                                                  |
| `/v1/encheres/placer`                                    | POST        | Connecté                 | Place une enchère sur un lot                                                                     |
| `/v1/encheres/lot/{lotId}`                               | GET         | Tout le monde            | Liste des enchères pour un lot                                                                   |
| `/v1/encheres/lot/{lotId}/meilleure`                     | GET         | Tout le monde            | Meilleure enchère pour un lot                                                                    |
| `/v1/encheres/utilisateur/{nomUtilisateur}`              | GET         | Connecté (propre compte) | Historique des enchères d’un utilisateur                                                         |
| `/v1/encheres/lots/{id}/confirmation`                    | POST        | Connecté (propre compte) | Confirme la vente d’un lot (gagné)                                                    |
| **Catégories**                                           |             |                          |                                                                                                  |
| `/v1/categories/principales`                             | GET         | Tout le monde            | Liste des catégories principales                                                                 |



---

## Structure du projet

Le projet est organisé comme suit :

```
.
├── .env
├── .gitignore
├── docker-compose.yml
├── Dockerfile
├── LICENSE
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── docs
│   ├── class_diagram.plantuml
│   ├── diagram_use_case.plantuml
│   ├── domain_model.puml
│   └── mdl.puml
├── images
├── src
│   ├── main
│   │   ├── java
│   │   │   └── ch
│   │   │       └── etmles
│   │   │           └── bidster
│   │   │               ├── BidsterApplication.java
│   │   │               ├── LoadDatabase.java
│   │   │               ├── StaticResourceConfig.java
│   │   │               ├── Categorie
│   │   │               │   ├── CategorieController.java
│   │   │               │   ├── CategorieDTO.java
│   │   │               │   ├── CategorieEntity.java
│   │   │               │   ├── CategorieNotFoundAdvice.java
│   │   │               │   ├── CategorieNotFoundException.java
│   │   │               │   ├── CategorieRepository.java
│   │   │               │   └── CategorieService.java
│   │   │               ├── Enchere
│   │   │               │   ├── DTO
│   │   │               │   ├── EnchereController.java
│   │   │               │   ├── EnchereEntity.java
│   │   │               │   ├── EnchereMontantInvalideException.java
│   │   │               │   ├── EnchereRepository.java
│   │   │               │   ├── EnchereService.java
│   │   │               │   ├── GlobalExceptionHandlerEnchere.java
│   │   │               │   └── SoldeInsuffisantException.java
│   │   │               ├── Lot
│   │   │               │   ├── DTO
│   │   │               │   ├── LotController.java
│   │   │               │   ├── LotEntity.java
│   │   │               │   ├── LotNotFoundAdvice.java
│   │   │               │   ├── LotNotFoundException.java
│   │   │               │   ├── LotRepository.java
│   │   │               │   └── LotService.java
│   │   │               ├── Security
│   │   │               │   ├── JwtAuthFilter.java
│   │   │               │   ├── JwtUtil.java
│   │   │               │   ├── RestAuthenticationEntryPoint.java
│   │   │               │   └── SecurityConfig.java
│   │   │               └── Utilisateur
│   │   │                   ├── Controller
│   │   │                   ├── DTO
│   │   │                   ├── EmailAlreadyExistsException.java
│   │   │                   ├── GlobalExceptionHandler.java
│   │   │                   ├── PasswordInvalidException.java
│   │   │                   ├── UserAlreadyExistsException.java
│   │   │                   ├── UtilisateurEntity.java
│   │   │                   ├── UtilisateurNotFoundException.java
│   │   │                   ├── UtilisateurRepository.java
│   │   │                   └── UtilisateurService.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── CREATE-DB-TABLES-USER.sql
│   │       └── DATASET.sql
│   └── test
│       └── java
│           └── ch
│               └── etmles
│                   └── bidster
│                       └── BidsterApplicationTests.java

```

## Détails des principaux dossiers

- `Categorie/` : Contient tout ce qui concerne la gestion des catégories et sous-catégories, y compris le contrôleur, les DTO, l'entité, le repository, le service, ainsi que la gestion des exceptions spécifiques.

- `Enchere/` : Gère les enchères, avec les composants habituels (contrôleur, entité, repository, service) et des exceptions spécifiques telles que `SoldeInsuffisantException` ou `EnchereMontantInvalideException`.

- `Lot/` : Regroupe la logique métier liée aux lots, avec une structure similaire aux modules précédents (contrôleur, entité, DTO, repository, service, exceptions).

- `Utilisateur/` : Prend en charge l'inscription, l'authentification et la gestion des utilisateurs, avec ses propres contrôleurs, DTO, entité, repository, service, et gestion d'exceptions (utilisateur existant, email déjà utilisé, etc.).

- `Security/` : Contient la configuration de la sécurité de l'application, notamment la gestion des JWT (`JwtUtil`, `JwtAuthFilter`), les points d’entrée sécurisés et la configuration globale (`SecurityConfig`).

- `BidsterApplication` : Point d’entrée principal de l’application Spring Boot.

- `LoadDatabase` : Classe utilitaire servant à initialiser ou charger des données en base au démarrage de l’application.

- `StaticResourceConfig` : Configuration pour l’accès aux ressources statiques (utile pour la gestion des fichiers côté front-end ou public).

- `resources/` : Contient les fichiers de configuration (`application.properties`) ainsi que les scripts SQL d’initialisation (`CREATE-DB-TABLES-USER.sql`, `DATASET.sql`).

- `test/` : Regroupe les tests unitaires et d’intégration pour les différentes couches de l’application. Par défaut, un test de chargement du contexte Spring est présent (`BidsterApplicationTests.java`).

- `docs/` : Dossier contenant les diagrammes UML du projet au format PlantUML, notamment le diagramme de classes, les cas d’utilisation, et le modèle de domaine.

- `images/` : Dossier prévu pour stocker les visuels utiles à la documentation ou à la présentation du projet.

- **Fichiers racine** : Le projet comprend également des fichiers de configuration et de gestion de projet à la racine, comme `.env`, `.gitignore`, `Dockerfile`, `docker-compose.yml`, `pom.xml`, et les wrappers Maven (`mvnw`, `mvnw.cmd`), ainsi que le fichier `README.md` et la licence (`LICENSE`).

---
## Tests

- Les endpoints peuvent être testés avec Postman ou via les commandes curl.
- (Tests automatisés à ajouter selon l’avancement du projet.)

---

## Collaboration

Vous souhaitez contribuer au projet ? Merci de suivre les étapes ci-dessous :

1. **Discuter de votre idée**  
   Proposez une *issue* pour présenter votre idée ou amélioration avant de commencer à coder.

2. **Forker et développer**  
   - Forkez le projet.  
   - Créez une branche dédiée pour chaque nouvelle fonctionnalité ou correction, en suivant la convention [Git Flow](https://nvie.com/posts/a-successful-git-branching-model/) :

     ```
     git checkout -b feature/ajout-gestion-encheres
     ```

3. **Créer une pull request**  
   - Soumettez une *pull request* avec une description claire et concise de vos changements.  
   - Expliquez le problème résolu ou la fonctionnalité ajoutée.

4. **Respecter la convention de commits**  
   - Utilisez le format suivant pour vos messages de commit :

     ```
     type: sujet
     ```

     Exemples :
     - `feat: ajout de la gestion des enchères`
     - `fix: correction du bug d'affichage des enchères`

   - Pour plus d'informations, vous pouvez consulter la documentation officielle :
     [https://www.conventionalcommits.org/fr/v1.0.0/](https://www.conventionalcommits.org/fr/v1.0.0/)

---

Toute contribution est la bienvenue. Merci pour votre implication !


## Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](./LICENSE) pour plus d’informations.

---

## Contact

Pour toute question ou suggestion, contactez-moi via **Teams** (prénom : Sam).
