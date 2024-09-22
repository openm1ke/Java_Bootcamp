INSERT INTO chat.User (login, password, list_of_created_chatroom, list_of_joined_chatroom) VALUES
    ('user1', 'password1', '{1}', '{1}'),
    ('user2', 'password2', '{2}', '{2}'),
    ('user3', 'password3', '{3}', '{3}'),
    ('user4', 'password4', '{4}', '{4}'),
    ('user5', 'password5', '{5}', '{5}');

INSERT INTO chat.Chatroom (name, owner, list_of_msg) VALUES
    ('room1', 1, '{1, 2}'),
    ('room2', 2, '{3, 4}'),
    ('room3', 3, '{5, 6}'),
    ('room4', 4, '{7, 8}'),
    ('room5', 5, '{9, 10}');

INSERT INTO chat.Message (author, room, text, time) VALUES
    (1, 1, 'message1', '2022-01-01 00:00:00'),
    (2, 1, 'message2', '2022-01-01 00:00:00'),
    (3, 2, 'message3', '2022-01-01 00:00:00'),
    (4, 2, 'message4', '2022-01-01 00:00:00'),
    (5, 3, 'message5', '2022-01-01 00:00:00'),
    (5, 3, 'message6', '2022-01-01 00:00:00'),
    (3, 4, 'message7', '2022-01-01 00:00:00'),
    (2, 4, 'message8', '2022-01-01 00:00:00'),
    (4, 5, 'message9', '2022-01-01 00:00:00'),
    (1, 5, 'message10', '2022-01-01 00:00:00');

