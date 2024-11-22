-- Create Leave Days table
CREATE TABLE `leave` (
                       id BIGINT AUTO_INCREMENT,
                       user_id INT NOT NULL,
                       start_date DATE NOT NULL,
                       end_date DATE NOT NULL,
                       reason VARCHAR(255),
                       FOREIGN KEY (userId) REFERENCES user(id)
);
