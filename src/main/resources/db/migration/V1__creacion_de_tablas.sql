CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE topico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo CHAR(50) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion DATE NOT NULL,
    status TINYINT(1) NOT NULL, -- Usamos TINYINT para booleanos
    autor INT NOT NULL,
    curso char(50) NOT NULL,
    CONSTRAINT fk_autor FOREIGN KEY (autor) REFERENCES usuario(id)
);
