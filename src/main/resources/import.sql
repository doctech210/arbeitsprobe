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

--Premium-Zutaten:
INSERT INTO Zutat (name, premium, id) VALUES ('Brownie (hausgemacht)', TRUE, 100);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (100, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Browniedough (hausgemacht)', TRUE, 101);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (101, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Cashews', TRUE, 102);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (102, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Daimtorte', TRUE, 103);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (103, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (103, 'EI');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (103, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Gebrannte Mandeln', TRUE, 104);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (104, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Honig-Salz-Nuesse', TRUE, 105);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (105, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (105, 'HONIG');

INSERT INTO Zutat (name, premium, id) VALUES ('Karamellisierte Wallnuesse', TRUE, 106);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (106, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Chocofresh', TRUE, 107);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (107, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Maxi King', TRUE, 108);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (108, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Kokos-Mandel-Creme', TRUE, 109);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (109, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Marzipan', TRUE, 110);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (110, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Mozartkugeln', TRUE, 111);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (111, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (111, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Pistaziencreme', TRUE, 112);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (112, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (112, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Weisse Nougat Creme', TRUE, 113);

INSERT INTO Zutat (name, premium, id) VALUES ('Weisse Schokoladencreme', TRUE, 114);

--Zutaten (Gebaeck):
INSERT INTO Zutat (name, premium, id) VALUES ('Apfelkuchen', FALSE, 115);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (115, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (115, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Brownie', FALSE, 116);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (116, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (116, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Cookie Dough', FALSE, 117);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (117, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (117, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Cookie Dough', FALSE, 118);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (118, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Kaesekuchen', FALSE, 119);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (119, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (119, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (119, 'EI');

--Zutaten(Fruechte nach Saison):

INSERT INTO Zutat (name, premium, id) VALUES ('Banane', FALSE, 120);

INSERT INTO Zutat (name, premium, id) VALUES ('Blaubeere', FALSE, 121);

INSERT INTO Zutat (name, premium, id) VALUES ('Erdbeere', FALSE, 122);

INSERT INTO Zutat (name, premium, id) VALUES ('Himbeere', FALSE, 123);

INSERT INTO Zutat (name, premium, id) VALUES ('Mango', FALSE, 124);

INSERT INTO Zutat (name, premium, id) VALUES ('Preiselbeeren', FALSE, 125);

--Zutaten(Suesses):

INSERT INTO Zutat (name, premium, id) VALUES ('Ahojbrause', FALSE, 126);

INSERT INTO Zutat (name, premium, id) VALUES ('Brauseufos', FALSE, 127);

INSERT INTO Zutat (name, premium, id) VALUES ('Cini Minis', FALSE, 128);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (128, 'Gluten');

INSERT INTO Zutat (name, premium, id) VALUES ('Cookies', FALSE, 129);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (129, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (129, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Cookies', FALSE, 130);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (130, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Daim', FALSE, 131);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (131, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Duplo', FALSE, 132);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (132, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (132, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Giotto', FALSE, 133);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (133, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (133, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (133, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Gummibaeren', FALSE, 134);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (134, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Hanuta', FALSE, 135);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (135, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (135, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (135, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Haselnusskrokant', FALSE, 136);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (136, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Karamellpopcorn', FALSE, 137);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (137, 'HONIG');

INSERT INTO Zutat (name, premium, id) VALUES ('Karamellwaffeln', FALSE, 138);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (138, 'HONIG');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Bueno', FALSE, 139);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (139, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Country', FALSE, 140);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (140, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (140, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Happy Hippo', FALSE, 141);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (141, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Pingui', FALSE, 142);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (142, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Schokolade', FALSE, 143);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (143, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Kit Kat', FALSE, 144);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (144, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (140, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Knisterbrause', FALSE, 145);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (145, 'HONIG');

INSERT INTO Zutat (name, premium, id) VALUES ('Knuspermuesli', FALSE, 146);

INSERT INTO Zutat (name, premium, id) VALUES ('Koalas', FALSE, 147);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (147, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('M&Ms', FALSE, 148);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (148, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (148, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Marshmallows', FALSE, 149);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (149, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Milchschnitte', FALSE, 150);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (150, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Minzschokolade', FALSE, 151);

INSERT INTO Zutat (name, premium, id) VALUES ('Mr. Tom', FALSE, 152);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (152, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('M&Ms', FALSE, 153);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (153, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Neapolitanerwaffeln', FALSE, 154);

INSERT INTO Zutat (name, premium, id) VALUES ('Oreo', FALSE, 155);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (155, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Peanutbutter Cups', FALSE, 156);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (156, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (156, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Pico Balla', FALSE, 157);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (157, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Puffreis', FALSE, 158);

INSERT INTO Zutat (name, premium, id) VALUES ('Raffaelo', FALSE, 159);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (159, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (159, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Rumkugeln', FALSE, 160);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (160, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (110, 'ALKOHOL');

INSERT INTO Zutat (name, premium, id) VALUES ('Salzbrezeln', FALSE, 161);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (161, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Salzige Erdnuesse', FALSE, 162);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (162, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Schaumkuesse', FALSE, 163);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (163, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (163, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Schokocrossies', FALSE, 164);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (164, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (164, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Schokoflakes', FALSE, 165);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (165, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (165, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Schokolinsen', FALSE, 166);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (166, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Snickers', FALSE, 167);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (167, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (167, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Wackelpudding', FALSE, 168);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (168, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Weisse Crisp', FALSE, 169);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (169, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (169, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Yogurette', FALSE, 170);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (170, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (170, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Zartbitterschokolade', FALSE, 171);

--Zutaten(Cremiges):

INSERT INTO Zutat (name, premium, id) VALUES ('Erdnussbutter', FALSE, 172);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (172, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Honig', FALSE, 173);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (173, 'HONIG');

INSERT INTO Zutat (name, premium, id) VALUES ('Karamellcreme', FALSE, 174);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (174, 'HONIG');

INSERT INTO Zutat (name, premium, id) VALUES ('Lemon Curd', FALSE, 175);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (175, 'EI');

INSERT INTO Zutat (name, premium, id) VALUES ('Marshemellowcreme', FALSE, 176);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (176, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Nutella', FALSE, 177);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (177, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (177, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Overmaltine', FALSE, 178);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (178, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (178, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Sahne', FALSE, 179);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (179, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Schokosahne', FALSE, 180);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (180, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Vegane Sahne', FALSE, 181);

INSERT INTO Zutat (name, premium, id) VALUES ('Matcha', FALSE, 182);

INSERT INTO Zutat (name, premium, id) VALUES ('Minze', FALSE, 183);

INSERT INTO Zutat (name, premium, id) VALUES ('Kokosstreusel', FALSE, 184);

--Hauskreation:
INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (101, 101, 'Cookie Lover Deluxe', 108, 100);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (100, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (100, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (100, 117);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (100, 129);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (100, 155);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (100, 100, 'Schoko Schock', 108, 101);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (101, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (101, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (101, 116);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (101, 165);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (101, 180);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (108, 108, 'Alter Schwede', 109, 102);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (102, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (102, 'GLUTEN');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (102, 'EI');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (102, 103);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (102, 125);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (102, 102, 'Hacuja Matata', 101, 103);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (103, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (103, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (103, 124);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (103, 159);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (103, 184);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (101, 101, 'Chefs Liebling', 108, 104);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (104, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (104, 'GLUTEN');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (104, 'NUSS');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (104, 116);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (104, 136);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (104, 172);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (102, 100, 'Banana Joe', 108, 105);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (105, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (105, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (105, 116);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (105, 120);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (105, 165);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (105, 179);



-- INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (102, 103, 'Glatteis', 101, 102);
-- INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (102, 'LAKTOSE');
-- INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (102, 'GLUTEN');
-- INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (102, 103);
-- INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (102, 108);
-- INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (102, 112);




-- INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (101, 101, 'Easy Cheesy', 101, 101);
-- INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (101, 'LAKTOSE');
-- INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (101, 'EI');
-- INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (101, 'GLUTEN');
-- INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (101, 103);
-- INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (101, 108);
-- INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (101, 109);



INSERT INTO Nutzer (name, passwort, role, id) VALUES ('admin', '$2a$10$YfY/ecjvrYqeFQ9xnuFLAe2EUhQBQkMal58kBUJx8sRbKL1VrbcC.', 'Admin', 0);
INSERT INTO Nutzer (name, passwort, role, id) VALUES ('user', '$2a$10$OfQWuRPsizS5HZJ7KSSMPuujQtu.ttm5X3PiWTuKoJ59At9TY8koe', 'Kunde', 100);
--INSERT INTO users (username, name, password, role) VALUES ('admin', 'Administrator', '$2a$10$YfY/ecjvrYqeFQ9xnuFLAe2EUhQBQkMal58kBUJx8sRbKL1VrbcC.', 'admin');