package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class Item implements Observable {
    private final String id;
    private final String name;
    private final String description;
    private final double startingPrice;
    @Setter private LocalDateTime endTime;
    private final List<Bid> bids;
    private final List<Observer> observers;
    private boolean isOpen;

    public Item(String id, String name, String description, double startingPrice, int durationInMinutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.endTime = LocalDateTime.now().plusMinutes(durationInMinutes);
        this.bids = new CopyOnWriteArrayList<>();
        this.isOpen = true;
        this.observers = new CopyOnWriteArrayList<>();
    }

    public void placeBid(Bid bid) {
        if (isOpen && LocalDateTime.now().isBefore(endTime)) {
            bids.add(bid);
            notifyObservers(bid.getBidder().getUsername() + " placed a bid of $" + bid.getAmount() + " on item " + name);
        } else {
            throw new IllegalStateException("Auction is closed or ended.");
        }
    }

    public void closeAuction() {
        isOpen = false;
        notifyObservers("Auction for item " + name + " has ended.");
    }

    public Bid getHighestBid() {
        return bids.stream().max(Comparator.comparingDouble(Bid::getAmount)).get();
    }

    @Override
    public void registerObserver(User user) {
        observers.add(user);
    }

    @Override
    public void removeObserver(User user) {
        observers.remove(user);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
