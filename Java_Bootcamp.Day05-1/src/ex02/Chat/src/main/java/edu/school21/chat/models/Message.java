package edu.school21.chat.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {

    private Long id;
    private String text;
    private User author;
    private Chatroom chatroom;
    private Timestamp time;

    public Message(Long id, String text, User author, Chatroom chatroom, Timestamp time) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.chatroom = chatroom;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message : {\n" +
                "   id=" + id + ",\n" +
                "   author=" + author + ",\n" +
                "   room=" + chatroom + ",\n" +
                "   text=\"" + text + "\",\n" +
                "   time=" + time + "\n" +
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
