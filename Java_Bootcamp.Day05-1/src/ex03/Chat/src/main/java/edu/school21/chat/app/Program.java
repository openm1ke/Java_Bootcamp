package edu.school21.chat.app;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.ArrayList;
import java.util.Optional;

public class Program {

    public static void main(String[] args) {

        MessagesRepositoryJdbcImpl messagesRepository = new MessagesRepositoryJdbcImpl();
        messagesRepository.executeSqlFile("schema.sql");
        messagesRepository.executeSqlFile("data.sql");

        Optional<Message> message = messagesRepository.findById(10L);
        if(message.isPresent()) {
            Message message1 = message.get();
            message1.setText("Hello!");
            message1.setTime(null);
            messagesRepository.update(message1);
        } else {
            System.out.println("Message not found");
        }
    }
}
