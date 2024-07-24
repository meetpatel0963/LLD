package org.example.service;

import org.example.model.Bid;
import org.example.model.Item;
import org.example.model.User;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ItemService {
    private static volatile ItemService itemService;
    private final List<Item> items;
    private final PaymentService paymentService;

    private ItemService(PaymentService paymentService) {
        items = new CopyOnWriteArrayList<>();
        this.paymentService = paymentService;
    }

    public static ItemService getInstance(PaymentService paymentService) {
        if (itemService == null) {
            synchronized (ItemService.class) {
                if (itemService == null) {
                    itemService = new ItemService(paymentService);
                }
            }
        }
        return itemService;
    }

    public Item createItem(String name, String description, double startingPrice, int durationInMinutes) {
        Item item = new Item(getItemId(), name, description, startingPrice, durationInMinutes);
        items.add(item);
        return item;
    }

    public void placeBid(Item item, User bidder, double amount) {
        item.placeBid(new Bid(getBidId(), bidder, amount));
    }

    public void closeAuction(Item item) {
        item.closeAuction();
        handlePayment(item);
    }

    private void handlePayment(Item item) {
        Bid winningBid = item.getHighestBid();
        if (winningBid != null) {
            User winner = winningBid.getBidder();
            double amountToPay = winningBid.getAmount();
            paymentService.handlePayment(amountToPay, winner);
        }
    }

    private String getBidId() {
        return UUID.randomUUID().toString();
    }

    private String getItemId() {
        return UUID.randomUUID().toString();
    }
}
