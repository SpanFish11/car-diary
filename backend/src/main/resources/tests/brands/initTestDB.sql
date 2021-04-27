DELETE FROM m_models;
DELETE FROM m_brands;

INSERT INTO m_brands (id, name) VALUES (1, 'Nissan');
INSERT INTO m_brands (id, name) VALUES (2, 'Geely');

INSERT INTO m_models (id, name, brand_id) VALUES (1, 'Coolray', 2);
INSERT INTO m_models (id, name, brand_id) VALUES (2, 'Terrano', 1);
INSERT INTO m_models (id, name, brand_id) VALUES (3, 'Emgrand', 2);