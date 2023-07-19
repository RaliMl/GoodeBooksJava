CREATE TABLE IF NOT EXISTS account (
    id bigint NOT NULL AUTO_INCREMENT,
    email varchar(100) NOT NULL,
    full_name varchar(100) NOT NULL,
    password varchar(20) NOT NULL,
    PRIMARY KEY (id)
)