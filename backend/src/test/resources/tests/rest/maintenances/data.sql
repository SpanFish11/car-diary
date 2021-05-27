DELETE
FROM m_maintenance;
DELETE
FROM m_details;

INSERT INTO m_maintenance (operation_number, mileage, time_interval)
VALUES ('TO-0', 2000, 2);
INSERT INTO m_maintenance (operation_number, mileage, time_interval)
VALUES ('TO-1(intermediate)', 15000, 12);

INSERT INTO m_details (name, price, maintenance_id)
VALUES ('Oil', 3.23, 1);
INSERT INTO m_details (name, price, maintenance_id)
VALUES ('Oil filter', 9.99, 1);
INSERT INTO m_details (name, price, maintenance_id)
VALUES ('Oil', 3.75, 2);
INSERT INTO m_details (name, price, maintenance_id)
VALUES ('Oil filter', 10.99, 2);
INSERT INTO m_details (name, price, maintenance_id)
VALUES ('Air filter', 14.56, 2);
INSERT INTO m_details (name, price, maintenance_id)
VALUES ('Cabin filter', 17.67, 2);

INSERT INTO m_operations (name, price, maintenance_id)
VALUES ('Oil and oil filter replacement', 75.09, 1);
INSERT INTO m_operations (name, price, maintenance_id)
VALUES ('Oil and oil filter replacement', 65.15, 2);
INSERT INTO m_operations (name, price, maintenance_id)
VALUES ('Air filter replacement', 56.34, 2);
INSERT INTO m_operations (name, price, maintenance_id)
VALUES ('Cabin filter replacement', 37.55, 2);