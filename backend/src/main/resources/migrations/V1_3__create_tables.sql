create table if not exists m_clients
(
    id        bigserial    not null
        constraint m_clients_pk
            primary key,
    firstname varchar(255) not null,
    lastname  varchar(255) not null,
    email     varchar(255) not null,
    password  varchar(255) not null
);

alter table m_clients
    owner to postgres;

create unique index m_clients_email_uindex
    on m_clients (email);

alter table m_cars
    drop column brand_id;

alter table m_cars
    add ours bool;

alter table m_cars
    add client_id bigint;

alter table m_cars
    add constraint m_cars_m_clients_id_fk
        foreign key (client_id) references m_clients;