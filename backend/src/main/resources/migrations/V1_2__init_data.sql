INSERT INTO m_brands (id, name) VALUES (1, 'Nissan');
INSERT INTO m_brands (id, name) VALUES (2, 'Geely');

INSERT INTO m_models (id, name, brand_id) VALUES (1, 'Qashqai', 1);
INSERT INTO m_models (id, name, brand_id) VALUES (2, 'X-Trail', 1);
INSERT INTO m_models (id, name, brand_id) VALUES (3, 'Terrano', 1);
INSERT INTO m_models (id, name, brand_id) VALUES (4, 'Murano', 1);

INSERT INTO m_models (id, name, brand_id) VALUES (5, 'GS', 2);
INSERT INTO m_models (id, name, brand_id) VALUES (6, 'Tugella', 2);
INSERT INTO m_models (id, name, brand_id) VALUES (7, 'Coolray', 2);
INSERT INTO m_models (id, name, brand_id) VALUES (8, 'Atlas', 2);
INSERT INTO m_models (id, name, brand_id) VALUES (9, 'Emgrand', 2);

INSERT INTO m_cars (model_id, brand_id, year, photo_url, vin_code,mileage) VALUES (8,2,2017,'https://spanfishbucket.s3.eu-central-1.amazonaws.com/cars/988912e4-5f92-4989-ad49-5cc2611b7f05.jpg','4S3BMHB68B3286050', 25405);
INSERT INTO m_cars (model_id, brand_id, year, photo_url, vin_code,mileage) VALUES (7,1,2021,'https://spanfishbucket.s3.eu-central-1.amazonaws.com/cars/988912e4-5f92-4989-ad49-5cc2611b7f05.jpg','4Y1SL65848Z411439', 0);
INSERT INTO m_cars (model_id, brand_id, year, photo_url, vin_code,mileage) VALUES (4,1,2019,'https://spanfishbucket.s3.eu-central-1.amazonaws.com/cars/988912e4-5f92-4989-ad49-5cc2611b7f05.jpg','5YJSA1DG9DFP14705', 8907);