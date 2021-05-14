INSERT INTO m_brands (name) VALUES ('Nissan');
INSERT INTO m_brands (name) VALUES ('Geely');

INSERT INTO m_models (name, brand_id) VALUES ('Qashqai', 1);
INSERT INTO m_models (name, brand_id) VALUES ('X-Trail', 1);
INSERT INTO m_models (name, brand_id) VALUES ('Terrano', 1);
INSERT INTO m_models (name, brand_id) VALUES ('Murano', 1);
INSERT INTO m_models (name, brand_id) VALUES ('GS', 2);
INSERT INTO m_models (name, brand_id) VALUES ('Tugella', 2);
INSERT INTO m_models (name, brand_id) VALUES ('Coolray', 2);
INSERT INTO m_models (name, brand_id) VALUES ('Atlas', 2);
INSERT INTO m_models (name, brand_id) VALUES ('Emgrand', 2);

INSERT INTO m_equipments (name, engine_type, transmission_type, engine_size, power) VALUES ('Basic configuration', 'Petrol', 'Manual Transmission', 2.0, 235);
INSERT INTO m_equipments (name, engine_type, transmission_type, engine_size, power) VALUES ('Average configuration', 'Diesel', 'Automated Manual Transmission', 2.0, 249);
INSERT INTO m_equipments (name, engine_type, transmission_type, engine_size, power) VALUES ('Maximum configuration', 'Petrol', 'Automatic Transmission', 2.0, 310);
INSERT INTO m_equipments (name, engine_type, transmission_type, engine_size, power) VALUES ('Special Edition', 'Diesel', 'Continuously Variable Transmission', 2.0, 450);

INSERT INTO m_maintenance (operation_number, mileage, time_interval) VALUES ('TO-0', 2000, 2);
INSERT INTO m_maintenance (operation_number, mileage, time_interval) VALUES ('TO-1(intermediate)', 15000, 12);
INSERT INTO m_maintenance (operation_number, mileage, time_interval) VALUES ('TO-2(full)', 30000, 24);
INSERT INTO m_maintenance (operation_number, mileage, time_interval) VALUES ('Extra TO-1', 90000, 36);
INSERT INTO m_maintenance (operation_number, mileage, time_interval) VALUES ('Extra TO-2', 60000, 48);
INSERT INTO m_maintenance (operation_number, mileage, time_interval) VALUES ('Extra TO-3', 120000, 96);

INSERT INTO m_details (name, price, maintenance_id) VALUES ('Oil', 3.23, 1);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Oil filter', 9.99, 1);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Oil', 3.75, 2);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Oil filter', 10.99, 2);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Air filter', 14.56, 2);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Cabin filter', 17.67, 2);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Brake fluid', 87.99, 3);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Spark plugs', 67.89, 3);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Coolant flush', 73.78, 4);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Driver belt', 31.56, 5);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Drive pulley', 61.56, 5);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Timing belt', 34.56, 5);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Timing belt rollers', 48.54, 5);
INSERT INTO m_details (name, price, maintenance_id) VALUES ('Fuel filter', 78.56, 6);

INSERT INTO m_operations (name, price, maintenance_id) VALUES ( 'Oil and oil filter replacement', 75.09, 1);
INSERT INTO m_operations (name, price, maintenance_id) VALUES ( 'Oil and oil filter replacement', 65.15, 2);
INSERT INTO m_operations (name, price, maintenance_id) VALUES ( 'Air filter replacement', 56.34, 2);
INSERT INTO m_operations (name, price, maintenance_id) VALUES ( 'Cabin filter replacement', 37.55, 2);
INSERT INTO m_operations (name, price, maintenance_id) VALUES ( 'Brake fluid replacement', 89.99, 3);
INSERT INTO m_operations (name, price, maintenance_id) VALUES ( 'Spark plugs replacement', 55.65, 3);
INSERT INTO m_operations (name, price, maintenance_id) VALUES ( 'Coolant change', 113.47, 4);
INSERT INTO m_operations (name, price, maintenance_id) VALUES ( 'Drive belt idler pulley replacement', 137.59, 5);
INSERT INTO m_operations (name, price, maintenance_id) VALUES ( ' Timing belt and rollers replacement', 377.76, 5);
INSERT INTO m_operations (name, price, maintenance_id) VALUES ( 'Fuel filter replacement ', 89.87, 6);

INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Christopher', 'Gray', 'sopice5177@gridmire.com', '$2a$10$I2qMFWjftjgIcoYAaday4uxfiJfe8q4b/ISefQDbPOX0D3T4pOjHW');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Spencer', 'Haynes', 'hepaj53378@iludir.com', '$2a$10$I2qMFWjftjgIcoYAaday4uxfiJfe8q4b/ISefQDbPOX0D3T4pOjHW');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Frank', 'Stephens', 'vemifo5403@iludir.com', '$2a$10$I2qMFWjftjgIcoYAaday4uxfiJfe8q4b/ISefQDbPOX0D3T4pOjHW');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Abigail', 'Watkins', 'mewok99092@gridmire.com', '$2a$10$I2qMFWjftjgIcoYAaday4uxfiJfe8q4b/ISefQDbPOX0D3T4pOjHW');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Dorothy', 'Leonard', 'fijosij228@laraskey.com', '$2a$10$I2qMFWjftjgIcoYAaday4uxfiJfe8q4b/ISefQDbPOX0D3T4pOjHW');

INSERT INTO m_roles (role_name) VALUES ('ROLE_USER');
INSERT INTO m_roles (role_name) VALUES ('ROLE_MANAGER');
INSERT INTO m_roles (role_name) VALUES ('ROLE_ADMIN');

INSERT INTO l_clients_roles(client_id, role_id) VALUES (1, 1);
INSERT INTO l_clients_roles(client_id, role_id) VALUES (2, 1);
INSERT INTO l_clients_roles(client_id, role_id) VALUES (3, 1);
INSERT INTO l_clients_roles(client_id, role_id) VALUES (4, 3);
INSERT INTO l_clients_roles(client_id, role_id) VALUES (4, 2);
INSERT INTO l_clients_roles(client_id, role_id) VALUES (5, 1);

INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id, price, used) VALUES (0, 'https://spanfishbucket.s3.eu-central-1.amazonaws.com/cars/988912e4-5f92-4989-ad49-5cc2611b7f05.jpg', '4Y1SL65848Z411439', 2021, 7, false, 2, 3, 40000.00, false);
INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id, price, used) VALUES (5476, 'https://spanfishbucket.s3.eu-central-1.amazonaws.com/cars/988912e4-5f92-4989-ad49-5cc2611b7f05.jpg', '43HHH765KGFG34SDF', 2020, 2, true, 5, 4, 100000.00, true);
INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id, price, used) VALUES (25405, 'https://spanfishbucket.s3.eu-central-1.amazonaws.com/cars/988912e4-5f92-4989-ad49-5cc2611b7f05.jpg', '4S3BMHB68B3286050', 2017, 8, true, 1, 1, 15000.00, true);
INSERT INTO m_cars (mileage, photo_url, vin_code, year, model_id, ours, client_id, equipment_id, price, used) VALUES (8907, 'https://spanfishbucket.s3.eu-central-1.amazonaws.com/cars/988912e4-5f92-4989-ad49-5cc2611b7f05.jpg', '5YJSA1DG9DFP14705', 2019, 4, false, 3, 2, 20000.00, true);

INSERT INTO m_guarantee (car_id, end_date, extended, start_date) VALUES (1, '2026-03-29', true, '2021-03-29');
INSERT INTO m_guarantee (car_id, end_date, extended, start_date) VALUES (2, '2023-04-11', false, '2020-04-11');
INSERT INTO m_guarantee (car_id, end_date, extended, start_date) VALUES (4, '2024-02-02', false, '2021-02-02');
INSERT INTO m_guarantee (car_id, end_date, extended, start_date) VALUES (3, '2025-01-20', true, '2020-01-20');

INSERT INTO m_service_records (date, mileage, operation_number, car_id) VALUES ('2021-02-03', 28745, null, 1);
INSERT INTO m_service_records (date, mileage, operation_number, car_id) VALUES ('2020-11-11', 35046, 'TO-2(full)', 1);
INSERT INTO m_service_records (date, mileage, operation_number, car_id) VALUES ('2020-02-03', 2075, 'TO-0', 2);
INSERT INTO m_service_records (date, mileage, operation_number, car_id) VALUES ('2020-06-07', 9000, null, 2);
INSERT INTO m_service_records (date, mileage, operation_number, car_id) VALUES ('2020-12-12', 17032, 'TO-1(intermediate)', 2);
INSERT INTO m_service_records (date, mileage, operation_number, car_id) VALUES ('2021-04-05', 11592, null, 4);

INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id) VALUES (true, 'Brake fluid replacement', 89.99, 2);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id) VALUES (true, 'Spark plugs replacement', 55.65, 2);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id) VALUES (false, 'Oil and oil filter replacement', 75.09, 1);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id) VALUES (true, 'Oil and oil filter replacement', 75.09, 3);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id) VALUES (false, 'Car repainting', 176.45, 4);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id) VALUES (true, 'Oil and oil filter replacement', 65.15, 5);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id) VALUES (true, 'Air filter replacement', 56.34, 5);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id) VALUES (true, 'Cabin filter replacement', 37.55, 5);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id) VALUES (false, 'Front fender replacement', 87.67, 6);
INSERT INTO m_service_work (guarantee, name, price, service_operation_record_id) VALUES (false, 'Flat tire and broken glass replacement', 48.65, 6);

INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Oil', 3.23, true, 1);
INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Oil filter', 9.99, false, 1);
INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Brake fluid', 87.99, true, 2);
INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Spark plugs', 67.89, true, 2);
INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Oil', 3.23, true, 3);
INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Oil filter', 9.99, true, 3);
INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Oil', 3.75, true, 5);
INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Oil filter', 10.99, true, 5);
INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Air filter', 14.56, true, 5);
INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Cabin filter', 17.67, true, 5);
INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Flat tire', 5.45, false, 6);
INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Glass', 25.67, true, 6);
INSERT INTO m_changable_parts (model, price, replaced, service_operation_record_id) VALUES ('Front fender', 76.45, true, 6);
