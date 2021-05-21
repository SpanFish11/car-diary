create table m_appointment
(
    id             bigserial    not null
        constraint m_appointment_pk
            primary key,
    date           date         not null,
    description    varchar(255) not null,
    repairment     boolean      not null,
    status         varchar(255) not null,
    maintenance_id bigint
        constraint m_appointment_m_maintenance_id_fk
            references m_maintenance,
    car_id         bigint
        constraint m_appointment_m_cars_id_fk references m_cars
);

alter table m_appointment
    owner to adjqovjatskhlb;
