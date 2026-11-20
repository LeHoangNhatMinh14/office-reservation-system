-- Create reservation table
CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    reservation_type VARCHAR(50) NOT NULL,
    table_id BIGINT NOT NULL,
    team_id BIGINT,
    user_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (table_id) REFERENCES room_table(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE
);
