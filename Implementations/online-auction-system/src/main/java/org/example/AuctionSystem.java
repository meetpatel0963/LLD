package org.example;

import lombok.AllArgsConstructor;
import org.example.model.Item;
import org.example.model.User;
import org.example.service.ItemService;
import org.example.service.UserService;

@AllArgsConstructor
public class AuctionSystem {
    private final UserService userService;
    private final ItemService itemService;

    public void registerUser(String username, String password) {
        userService.register(username, password);
    }

    public User loginUser(String username, String password) {
        return userService.login(username, password);
    }

    public Item createItem(String itemName, String description, double startingPrice, int durationMinutes) {
        return itemService.createItem(itemName, description, startingPrice, durationMinutes);
    }

    public void placeBid(Item item, User bidder, double amount) {
        itemService.placeBid(item, bidder, amount);
    }

    public void closeItem(Item item) {
        itemService.closeAuction(item);
    }
}
