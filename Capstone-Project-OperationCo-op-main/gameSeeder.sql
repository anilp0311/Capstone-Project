
USE game_db;
INSERT INTO games(title, art_cover, description) VALUES ('HASSLE 1977','117753','Break into the Baltic City and join the resistance force Free Breath! Show and prove your skills in PvP shootouts, PvE missions, online drift and street races among other players-rebels and earn respect among them!');
INSERT INTO games(title, art_cover, description) VALUES ('Kentucky Route Zero: TV Edition','90105','Kentucky Route Zero is a magical realist adventure game about a secret highway in the caves beneath Kentucky, and the mysterious folks who travel it');
INSERT INTO games(title, art_cover, description) VALUES ('Dark Prospect','111163','Dark Prospect is a FPS/RTS Hybrid, a genre mix, featuring 4v1 battles between upto 4 FPS player and 1 RTS player.');
INSERT INTO games(title, art_cover, description) VALUES ('Turrican Anthology Vol. 1','undefined','Turrican Anthology Vol. 1');
INSERT INTO games(title, art_cover, description) VALUES ('Ben 10 - Power Trip','112709','Ben and his family are enjoying their European vacation... until evil magician Hex unleashes the power of four mysterious crystals! Only Ben 10 can break the curse – local split-screen co-op.');
INSERT INTO games(title, art_cover, description) VALUES ('Turrican Anthology Vol. 2','undefined','Turrican Anthology Vol. 2 ');
INSERT INTO games(title, art_cover, description) VALUES ('Moose Life','undefined','Moose Life is a trippy, psychedelic trance shooter. Created in the style of a 1980s arcade game.Spectacular shooty action and a top techno soundtrack will raise your mood. If you have VR, immerse yourself for greater satisfaction.');
INSERT INTO games(title, art_cover, description) VALUES ('12 MiniBattles','116446','This is a game for 2 players who will face each other in many battles locally and it is made up of 44 incredible mini-games. The gameplay is a button for each player (red and blue) and a general counter to know who is the true champion. ');
INSERT INTO games(title, art_cover, description) VALUES ('Shrine''s Legacy','116425','Shrine''s Legacy is a SNES-like action adventure RPG where you’ll wield elemental magic to explore the nooks and crannies of Ardemia and battle a rising evil. Play single player or with a friend in co-op!');
INSERT INTO games(title, art_cover, description) VALUES ('Shin Megami Tensei V','111634','For the 25th anniversary of the Shin Megami Tensei series, Shin Megami Tensei V was revealed (after being previously announced as SMT HD Project) for release on Nintendo Switch, running on Unreal Engine 4.');
INSERT INTO games(title, art_cover, description) VALUES ('LEGO Star Wars: The Skywalker Saga - Deluxe Edition','113600','The Deluxe Edition of LEGO Star Wars: The Skywalker Saga includes the base game and a Character Collection bundle featuring new playable characters from Rogue One: A Star Wars Story, Solo: ...');
INSERT INTO games(title, art_cover, description) VALUES ('NIGHT OF THE CONSUMERS','109826','It''s your first day on the job and the store is closing in a matter on minutes. The consumers, with their unrelenting thirst for customer service are still prowling the aisles and there are shelves still to be stocked up. Welcome to hell.');

-- LOAD DATA LOCAL INFILE '/Users/mr.cuvillier/games.csv'
--     INTO TABLE games
--     FIELDS TERMINATED BY '|' ENCLOSED BY '"'
--     LINES TERMINATED BY '\n'
--     IGNORE 1 LINES
--     (title, release_date, description, critics_rating, art_cover, igdb_api_id);


USE capstone_db;
INSERT INTO games(title, art_cover, description) VALUES ('HASSLE 1977','117753','Break into the Baltic City and join the resistance force Free Breath! Show and prove your skills in PvP shootouts, PvE missions, online drift and street races among other players-rebels and earn respect among them!');
INSERT INTO games(title, art_cover, description) VALUES ('Kentucky Route Zero: TV Edition','90105','Kentucky Route Zero is a magical realist adventure game about a secret highway in the caves beneath Kentucky, and the mysterious folks who travel it');
INSERT INTO games(title, art_cover, description) VALUES ('Dark Prospect','111163','Dark Prospect is a FPS/RTS Hybrid, a genre mix, featuring 4v1 battles between upto 4 FPS player and 1 RTS player.');
INSERT INTO games(title, art_cover, description) VALUES ('Turrican Anthology Vol. 1','undefined','Turrican Anthology Vol. 1');
INSERT INTO games(title, art_cover, description) VALUES ('Ben 10 - Power Trip','112709','Ben and his family are enjoying their European vacation... until evil magician Hex unleashes the power of four mysterious crystals! Only Ben 10 can break the curse – local split-screen co-op.');
INSERT INTO games(title, art_cover, description) VALUES ('Turrican Anthology Vol. 2','undefined','Turrican Anthology Vol. 2 ');
INSERT INTO games(title, art_cover, description) VALUES ('Moose Life','undefined','Moose Life is a trippy, psychedelic trance shooter. Created in the style of a 1980s arcade game.Spectacular shooty action and a top techno soundtrack will raise your mood. If you have VR, immerse yourself for greater satisfaction.');
INSERT INTO games(title, art_cover, description) VALUES ('12 MiniBattles','116446','This is a game for 2 players who will face each other in many battles locally and it is made up of 44 incredible mini-games. The gameplay is a button for each player (red and blue) and a general counter to know who is the true champion. ');
INSERT INTO games(title, art_cover, description) VALUES ('Shrine''s Legacy','116425','Shrine''s Legacy is a SNES-like action adventure RPG where you’ll wield elemental magic to explore the nooks and crannies of Ardemia and battle a rising evil. Play single player or with a friend in co-op!');
INSERT INTO games(title, art_cover, description) VALUES ('Shin Megami Tensei V','111634','For the 25th anniversary of the Shin Megami Tensei series, Shin Megami Tensei V was revealed (after being previously announced as SMT HD Project) for release on Nintendo Switch, running on Unreal Engine 4.');
INSERT INTO games(title, art_cover, description) VALUES ('LEGO Star Wars: The Skywalker Saga - Deluxe Edition','113600','The Deluxe Edition of LEGO Star Wars: The Skywalker Saga includes the base game and a Character Collection bundle featuring new playable characters from Rogue One: A Star Wars Story, Solo: ...');
INSERT INTO games(title, art_cover, description) VALUES ('NIGHT OF THE CONSUMERS','109826','It''s your first day on the job and the store is closing in a matter on minutes. The consumers, with their unrelenting thirst for customer service are still prowling the aisles and there are shelves still to be stocked up. Welcome to hell.');

-- LOAD DATA LOCAL INFILE '/Users/mr.cuvillier/games.csv'
--     INTO TABLE games
--     FIELDS TERMINATED BY '|' ENCLOSED BY '"'
--     LINES TERMINATED BY '\n'
--     IGNORE 1 LINES
--     (title, release_date, description, critics_rating, art_cover, igdb_api_id);

USE capstone_db;
LOAD DATA LOCAL INFILE '/Users/amaroterrazas/Documents/csv/games.csv'
    INTO TABLE games
    FIELDS TERMINATED BY '|' ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (title, release_date, description, critics_rating, art_cover, igdb_api_id);

