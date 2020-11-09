-- -----------------------------------------------------
-- Table password_reset
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS password_reset
(
    email      VARCHAR(100) NOT NULL UNIQUE,
    secret     VARCHAR(500) NOT NULL,
    when_requested DATETIME NOT NULL,
    status     VARCHAR(50)  NOT NULL DEFAULT 'PENDING',
    PRIMARY KEY (email)
)
    ENGINE = InnoDB;