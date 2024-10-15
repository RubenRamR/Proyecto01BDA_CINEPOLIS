CREATE DATABASE CinepolisDB;
USE CinepolisDB;

CREATE TABLE ciudad (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30),
    localizacion VARCHAR(100)
);

CREATE TABLE cinepolisdb.clientes (
    idCliente BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30),
    apellidoPaterno VARCHAR(20),
    apellidoMaterno VARCHAR(20),
    correo VARCHAR(40) UNIQUE,
    contrasena VARCHAR(20),
    ubicacion POINT,
    fechaNacimiento DATE,
    ciudad BIGINT,
    FOREIGN KEY (ciudad) REFERENCES ciudad(id)
);

CREATE TABLE sucursales (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30),
    ciudad BIGINT,
    ubicacion POINT,
    FOREIGN KEY (ciudad) REFERENCES ciudad(id)
);

CREATE TABLE genero (
    idGenero BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(45)
);

CREATE TABLE clasificacion (
    idClasificacion BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(45)
);

CREATE TABLE peliculas (
    idPelicula BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(30),
    sinopsis VARCHAR(200),
    clasificacion BIGINT,
    genero BIGINT,
    pais VARCHAR(30),
    trailer VARCHAR(150),
    duracion INT,
    idGenero BIGINT,
    idClasificacion BIGINT,
    FOREIGN KEY (idGenero) REFERENCES genero (idGenero),
    FOREIGN KEY (idClasificacion) REFERENCES clasificacion (idClasificacion)
);

CREATE TABLE salas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30),
    numero_asientos INT,
    tipoSala VARCHAR(30),
    costo FLOAT,
    idSucursal BIGINT,
    FOREIGN KEY (idSucursal) REFERENCES sucursales (id)
);

CREATE TABLE funciones (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tiempo_limpieza DATETIME,
    tiempo_inicio DATETIME,
    tiempo_fin DATETIME,
    numero_asientos_disponibles INT,
    dia INT,
    idSala BIGINT,
    idPelicula BIGINT,
    FOREIGN KEY (idSala) REFERENCES salas (id),
    FOREIGN KEY (idPelicula) REFERENCES peliculas (idPelicula)
);

CREATE TABLE clienteCompraFuncion (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cantidad_boletos INT,
    costo_total FLOAT,
    tipo_pago VARCHAR(30),
    fecha_compra DATETIME,
    idCliente BIGINT,
    idFuncion BIGINT,
    FOREIGN KEY (idCliente) REFERENCES clientes (idCliente),
    FOREIGN KEY (idFuncion) REFERENCES funciones (id)
);

CREATE TABLE sucursalPeliculas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    fecha DATETIME,
    idSucursal BIGINT,
    idPelicula BIGINT,
    FOREIGN KEY (idSucursal) REFERENCES sucursales (id),
    FOREIGN KEY (idPelicula) REFERENCES peliculas (idPelicula)
);

CREATE TABLE boleto (
    idBoleto BIGINT PRIMARY KEY AUTO_INCREMENT,
    costo DOUBLE,
    estado TINYINT,
    fechaCompra DATE,
    idFuncion BIGINT,
    idCliente BIGINT,
    FOREIGN KEY (idFuncion) REFERENCES funciones (id),
    FOREIGN KEY (idCliente) REFERENCES clientes (idCliente)
);

-- Insertar ciudades
INSERT INTO ciudad (nombre, localizacion) VALUES
('Ciudad de México', 'Centro de México'),
('Guadalajara', 'Oeste de México'),
('Monterrey', 'Noreste de México'),
('Puebla', 'Este de México'),
('Cancún', 'Sureste de México');

INSERT INTO clientes (nombre, apellidoPaterno, apellidoMaterno, correo, contrasena, ubicacion, fechaNacimiento, ciudad)
VALUES 
('Ana', 'López', 'Martínez', '1', '1', POINT(19.432608, -99.133209), '1995-05-15', 2),
('María', 'Hernández', 'Lozano', 'maria.hernandez@example.com', 'contraseña789', POINT(20.659699, -103.349609), '1990-02-14', 2),
('Pedro', 'Sánchez', 'Ruiz', 'pedro.sanchez@example.com', 'contraseña321', POINT(19.182951, -96.143069), '1985-11-05', 3),
('Laura', 'Martínez', 'Soto', 'laura.martinez@example.com', 'contraseña654', POINT(25.686614, -100.316113), '1992-08-30', 4),
('Carlos', 'Ramírez', 'Vargas', 'carlos.ramirez@example.com', 'contraseña987', POINT(18.520430, -69.983165), '1980-04-25', 5);

-- Insertar sucursales
INSERT INTO sucursales (nombre, ciudad, ubicacion) VALUES  
('Sucursal Centro', 1, POINT(20.659699, -103.349609)), 
('Sucursal Sur', 2, POINT(18.520430, -69.983165)), 
('Sucursal Norte', 3, POINT(25.686614, -100.316113));

-- Insertar géneros
INSERT INTO genero (tipo) VALUES 
('Acción'),
('Comedia'),
('Drama'),
('Terror'),
('Ciencia Ficción');

-- Insertar clasificaciones
INSERT INTO clasificacion (tipo) VALUES 
('G'),
('PG'),
('PG-13'),
('R'),
('NC-17');

-- Insertar películas
INSERT INTO peliculas (titulo, sinopsis, clasificacion, genero, pais, trailer, duracion, idGenero, idClasificacion) VALUES 
('Inception', 'Un ladrón especializado en robar secretos de los sueños es dado una última oportunidad de redención.', 3, 5, 'Estados Unidos', 'trailer1.com', 148, 5, 2),
('The Godfather', 'La historia de la familia mafiosa Corleone en la década de 1940.', 4, 3, 'Estados Unidos', 'trailer2.com', 175, 2, 4),
('Coco', 'Un joven músico viaja al mundo de los muertos para descubrir la verdad sobre su familia.', 2, 1, 'México', 'trailer3.com', 105, 1, 1),
('The Dark Knight', 'Batman enfrenta al Joker, un criminal que busca sumergir a Gotham en el caos.', 3, 5, 'Estados Unidos', 'trailer4.com', 152, 5, 2),
('Parasite', 'Una familia pobre se infiltra en la vida de una familia rica, desatando una serie de eventos inesperados.', 4, 4, 'Corea del Sur', 'trailer5.com', 132, 3, 4);