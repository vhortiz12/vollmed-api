alter table pacientes add calle VARCHAR(100) NOT NULL;
alter table pacientes add distrito VARCHAR(100) NOT NULL;
alter table pacientes add complemento VARCHAR(100);
alter table pacientes add telefono varchar(20) not null;
alter table pacientes add numero INT;
alter table pacientes add column activo tinyint;
update pacientes set activo = 1;
alter table pacientes modify activo tinyint not null;