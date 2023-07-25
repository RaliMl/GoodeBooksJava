CREATE TABLE IF NOT EXISTS book (
    id bigint NOT NULL AUTO_INCREMENT,
    title varchar(1000) NOT NULL,
    published_date DATE NOT NULL,
    page_count int NOT NULL,
    language varchar(100) NOT NULL,
    PRIMARY KEY (id)
)