CREATE TABLE IF NOT EXISTS blogs
(
    id VARCHAR(60) DEFAULT random() PRIMARY KEY,
    title VARCHAR NOT NULL,
    content VARCHAR NOT NULL,
    authorId VARCHAR NOT NULL
);