DROP TABLE IF EXISTS card;
DROP TABLE IF EXISTS scenario_country;
DROP TABLE IF EXISTS country;
DROP TABLE IF EXISTS scenario;
DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS gamemode;

CREATE TABLE gamemode
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE player (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255),
                        type ENUM('HUMAN', 'AI'),
                        gamemode_id INT,
                        FOREIGN KEY (gamemode_id) REFERENCES gamemode(id) ON DELETE CASCADE
);

CREATE TABLE scenario (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE TABLE country (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         player_id INT,
                         FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE
);

CREATE TABLE scenario_country (
                                  scenario_id INT,
                                  country_id INT,
                                  PRIMARY KEY (scenario_id, country_id),
                                  FOREIGN KEY (scenario_id) REFERENCES scenario(id) ON DELETE CASCADE,
                                  FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE CASCADE
);

CREATE TABLE card (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      hp INT,
                      type ENUM('ATTACK', 'HEAL'),
                      power INT,
                      country_id INT,
                      FOREIGN KEY (country_id) REFERENCES country(id) ON DELETE CASCADE
);

INSERT INTO player (name, type, gamemode_id) VALUES
                                    ('Player 1', 'HUMAN', NULL),
                                    ('Player 2', 'HUMAN', NULL),
                                    ('AI Player 1', 'AI', NULL),
                                    ('AI Player 2', 'AI', NULL);

INSERT INTO scenario (name) VALUES
                                ('Germany vs USSR'),
                                ('Germany vs France'),
                                ('Italy vs France'),
                                ('Italy vs England'),
                                ('Germany vs England');

INSERT INTO country (name, player_id) VALUES
                                          ('Germany', NULL),
                                          ('USSR', NULL),
                                          ('France', NULL),
                                          ('England', NULL),
                                          ('Italy', NULL);

INSERT INTO card (name, hp, type, power, country_id) VALUES
                                                         -- Germany
                                                         ('Tiger I', 100, 'ATTACK', 20, 1),
                                                         ('Ju-87', 80, 'ATTACK', 20, 1),
                                                         ('Bf-109', 70, 'ATTACK', 10, 1),
                                                         ('Panther', 90, 'ATTACK', 18, 1),
                                                         ('King Tiger', 120, 'ATTACK', 25, 1),
                                                         ('Dora', 10, 'ATTACK', 50, 1),
                                                         ('U-boat', 20, 'ATTACK', 40, 1),
                                                         ('Bismark', 200, 'ATTACK', 30, 1),
                                                         ('Feldlazarette', 50, 'HEAL', 15, 1),
                                                         ('Ingenieurbüro', 60, 'HEAL', 10, 1),
                                                         ('Wartungsunternehmen', 40, 'HEAL', 17, 1),

                                                         -- USSR
                                                         ('T-34', 80, 'ATTACK', 15, 2),
                                                         ('KV-1', 90, 'ATTACK', 18, 2),
                                                         ('IS-2', 100, 'ATTACK', 20, 2),
                                                         ('ASU-57', 70, 'ATTACK', 25, 2),
                                                         ('Pe-8', 50, 'ATTACK', 40, 2),
                                                         ('Yak-3', 60, 'ATTACK', 10, 2),
                                                         ('IL-2 Sturmovik', 70, 'ATTACK', 18, 2),
                                                         ('Obsluzhivaniye Predpriyatiye', 50, 'HEAL', 15, 2),
                                                         ('American volunteer medics', 100, 'HEAL', 20, 2),

                                                         -- France
                                                         ('B1 bis', 200, 'ATTACK', 20, 3),
                                                         ('ARL-44', 200, 'ATTACK', 25, 3),
                                                         ('EBR', 80, 'ATTACK', 25, 3),
                                                         ('Infanterie', 70, 'ATTACK', 10, 3),
                                                         ('Cheval avec anti-tank', 80, 'ATTACK', 30, 3),
                                                         ('Chevalier', 70, 'ATTACK', 15, 3),
                                                         ('Bran Carrier', 200, 'HEAL', 10, 3),
                                                         ('Cheval', 60, 'HEAL', 5, 3),
                                                         ('American volunteer medics', 100, 'HEAL', 20, 3),
                                                         ('Amir with Baguette', 10, 'HEAL', 25, 3),

                                                         -- England
                                                         ('Churchill', 200, 'ATTACK', 20, 4),
                                                         ('Matilda', 200, 'ATTACK', 20, 4),
                                                         ('Cromwell', 100, 'ATTACK', 10, 4),
                                                         ('Spitfire', 80, 'ATTACK', 15, 4),
                                                         ('Lancaster', 100, 'ATTACK', 25, 4),
                                                         ('HMS-Hood', 200, 'ATTACK', 20, 4),
                                                         ('Sea Gladiator', 80, 'HEAL', 15, 4),
                                                         ('British Red Cross', 50, 'HEAL', 10, 4),
                                                         ('American volunteer medics', 100, 'HEAL', 20, 4),

                                                         -- Italy
                                                         ('L3', 40, 'ATTACK', 5, 5),
                                                         ('R3 T20 FA-HS', 70, 'ATTACK', 30, 5),
                                                         ('M13/40', 80, 'ATTACK', 15, 5),
                                                         ('AS-42', 40, 'ATTACK', 13, 5),
                                                         ('P40', 80, 'ATTACK', 20, 5),
                                                         ('Pasta ristorante', 50, 'HEAL', 20, 5),
                                                         ('Erwin Rommel Feldlazarette', 100, 'HEAL', 15, 5),
                                                         ('Bersaglieri', 60, 'ATTACK', 12, 5),
                                                         ('Medico Militare', 70, 'HEAL', 15, 5);

-- Germany vs USSR
INSERT INTO scenario_country (scenario_id, country_id) VALUES (1, 1), (1, 2);
-- Germany vs France
INSERT INTO scenario_country (scenario_id, country_id) VALUES (2, 1), (2, 3);
-- Italy vs France
INSERT INTO scenario_country (scenario_id, country_id) VALUES (3, 5), (3, 3);
-- Italy vs England
INSERT INTO scenario_country (scenario_id, country_id) VALUES (4, 5), (4, 4);
-- Germany vs England
INSERT INTO scenario_country (scenario_id, country_id) VALUES (5, 1), (5, 4);

INSERT INTO gamemode(name) VALUES
                                    ('PvP'),
                                    ('PvAI'),
                                    ('AIvAI');