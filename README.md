
# BackEnd – Vente aux enchères

## Description

Ce projet constitue le back-end d’une application de gestion de lots pour un site de vente aux enchères.  
Il permet de visualiser les lots disponibles, de les filtrer par catégorie ou sous-catégorie, et d’obtenir le détail de chaque lot.  
L’objectif de ce sprint est de permettre la visualisation des lots dans des catégories et sous-catégories, ces dernières étant affichées hiérarchiquement sous chaque catégorie principale.

Ce projet s’adresse principalement à aux professeurs dans le cadre d’un projet

## Statut du projet

-   **En développement**
    
-   Fonctionnalités de base d’affichage et de filtrage des lots par catégorie/sous-catégorie implémentées.
    

## Prérequis

-   Java 17
    
-   Maven
    
-   MySQL (testé avec MySQL 8+)
    
-   Spring Boot 3.2.4


## Installation

1.  **Cloner le dépôt GitHub**
    
    bash
    
    `git clone <url-du-depot> cd  <nom-du-repo>` 
    
2.  **Configurer la base de données**
    
    -   Créer la base de données  `bidster`  dans MySQL.
        
    -   Exécuter le script SQL fourni (`database.sql`) pour créer les tables et insérer un jeu de données.
        
3.  **Configurer les variables d’environnement**
    
    -   Modifier le fichier  `src/main/resources/application.properties`  avec vos identifiants MySQL.
        
4.  **Installer les dépendances et démarrer l’application**
    
    bash
    
    `mvn clean install mvn spring-boot:run` 
    

## Utilisation

## Démarrage du serveur

Lancer :

bash

`mvn spring-boot:run` 

Le serveur démarre par défaut sur le port  `8080`.

## Endpoints principaux (exemples de requêtes)

-   **Afficher tous les lots d’une catégorie principale (et sous-catégories) :**
    
    
    `curl http://localhost:8080/lots/categorie-principale/Bijoux` 
    
-   **Afficher tous les lots d’une sous-catégorie :**
    
    
    `curl http://localhost:8080/lots/sous-categorie/Bagues` 
    
-   **Afficher le détail d’un lot :**
    
    
    `curl http://localhost:8080/lots/1` 
    
-   **Afficher toutes les catégories principales (avec sous-catégories) :**
    
    
    `curl http://localhost:8080/categories/principales` 
    
-   **Afficher une catégorie et toutes ses sous-catégories :**
  
    
    `curl http://localhost:8080/categories/nom/Bijoux` 
    

## Tests

-   Les endpoints peuvent être testés avec  **Postman**  ou via les commandes  `curl`  ci-dessus.
    
-   (Tests automatisés à ajouter selon l’avancement du projet.)
    

## Technologies utilisées

-   [Spring Boot 3.2.4](https://spring.io/projects/spring-boot)
    
-   [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
    
-   [MySQL](https://www.mysql.com/)
    
-   [Maven](https://maven.apache.org/)
    

## Structure du projet

src/
├── main/
│   ├── java/
│   │   └── ch/
│   │       └── etmles/
│   │           └── payroll/
│   │               ├── categorie/
│   │               │   ├── CategorieController.java
│   │               │   ├── CategorieDTO.java
│   │               │   ├── CategorieEntity.java
│   │               │   ├── CategorieNotFoundAdvice.java
│   │               │   ├── CategorieNotFoundException.java
│   │               │   ├── CategorieRepository.java
│   │               │   └── CategorieService.java
│   │               │
│   │               ├── lot/
│   │               │   ├── LotController.java
│   │               │   ├── LotEntity.java
│   │               │   ├── LotNotFoundAdvice.java
│   │               │   ├── LotRepository.java
│   │               │   └── LotService.java
│   │               │
│   │               └── PayrollApplication.java
│   │
│   └── resources/
│       ├── application.properties
│       ├── CREATE-DB-USER.sql
│       └── DATASET.sql



-   **Categorie/** : gestion des catégories et sous-catégories (contrôleur, entité, DTO, repository, service, gestion des exceptions)
    
-   **Lot/** : gestion des lots (contrôleur, entité, repository, service, gestion des exceptions)
    
-   **PayrollApplication.java** : point d’entrée principal de l’application Spring Boot
    

> Les ressources (configuration, scripts SQL, etc.) sont à placer dans  `src/main/resources/`.
