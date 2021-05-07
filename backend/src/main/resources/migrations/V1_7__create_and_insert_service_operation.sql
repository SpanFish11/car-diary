create table m_service_records
(
    id               bigserial not null
        constraint m_service_records_pk
            primary key,
    date             date      not null,
    mileage          integer   not null,
    operation_number varchar(100),
    car_id           bigint    not null
        constraint m_service_records_m_cars_id_fk
            references m_cars
);

alter table m_service_records
    owner to ctdidtksihqvpb;

create unique index m_service_records_id_uindex
    on m_service_records (id);

create table m_service_work
(
    id                          bigserial not null
        constraint m_service_work_pk
            primary key,
    guarantee                   boolean   not null,
    name                        varchar   not null,
    price                       numeric(10, 2),
    service_operation_record_id bigint
        constraint m_service_work_m_service_records_id_fk
            references m_service_records
);

alter table m_service_work
    owner to ctdidtksihqvpb;

create unique index m_service_work_id_uindex
    on m_service_work (id);

create table m_changable_parts
(
    id                          bigserial      not null
        constraint m_changable_parts_pk
            primary key,
    model                       varchar        not null,
    price                       numeric(10, 2) not null,
    replaced                    boolean        not null,
    service_operation_record_id bigint
        constraint m_changable_parts_m_service_records_id_fk
            references m_service_records
);

alter table m_changable_parts
    owner to ctdidtksihqvpb;

create unique index m_changable_parts_id_uindex
    on m_changable_parts (id);

INSERT INTO m_service_records (id, date, mileage, operation_number, car_id)
VALUES (1, '2021-02-03', 28745, null, 1);
INSERT INTO m_service_records (id, date, mileage, operation_number, car_id)
VALUES (2, '2020-11-11', 35046, 'TO-2(full)', 1);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id)
VALUES (true, 'Brake fluid replacement', 89.99, 2);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id)
VALUES (true, 'Spark plugs replacement', 55.65, 2);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id)
VALUES (false, 'Oil and oil filter replacement', 75.09, 1);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Oil', 3.23, true, 1);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Oil filter', 9.99, false, 1);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Brake fluid', 87.99, true, 2);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Spark plugs', 67.89, true, 2);

INSERT INTO m_service_records (id, date, mileage, operation_number, car_id)
VALUES (3, '2020-02-03', 2075, 'TO-0', 2);
INSERT INTO m_service_records (id, date, mileage, operation_number, car_id)
VALUES (4, '2020-06-07', 9000, null, 2);
INSERT INTO m_service_records (id, date, mileage, operation_number, car_id)
VALUES (5, '2020-12-12', 17032, 'TO-1(intermediate)', 2);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id)
VALUES (true, 'Oil and oil filter replacement', 75.09, 3);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id)
VALUES (false, 'Car repainting', 176.45, 4);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id)
VALUES (true, 'Oil and oil filter replacement', 65.15, 5);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id)
VALUES (true, 'Air filter replacement', 56.34, 5);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id)
VALUES (true, 'Cabin filter replacement', 37.55, 5);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Oil', 3.23, true, 3);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Oil filter', 9.99, true, 3);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Oil', 3.75, true, 5);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Oil filter', 10.99, true, 5);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Air filter', 14.56, true, 5);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Cabin filter', 17.67, true, 5);

INSERT INTO m_service_records (id, date, mileage, operation_number, car_id)
VALUES (8, '2021-04-05', 11592, null, 4);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id)
VALUES (false, 'Front fender replacement', 87.67, 8);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id)
VALUES (false, 'Flat tire and broken glass replacement', 48.65, 8);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Flat tire', 5.45, false, 8);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Glass', 25.67, true, 8);
INSERT INTO m_changable_parts(model, price, replaced, service_operation_record_id)
VALUES ('Front fender', 76.45, true, 8);
