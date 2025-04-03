CREATE TABLE players (
                         id SERIAL PRIMARY KEY,
                         username VARCHAR(50) UNIQUE NOT NULL,
                         password_hash TEXT NOT NULL
);

CREATE TABLE match_history (
                               id SERIAL PRIMARY KEY,
                               player1_id INT REFERENCES players(id),
                               player2_id INT REFERENCES players(id),
                               winner_id INT REFERENCES players(id),
                               game_data JSONB,
                               timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE leaderboard (
                             player_id INT REFERENCES players(id),
                             wins INT DEFAULT 0,
                             losses INT DEFAULT 0,
                             PRIMARY KEY (player_id)
);
