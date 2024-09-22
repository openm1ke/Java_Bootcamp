package edu.school21.chat.models;

import java.util.*;

public class Message {

    private Long id;
    private String text;
    private User author;
    private Chatroom chatroom;
    private Date time;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", chatroom=" + chatroom +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
