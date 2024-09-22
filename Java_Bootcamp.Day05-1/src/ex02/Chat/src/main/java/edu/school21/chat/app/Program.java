package edu.school21.chat.app;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        MessagesRepositoryJdbcImpl messagesRepository = new MessagesRepositoryJdbcImpl();
        messagesRepository.executeSqlFile("schema.sql");
        messagesRepository.executeSqlFile("data.sql");

        try {
            User user5 = new User(5L, "user5", "password5", new ArrayList<>(), new ArrayList<>());

            Chatroom room5 = new Chatroom(1L, "room1", user5, new ArrayList<>());

            Message message1 = new Message(null, "message1", user5, room5, new java.sql.Timestamp(System.currentTimeMillis()));

            messagesRepository.save(message1);
            System.out.println("Message saved with ID: " + message1.getId());

        } catch (NotSavedSubEntityException e) {
            e.printStackTrace();
        }
    }
}
