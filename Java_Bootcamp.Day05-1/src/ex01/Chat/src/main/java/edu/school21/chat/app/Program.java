package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.Optional;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        MessagesRepositoryJdbcImpl messagesRepository = new MessagesRepositoryJdbcImpl();
        messagesRepository.executeSqlFile("schema.sql");
        messagesRepository.executeSqlFile("data.sql");

        Scanner scanner = new Scanner(System.in);
        long id = 0;

        while(true) {
            System.out.println("Enter a message ID");
            try {
                id = scanner.nextLong();
                scanner.nextLine();
                if(id == -1) {
                    break;
                }

                Optional<Message> message = messagesRepository.findById(id);
                if(message.isPresent()) {
                    System.out.println(message.get());
                } else {
                    System.out.println("Not found");
                }
            } catch (Exception e) {
                System.out.println("Incorrect ID");
            }
        }
        scanner.close();
    }
}
