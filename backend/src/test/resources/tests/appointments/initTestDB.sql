DELETE
FROM m_appointment;
DELETE
FROM m_guarantee;
DELETE
FROM m_cars;
DELETE
FROM l_clients_roles;
DELETE
FROM m_roles;
DELETE
FROM m_clients;
DELETE
FROM m_equipments;
DELETE
FROM m_models;
DELETE
FROM m_brands;

INSERT INTO m_brands (name)
VALUES ('Nissan');

INSERT INTO m_models (name, brand_id)
VALUES ('Qashqai', 1);

INSERT INTO m_equipments (name, engine_type, transmission_type, engine_size, power)
VALUES ('Basic configuration', 'Petrol', 'Manual Transmission', 2.0, 235);

INSERT INTO m_clients (firstname, lastname, email, password)
VALUES ('Christopher', 'Gray', 'sopice5177@gridmire.com',
        '$2a$10$I2qMFWjftjgIcoYAaday4uxfiJfe8q4b/ISefQDbPOX0D3T4pOjHW');

INSERT INTO m_roles (role_name)
VALUES ('ROLE_USER');

INSERT INTO l_clients_roles(client_id, role_id)
VALUES (1, 1);

INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id,
                    price, used)
VALUES (25405,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/cars/988912e4-5f92-4989-ad49-5cc2611b7f05.jpg',
        '4S3BMHB68B3286050', 2017, 1, true, 1, 1, 15000.00, true);

INSERT INTO m_guarantee (car_id, end_date, extended, start_date, mileage)
VALUES (1, '2025-01-20', true, '2020-01-20', 150000);

INSERT INTO m_appointment (id, date, description, repairment, status, maintenance_id, car_id)
VALUES (1, '2021-05-24', 'Something go wrong', true, 'PENDING', null, 1);