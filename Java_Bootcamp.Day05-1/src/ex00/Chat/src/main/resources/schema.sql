DROP SCHEMA IF EXISTS chat CASCADE;
CREATE SCHEMA IF NOT EXISTS chat;

CREATE TABLE IF NOT EXISTS chat.User
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    list_of_created_chatroom INTEGER [],
    list_of_joined_chatroom INTEGER []
);

CREATE TABLE IF NOT EXISTS chat.Chatroom
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    owner INTEGER REFERENCES chat.User(id),
    list_of_msg INTEGER []
);

CREATE TABLE IF NOT EXISTS chat.Message
(
    id     SERIAL PRIMARY KEY,
    author INTEGER REFERENCES chat.User (id),
    room   INTEGER REFERENCES chat.Chatroom (id),
    text   VARCHAR(1000) NOT NULL,
    time   timestamp     NOT NULL
);

