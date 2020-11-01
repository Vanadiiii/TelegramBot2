CREATE TABLE USERS2
(
    id          SERIAL PRIMARY KEY,
    chat_id     INTEGER NOT NULL,
    name        VARCHAR(50) NULL,
    day         INTEGER NULL,
    month       INTEGER NULL,
    phone       VARCHAR NULL,
    first_name  VARCHAR NULL,
    second_name VARCHAR NULL,
    CONSTRAINT day_check CHECK ( day BETWEEN 1 AND 31),
    CONSTRAINT month_check CHECK ( month BETWEEN 1 AND 12)
)