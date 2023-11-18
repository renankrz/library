INSERT INTO
    author(name)
VALUES
    ('marco dimas gubitoso'),
    ('richard stallman');

INSERT INTO
    book(name, year, edition)
VALUES
    ('hacking in C', 2000, 1),
    ('hacking in java', 2001, 2),
    ('hacking in python', 2002, 3);

INSERT INTO
    tag(name)
VALUES
    ('hacking'),
    ('C'),
    ('java'),
    ('python');

INSERT INTO
    book_author(book_id, author_id)
VALUES
    (
        (SELECT id FROM book WHERE name = 'hacking in C'),
        (SELECT id FROM author WHERE name = 'marco dimas gubitoso')
    ),
    (
        (SELECT id FROM book WHERE name = 'hacking in java'),
        (SELECT id FROM author WHERE name = 'richard stallman')
    ),
    (
        (SELECT id FROM book WHERE name = 'hacking in python'),
        (SELECT id FROM author WHERE name = 'marco dimas gubitoso')
    ),
    (
        (SELECT id FROM book WHERE name = 'hacking in python'),
        (SELECT id FROM author WHERE name = 'richard stallman')
    );

INSERT INTO
    book_tag(book_id, tag_id)
VALUES
    (
        (SELECT id FROM book WHERE name = 'hacking in C'),
        (SELECT id FROM tag WHERE name = 'hacking')
    ),
    (
        (SELECT id FROM book WHERE name = 'hacking in C'),
        (SELECT id FROM tag WHERE name = 'C')
    ),
    (
        (SELECT id FROM book WHERE name = 'hacking in java'),
        (SELECT id FROM tag WHERE name = 'hacking')
    ),
    (
        (SELECT id FROM book WHERE name = 'hacking in java'),
        (SELECT id FROM tag WHERE name = 'java')
    ),
    (
        (SELECT id FROM book WHERE name = 'hacking in python'),
        (SELECT id FROM tag WHERE name = 'hacking')
    ),
    (
        (SELECT id FROM book WHERE name = 'hacking in python'),
        (SELECT id FROM tag WHERE name = 'python')
    );
