-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS challenge3DB;
USE challenge3DB;

-- Tabla reservas
CREATE TABLE reservas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fecha_entrada TIMESTAMP NOT NULL,
    fecha_salida TIMESTAMP NOT NULL,
    valor DOUBLE NOT NULL,
    forma_pago VARCHAR(50) NOT NULL
);

-- Tabla huespedes
CREATE TABLE huespedes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    nacimiento TIMESTAMP NOT NULL,
    nacionalidad VARCHAR(50) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    id_reserva INT,
    FOREIGN KEY (id_reserva) REFERENCES reservas(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- Tabla usuarios
CREATE TABLE usuarios (
    usuario VARCHAR(50) PRIMARY KEY,
    pass VARCHAR(100) NOT NULL
);

INSERT INTO usuarios (usuario, pass) VALUES ('admin', '123');
SELECT * FROM usuarios;

SELECT * FROM huespedes;
SELECT * FROM reservas;

-- script de llenado inicial

-- Insertar datos de ejemplo en reservas
INSERT INTO reservas (fecha_entrada, fecha_salida, valor, forma_pago) VALUES
('2025-07-11 14:00:00', '2025-07-13 11:00:00', 50.00, 'Tarjeta de Crédito'),
('2025-07-15 16:00:00', '2025-07-18 12:00:00', 75.00, 'Dinero en efectivo'),
('2025-07-20 13:00:00', '2025-07-22 11:30:00', 50.00, 'Tarjeta de Débito'),
('2025-07-25 15:00:00', '2025-07-27 12:00:00', 50.00, 'Tarjeta de Crédito');

-- Insertar datos de ejemplo en huespedes
INSERT INTO huespedes (nombre, apellido, nacimiento, nacionalidad, telefono, id_reserva) VALUES
('Edwin', 'Pacheco', '1999-03-05 00:00:00', 'salvadoreño-salvadoreña', '78952213', 1),
('Lucía', 'Martínez', '1988-11-22 00:00:00', 'guatemalteco-guatemalteca', '78452211', 1),
('Carlos', 'Lopez', '1975-08-19 00:00:00', 'costarricense-costarricense', '71505500', 2),
('Valeria', 'Hernández', '2002-01-15 00:00:00', 'mexicano-mexicana', '77234477', 3),
('Ricardo', 'Morales', '1985-06-30 00:00:00', 'hondureño-hondureña', '70117744', 4);



