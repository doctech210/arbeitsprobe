-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-1');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-2');
-- insert into myentity (id, field) values(nextval('hibernate_sequence'), 'field-3');



--Eissorten:
INSERT INTO Eis (id, name) VALUES (100, 'Schokolade');
INSERT INTO Eis_allergene (Eis_id, allergene) VALUES (100, 'LAKTOSE');
INSERT INTO Eis (id, name) VALUES (101, 'Vanille');
INSERT INTO Eis_allergene (Eis_id, allergene) VALUES (101, 'LAKTOSE');
INSERT INTO Eis (id, name) VALUES (102, 'Joghurt');
INSERT INTO Eis_allergene (Eis_id, allergene) VALUES (102, 'LAKTOSE');
INSERT INTO Eis (id, name) VALUES (103, 'Erdbeere');
INSERT INTO Eis (id, name) VALUES (104, 'Mango');
INSERT INTO Eis (id, name) VALUES (105, 'Schokolade Vegan');
INSERT INTO Eis (id, name) VALUES (106, 'Vanille Vegan');
INSERT INTO Eis (id, name) VALUES (107, 'Nuss-Nougat');
INSERT INTO Eis_allergene (Eis_id, allergene) VALUES (107, 'NUSS');
INSERT INTO Eis (id, name) VALUES (108, 'Salted Caramell');
INSERT INTO Eis_allergene (Eis_id, allergene) VALUES (108, 'LAKTOSE');

--Saucen:
INSERT INTO Sauce (name, id) VALUES ('Ahornsirup', 100);
INSERT INTO Sauce (name, id) VALUES ('Erdbeere hausgemacht', 101);
INSERT INTO Sauce (name, id) VALUES ('Haselnuss', 102);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (102, 'NUSS');
INSERT INTO Sauce (name, id) VALUES ('Karamell', 103);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (103, 'LAKTOSE');
INSERT INTO Sauce (name, id) VALUES ('Karamell', 104);
INSERT INTO Sauce (name, id) VALUES ('Lakritzsauce', 105);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (105, 'LAKTOSE');
INSERT INTO Sauce (name, id) VALUES ('Mangopueree', 106);
INSERT INTO Sauce (name, id) VALUES ('Mokka', 107);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (107, 'KOFFEIN');
INSERT INTO Sauce (name, id) VALUES ('Schokolade', 108);
INSERT INTO Sauce (name, id) VALUES ('Vanille', 109);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (109, 'LAKTOSE');
INSERT INTO Sauce (name, id) VALUES ('Waldfrucht', 110);
INSERT INTO Sauce (name, id) VALUES ('Zimtsauce', 111);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (111, 'LAKTOSE');
INSERT INTO Sauce (name, id) VALUES ('Baileys', 112);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (112, 'ALKOHOL');
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (112, 'LAKTOSE');
INSERT INTO Sauce (name, id) VALUES ('Eierlikoer', 113);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (113, 'LAKTOSE');
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (113, 'EI');
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (113, 'ALKOHOL');



--Zutaten:
INSERT INTO Zutat (name, premium, id) VALUES ('Cookie Dough', FALSE, 100);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (100, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (100, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Oreo', FALSE, 101);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (101, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Cookie', FALSE, 102);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (102, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (102, 'LAKTOSE');

--Hauskreation:
INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (101, 101, 'Cookie Lover Deluxe', 108, 100);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (100, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (100, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (100, 100);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (100, 101);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (100, 102);


--INSERT INTO users (username, name, password, role) VALUES ('admin', 'Administrator', '$2a$10$YfY/ecjvrYqeFQ9xnuFLAe2EUhQBQkMal58kBUJx8sRbKL1VrbcC.', 'admin');