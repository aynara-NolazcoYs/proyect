-- SQL Server Script to create tables for EduFuturo Database
-- This is optional as Hibernate will create tables automatically if not present

CREATE TABLE Students (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,
    apellido NVARCHAR(100) NOT NULL,
    dni NVARCHAR(20) NOT NULL UNIQUE,
    correo NVARCHAR(150) NOT NULL UNIQUE,
    fecha_registro DATETIME,
    CONSTRAINT PK_Students PRIMARY KEY (id)
);

CREATE TABLE Promoters (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    dni NVARCHAR(20) NOT NULL UNIQUE,
    nombreCompleto NVARCHAR(150) NOT NULL,
    sede NVARCHAR(50) NOT NULL,
    email NVARCHAR(150) NOT NULL UNIQUE,
    fecha_registro DATETIME,
    CONSTRAINT PK_Promoters PRIMARY KEY (id)
);

CREATE TABLE Enrollments (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    nombre NVARCHAR(100) NOT NULL,
    apellido NVARCHAR(100) NOT NULL,
    dni NVARCHAR(20) NOT NULL,
    sede NVARCHAR(50) NOT NULL,
    promotor NVARCHAR(150) NOT NULL,
    programa NVARCHAR(150) NOT NULL,
    modalidad NVARCHAR(50) NOT NULL,
    correo NVARCHAR(150) NOT NULL,
    fecha_registro DATETIME,
    CONSTRAINT PK_Enrollments PRIMARY KEY (id)
);

-- Create indexes for better query performance
CREATE INDEX IDX_Students_DNI ON Students(dni);
CREATE INDEX IDX_Students_Correo ON Students(correo);
CREATE INDEX IDX_Promoters_DNI ON Promoters(dni);
CREATE INDEX IDX_Promoters_Email ON Promoters(email);
CREATE INDEX IDX_Enrollments_DNI ON Enrollments(dni);
CREATE INDEX IDX_Enrollments_Promotor ON Enrollments(promotor);
