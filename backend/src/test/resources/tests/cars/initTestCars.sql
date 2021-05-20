DELETE
FROM m_cars;
DELETE
FROM m_models;
DELETE
FROM m_brands;

INSERT INTO m_brands (id, name)
VALUES (1, 'Nissan');
INSERT INTO m_brands (id, name)
VALUES (2, 'Geely');

INSERT INTO m_models (id, name, brand_id)
VALUES (1, 'Coolray', 2);
INSERT INTO m_models (id, name, brand_id)
VALUES (2, 'Terrano', 1);

INSERT INTO m_cars (id, model_id, year, photo_url, vin_code, mileage)
VALUES (1, 2, 2017, 'nissan.jpg', '4S3BMHB68B3286050', 25405);
INSERT INTO m_cars (id, model_id, year, photo_url, vin_code, mileage)
VALUES (2, 1, 2021, 'geely.jpg', '4Y1SL65848Z411439', 0);