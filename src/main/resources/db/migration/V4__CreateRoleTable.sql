CREATE TABLE IF NOT EXISTS role (
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    account bigint NULL,
    PRIMARY KEY (id)
)
