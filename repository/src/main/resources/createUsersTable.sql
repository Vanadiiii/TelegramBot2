CREATE TABLE USERS
(
    chat_id     INTEGER PRIMARY KEY,
    name        VARCHAR(50) NULL,
    birthday    TIMESTAMP WITHOUT TIME ZONE NULL,
    phone       VARCHAR NULL,
    first_name  VARCHAR(60) NULL,
    second_name VARCHAR(60) NULL
)