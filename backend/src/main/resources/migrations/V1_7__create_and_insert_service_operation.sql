create table m_service_records
(
    id               bigserial    not null
        constraint m_service_records_pk
            primary key,
    date             date         not null,
    mileage          integer      not null,
    operation_number varchar(100) not null,
    car_id           bigint       not null
        constraint m_service_records_m_cars_id_fk
            references m_cars
);

alter table m_service_records
    owner to ctdidtksihqvpb;

create unique index m_service_records_id_uindex
    on m_service_records (id);

create table m_service_work
(
    id                          bigserial      not null
        constraint m_service_work_pk
            primary key,
    guarantee                   boolean        not null,
    name                        varchar        not null,
    price                       numeric(10, 2) not null,
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
