CREATE DATABASE CinepolisDB;
USE CinepolisDB;

CREATE TABLE cinepolisdb.clientes (
	idCliente BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(30),
    apellidoPaterno VARCHAR(20),
    apellidoMaterno VARCHAR(20),
    correo VARCHAR(40) UNIQUE,
    contrasena VARCHAR(20),
    ubicacion VARCHAR(20),
    fechaNacimiento DATE,
    ciudad VARCHAR(30),
    coordenada_y FLOAT,
    coordenada_x FLOAT
    );
    
CREATE TABLE sucursales (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(30),
    ciudad VARCHAR(30),
    coordenada_y FLOAT,
    coordenada_x FLOAT
    );
    
CREATE TABLE peliculas (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(30),
    sinopsis VARCHAR(200),
    clasificacion VARCHAR(30),
    genero VARCHAR(30),
    pais VARCHAR(30),
    trailer VARCHAR(150),
    duracion INT,
    idGenero BIGINT,
    idPelicula BIGINT,
    FOREIGN KEY (idGenero) REFERENCES generos (id),
    FOREIGN KEY (idClasificacion) REFERENCES clasificacion (id)
    );

CREATE TABLE salas (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(30),
    numero_asientos INT,
    tipoSala VARCHAR(30),
    costo FLOAT,
    idSucursal BIGINT,
    FOREIGN KEY (idSucursal) REFERENCES sucursal (id)
    );

CREATE TABLE funciones (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tiempo_limpieza DATETIME,
    tiempo_inicio DATETIME,
    tiempo_fin DATETIME,
    numero_dsientos_disponibles INT,
    dia INT,
    idSala BIGINT,
    idPelicula BIGINT,
    FOREIGN KEY (idSala) REFERENCES sala (id),
    FOREIGN KEY (idPelicula) REFERENCES pelicula (id)
    );

CREATE TABLE clienteCompraFuncion (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cantidad_boletos INT,
    costo_total FLOAT,
    tipo_pago VARCHAR(30),
    fecha_compra DATETIME,
    idCliente BIGINT,
    idFuncion BIGINT,
    FOREIGN KEY (idCliente) REFERENCES cliente (id),
    FOREIGN KEY (idFuncion) REFERENCES funcion (id)
    );
    
CREATE TABLE sucursalPeliculas (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	fecha DATETIME,
    idSucursal BIGINT,
    idPelicula BIGINT,
    FOREIGN KEY (idSucursal) REFERENCES pelicula (id),
    FOREIGN KEY (idPelicula) REFERENCES sucursal (id)
    );
    
    CREATE TABLE boleto (
	idBoleto BIGINT PRIMARY KEY AUTO_INCREMENT,
	costo DOUBLE,
    estado tinyint,
    fechaCompra DATE,
    FOREIGN KEY (idFuncion) REFERENCES funciones (id),
    FOREIGN KEY (idCliente) REFERENCES clientes (id)
    );
    
    CREATE TABLE genero (
	idGeneros BIGINT PRIMARY KEY AUTO_INCREMENT,
	tipo VARCHAR(45)
    
    );
    
    CREATE TABLE clasfificacion (
    idClasificacion BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(45)
    );

    
    INSERT INTO clientes (nombre, apellidoPaterno, apellidoMaterno, correo, contraseña, ubicacion, fechaNacimiento, ciudad, coordenada_y, coordenada_x) VALUES  
    ('Juan', 'Pérez', 'García', 'juan.perez@example.com', 'contraseña123', 'Calle Falsa 123', '1990-01-15', 'Ciudad de México', 19.4326, -99.1332), 
    ('María', 'López', 'Martínez', 'maria.lopez@example.com', 'contraseña456', 'Avenida Siempre Viva 456', '1985-05-20', 'Guadalajara', 20.6597, -103.3496), 
    ('Carlos', 'Hernández', 'Jiménez', 'carlos.hernandez@example.com', 'contraseña789', 'Boulevard de los Sueños 789', '1992-09-30', 'Monterrey', 25.6866, -100.3161),
    ('Ana', 'Ramírez', 'Sánchez', 'ana.ramirez@example.com', 'contraseña101', 'Pasaje del Tiempo 101', '1988-03-12', 'Puebla', 19.0452, -98.2033), 
    ('Luis', 'González', 'Torres', 'luis.gonzalez@example.com', 'contraseña202', 'Ruta del Sol 202', '1995-11-22', 'Cancún', 21.1619, -86.8515);

INSERT INTO sucursales (nombre, ciudad, coordenada_y, coordenada_x) VALUES  
('Sucursal Centro', 'Ciudad de México', 19.4326, -99.1332), 
('Sucursal Sur', 'Guadalajara', 20.6597, -103.3496), 
('Sucursal Norte', 'Monterrey', 25.6866, -100.3161),
 ('Sucursal Playa', 'Cancún', 21.1619, -86.8515);

INSERT INTO peliculas (titulo, sinopsis, clasificacion, genero, pais, trailer, duracion, idGenero, idPelicula)
VALUES 
('Inception', 'Un ladrón especializado en robar secretos de los sueños es dado una última oportunidad de redención.', 'PG-13', 'Ciencia Ficción', 'Estados Unidos', 'trailer1 com', 148, 1, 1),
('The Godfather', 'La historia de la familia mafiosa Corleone en la década de 1940.', 'R', 'Drama', 'Estados Unidos', 'trailer2 com', 175, 2, 2),
('Coco', 'Un joven músico viaja al mundo de los muertos para descubrir la verdad sobre su familia.', 'PG', 'Animación', 'México', 'trailer3 com', 105, 3, 3),
('The Dark Knight', 'Batman enfrenta al Joker, un criminal que busca sumergir a Gotham en el caos.', 'PG-13', 'Acción', 'Estados Unidos', 'trailer4 com', 152, 4, 4),
('Parasite', 'Una familia pobre se infiltra en la vida de una familia rica, desatando una serie de eventos inesperados.', 'R', 'Thriller', 'Corea del Sur', 'trailer5vcom', 132, 5, 5);

INSERT INTO genero (tipo)
VALUES 
('Acción'),
('Comedia'),
('Drama'),
('Terror'),
('Ciencia Ficción');

INSERT INTO clasificacion (tipo)
VALUES 
('G'),
('PG'),
('PG-13'),
('R'),
('NC-17');



