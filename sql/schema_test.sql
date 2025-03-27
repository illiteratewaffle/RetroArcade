CREATE TABLE profiles
(
    id                   SERIAL PRIMARY KEY,
    username             VARCHAR(50) UNIQUE  NOT NULL,
    nickname             VARCHAR(50),
    email                VARCHAR(255) UNIQUE NOT NULL,
    hashed_password      VARCHAR(255)        NOT NULL,
    bio                  TEXT,
    profile_pic_path     TEXT, -- storing image file path as string
    current_game         VARCHAR(100),
    is_online            BOOLEAN          DEFAULT FALSE,
    win_loss_ratio       DOUBLE PRECISION DEFAULT 0.0,
    rating               INT              DEFAULT 0,
    rank                 VARCHAR(50)      DEFAULT 'unranked',
    wins                 INT              DEFAULT 0,
    games_played         JSONB,
    achievement_progress JSONB,
    friends              JSONB,
    friend_requests      JSONB,
    created_at           TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);
