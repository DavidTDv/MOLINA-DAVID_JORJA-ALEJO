DROP TABLE IF EXISTS Odontologos;

CREATE TABLE Odontologos (id INT AUTO_INCREMENT PRIMARY KEY, numero_matricula VARCHAR(255) NOT NULL, nombre VARCHAR(255) NOT NULL,apellido VARCHAR(255) NOT NULL);

INSERT INTO Odontologos (numero_matricula,nombre,apellido) VALUES (23498,"Alejo", "Jorja") (93845, "David", "Molina")