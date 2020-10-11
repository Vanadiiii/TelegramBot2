CREATE TABLE USERS2
(
    id      SERIAL PRIMARY KEY,
    chat_id INTEGER NOT NULL,
    name    VARCHAR(40),
    day     INTEGER NULL,
    month   INTEGER NULL,
    CONSTRAINT day_check CHECK ( day BETWEEN 1 AND 31),
    CONSTRAINT month_check CHECK ( month BETWEEN 1 AND 12)
)