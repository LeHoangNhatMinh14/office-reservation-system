-- Create room table
CREATE TABLE room (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create room_table table
CREATE TABLE room_table (
    id BIGINT PRIMARY KEY,
    island_number INT NOT NULL,
    room_id BIGINT,
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

-- Create team table
CREATE TABLE team (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create user table
CREATE TABLE user (
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN NOT NULL DEFAULT FALSE
);

-- Create team_user table
CREATE TABLE team_user (
    team_id BIGINT,
    user_id BIGINT,
    PRIMARY KEY (team_id, user_id),
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- Create team_manager table
CREATE TABLE team_manager (
    team_id BIGINT,
    manager_id BIGINT,
    PRIMARY KEY (team_id, manager_id),
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (manager_id) REFERENCES user(id) ON DELETE CASCADE
);
