CREATE TABLE IF NOT EXISTS author
(
    id          SERIAL,
    name        VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book
(
    id          SERIAL,
    name        VARCHAR(255) NOT NULL,
    edition     INT,
    year        INT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tag
(
    id          SERIAL,
    name        VARCHAR(32) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_author
(
    book_id     INT,
    author_id   INT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE TABLE IF NOT EXISTS book_tag
(
    book_id     INT,
    tag_id      INT,
    PRIMARY KEY (book_id, tag_id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (tag_id) REFERENCES tag(id)
);
