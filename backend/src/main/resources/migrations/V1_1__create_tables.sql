create table m_brands
(
    id   bigserial    not null
        constraint m_brands_pk
            primary key,
    name varchar(100) not null
);

alter table m_brands
    owner to postgres;

create unique index m_brands_id_uindex
    on m_brands (id);

create unique index m_brands_name_uindex
    on m_brands (name);


create table m_models
(
    id       bigserial    not null
        constraint m_models_pk
            primary key,
    name     varchar(100) not null,
    brand_id bigint       not null
        constraint m_models_m_brands_id_fk
            references m_brands
);

alter table m_models
    owner to postgres;

create unique index m_models_id_uindex
    on m_models (id);

create unique index m_models_name_uindex
    on m_models (name);


create table m_cars
(
    id        bigserial   not null
        constraint m_cars_pk
            primary key,
    mileage   integer,
    photo_url varchar(255),
    vin_code  varchar(17) not null,
    year      integer     not null,
    brand_id  bigint      not null
        constraint m_cars_m_brands_id_fk
            references m_brands,
    model_id  bigint      not null
        constraint m_cars_m_models_id_fk
            references m_models
);

alter table m_cars
    owner to postgres;

create unique index m_cars_id_uindex
    on m_cars (id);

create unique index m_cars_vin_code_uindex
    on m_cars (vin_code);



