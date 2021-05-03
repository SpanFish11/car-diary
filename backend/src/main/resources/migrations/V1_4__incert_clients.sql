INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Christopher', 'Gray', 'sopice5177@gridmire.com', 'iWfmxwRB');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Spencer', 'Haynes', 'hepaj53378@iludir.com', 'QTGiwrEI');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Frank', 'Stephens', 'vemifo5403@iludir.com', 'iJZyaqCB');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Abigail', 'Watkins', 'mewok99092@gridmire.com', 'vnGcCiMX');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Dorothy', 'Leonard', 'fijosij228@laraskey.com', 'pJipTKPX');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Mary', 'Simmons', 'padiwoh422@gridmire.com', 'PQsgsxiG');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Thomas', 'York', 'hohoyo2619@gridmire.com', 'EWnjvgnq');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Sybil', 'Brown', 'vigerix441@cnxingye.com', 'GsmrMqgV');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Robert', 'Shepherd', 'cedigag747@laraskey.com', 'ITuOBKqt');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Gilbert', 'Day', 'wojavaf117@gridmire.com', 'tQVQWyGp');
INSERT INTO m_clients (firstname, lastname, email, password) VALUES ('Lionel', 'Bryan', 'mijodop879@laraskey.com', 'oNNtPuSh');


UPDATE m_cars
SET client_id = 3
WHERE id = 3;

UPDATE m_cars
SET client_id = 1
WHERE id = 1;

UPDATE m_cars
SET client_id = 2
WHERE id = 2;

alter table m_cars alter column client_id set not null;