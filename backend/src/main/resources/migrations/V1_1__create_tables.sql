create table m_brands
(
    id bigserial not null
        constraint m_brands_pk
            primary key,
    name varchar(100) not null
);

alter table m_brands owner to ctdidtksihqvpb;

create unique index m_brands_id_uindex
    on m_brands (id);

create unique index m_brands_name_uindex
    on m_brands (name);

create table m_models
(
    id bigserial not null
        constraint m_models_pk
            primary key,
    name varchar(100) not null,
    brand_id bigint not null
        constraint m_models_m_brands_id_fk
            references m_brands
);

alter table m_models owner to ctdidtksihqvpb;

create unique index m_models_id_uindex
    on m_models (id);

create unique index m_models_name_uindex
    on m_models (name);

create table m_clients
(
    id bigserial not null
        constraint m_clients_pk
            primary key,
    firstname varchar(255) not null,
    lastname varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null
);

alter table m_clients owner to ctdidtksihqvpb;

create unique index m_clients_email_uindex
    on m_clients (email);

create table m_equipments
(
    id bigserial not null
        constraint table_name_pk
            primary key,
    name varchar(100) not null,
    engine_type varchar(100) not null,
    transmission_type varchar(100) not null,
    engine_size numeric(3,1) not null,
    power integer not null
);

alter table m_equipments owner to ctdidtksihqvpb;

create table m_cars
(
    id bigserial not null
        constraint m_cars_pk
            primary key,
    mileage integer,
    photo_url varchar(255),
    vin_code varchar(17) not null,
    year integer not null,
    model_id bigint not null
        constraint m_cars_m_models_id_fk
            references m_models,
    ours boolean not null,
    client_id bigint not null
        constraint m_cars_m_clients_id_fk
            references m_clients,
    equipment_id bigint not null
        constraint m_cars_m_equipments_id_fk
            references m_equipments,
    price numeric(10,2) not null,
    used boolean not null
);

alter table m_cars owner to ctdidtksihqvpb;

create unique index m_cars_id_uindex
    on m_cars (id);

create unique index m_cars_vin_code_uindex
    on m_cars (vin_code);

create unique index table_name_id_uindex
    on m_equipments (id);

create unique index table_name_name_uindex
    on m_equipments (name);

create table m_service_records
(
    id bigserial not null
        constraint m_service_records_pk
            primary key,
    date date not null,
    mileage integer not null,
    operation_number varchar(100),
    car_id bigint not null
        constraint m_service_records_m_cars_id_fk
            references m_cars
);

alter table m_service_records owner to ctdidtksihqvpb;

create unique index m_service_records_id_uindex
    on m_service_records (id);

create table m_service_work
(
    id bigserial not null
        constraint m_service_work_pk
            primary key,
    guarantee boolean not null,
    name varchar not null,
    price numeric(10,2),
    service_operation_record_id bigint
        constraint m_service_work_m_service_records_id_fk
            references m_service_records
);

alter table m_service_work owner to ctdidtksihqvpb;

create unique index m_service_work_id_uindex
    on m_service_work (id);

create table m_changable_parts
(
    id bigserial not null
        constraint m_changable_parts_pk
            primary key,
    model varchar not null,
    price numeric(10,2) not null,
    replaced boolean not null,
    service_operation_record_id bigint
        constraint m_changable_parts_m_service_records_id_fk
            references m_service_records
);

alter table m_changable_parts owner to ctdidtksihqvpb;

create unique index m_changable_parts_id_uindex
    on m_changable_parts (id);

create table m_maintenance
(
    id bigserial not null
        constraint m_maintenance_pk
            primary key,
    operation_number varchar(50) not null,
    mileage bigint not null,
    time_interval integer not null
);

alter table m_maintenance owner to ctdidtksihqvpb;

create unique index m_maintenance_id_uindex
    on m_maintenance (id);

create unique index m_maintenance_operation_number_uindex
    on m_maintenance (operation_number);

create table m_operations
(
    id bigserial not null
        constraint m_operations_pk
            primary key,
    name varchar not null,
    price numeric(10,2) not null,
    maintenance_id bigint
        constraint m_operations_m_maintenance_id_fk
            references m_maintenance
);

alter table m_operations owner to ctdidtksihqvpb;

create unique index m_operations_id_uindex
    on m_operations (id);

create table m_details
(
    id bigserial not null
        constraint m_details_parts_pk
            primary key,
    name varchar not null,
    price numeric(10,2) not null,
    maintenance_id bigint
        constraint m_details_m_maintenance_id_fk
            references m_maintenance
);

alter table m_details owner to ctdidtksihqvpb;

create unique index m_details_id_uindex
    on m_details (id);

create table m_guarantee
(
    id bigserial not null
        constraint m_guarantee_pk
            primary key,
    car_id bigint not null
        constraint m_guarantee_m_cars_id_fk
            references m_cars,
    end_date date not null,
    extended boolean not null,
    start_date date not null
);

alter table m_guarantee owner to ctdidtksihqvpb;

create table m_roles
(
    id bigserial not null
        constraint m_roles_pk
            primary key,
    role_name varchar(100) not null
);

alter table m_roles owner to ctdidtksihqvpb;

create unique index m_roles_id_uindex
    on m_roles (id);

create unique index m_roles_role_name_uindex
    on m_roles (role_name);

create table l_clients_roles
(
    client_id bigint not null
        constraint m_clients_roles_m_clients_id_fk
            references m_clients,
    role_id bigint not null
        constraint m_clients_roles_m_roles_id_fk
            references m_roles
);

alter table l_clients_roles owner to ctdidtksihqvpb;

CREATE FUNCTION change_mileage() RETURNS trigger AS $change_mileage$
BEGIN
    UPDATE m_cars
    SET mileage = NEW.mileage
    WHERE id = NEW.car_id;
    RETURN NEW;
END;
$change_mileage$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS change_mileage ON m_service_records;
CREATE TRIGGER change_mileage AFTER INSERT OR UPDATE ON m_service_records
    FOR EACH ROW EXECUTE PROCEDURE change_mileage();

ALTER FUNCTION change_mileage() OWNER TO ctdidtksihqvpb;