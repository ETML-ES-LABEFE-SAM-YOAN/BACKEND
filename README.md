# BackEnd – Vente aux enchères

## Description

Ce projet constitue le back-end d’une application de gestion de lots pour un site de vente aux enchères.  
Il permet notamment :
- De visualiser les lots disponibles
- De filtrer par catégorie ou sous-catégorie (hiérarchisation)
- D’obtenir le détail de chaque lot
- De gérer les enchères (placer, consulter, historique)
- De gérer les utilisateurs (inscription)

Ce projet s’adresse principalement aux professeurs dans le cadre d’un projet pédagogique.

---

## Statut du projet

**En développement**  
Les fonctionnalités de base d’affichage, de filtrage des lots et de gestion des enchères sont implémentées.

---

## Fonctionnalités principales

- Affichage des lots par catégorie/sous-catégorie
- Détail d’un lot
- Gestion des enchères (meilleure enchère, historique, placer une enchère)
- Inscription utilisateur

---

## Prérequis

| Dépendance           | Version recommandée      |
|----------------------|-------------------------|
| Java                 | 17                      |
| Maven                | 3.8+                    |
| MySQL                | 8+                      |
| Spring Boot          | 3.2.4                   |
| IDE                  | IntelliJ|
| OS                   | Windows 10/11 |


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

## Endpoints principaux

| Endpoint                                                | Accès              | Description                                                                 |
|---------------------------------------------------------|--------------------|-----------------------------------------------------------------------------|
| **Enchère**                                             |                    |                                                                             |
| `GET /encheres/lot/{id}/meilleure`                      | Tout le monde      | Affiche la meilleure enchère pour un lot donné                              |
| `GET /encheres/lot/{id}`                                | Tout le monde      | Affiche la liste de toutes les enchères pour un lot donné                   |
| `POST /encheres/placer`                                 | Connecté seulement | Permet à un utilisateur connecté de placer une enchère                      |
| `GET /encheres/utilisateur/{utilisateurName}`           | Connecté seulement | Affiche l’historique des enchères d’un utilisateur                          |
| **Lot & Catégorie**                                     |                    |                                                                             |
| `GET /lots/categorie-principale/{nom}`                  | Tout le monde      | Affiche tous les lots d’une catégorie principale (et ses sous-catégories)   |
| `GET /lots/sous-categorie/{nom}`                        | Tout le monde      | Affiche tous les lots d’une sous-catégorie                                  |
| `GET /lots/all`                                         | Tout le monde      | Affiche la liste de tous les lots                                           |
| `GET /lots/{id}`                                        | Tout le monde      | Affiche le détail d’un lot                                                  |
| `POST /lots`                                            | Connecté seulement | Permet à un utilisateur connecté de créer un lot                            |
| `GET /categories/principales`                           | Tout le monde      | Affiche toutes les catégories principales (avec sous-catégories)            |
| `GET /categories/nom/{nom}`                             | Tout le monde      | Affiche une catégorie et toutes ses sous-catégories                         |
| **Utilisateur**                                         |                    |                                                                             |
| `POST /utilisateurs/creer`                              | Tout le monde      | Permet à un utilisateur de s’inscrire                                       |

---

## Structure du projet

Le projet est organisé comme suit :

```
.
├── C:.
├── │   .env
├── │   .gitignore
├── │   mvnw
├── │   mvnw.cmd
├── │   pom.xml
├── │   README.md
├── │
├── ├───docs
├── │       class_diagram.plantuml
├── │       diagram_use_case.plantuml
├── │       domain_model.puml
├── │       mdl.puml
├── │
├── ├───src
├── │   ├───main
├── │   │   ├───java
├── │   │   │   └───ch
├── │   │   │       └───etmles
├── │   │   │           └───bidster
├── │   │   │               │   BidsterApplication.java
├── │   │   │               │   LoadDatabase.java
├── │   │   │               │
├── │   │   │               ├───Categorie
├── │   │   │               │
├── │   │   │               ├───Config
├── │   │   │               │       SecurityConfig.java
├── │   │   │               │
├── │   │   │               ├───Enchere
├── │   │   │               │   │
├── │   │   │               │   └───DTO
├── │   │   │               │
├── │   │   │               ├───Lot
├── │   │   │               │   │
├── │   │   │               │   └───DTO
├── │   │   │               │
├── │   │   │               └───Utilisateur
├── │   │   │
├── │   │   └───resources
├── │   │           application.properties
├── │   │           CREATE-DB-TABLES-USER.sql
├── │   │           DATASET.sql
├── │   │
├── │   └───test
├── │       └───java
├── │           └───ch
├── │               └───etmles
├── │                   └───bidster
└── │                           BidsterApplicationTests.java
```

**Détails des principaux dossiers :**

-`Categorie/` :
Contient tout ce qui concerne la gestion des catégories et sous-catégories (contrôleur, DTO, entité, repository, service, gestion des exceptions).

-`Config/` :
Contient les classes de configuration spécifiques à l’application (ex : configuration Spring, sécurité, etc.).

-`Enchere/` :
Contient la gestion des enchères (contrôleur, entité, repository, service, gestion des exceptions).

-`Lot/` :
Contient la gestion des lots (contrôleur, entité, repository, service, gestion des exceptions).

-`Utilisateur/` :
Contient la gestion des utilisateurs (inscription, authentification, entité, repository, service, etc.).

-`BidsterApplication` :
Point d’entrée principal de l’application Spring Boot.

-`LoadDatabase` :
Classe utilitaire pour l’initialisation ou le chargement de la base de données au démarrage.

-`payroll.Exemple/` :
Exemple ou module hérité d’un autre projet (à adapter ou supprimer selon l’usage réel dans ton projet).

-`resources/` :
Contient les fichiers de configuration (application.properties) et les scripts SQL d'initialisation (CREATE-DB-TABLES-USER.sql, DATASET.sql).

-`test/` :
Contiendra les tests unitaires et d’intégration pour les différentes couches de l’application.

---
## Tests

- Les endpoints peuvent être testés avec Postman ou via les commandes curl.
- (Tests automatisés à ajouter selon l’avancement du projet.)

---

## Collaboration

Vous souhaitez contribuer ?  
Merci de suivre les étapes suivantes :
- Proposez une **issue** pour discuter de votre idée/amélioration.
- Forkez le projet et créez une **pull request** avec une description claire.
- Respectez la convention de commit suivante : `type: sujet` (ex : `feat: ajout de la gestion des enchères`)
- Toute contribution est la bienvenue !

---

## Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](./LICENSE) pour plus d’informations.

---

## Contact

Pour toute question ou suggestion, contactez-moi via **Teams** (prénom : Sam).
