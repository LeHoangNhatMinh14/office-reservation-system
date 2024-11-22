-- Create Leave Days table
CREATE TABLE `leave` (
                       id BIGINT AUTO_INCREMENT,
                       user_id BIGINT NOT NULL,
                       start_date DATE NOT NULL,
                       end_date DATE NOT NULL,
                       reason VARCHAR(255),
                       PRIMARY KEY (id),
                       FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);
