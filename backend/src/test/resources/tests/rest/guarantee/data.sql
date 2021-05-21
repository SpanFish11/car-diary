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

INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id,
                    price, used)
VALUES (0,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/cars/988912e4-5f92-4989-ad49-5cc2611b7f05.jpg',
        '4S3BMHB68B3386050', 2021, 1, true, 1, 1, 10000.00, false);

INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id,
                    price, used)
VALUES (0,
        'https://spanfishbucket.s3.eu-central-1.amazonaws.com/cars/988912e4-5f92-4989-ad49-5cc2611b7f05.jpg',
        '4S3BMHB68B3486050', 2021, 1, true, 1, 1, 20000.00, false);

INSERT INTO m_guarantee (car_id, end_date, extended, start_date, mileage)
VALUES (1, '2023-04-11', false, '2020-04-11', 100000);

INSERT INTO m_guarantee (car_id, end_date, extended, start_date, mileage)
VALUES (3, '2025-04-11', true, '2020-04-11', 150000);