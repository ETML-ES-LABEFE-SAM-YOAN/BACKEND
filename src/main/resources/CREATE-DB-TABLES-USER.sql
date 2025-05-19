CREATE DATABASE IF NOT EXISTS bidster
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;
USE bidster;

CREATE USER IF NOT EXISTS 'bidster_connector'@'localhost' IDENTIFIED BY 'secret';
GRANT ALL PRIVILEGES ON bidster.* TO 'bidster_connector'@'localhost';
FLUSH PRIVILEGES;


SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS lot_entity;
DROP TABLE IF EXISTS utilisateur_entity;
DROP TABLE IF EXISTS categorie_entity;
SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE categorie_entity (
  id_categorie BIGINT PRIMARY KEY,
  nom VARCHAR(255) NOT NULL,
  id_parent BIGINT,
  FOREIGN KEY (id_parent) REFERENCES categorie_entity(id_categorie) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE utilisateur_entity (
    id BIGINT PRIMARY KEY,
    nom_utilisateur VARCHAR(255) NOT NULL UNIQUE,
    nom VARCHAR(255) DEFAULT NULL,
    prenom VARCHAR(255) DEFAULT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    telephone VARCHAR(255) DEFAULT NULL,
    rue VARCHAR(255) DEFAULT NULL,
    npa VARCHAR(255) DEFAULT NULL,
    localite VARCHAR(255) DEFAULT NULL,
    solde DOUBLE DEFAULT 0,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE lot_entity (
    id_lot BIGINT PRIMARY KEY,
    date_heure_fin DATETIME(6) DEFAULT NULL,
    description VARCHAR(255) DEFAULT NULL,
    details VARCHAR(255) DEFAULT NULL,
    enchere_depart DOUBLE DEFAULT NULL,
    image VARCHAR(255) DEFAULT NULL,
    nom_article VARCHAR(255) DEFAULT NULL,
    status VARCHAR(50) DEFAULT NULL,
    id_categorie BIGINT DEFAULT NULL,
    utilisateur_id BIGINT DEFAULT NULL,
    FOREIGN KEY (id_categorie) REFERENCES categorie_entity(id_categorie) ON DELETE SET NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur_entity(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;