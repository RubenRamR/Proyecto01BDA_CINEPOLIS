CREATE DATABASE CinepolisDB;
USE CinepolisDB;

CREATE TABLE `ciudad` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `localizacion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `clientes` (
  `idCliente` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `apellidoPaterno` varchar(20) DEFAULT NULL,
  `apellidoMaterno` varchar(20) DEFAULT NULL,
  `correo` varchar(40) DEFAULT NULL,
  `contrasena` varchar(20) DEFAULT NULL,
  `ubicacion` point DEFAULT NULL,
  `fechaNacimiento` date DEFAULT NULL,
  `ciudad` bigint DEFAULT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE KEY `correo` (`correo`),
  KEY `ciudad` (`ciudad`),
  CONSTRAINT `clientes_ibfk_1` FOREIGN KEY (`ciudad`) REFERENCES `ciudad` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
	

CREATE TABLE `sucursales` (
  `idSucursal` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `ciudad` bigint DEFAULT NULL,
  `ubicacion` point DEFAULT NULL,
  PRIMARY KEY (`idSucursal`),
  KEY `ciudad` (`ciudad`),
  CONSTRAINT `sucursales_ibfk_1` FOREIGN KEY (`ciudad`) REFERENCES `ciudad` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `genero` (
  `idGenero` bigint NOT NULL AUTO_INCREMENT,
  `tipo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idGenero`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `clasificacion` (
  `idClasificacion` bigint NOT NULL AUTO_INCREMENT,
  `tipo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idClasificacion`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `peliculas` (
  `idPelicula` bigint NOT NULL AUTO_INCREMENT,
  `titulo` varchar(30) DEFAULT NULL,
  `sinopsis` varchar(200) DEFAULT NULL,
  `clasificacion` bigint DEFAULT NULL,
  `genero` bigint DEFAULT NULL,
  `pais` varchar(30) DEFAULT NULL,
  `trailer` varchar(150) DEFAULT NULL,
  `duracion` int DEFAULT NULL,
  `idGenero` bigint DEFAULT NULL,
  `idClasificacion` bigint DEFAULT NULL,
  PRIMARY KEY (`idPelicula`),
  KEY `idGenero` (`idGenero`),
  KEY `idClasificacion` (`idClasificacion`),
  CONSTRAINT `peliculas_ibfk_1` FOREIGN KEY (`idGenero`) REFERENCES `genero` (`idGenero`),
  CONSTRAINT `peliculas_ibfk_2` FOREIGN KEY (`idClasificacion`) REFERENCES `clasificacion` (`idClasificacion`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `salas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) DEFAULT NULL,
  `numero_asientos` int DEFAULT NULL,
  `tipoSala` varchar(30) DEFAULT NULL,
  `costo` float DEFAULT NULL,
  `idSucursal` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idSucursal` (`idSucursal`),
  CONSTRAINT `salas_ibfk_1` FOREIGN KEY (`idSucursal`) REFERENCES `sucursales` (`idSucursal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `funciones` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tiempo_limpieza` datetime DEFAULT NULL,
  `tiempo_inicio` datetime DEFAULT NULL,
  `tiempo_fin` datetime DEFAULT NULL,
  `numero_asientos_disponibles` int DEFAULT NULL,
  `dia` int DEFAULT NULL,
  `idSala` bigint DEFAULT NULL,
  `idPelicula` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idSala` (`idSala`),
  KEY `idPelicula` (`idPelicula`),
  CONSTRAINT `funciones_ibfk_1` FOREIGN KEY (`idSala`) REFERENCES `salas` (`id`),
  CONSTRAINT `funciones_ibfk_2` FOREIGN KEY (`idPelicula`) REFERENCES `peliculas` (`idPelicula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `clientecomprafuncion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad_boletos` int DEFAULT NULL,
  `costo_total` float DEFAULT NULL,
  `tipo_pago` varchar(30) DEFAULT NULL,
  `fecha_compra` datetime DEFAULT NULL,
  `idCliente` bigint DEFAULT NULL,
  `idFuncion` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idCliente` (`idCliente`),
  KEY `idFuncion` (`idFuncion`),
  CONSTRAINT `clientecomprafuncion_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `clientes` (`idCliente`),
  CONSTRAINT `clientecomprafuncion_ibfk_2` FOREIGN KEY (`idFuncion`) REFERENCES `funciones` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `sucursalpeliculas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha` datetime DEFAULT NULL,
  `idSucursal` bigint DEFAULT NULL,
  `idPelicula` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idSucursal` (`idSucursal`),
  KEY `idPelicula` (`idPelicula`),
  CONSTRAINT `sucursalpeliculas_ibfk_1` FOREIGN KEY (`idSucursal`) REFERENCES `sucursales` (`idSucursal`),
  CONSTRAINT `sucursalpeliculas_ibfk_2` FOREIGN KEY (`idPelicula`) REFERENCES `peliculas` (`idPelicula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `boleto` (
  `idBoleto` bigint NOT NULL AUTO_INCREMENT,
  `costo` double DEFAULT NULL,
  `estado` tinyint DEFAULT NULL,
  `fechaCompra` date DEFAULT NULL,
  `idFuncion` bigint DEFAULT NULL,
  `idCliente` bigint DEFAULT NULL,
  PRIMARY KEY (`idBoleto`),
  KEY `idFuncion` (`idFuncion`),
  KEY `idCliente` (`idCliente`),
  CONSTRAINT `boleto_ibfk_1` FOREIGN KEY (`idFuncion`) REFERENCES `funciones` (`id`),
  CONSTRAINT `boleto_ibfk_2` FOREIGN KEY (`idCliente`) REFERENCES `clientes` (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO ciudad (nombre, localizacion) VALUES
('Ciudad de México', 'Centro de México'),
('Guadalajara', 'Oeste de México'),
('Monterrey', 'Noreste de México'),
('Puebla', 'Este de México'),
('Cancún', 'Sureste de México');


-- Insertar clientes
INSERT INTO clientes (nombre, apellidoPaterno, apellidoMaterno, correo, contrasena, ubicacion, fechaNacimiento, ciudad) 
VALUES 
('Juan', 'j', 'j', '1', '1', POINT(19.432608, -99.133209), '1995-05-15', 6),
('Ana', 'López', 'Martínez', 'ana.lopez@example.com', 'contraseña123', POINT(19.432608, -99.133209), '1995-05-15', 6),
('María', 'Hernández', 'Lozano', 'maria.hernandez@example.com', 'contraseña789', POINT(20.659699, -103.349609), '1990-02-14', 7),
('Pedro', 'Sánchez', 'Ruiz', 'pedro.sanchez@example.com', 'contraseña321', POINT(19.182951, -96.143069), '1985-11-05', 8),
('Laura', 'Martínez', 'Soto', 'laura.martinez@example.com', 'contraseña654', POINT(25.686614, -100.316113), '1992-08-30', 9),
('Carlos', 'Ramírez', 'Vargas', 'carlos.ramirez@example.com', 'contraseña987', POINT(18.520430, -69.983165), '1980-04-25', 10);

-- Insertar sucursales
INSERT INTO sucursales (nombre, ciudad, ubicacion) VALUES  
('Sucursal Centro', 6, POINT(19.432608, -99.133209)), 
('Sucursal Sur', 7, POINT(20.659699, -103.349609)), 
('Sucursal Norte', 8, POINT(25.686614, -100.316113));

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

INSERT INTO peliculas (titulo, sinopsis, clasificacion, genero, pais, trailer, duracion, idGenero, idClasificacion) VALUES 
('Inception', 'Un ladrón especializado en robar secretos de los sueños es dado una última oportunidad de redención.', 3, 5, 'Estados Unidos', 'trailer1.com', 148, 6, 6),
('The Godfather', 'La historia de la familia mafiosa Corleone en la década de 1940.', 4, 3, 'Estados Unidos', 'trailer2.com', 175, 7, 7),
('Coco', 'Un joven músico viaja al mundo de los muertos para descubrir la verdad sobre su familia.', 2, 1, 'México', 'trailer3.com', 105, 8, 6),
('The Dark Knight', 'Batman enfrenta al Joker, un criminal que busca sumergir a Gotham en el caos.', 3, 5, 'Estados Unidos', 'trailer4.com', 152, 9, 8),
('Parasite', 'Una familia pobre se infiltra en la vida de una familia rica, desatando una serie de eventos inesperados.', 4, 4, 'Corea del Sur', 'trailer5.com', 132, 10, 9);

INSERT INTO funciones (tiempo_limpieza, tiempo_inicio, tiempo_fin, numero_asientos_disponibles, dia, idSala, idPelicula) VALUES 
('00:15:00', '2024-10-01 14:00:00', '2024-10-01 16:30:00', 100, 1, 1, 1), 
('00:15:00', '2024-10-01 17:00:00', '2024-10-01 19:30:00', 80, 1, 1, 2),
('00:15:00', '2024-10-01 20:00:00', '2024-10-01 22:30:00', 50, 1, 2, 3),
('00:15:00', '2024-10-02 14:00:00', '2024-10-02 16:30:00', 120, 2, 2, 4),
('00:15:00', '2024-10-02 17:00:00', '2024-10-02 19:30:00', 90, 2, 3, 5),
('00:15:00', '2024-10-03 14:00:00', '2024-10-03 16:30:00', 110, 3, 3, 1),
('00:15:00', '2024-10-03 17:00:00', '2024-10-03 19:30:00', 70, 3, 1, 2),
('00:15:00', '2024-10-03 20:00:00', '2024-10-03 22:30:00', 65, 3, 2, 3),
('00:15:00', '2024-10-04 14:00:00', '2024-10-04 16:30:00', 95, 4, 2, 4),
('00:15:00', '2024-10-04 17:00:00', '2024-10-04 19:30:00', 85, 4, 3, 5),
('00:15:00', '2024-10-05 14:00:00', '2024-10-05 16:30:00', 100, 5, 1, 1),
('00:15:00', '2024-10-05 17:00:00', '2024-10-05 19:30:00', 75, 5, 2, 2),
('00:15:00', '2024-10-06 14:00:00', '2024-10-06 16:30:00', 120, 6, 3, 3),
('00:15:00', '2024-10-06 17:00:00', '2024-10-06 19:30:00', 90, 6, 1, 4),
('00:15:00', '2024-10-06 20:00:00', '2024-10-06 22:30:00', 80, 6, 2, 5);
