alter table m_cars
    add price numeric(10,2);

UPDATE m_cars
SET price = 40000.00
WHERE id = 2;

UPDATE m_cars
SET price = 20000.00
WHERE id = 3;

UPDATE m_cars
SET price = 15000.00
WHERE id = 1;

UPDATE m_cars
SET price = 100000.00
WHERE id = 4;

alter table m_cars alter column price set not null;

alter table m_cars
    add used boolean;

UPDATE m_cars
SET used = false
WHERE id = 2;

UPDATE m_cars
SET used = true
WHERE id = 4;

UPDATE m_cars
SET used = true
WHERE id = 1;

UPDATE m_cars
SET used = true
WHERE id = 3;

alter table m_cars alter column used set not null;