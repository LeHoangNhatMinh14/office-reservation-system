-- Create Leave Days table
CREATE TABLE leave_day (
    id BIGINT AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);
