CREATE TABLE profiles (
                          id BIGSERIAL PRIMARY KEY,
                          username VARCHAR(50) UNIQUE NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          hashed_password VARCHAR(255) NOT NULL,
                          nickname VARCHAR(50),
                          bio TEXT,
                          is_online BOOLEAN DEFAULT FALSE,
                          current_game VARCHAR(100),
                          win_loss_ratio DOUBLE PRECISION DEFAULT 0.0,
                          games_played JSON,
                          profile_pic BYTEA,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);