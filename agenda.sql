use agenda;
create table usuario(
numero int,
nombre varchar(40),
apellido varchar(40),
direccion varchar(40),
operadora varchar(40));

insert into usuario(numero, nombre, apellido,direccion,operadora )
values
(0999759756,"Kathy","Cangahuamin","sangolqui","tuenti"),
(0980192565,"Geovana","Cangahuamin","sangolqui","claro"),
(0987407106,"Franklin","Cangahuamin","capelo","movistar"),
(0993216269,"Luis","Andrango","conocoto","claro");
select * from usuario;
create table operadora
(
operadora varchar(40)
);
insert into operadora(operadora)values
("seleccione una operadora"),
("claro"),
("movistar"),
("tuenti"),
("cnt"),
("otra");

