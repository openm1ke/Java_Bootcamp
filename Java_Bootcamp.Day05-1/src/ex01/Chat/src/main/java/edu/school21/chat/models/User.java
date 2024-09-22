package edu.school21.chat.models;

import java.util.*;

public class User {

    private Long id;
    private String login;
    private String password;
    private List<Chatroom> createdChatrooms;
    private List<Chatroom> joinedChatrooms;

    public User(Long userId, String login, String password, List<Chatroom> createdChatrooms, List<Chatroom> joinedChatrooms) {
        this.id = userId;
        this.login = login;
        this.password = password;
        this.createdChatrooms = createdChatrooms;
        this.joinedChatrooms = joinedChatrooms;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdChatrooms=" + (createdChatrooms != null ? createdChatrooms.toString() : "null") +
                ", joinedChatrooms=" + (joinedChatrooms != null ? joinedChatrooms.toString() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}