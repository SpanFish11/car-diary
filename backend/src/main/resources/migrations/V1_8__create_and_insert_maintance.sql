create table m_maintenance
(
    id               bigserial   not null
        constraint m_maintenance_pk
            primary key,
    operation_number varchar(50) not null,
    mileage          bigint      not null,
    time_interval    int         not null
);

alter table m_maintenance
    owner to ctdidtksihqvpb;

create
    unique index m_maintenance_id_uindex
    on m_maintenance (id);

create
    unique index m_maintenance_operation_number_uindex
    on m_maintenance (operation_number);

INSERT INTO m_maintenance (id, operation_number, mileage, time_interval)
VALUES (1, 'TO-0', 2000, 2);
INSERT INTO m_maintenance (id, operation_number, mileage, time_interval)
VALUES (2, 'TO-1(intermediate)', 15000, 12);
INSERT INTO m_maintenance (id, operation_number, mileage, time_interval)
VALUES (3, 'TO-2(full)', 30000, 24);
INSERT INTO m_maintenance (id, operation_number, mileage, time_interval)
VALUES (4, 'Extra TO-1', 90000, 36);
INSERT INTO m_maintenance (id, operation_number, mileage, time_interval)
VALUES (5, 'Extra TO-2', 60000, 48);
INSERT INTO m_maintenance (id, operation_number, mileage, time_interval)
VALUES (6, 'Extra TO-3', 120000, 96);

create table m_operations
(
    id             bigserial      not null
        constraint m_operations
            primary key,
    name           varchar        not null,
    price          numeric(10, 2) not null,
    maintenance_id bigint
        constraint m_operations_m_maintenance_id_fk
            references m_maintenance
);

alter table m_operations
    owner to ctdidtksihqvpb;

create
    unique index m_operations_id_uindex
    on m_operations (id);

INSERT INTO m_operations (id, name, price, maintenance_id)
VALUES (1, 'Changing the oil and oil filter', 75.09, 1);
INSERT INTO m_operations (id, name, price, maintenance_id)
VALUES (2, 'Changing the oil and oil filter', 65.15, 2);
INSERT INTO m_operations (id, name, price, maintenance_id)
VALUES (3, 'Air filter replacement', 56.34, 2);
INSERT INTO m_operations (id, name, price, maintenance_id)
VALUES (4, 'Replacing the cabin filter', 37.55, 2);
INSERT INTO m_operations (id, name, price, maintenance_id)
VALUES (5, 'Brake fluid replacement', 89.99, 3);
INSERT INTO m_operations (id, name, price, maintenance_id)
VALUES (6, 'Spark plugs replacement', 55.65, 3);
INSERT INTO m_operations (id, name, price, maintenance_id)
VALUES (7, 'Coolant change', 113.47, 4);
INSERT INTO m_operations (id, name, price, maintenance_id)
VALUES (8, 'Drive belt idler pulley replacement', 137.59, 5);
INSERT INTO m_operations (id, name, price, maintenance_id)
VALUES (9, ' Timing belt and rollers replacement', 377.76, 5);
INSERT INTO m_operations (id, name, price, maintenance_id)
VALUES (10, 'Fuel filter replacement ', 89.87, 6);

create table m_details
(
    id             bigserial      not null
        constraint m_details_parts_pk
            primary key,
    name           varchar        not null,
    price          numeric(10, 2) not null,
    maintenance_id bigint
        constraint m_details_m_maintenance_id_fk
            references m_maintenance
);

alter table m_details
    owner to ctdidtksihqvpb;

create
    unique index m_details_id_uindex
    on m_details (id);

INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (1, 'Oil', 3.23, 1);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (2, 'Oil filter', 9.99, 1);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (3, 'Oil', 3.75, 2);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (4, 'Oil filter', 10.99, 2);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (5, 'Air filter', 14.56, 2);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (6, 'Cabin filter', 17.67, 2);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (7, 'Brake fluid', 87.99, 3);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (8, 'Spark plugs', 67.89, 3);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (9, 'Coolant flush', 73.78, 4);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (10, 'Driver belt', 31.56, 5);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (11, 'Drive pulley', 61.56, 5);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (12, 'Timing belt', 34.56, 5);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (13, 'Timing belt rollers', 48.54, 5);
INSERT INTO m_details (id, name, price, maintenance_id)
VALUES (14, 'Fuel filter', 78.56, 6);