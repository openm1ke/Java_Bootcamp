package org.example.service.models;

public class UserIdGenerator {
    private static final UserIdGenerator INSTANCE = new UserIdGenerator();
    private long id = 1;

    private UserIdGenerator() {
    }

    public static UserIdGenerator getInstance() {
        return INSTANCE;
    }

    public long getNextId() {
        return id++;
    }
}
