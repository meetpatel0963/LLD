package org.example.service;

import org.example.model.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {
    private static volatile UserService userService;
    private final Map<String, User> users;

    private UserService() {
        users = new ConcurrentHashMap<>();
    }

    public static UserService getInstance() {
        if (userService == null) {
            synchronized (UserService.class) {
                if (userService == null) {
                    userService = new UserService();
                }
            }
        }
        return userService;
    }

    public void register(String username, String password) {
        final User user = new User(getUserId(), username, password);
        users.put(user.getId(), user);
    }

    public User login(String username, String password) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private String getUserId() {
        return UUID.randomUUID().toString();
    }
}
