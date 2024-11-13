-- Create reservation table
CREATE TABLE reservation (
    id BIGINT AUTO_INCREMENT,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    reservation_type VARCHAR(50) NOT NULL,
    room_id BIGINT NOT NULL,
    table_id BIGINT NOT NULL,
    seated_user_id BIGINT NOT NULL,
    reservation_user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE,
    FOREIGN KEY (table_id) REFERENCES room_table(id) ON DELETE CASCADE,
    FOREIGN KEY (seated_user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (reservation_user_id) REFERENCES user(id) ON DELETE CASCADE
);
