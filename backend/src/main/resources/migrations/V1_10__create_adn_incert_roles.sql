create table m_roles
(
    id        bigserial    not null
        constraint m_roles_pk
            primary key,
    role_name varchar(100) not null
);

alter table m_roles
    owner to ctdidtksihqvpb;

create unique index m_roles_id_uindex
    on m_roles (id);

create unique index m_roles_role_name_uindex
    on m_roles (role_name);

INSERT INTO m_roles (role_name) VALUES ('ROLE_USER');
INSERT INTO m_roles (role_name) VALUES ('ROLE_MANAGER');
INSERT INTO m_roles (role_name) VALUES ('ROLE_ADMIN');

create table l_clients_roles
(
    client_id bigint not null
        constraint m_clients_roles_m_clients_id_fk
            references m_clients,
    role_id   bigint not null
        constraint m_clients_roles_m_roles_id_fk
            references m_roles
);

alter table l_clients_roles
    owner to ctdidtksihqvpb;

