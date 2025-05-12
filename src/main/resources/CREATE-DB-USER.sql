-- 1. Création de la base de données
CREATE DATABASE IF NOT EXISTS bidster
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

-- 2. Création de l'utilisateur (si besoin)
CREATE USER IF NOT EXISTS 'bidster_connector'@'localhost' IDENTIFIED BY 'secret';

-- 3. Attribution des droits à l'utilisateur sur la base de données
GRANT ALL PRIVILEGES ON bidster.* TO 'bidster_connector'@'localhost';

-- 4. Appliquer les changements de privilèges
FLUSH PRIVILEGES;
