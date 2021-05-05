create table m_guarantee
(
    id        bigserial   not null
        constraint m_guarantee_pk
            primary key,
    car_id     bigint  not null
        constraint m_guarantee_m_cars_id_fk
            references m_cars,
    end_date   date    not null,
    extended   boolean not null,
    start_date date    not null
);

alter table m_guarantee
    owner to ctdidtksihqvpb;

INSERT INTO m_guarantee (car_id, end_date, extended, start_date) VALUES (1, '2026-03-29' ,true, '2021-03-29');
INSERT INTO m_guarantee (car_id, end_date, extended, start_date) VALUES (2, '2023-04-11', false, '2020-04-11');
INSERT INTO m_guarantee (car_id, end_date, extended, start_date) VALUES (4, '2024-02-02', false, '2021-02-02');
INSERT INTO m_guarantee (car_id, end_date, extended, start_date) VALUES (3, '2025-01-20', true, '2020-01-20');