
-- Crear usuarios
INSERT INTO usuario (nombre, email, password) VALUES ('Enrique', 'enrique@hotmail.com', '12345');
INSERT INTO usuario (nombre, email, password) VALUES ('Juan', 'juan@hotmail.com', '12345');
INSERT INTO usuario (nombre, email, password) VALUES ('Rodrigo', 'ro.jg01@hotmail.com'  , '12345');

-- Crear tópicos
INSERT INTO topico (titulo, mensaje, fecha_creacion, status, autor, curso) VALUES ('Problema con el curso', 'No puedo acceder al curso, profesor', '2021-01-01', 1, 3, 'Java');
INSERT INTO topico (titulo, mensaje, fecha_creacion, status, autor, curso) VALUES ('Problema con el curso x2', 'Tampoco puedo acceder al curso, profesor', '2021-01-02', 1, 2, 'Java');