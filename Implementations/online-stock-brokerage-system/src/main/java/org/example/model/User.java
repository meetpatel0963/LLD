package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@AllArgsConstructor
@Getter
public class User {
    private final String id;
    private final String username;
    @Setter private String email;
    @Setter private String password;
    @Setter private double balance;
    private final Portfolio portfolio;
    private final List<Order> orders;


    public User(String id, String username, String password, String email, double balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.balance = balance;
        this.portfolio = new Portfolio(id);
        this.orders = new CopyOnWriteArrayList<>();
    }

    public void addOrder(@NonNull final Order order) {
        orders.add(order);
    }

}
