create table m_equipments
(
    id                bigserial     not null
        constraint table_name_pk
            primary key,
    name              varchar(100)  not null,
    engine_type       varchar(100)  not null,
    transmission_type varchar(100)  not null,
    engine_size       numeric(3, 1) not null,
    power             integer       not null
);

alter table m_equipments
    owner to ctdidtksihqvpb;

create unique index table_name_id_uindex
    on m_equipments (id);

create unique index table_name_name_uindex
    on m_equipments (name);

INSERT INTO m_equipments (name, engine_type, transmission_type, engine_size, power) VALUES ('Basic configuration', 'Petrol', 'Manual Transmission', 2.0, 235);
INSERT INTO m_equipments (name, engine_type, transmission_type, engine_size, power) VALUES ('Average configuration', 'Diesel', 'Automated Manual Transmission', 2.0, 249);
INSERT INTO m_equipments (name, engine_type, transmission_type, engine_size, power) VALUES ('Maximum configuration', 'Petrol', 'Automatic Transmission', 2.0, 310);
INSERT INTO m_equipments (name, engine_type, transmission_type, engine_size, power) VALUES ('Special Edition', 'Diesel', 'Continuously Variable Transmission', 2.0, 450);

alter table m_cars
    add equipment_id bigint;

alter table m_cars
    add constraint m_cars_m_equipments_id_fk
        foreign key (equipment_id) references m_equipments;

UPDATE m_cars
SET ours         = false,
    equipment_id = 2
WHERE id = 3;

UPDATE m_cars
SET ours         = true,
    equipment_id = 1
WHERE id = 1;

UPDATE m_cars
SET ours         = false,
    equipment_id = 3
WHERE id = 2;

INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id)
VALUES (5476,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/cars/988912e4-5f92-4989-ad49-5cc2611b7f05.jpg',
        '43HHH765KGFG34SDF', 2020, 2, true, 5, 4);

alter table m_cars alter column ours set not null;

alter table m_cars alter column equipment_id set not null;