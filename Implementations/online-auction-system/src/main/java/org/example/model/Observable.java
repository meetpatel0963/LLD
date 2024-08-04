package org.example.model;

public interface Observable {
    void registerObserver(User user);
    void removeObserver(User user);
    void notifyObservers(String message);
}
