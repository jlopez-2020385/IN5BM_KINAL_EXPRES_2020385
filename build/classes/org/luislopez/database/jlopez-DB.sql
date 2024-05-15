drop database if exists DBKinalExpessIN5BM;

create database DBKinalExpessIN5BM;

use DBKinalExpessIN5BM;

set global time_zone='-6:00';



create table Clientes(
	clienteID int auto_increment,
    nombreClientes varchar (50),
    apellidosClientes varchar (50),
    direccionClientes varchar (50),
    NIT varchar (10),
    telefonoClientes varchar (8),
    correoClientes varchar (45),
    primary key PK_ClienteID(clienteID)
);

create table TipoProducto(
	codigoTipoProducto int auto_increment,
    descripcionProducto varchar (100),
    primary key PK_TipoProducto(codigoTipoProducto)
);

create table Compras(
	numeroDocumento int auto_increment,
    fechaDocumento date,
    descripcion varchar(100),
    totalDocumento decimal(10,2),
    primary key PK_NumeroDocumento(numeroDocumento)
);

create table CargoEmpleado (
	codigoCargoEmpleado int auto_increment,
    nombreCargo varchar (45),
    descripcionCargo varchar (65),
    primary key PK_codigoCargoEmpleado (codigoCargoEmpleado)
);

create table Proveedores(
	codigoProveedor int auto_increment,
    NITProveedor varchar (60),
    nombresProveedor varchar (60),
    apellidosProveedor varchar (60),
    direccionProveedor varchar (150),
    rasonSocial varchar (60),
    contactoPrincipal varchar (100),
    paginaWeb varchar (50),
    primary key PK_codigoProveedor (codigoProveedor)

);



create table Productos(
	codigoProducto varchar(15),
	descripcionProducto varchar(15),
	precioUnitario decimal(10,2),
	precioDocena decimal(10,2),
	precioMayor decimal(10,2),
	imagenProducto varchar(45),
	existencia int,
	codigoTipoProducto int,
	codigoProveedor int,
	primary key  PK_codigoProducto (codigoProducto),
	foreign key (codigoTipoProducto) references TipoProducto(codigoTipoProducto),
	foreign key (codigoProveedor) references Proveedores(codigoProveedor)
);


-- ------------------------------------------------------------------------------- Clientes --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_AgregarClientes (in ombre varchar (50), in pellidos varchar(50), in ireccionClientes varchar(50), in it varchar(10), in elefono varchar (8), in orreo varchar(45))
begin 
	insert into Clientes (Clientes.nombreClientes, Clientes.apellidosClientes, Clientes.direccionClientes, Clientes.NIT, Clientes.telefonoClientes, Clientes.correoClientes)
		values (ombre, pellidos, ireccionClientes, it, elefono,orreo);
end $$        
delimiter ;
call sp_AgregarClientes('María López', 'González', 'zona 15', '9876543210', '87654321', 'maria@gmail.com');
call sp_AgregarClientes('Juan Pérez', 'Martínez', 'zona 7', '4567890123', '23456789', 'juan@gmail.com');
call sp_AgregarClientes('Ana García', 'Fernández', 'zona 10', '8765432109', '34567890', 'ana@gmail.com');
call sp_AgregarClientes('Pedro Sánchez', 'Rodríguez', 'zona 3', '7654321098', '45678901', 'pedro@gmail.com');
call sp_AgregarClientes('Laura Martínez', 'Gómez', 'zona 17', '6543210987', '56789012', 'laura@gmail.com');
call sp_AgregarClientes('Carlos Rodríguez', 'Pérez', 'zona 12', '5432109876', '67890123', 'carlos@gmail.com');
call sp_AgregarClientes('Sofía Fernández', 'López', 'zona 5', '4321098765', '78901234', 'sofia@gmail.com');
call sp_AgregarClientes('David García', 'Hernández', 'zona 8', '3210987654', '89012345', 'david@gmail.com');
call sp_AgregarClientes('Fernanda Martínez', 'Díaz', 'zona 14', '2109876543', '90123456', 'fernanda@gmail.com');
call sp_AgregarClientes('Roberto López', 'Sánchez', 'zona 6', '1098765432', '01234567', 'roberto@gmail.com');
call sp_AgregarClientes('Patricia Pérez', 'Gómez', 'zona 19', '9876543210', '89012345', 'patricia@gmail.com');
call sp_AgregarClientes('Jorge González', 'Fernández', 'zona 11', '8765432109', '90123456', 'jorge@gmail.com');
call sp_AgregarClientes('Isabel Rodríguez', 'Martínez', 'zona 4', '7654321098', '01234567', 'isabel@gmail.com');
call sp_AgregarClientes('Santiago Hernández', 'Pérez', 'zona 22', '6543210987', '90123456', 'santiago@gmail.com');
call sp_AgregarClientes('Alejandro Pérez', 'López', 'zona 2', '4398765432', '78901234', 'alejandro@gmail.com');
call sp_AgregarClientes('Marcela Rodríguez', 'González', 'zona 18', '3987654321', '89012345', 'marcela@gmail.com');
call sp_AgregarClientes('Ricardo Martínez', 'Sánchez', 'zona 9', '2109543210', '90123456', 'ricardo@gmail.com');
-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarClientes(in ienteID varchar(10))
begin
    delete from Clientes
    where Clientes.clienteID = ienteID;
end$$
delimiter ;
call sp_EliminarClientes ('12');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarClientes(in ienteID  int,in ombre varchar (50), in pellidos varchar(50), in ireccionClientes varchar(50), in it varchar(10), in elefono varchar (8), in orreo varchar(45))
begin
    update Clientes
    set 
        Clientes.nombreClientes= ombre,
        Clientes.apellidosClientes =  pellidos ,
        Clientes.direccionClientes = ireccionClientes,
        Clientes.NIT = it,
        Clientes.telefonoClientes = elefono,
        Clientes.correoClientes = orreo
        where
        Clientes.clienteID = ienteID;
end$$
delimiter ;

call sp_ActualizarClientes(1,'Daniela García', 'Martínez', 'zona 16', '5432109543', '67890123', 'daniela@gmail.com');
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarClientes ()
begin 
	select
    c.clienteID,
    c.nombreClientes,
    c.apellidosClientes,
    c.direccionClientes,
    c.NIT,
    c.telefonoClientes,
    c.correoClientes
    from clientes c;
end $$        
delimiter ;
call sp_MostrarClientes;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarClientes(in ienteID int)
begin 
  select *from Clientes where Clientes.clienteID=ienteID;
end$$ 
delimiter ;
call sp_BuscarClientes(1);









-- ------------------------------------------------------------------------------- TipoProducto --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_AgregarTipoProducto (in cripcion varchar (100))
begin 
	insert into TipoProducto (TipoProducto.descripcionProducto)
		values (cripcion);
end $$        
delimiter ;
call sp_AgregarTipoProducto('Bicicleta Plegable UrbanRide: Compacta, ligera, perfecta para la ciudad.');
call sp_AgregarTipoProducto('Auriculares Inalámbricos AirBeat: Sonido claro, conexión estable, diseño ergonómico.');
call sp_AgregarTipoProducto('Cafetera Expresso MiniBar: Café delicioso, tamaño compacto, fácil de usar.');
call sp_AgregarTipoProducto('Altavoz Portátil BeatBox: Sonido potente, resistente al agua, ideal para exteriores.');
call sp_AgregarTipoProducto('Tablet Multifunción TechTab: Pantalla HD, rendimiento rápido, versátil.');
call sp_AgregarTipoProducto('Mochila UrbanX 300: Espaciosa, duradera, estilo moderno.');
call sp_AgregarTipoProducto('Reloj Deportivo FitTrack: Seguimiento de actividad, resistente al agua, diseño deportivo.');
call sp_AgregarTipoProducto('Cámara Compacta SnapShot: Fotos nítidas, fácil de llevar, perfecta para viajes.');
call sp_AgregarTipoProducto('Secador de Pelo TurboDry: Secado rápido, ajustes de temperatura, portátil.');
call sp_AgregarTipoProducto('Maletín Ejecutivo ElitePro: Elegante, organizado, ideal para negocios.');
call sp_AgregarTipoProducto('Botella Térmica EcoTherm: Mantiene las bebidas frías o calientes, diseño elegante, duradera.');
call sp_AgregarTipoProducto('Zapatillas Running SpeedFlex: Amortiguación, ligereza, máximo confort.');
call sp_AgregarTipoProducto('Silla Oficina ErgoPro: Ergonómica, ajustable, comodidad garantizada.');
call sp_AgregarTipoProducto('Teclado Gamer TurboKeys: Respuesta rápida, retroiluminación LED, diseño gamer.');
call sp_AgregarTipoProducto('Parrilla Eléctrica BBQMaster: Grill sin humo, fácil de limpiar, perfecta para interiores.');
-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarTipoProducto(in digoTipoProducto int)
begin
    delete from TipoProducto
    where TipoProducto.codigoTipoProducto = digoTipoProducto;
end$$
delimiter ;
call sp_EliminarTipoProducto (8);
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarTipoProducto(in digoTipoProducto int,in cripcion varchar (100))
begin
    update TipoProducto
    set 
        TipoProducto.descripcionProducto= cripcion
        where
        TipoProducto.codigoTipoProducto = digoTipoProducto;
end$$
delimiter ;

call sp_ActualizarTipoProducto(1,'Robot Aspirador CleanBot: Limpieza automática, navegación inteligente, eficiente.');
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarTipoProducto ()
begin 
	select
    t.codigoTipoProducto,
    t.descripcionProducto
    from TipoProducto t;
end $$        
delimiter ;
call sp_MostrarTipoProducto;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarTipoProducto(in digoTipoProducto int)
begin 
  select *from TipoProducto where TipoProducto.codigoTipoProducto=digoTipoProducto;
end$$ 
delimiter ;
call sp_BuscarTipoProducto(1);









-- ------------------------------------------------------------------------------- Proveedores --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_AgregarProveedores (in ITProveedor varchar (60),in ombresProveedor varchar (60),in pellidosProveedor varchar (60),in ireccionProveedor varchar (150),in asonSocial varchar (60),in ontactoPrincipal varchar (100),in aginaWeb varchar (50))
begin
	insert into Proveedores ( Proveedores.NITProveedor, Proveedores.nombresProveedor, Proveedores.apellidosProveedor, Proveedores.direccionProveedor, Proveedores.rasonSocial, Proveedores.contactoPrincipal, Proveedores.paginaWeb)
		values (ITProveedor,ombresProveedor,pellidosProveedor,ireccionProveedor,asonSocial,ontactoPrincipal,aginaWeb);
end $$        
delimiter ;
call sp_AgregarProveedores('5435455345345', 'González', 'Luis', 'zona 22', 'Pepsico  Servicios , S.L','43534534','https://www.google.com');
call sp_AgregarProveedores('3323213123323', 'Martínez', 'Emanue', 'zona 23', 'Servicios Centrales, S.L', '65646546','https://www.google.com');
CALL sp_AgregarProveedores('1234567890123', 'García', 'Ana', 'zona 24', 'García Hermanos, S.A.','78901234','https://www.google.com');
CALL sp_AgregarProveedores('9876543210987', 'López', 'Juan', 'zona 25', 'López & Asociados, S.L.', '56789012','https://www.google.com');
CALL sp_AgregarProveedores('1112223334445', 'Hernández', 'María', 'zona 26', 'Hernández y Cía, S.C.P.', '34567890','https://www.google.com');
CALL sp_AgregarProveedores('5554443332221', 'Pérez', 'Pedro', 'zona 27', 'Pérez Servicios Integrales, S.A.', '12345678','https://www.google.com');
CALL sp_AgregarProveedores('6667778889990', 'Rodríguez', 'David', 'zona 28', 'Rodríguez Soluciones Empresariales, S.L.','90123456','https://www.google.com');
CALL sp_AgregarProveedores('4445556667772', 'Sánchez', 'Laura', 'zona 29', 'Sánchez Consultoría y Asesoría, S.A.', '67890123','https://www.google.com');
CALL sp_AgregarProveedores('1239874563210', 'Gómez', 'Carlos', 'zona 30', 'Gómez Innovaciones Tecnológicas, S.A.', '45678901','https://www.google.com');
CALL sp_AgregarProveedores('9998887776663', 'Fernández', 'Sofía', 'zona 31', 'Fernández Soluciones Digitales, S.L.', '23456789','https://www.google.com');
CALL sp_AgregarProveedores('6543210987654', 'Martín', 'Diego', 'zona 32', 'Martín Consultoría Empresarial, S.A.','01234567','https://www.google.com');
CALL sp_AgregarProveedores('1110002223337', 'Moreno', 'Lucía', 'zona 33', 'Moreno Servicios Informáticos, S.L.', '89012345','https://www.google.com');

-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarProveedores(in odigoProveedor int)
begin
    delete from Proveedores
    where Proveedores.codigoProveedor = odigoProveedor;
end$$
delimiter ;
call sp_EliminarProveedores ('5');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarProveedores(in odigoProveedor int,in ITProveedor varchar (60),in ombresProveedor varchar (60),in pellidosProveedor varchar (60),in ireccionProveedor varchar (150),in asonSocial varchar (60),in ontactoPrincipal varchar (100),in aginaWeb varchar (50))
begin
    update Proveedores
    set 
        Proveedores.NITProveedor =  ITProveedor ,
        Proveedores.nombresProveedor = ombresProveedor,
        Proveedores.apellidosProveedor = pellidosProveedor,
        Proveedores.direccionProveedor = ireccionProveedor,
        Proveedores.rasonSocial = asonSocial,
        Proveedores.contactoPrincipal = ontactoPrincipal,
        Proveedores.paginaWeb = aginaWeb
        
        where
        Proveedores.codigoProveedor = odigoProveedor;
end$$
delimiter ;

CALL sp_ActualizarProveedores(1,'8889990001112', 'Torres', 'Miguel', 'zona 35', 'Torres Servicios de Mantenimiento, S.L.', '45678901','https://www.google.com');
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarProveedores ()
begin 
	select
	p.codigoProveedor ,
	p.NITProveedor  ,
	p.nombresProveedor ,
	p.apellidosProveedor ,
	p.direccionProveedor,
	p.rasonSocial,
	p.contactoPrincipal,
	p.paginaWeb 
    from proveedores p;
end $$        
delimiter ;
call sp_MostrarProveedores;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarProveedores(in odigoProveedor int)
begin 
  select *from Proveedores where Proveedores.codigoProveedor = odigoProveedor;
end$$ 
delimiter ;
call sp_BuscarProveedores(1);






-- ------------------------------------------------------------------------------- compras --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_AgregarCompras (in echaDocumento date,in escripcion varchar(100),in otalDocumento decimal(10,2))
begin
	insert into Compras ( Compras.fechaDocumento, Compras.descripcion, Compras.totalDocumento)
		values (echaDocumento,escripcion,otalDocumento);
end $$        
delimiter ;
call sp_AgregarCompras('2020-06-12','2 Camisetas de algodón, 1 Pantalón de mezclilla, 1 Par de zapatos deportivos, 1 Mochila resistente.',199.88);
call sp_AgregarCompras('2021-07-17','1 Conjunto de pijama de franela, 1 Bata de baño suave, 1 Par de pantuflas cómodas.',99.99);
call sp_AgregarCompras('2021-06-30','2 Blusas de moda, 1 Falda plisada, 1 Collar de perlas.',149.50);
call sp_AgregarCompras('2021-04-18','1 Camiseta de algodón orgánico, 1 Pantalón de yoga, 1 Tapete de yoga antideslizante.',79.99);
call sp_AgregarCompras('2021-02-13','1 Vestido de noche elegante, 1 Bolso de fiesta, 1 Par de tacones altos.',199.99);
call sp_AgregarCompras('2020-12-09','1 Set de cocina de acero inoxidable, 1 Juego de cuchillos profesionales, 1 Tabla de cortar de bambú.',129.95);



-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarCompras(in umeroDocumento int)
begin
    delete from Compras
    where Compras.numeroDocumento = umeroDocumento;
end$$
delimiter ;
call sp_EliminarCompras ('5');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarCompras(in umeroDocumento int,in echaDocumento date,in escripcion varchar(100),in otalDocumento decimal(10,2))
begin
    update Compras
    set 
        Compras.fechaDocumento =  echaDocumento ,
        Compras.descripcion = escripcion,
        Compras.totalDocumento = otalDocumento
        
        where
        Compras.numeroDocumento = umeroDocumento;
end$$
delimiter ;

call sp_ActualizarCompras(1,'2020-06-12','Mima a tu mascota con una variedad de alimentos y juguetes en PetParadise',100.45);
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarCompras ()
begin 
	select
	o.numeroDocumento ,
	o.fechaDocumento  ,
	o.descripcion ,
	o.totalDocumento 
    from compras o;
end $$        
delimiter ;
call sp_MostrarCompras;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarCompras(in umeroDocumento int)
begin 
  select *from Compras where Compras.numeroDocumento = umeroDocumento;
end$$ 
delimiter ;
call sp_BuscarCompras(1);







-- ------------------------------------------------------------------------------- Empleado --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ------------------------------------------------------------------------------- AGREGAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_AgregarCargoEmpleado (in ombreCargo varchar(45),in escripcionCargo varchar (65))
begin
	insert into CargoEmpleado ( CargoEmpleado.nombreCargo, CargoEmpleado.descripcionCargo)
		values (ombreCargo,escripcionCargo);
end $$        
delimiter ;
call sp_AgregarCargoEmpleado('Gerente de Ventas','Lidera equipo, establece objetivos, incrementa ventas.');
call sp_AgregarCargoEmpleado('Analista de Marketing','Investiga mercado, desarrolla estrategias, analiza datos.');
call sp_AgregarCargoEmpleado('Ingeniero de Software','Diseña, desarrolla, prueba software.');
call sp_AgregarCargoEmpleado('Asistente Administrativo','Brinda apoyo administrativo.');
call sp_AgregarCargoEmpleado('Especialista en Recursos Humanos','Gestiona personal, administra beneficios, resuelve conflictos.');
call sp_AgregarCargoEmpleado('Contador Financiero','Gestiona finanzas, prepara informes, asesora financiero.');



-- ------------------------------------------------------------------------------- ELIMINAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_EliminarCargoEmpleado(in odigoCargoEmpleado int)
begin
    delete from CargoEmpleado
    where CargoEmpleado.codigoCargoEmpleado = odigoCargoEmpleado;
end$$
delimiter ;
call sp_EliminarCargoEmpleado ('5');
-- ------------------------------------------------------------------------------- EDITAR --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_ActualizarCargoEmpleado(in umeroDocumento int,in ombreCargo varchar(45),in escripcionCargo varchar (65))
begin
    update CargoEmpleado
    set 
        CargoEmpleado.nombreCargo = ombreCargo,
        CargoEmpleado.descripcionCargo = escripcionCargo
        
        where
        CargoEmpleado.codigoCargoEmpleado = umeroDocumento;
end$$
delimiter ;

call sp_ActualizarCargoEmpleado(1,'Coordinador de Eventos','Planifica, coordina eventos, gestiona logística.');
-- ------------------------------------------------------------------------------- LISTA --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

delimiter $$
create procedure sp_MostrarCargoEmpleado ()
begin 
	select
	e.codigoCargoEmpleado ,
	e.nombreCargo  ,
	e.descripcionCargo 
    from cargoEmpleado e;
end $$        
delimiter ;
call sp_MostrarCargoEmpleado;
-- ------------------------------------------------------------------------------- Buscar --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
delimiter $$
create procedure sp_BuscarCargoEmpleado(in odigoCargoEmpleado int)
begin 
  select *from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado = odigoCargoEmpleado;
end$$ 
delimiter ;
call sp_BuscarCargoEmpleado(1);









-- ------------------------------------------------------------------------------- Producto --------------------------------------------------------------------------------------------------------
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


 -- -------------------------- Productos  Procedimiento Almacenados -----------------------------
 -- CRUD PRODUCTOS
 -- ---------------------------Agregar Producto-----------------------------

DELIMITER $$

CREATE PROCEDURE sp_agregarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_descripcionProducto VARCHAR(15),
    IN p_precioUnitario DECIMAL(10,2),
    IN p_precioDocena DECIMAL(10,2),
    IN p_precioMayor DECIMAL(10,2),
    IN p_existencia INT,
    IN p_codigoTipoProducto INT,
    IN p_codigoProveedor INT
)
BEGIN
    INSERT INTO Productos(codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, existencia, codigoTipoProducto, codigoProveedor)
    VALUES(p_codigoProducto, p_descripcionProducto, p_precioUnitario, p_precioDocena, p_precioMayor, p_existencia, p_codigoTipoProducto, p_codigoProveedor);
END$$
DELIMITER ;

CALL sp_agregarProducto('P001', 'Arroz', 5.99, 68.99, 129.99, 100, 2, 2);
CALL sp_agregarProducto('P002', 'Frijoles', 3.49, 39.99, 74.99, 150, 2, 2);
CALL sp_agregarProducto('P003', 'Aceite', 8.99, 102.99, 194.99,  80, 3, 2);
CALL sp_agregarProducto('P004', 'Leche Entera', 2.99, 32.99, 62.99, 120, 3, 4);
CALL sp_agregarProducto('P005', 'Azúcar', 4.49, 51.99, 98.99, 90, 4, 3);

-- -----------------------Listar Productos--------------------------------

Delimiter $$
create procedure sp_mostrarProductos()
	begin
    select
		p.codigoProducto,
        p.descripcionProducto,
        p.precioUnitario,
        p.precioDocena,
        p.precioMayor,
        p.existencia,
        p.codigoTipoProducto,
        p.codigoProveedor
        from
        productos p;
	end$$
Delimiter ;

call sp_mostrarProductos();

-- ----------------------------Editar Producto------------------------------------

DELIMITER $$
CREATE PROCEDURE sp_actualizarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_nuevaDescripcionProducto VARCHAR(15),
    IN p_nuevoPrecioUnitario DECIMAL(10,2),
    IN p_nuevoPrecioDocena DECIMAL(10,2),
    IN p_nuevoPrecioMayor DECIMAL(10,2),
    IN p_nuevaExistencia INT,
    IN p_nuevoCodigoTipoProducto INT,
    IN p_nuevoCodigoProveedor INT
)
BEGIN
    UPDATE Productos
    SET descripcionProducto = p_nuevaDescripcionProducto,
        precioUnitario = p_nuevoPrecioUnitario,
        precioDocena = p_nuevoPrecioDocena,
        precioMayor = p_nuevoPrecioMayor,
        existencia = p_nuevaExistencia,
        codigoTipoProducto = p_nuevoCodigoTipoProducto,
        codigoProveedor = p_nuevoCodigoProveedor
    WHERE codigoProducto = p_codigoProducto;
END$$
DELIMITER ;

call sp_actualizarProducto('P001', 'Pollo', 8.99, 69.99, 130.99, 100, 2, 2);

-- -----------------------Eliminar Producto------------------------------

Delimiter $$
CREATE PROCEDURE sp_eliminarProducto(IN _codigoProducto VARCHAR(15))
BEGIN
    DELETE FROM Productos
    WHERE codigoProducto = _codigoProducto;
END $$

DELIMITER ;

call sp_eliminarProducto('P001');
-- -----------------------------------