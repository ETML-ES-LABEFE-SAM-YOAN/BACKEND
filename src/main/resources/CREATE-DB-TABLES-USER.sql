CREATE DATABASE IF NOT EXISTS bidster
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE bidster;

CREATE USER IF NOT EXISTS 'bidster_connector'@'localhost' IDENTIFIED BY 'secret';

GRANT ALL PRIVILEGES ON bidster.* TO 'bidster_connector'@'localhost';

FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS categorie_entity (
    id_categorie BIGINT NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    id_parent BIGINT DEFAULT NULL,
    PRIMARY KEY (id_categorie),
    KEY idx_parent (id_parent),
    CONSTRAINT fk_categorie_parent FOREIGN KEY (id_parent) REFERENCES categorie_entity(id_categorie) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS lot_entity (
    id_lot BIGINT NOT NULL,
    date_heure_fin DATETIME(6) DEFAULT NULL,
    description VARCHAR(255) DEFAULT NULL,
    details VARCHAR(255) DEFAULT NULL,
    enchere_depart DOUBLE DEFAULT NULL,
    image VARCHAR(255) DEFAULT NULL,
    nom_article VARCHAR(255) DEFAULT NULL,
    status VARBINARY(255) DEFAULT NULL,
    id_categorie BIGINT DEFAULT NULL,
    PRIMARY KEY (id_lot),
    KEY idx_categorie (id_categorie),
    CONSTRAINT fk_lot_categorie FOREIGN KEY (id_categorie) REFERENCES categorie_entity(id_categorie) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
