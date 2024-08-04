package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class User implements Observer {
    private final String id;
    private final String username;
    private final String password;

    @Override
    public void update(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS"));
        System.out.println(timestamp + ": Notification for " + username + ": " + message);
    }
}
