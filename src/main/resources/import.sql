--Eissorten:
INSERT INTO Eis (id, name) VALUES (1, 'Schokolade');
INSERT INTO Eis_allergene (Eis_id, allergene) VALUES (1, 'LAKTOSE');
INSERT INTO Eis (id, name) VALUES (2, 'Vanille');
INSERT INTO Eis_allergene (Eis_id, allergene) VALUES (2, 'LAKTOSE');
INSERT INTO Eis (id, name) VALUES (3, 'Joghurt');
INSERT INTO Eis_allergene (Eis_id, allergene) VALUES (3, 'LAKTOSE');
INSERT INTO Eis (id, name) VALUES (4, 'Erdbeere');
INSERT INTO Eis (id, name) VALUES (5, 'Mango');
INSERT INTO Eis (id, name) VALUES (6, 'Schokolade Vegan');
INSERT INTO Eis (id, name) VALUES (7, 'Vanille Vegan');
INSERT INTO Eis (id, name) VALUES (8, 'Nuss-Nougat');
INSERT INTO Eis_allergene (Eis_id, allergene) VALUES (8, 'NUSS');
INSERT INTO Eis (id, name) VALUES (9, 'Salted Caramell');
INSERT INTO Eis_allergene (Eis_id, allergene) VALUES (9, 'LAKTOSE');

--Saucen:
INSERT INTO Sauce (name, id) VALUES ('Ahornsirup', 1);
INSERT INTO Sauce (name, id) VALUES ('Erdbeere hausgemacht', 2);
INSERT INTO Sauce (name, id) VALUES ('Haselnuss', 3);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (3, 'NUSS');
INSERT INTO Sauce (name, id) VALUES ('Karamell', 4);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (4, 'LAKTOSE');
INSERT INTO Sauce (name, id) VALUES ('Karamell', 5);
INSERT INTO Sauce (name, id) VALUES ('Lakritzsauce', 6);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (6, 'LAKTOSE');
INSERT INTO Sauce (name, id) VALUES ('Mangopueree', 7);
INSERT INTO Sauce (name, id) VALUES ('Mokka', 8);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (8, 'KOFFEIN');
INSERT INTO Sauce (name, id) VALUES ('Schokolade', 9);
INSERT INTO Sauce (name, id) VALUES ('Vanille', 10);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (10, 'LAKTOSE');
INSERT INTO Sauce (name, id) VALUES ('Waldfrucht', 11);
INSERT INTO Sauce (name, id) VALUES ('Zimtsauce', 12);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (12, 'LAKTOSE');
INSERT INTO Sauce (name, id) VALUES ('Baileys', 13);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (13, 'ALKOHOL');
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (13, 'LAKTOSE');
INSERT INTO Sauce (name, id) VALUES ('Eierlikoer', 14);
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (14, 'LAKTOSE');
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (14, 'EI');
INSERT INTO Sauce_allergene (Sauce_id, allergene) VALUES (14, 'ALKOHOL');

--Premium-Zutaten:
INSERT INTO Zutat (name, premium, id) VALUES ('Brownie (hausgemacht)', TRUE, 1);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (1, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Browniedough (hausgemacht)', TRUE, 2);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (2, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Cashews', TRUE, 3);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (3, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Daimtorte', TRUE, 4);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (4, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (4, 'EI');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (4, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Gebrannte Mandeln', TRUE, 5);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (5, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Honig-Salz-Nuesse', TRUE, 6);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (6, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (6, 'HONIG');

INSERT INTO Zutat (name, premium, id) VALUES ('Karamellisierte Wallnuesse', TRUE, 7);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (7, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Chocofresh', TRUE, 8);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (8, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Maxi King', TRUE, 9);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (9, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Kokos-Mandel-Creme', TRUE, 10);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (10, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Marzipan', TRUE, 11);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (11, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Mozartkugeln', TRUE, 12);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (12, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (12, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Pistaziencreme', TRUE, 13);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (13, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (13, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Weisse Nougat Crisp', TRUE, 14);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (14, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Weisse Schokoladencreme', TRUE, 15);

--Zutaten (Gebaeck):
INSERT INTO Zutat (name, premium, id) VALUES ('Apfelkuchen', FALSE, 16);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (16, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (16, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Brownie', FALSE, 17);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (17, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (17, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Cookie Dough', FALSE, 18);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (18, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (18, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Cookie Dough', FALSE, 19);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (19, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Kaesekuchen', FALSE, 20);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (20, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (20, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (20, 'EI');

--Zutaten(Fruechte nach Saison):

INSERT INTO Zutat (name, premium, id) VALUES ('Banane', FALSE, 21);

INSERT INTO Zutat (name, premium, id) VALUES ('Blaubeere', FALSE, 22);

INSERT INTO Zutat (name, premium, id) VALUES ('Erdbeere', FALSE, 23);

INSERT INTO Zutat (name, premium, id) VALUES ('Himbeere', FALSE, 24);

INSERT INTO Zutat (name, premium, id) VALUES ('Mango', FALSE, 25);

INSERT INTO Zutat (name, premium, id) VALUES ('Preiselbeeren', FALSE, 26);

--Zutaten(Suesses):

INSERT INTO Zutat (name, premium, id) VALUES ('Ahojbrause', FALSE, 27);

INSERT INTO Zutat (name, premium, id) VALUES ('Brauseufos', FALSE, 28);

INSERT INTO Zutat (name, premium, id) VALUES ('Cini Minis', FALSE, 29);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (29, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Cookies', FALSE, 30);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (30, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (30, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Cookies', FALSE, 31);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (31, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Daim', FALSE, 32);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (32, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Duplo', FALSE, 33);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (33, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (33, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Giotto', FALSE, 34);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (34, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (34, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (34, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Gummibaeren', FALSE, 35);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (35, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Hanuta', FALSE, 36);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (36, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (36, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (36, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Haselnusskrokant', FALSE, 37);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (37, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Karamellpopcorn', FALSE, 38);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (38, 'HONIG');

INSERT INTO Zutat (name, premium, id) VALUES ('Karamellwaffeln', FALSE, 39);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (39, 'HONIG');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Bueno', FALSE, 40);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (40, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Country', FALSE, 41);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (41, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (41, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Happy Hippo', FALSE, 42);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (42, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Pingui', FALSE, 43);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (43, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Kinder Schokolade', FALSE, 44);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (44, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Kit Kat', FALSE, 45);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (45, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (45, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Knisterbrause', FALSE, 46);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (46, 'HONIG');

INSERT INTO Zutat (name, premium, id) VALUES ('Knuspermuesli', FALSE, 47);

INSERT INTO Zutat (name, premium, id) VALUES ('Koalas', FALSE, 48);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (48, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('M&Ms', FALSE, 49);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (49, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (49, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Marshmallows', FALSE, 50);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (50, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Milchschnitte', FALSE, 51);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (51, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Minzschokolade', FALSE, 52);

INSERT INTO Zutat (name, premium, id) VALUES ('Mr. Tom', FALSE, 53);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (53, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('M&Ms', FALSE, 54);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (54, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Neapolitanerwaffeln', FALSE, 55);

INSERT INTO Zutat (name, premium, id) VALUES ('Oreo', FALSE, 56);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (56, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Peanutbutter Cups', FALSE, 57);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (57, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (57, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Pico Balla', FALSE, 58);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (58, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Puffreis', FALSE, 59);

INSERT INTO Zutat (name, premium, id) VALUES ('Raffaelo', FALSE, 60);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (60, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (60, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Rumkugeln', FALSE, 61);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (61, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (61, 'ALKOHOL');

INSERT INTO Zutat (name, premium, id) VALUES ('Salzbrezeln', FALSE, 62);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (62, 'GLUTEN');

INSERT INTO Zutat (name, premium, id) VALUES ('Salzige Erdnuesse', FALSE, 63);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (63, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Schaumkuesse', FALSE, 64);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (64, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (64, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Schokocrossies', FALSE, 65);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (65, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (65, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Schokoflakes', FALSE, 66);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (66, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (66, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Schokolinsen', FALSE, 67);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (67, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Snickers', FALSE, 68);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (68, 'NUSS');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (68, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Wackelpudding', FALSE, 69);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (69, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Weisse Crisp', FALSE, 70);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (70, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (70, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Yogurette', FALSE, 71);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (71, 'GLUTEN');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (71, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Zartbitterschokolade', FALSE, 72);

--Zutaten(Cremiges):

INSERT INTO Zutat (name, premium, id) VALUES ('Erdnussbutter', FALSE, 73);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (73, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Honig', FALSE, 74);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (74, 'HONIG');

INSERT INTO Zutat (name, premium, id) VALUES ('Karamellcreme', FALSE, 75);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (75, 'HONIG');

INSERT INTO Zutat (name, premium, id) VALUES ('Lemon Curd', FALSE, 76);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (76, 'EI');

INSERT INTO Zutat (name, premium, id) VALUES ('Marshemellowcreme', FALSE, 77);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (77, 'GELANTINE');

INSERT INTO Zutat (name, premium, id) VALUES ('Nutella', FALSE, 78);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (78, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (78, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Overmaltine', FALSE, 79);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (79, 'LAKTOSE');
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (79, 'NUSS');

INSERT INTO Zutat (name, premium, id) VALUES ('Sahne', FALSE, 80);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (80, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Schokosahne', FALSE, 81);
INSERT INTO Zutat_allergene (Zutat_id, allergene) VALUES (81, 'LAKTOSE');

INSERT INTO Zutat (name, premium, id) VALUES ('Vegane Sahne', FALSE, 82);

INSERT INTO Zutat (name, premium, id) VALUES ('Matcha', FALSE, 83);

INSERT INTO Zutat (name, premium, id) VALUES ('Minze', FALSE, 84);

INSERT INTO Zutat (name, premium, id) VALUES ('Kokosstreusel', FALSE, 85);

--Hauskreation:
INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (2, 2, 'Cookie Lover Deluxe', 9, 1);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (1, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (1, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (1, 18);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (1, 32);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (1, 56);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (1, 1, 'Schoko Schock', 9, 2);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (2, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (2, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (2, 17);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (2, 66);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (2, 81);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (9, 9, 'Alter Schwede', 10, 3);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (3, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (3, 'GLUTEN');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (3, 'EI');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (3, 4);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (3, 26);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (3, 3, 'Hacuja Matata', 7, 4);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (4, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (4, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (4, 25);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (4, 60);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (4, 85);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (2, 2, 'Chefs Liebling', 9, 5);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (5, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (5, 'GLUTEN');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (5, 'NUSS');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (5, 17);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (5, 37);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (5, 73);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (2, 1, 'Banana Joe', 9, 6);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (6, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (6, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (6, 17);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (6, 21);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (6, 66);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (6, 80);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (3, 4, 'Glatteis', 2, 7);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (7, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (7, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (7, 23);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (7, 70);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (7, 71);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (3, 4, 'Frische Brise', 2, 8);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (8, 'LAKTOSE');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (8, 70);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (8, 23);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (8, 84);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (2, 2, 'Kinder Wahnsinn', 9, 9);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (9, 'LAKTOSE');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (9, 42);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (9, 8);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (9, 40);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (2, 2, 'Easy Cheesy', 2, 10);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (10, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (10, 'GLUTEN');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (10, 'EI');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (10, 20);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (10, 23);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (10, 70);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (7, 4, 'Vegan Strawberry', 2, 11);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (11, 'NUSS');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (11, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (11, 23);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (11, 14);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (11, 56);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (1, 1, 'Berry Brownie', 10, 12);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (12, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (12, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (12, 2);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (12, 23);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (2, 2, 'Mapfelbecher', 2, 13);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (13, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (13, 'GLUTEN');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (13, 'NUSS');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (13, 23);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (13, 70);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (13, 71);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (7, 7, 'Schmackofatz', NULL, 14);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (14, 'NUSS');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (14, 'GLUTEN');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (14, 1);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (14, 62);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (14, 73);

INSERT INTO Hauskreation (eissorte_id, eissorte2_id, name, sauce_id, id) VALUES (2, 2, 'Fliegender Hollaender', 4, 15);
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (15, 'LAKTOSE');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (15, 'HONIG');
INSERT INTO Hauskreation_allergene (Hauskreation_id, allergene) VALUES (15, 'NUSS');
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (15, 16);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (15, 39);
INSERT INTO Hauskreation_Zutat (Hauskreation_id, zutaten_id) VALUES (15, 73);

INSERT INTO Nutzer (name, passwort, role, id) VALUES ('admin', '$2a$10$YfY/ecjvrYqeFQ9xnuFLAe2EUhQBQkMal58kBUJx8sRbKL1VrbcC.', 'Admin', 0);
INSERT INTO Nutzer (name, passwort, role, id) VALUES ('user', '$2a$10$OfQWuRPsizS5HZJ7KSSMPuujQtu.ttm5X3PiWTuKoJ59At9TY8koe', 'Kunde', 1);