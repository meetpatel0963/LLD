package org.example.service;

import lombok.NonNull;
import org.example.exception.InvalidUserException;
import org.example.model.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {
    private final Map<String, User> users;

    public UserService() {
        users = new ConcurrentHashMap<>();
    }

    public void registerUser(@NonNull final String username,
                             @NonNull final String password,
                             @NonNull final String email,
                             final double balance) {
        User user = new User(generateUserId(), username, password, email, balance);
        users.put(user.getId(), user);
    }
    
    public User loginUser(String username, String password) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username)
                        && user.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new InvalidUserException("Invalid Username or Password."));
    }

    private String generateUserId() {
        return UUID.randomUUID().toString();
    }

    public User getUser(final String userId) {
        return users.get(userId);
    }
}
