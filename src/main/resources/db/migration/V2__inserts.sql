
-- Crear usuarios  (con contraseñas ya encriptadas en bcrypt)
INSERT INTO usuario (nombre, email, password) VALUES ('Enrique', 'enrique@hotmail.com', '$2a$10$ATh4EiyMyufvTkR7kI8ikeVobrBro2wx6ms0zM/WQLZBTFlm7rani');
INSERT INTO usuario (nombre, email, password) VALUES ('Juan', 'juan@hotmail.com', '$2a$10$7vF58kNXx9cMLRJIjQZTk.rF0OfeDR1RHXs77ZcIc3AzZJTnMU2ey');
INSERT INTO usuario (nombre, email, password) VALUES ('Rodrigo', 'ro.jg01@hotmail.com'  , '$2a$10$KXk71VAn0DHUr/ok1dbLCubD4.8ICQsleh0CcvSwzUi7i5cJYWdjG');

-- Crear tópicos
INSERT INTO topico (titulo, mensaje, fecha_creacion, status, autor, curso) VALUES ('Problema con el curso', 'No puedo acceder al curso, profesor', '2021-01-01', 1, 3, 'Java');
INSERT INTO topico (titulo, mensaje, fecha_creacion, status, autor, curso) VALUES ('Problema con el curso x2', 'Tampoco puedo acceder al curso, profesor', '2021-01-02', 1, 2, 'Java');