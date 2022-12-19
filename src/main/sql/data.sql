create database barogo character set utf8mb4 collate utf8mb4_general_ci;

CREATE USER 'barogouser'@'localhost' IDENTIFIED BY 'barogopass';
CREATE USER 'barogouser'@'%' IDENTIFIED BY 'barogopass';

GRANT ALL PRIVILEGES ON barogo.* TO 'barogouser'@'localhost';
GRANT ALL PRIVILEGES ON barogo.* TO 'barogouser'@'%';

COMMIT;