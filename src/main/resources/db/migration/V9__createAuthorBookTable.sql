CREATE TABLE IF NOT EXISTS author_book (
    author_id bigint NOT NULL,
    book_id bigint NOT NULL,
    PRIMARY KEY (author_id, book_id),
    FOREIGN KEY (author_id) REFERENCES author(id),
    FOREIGN KEY (book_id) REFERENCES book(id)
)