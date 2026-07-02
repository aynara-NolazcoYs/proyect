-- Insert sample students
INSERT INTO Students (nombre, apellido, dni, correo, fecha_registro) VALUES 
('Juan', 'Pérez', '12345678', 'juan.perez@example.com', GETDATE());

INSERT INTO Students (nombre, apellido, dni, correo, fecha_registro) VALUES 
('María', 'González', '87654321', 'maria.gonzalez@example.com', GETDATE());

INSERT INTO Students (nombre, apellido, dni, correo, fecha_registro) VALUES 
('Carlos', 'López', '11223344', 'carlos.lopez@example.com', GETDATE());

-- Insert sample promoters
INSERT INTO Promoters (dni, nombreCompleto, sede, email, fecha_registro) VALUES 
('98765432', 'Roberto Silva', 'SED-001', 'roberto.silva@edufuturo.com', GETDATE());

INSERT INTO Promoters (dni, nombreCompleto, sede, email, fecha_registro) VALUES 
('55443322', 'Ana Martínez', 'SED-002', 'ana.martinez@edufuturo.com', GETDATE());

INSERT INTO Promoters (dni, nombreCompleto, sede, email, fecha_registro) VALUES 
('66778899', 'Luis Fernández', 'SED-003', 'luis.fernandez@edufuturo.com', GETDATE());

-- Insert sample enrollments
INSERT INTO Enrollments (nombre, apellido, dni, sede, promotor, programa, modalidad, correo, fecha_registro) VALUES 
('Juan', 'Pérez', '12345678', 'SED-001', 'Roberto Silva', 'Programa 1', 'Virtual', 'juan.perez@example.com', GETDATE());

INSERT INTO Enrollments (nombre, apellido, dni, sede, promotor, programa, modalidad, correo, fecha_registro) VALUES 
('María', 'González', '87654321', 'SED-002', 'Ana Martínez', 'Programa 2', 'Presencial', 'maria.gonzalez@example.com', GETDATE());
