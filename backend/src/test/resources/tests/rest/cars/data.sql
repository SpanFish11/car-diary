INSERT INTO m_brands (id, name)
VALUES (1, 'Nissan');
INSERT INTO m_brands (id, name)
VALUES (2, 'Geely');

INSERT INTO m_models (id, name, brand_id)
VALUES (1, 'Coolray', 2);
INSERT INTO m_models (id, name, brand_id)
VALUES (2, 'Terrano', 1);

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
VALUES (25405, 'url', '4S3BMHB68B3286050', 2017, 1, true, 1, 1, 15000.00, true);
INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id,
                    price, used)
VALUES (0, 'url', '4S3BMHB68B3386050', 2021, 1, true, 1, 1, 10000.00, false);
INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id,
                    price, used)
VALUES (0, 'url', '4S3BMHB68B3486050', 2021, 1, true, 1, 1, 20000.00, false);
INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id,
                    price, used)
VALUES (0, 'url', '4S3BMHB68B3586050', 2021, 1, true, 1, 1, 20000.00, false);

INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id,
                    price, used)
VALUES (0, 'url', '4S3BMHB68B3686050', 2021, 1, true, 1, 1, 20000.00, false);
INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id,
                    price, used)
VALUES (0, 'url', '4S3BMHB68B3786050', 2021, 1, true, 1, 1, 20000.00, false);