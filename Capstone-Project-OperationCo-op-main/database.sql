
USE operationCoop_db;

DROP DATABASE IF EXISTS operationCoop_db;
CREATE DATABASE IF NOT EXISTS operationCoop_db;

CREATE USER capstone_18@localhost IDENTIFIED BY 'capstone18';
GRANT ALL ON operationCoop_db.* TO capstone_18@localhost;

