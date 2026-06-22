create database AgendasSena;
use AgendasSena;
SELECT * FROM reservas;
-- AMBIENTES
INSERT INTO ambientes (nombre, tipo, capacidad, activo) VALUES
('Sala A', 'Sala', 20, true),
('Sala B', 'Sala', 15, true),
('Laboratorio Sistemas', 'Laboratorio', 25, true),
('Auditorio Principal', 'Auditorio', 100, false);

-- INSTRUCTORES
INSERT INTO instructores (nombre, telefono) VALUES
('Carlos Pérez', '3001234567'),
('Ana Gómez', '3009876543'),
('Luis Rodríguez', '3014567890'),
('María Torres', '3027891234');

--  RESERVAS

-- probar Regla 1
INSERT INTO reservas (ambiente_id, instructor_id, fecha_hora_inicio, fecha_hora_fin, numero_aprendices, estado) VALUES
(1, 1, CONCAT(CURDATE(), ' 09:00:00'), CONCAT(CURDATE(), ' 11:00:00'), 18, 'Activa');

-- reporte de ocupación
INSERT INTO reservas (ambiente_id, instructor_id, fecha_hora_inicio, fecha_hora_fin, numero_aprendices, estado) VALUES
(2, 2, CONCAT(CURDATE(), ' 14:00:00'), CONCAT(CURDATE(), ' 16:00:00'), 12, 'Activa');

-- Regla 2 (capacidad)
INSERT INTO reservas (ambiente_id, instructor_id, fecha_hora_inicio, fecha_hora_fin, numero_aprendices, estado) VALUES
(3, 3, CONCAT(CURDATE(), ' 08:00:00'), CONCAT(CURDATE(), ' 10:00:00'), 20, 'Activa');

-- útil para /ambientes/disponibles
INSERT INTO reservas (ambiente_id, instructor_id, fecha_hora_inicio, fecha_hora_fin, numero_aprendices, estado) VALUES
(3, 1, CONCAT(CURDATE(), ' 18:00:00'), CONCAT(CURDATE(), ' 20:00:00'), 22, 'Activa');

-- avanza el conteo de Regla 6
INSERT INTO reservas (ambiente_id, instructor_id, fecha_hora_inicio, fecha_hora_fin, numero_aprendices, estado) VALUES
(1, 1, CONCAT(CURDATE(), ' 12:00:00'), CONCAT(CURDATE(), ' 13:00:00'), 10, 'Activa');

--  crear una CUARTA reserva
INSERT INTO reservas (ambiente_id, instructor_id, fecha_hora_inicio, fecha_hora_fin, numero_aprendices, estado) VALUES
(2, 1, CONCAT(CURDATE(), ' 17:00:00'), CONCAT(CURDATE(), ' 18:00:00'), 8, 'Activa');

-- Sirve para distinguir fechas en consultas.
INSERT INTO reservas (ambiente_id, instructor_id, fecha_hora_inicio, fecha_hora_fin, numero_aprendices, estado) VALUES
(1, 2, CONCAT(DATE_SUB(CURDATE(), INTERVAL 1 DAY), ' 09:00:00'), CONCAT(DATE_SUB(CURDATE(), INTERVAL 1 DAY), ' 12:00:00'), 15, 'Finalizada');

-- reserva CANCELADA no cuenta para solapamiento
INSERT INTO reservas (ambiente_id, instructor_id, fecha_hora_inicio, fecha_hora_fin, numero_aprendices, estado) VALUES
(2, 4, CONCAT(CURDATE(), ' 09:00:00'), CONCAT(CURDATE(), ' 10:00:00'), 9, 'Cancelada');