package org.example;

import org.example.model.Item;
import org.example.model.User;
import org.example.service.ItemService;
import org.example.service.PaymentService;
import org.example.service.UserService;
import org.example.strategy.DefaultPaymentStrategy;
import org.example.strategy.PaymentStrategy;

public class AuctionSystemDemo {
    public static void main(String[] args) {
        // Create a UserService and ItemService with a DefaultPaymentStrategy
        UserService userService = UserService.getInstance();
        PaymentStrategy paymentStrategy = new DefaultPaymentStrategy();
        PaymentService paymentService = PaymentService.getInstance(paymentStrategy);
        ItemService itemService = ItemService.getInstance(paymentService);
        AuctionSystem auctionSystem = new AuctionSystem(userService, itemService);

        // Register users
        auctionSystem.registerUser("Alice", "password123");
        auctionSystem.registerUser("Bob", "password123");
        auctionSystem.registerUser("Carol", "password123");


        // Login users
        User alice = auctionSystem.loginUser("Alice", "password123");
        User bob = auctionSystem.loginUser("Bob", "password123");
        User carol = auctionSystem.loginUser("Carol", "password123");

        // Create items
        Item laptopItem = auctionSystem.createItem("Laptop", "A high-end laptop", 500, 10);
        Item smartphoneItem = auctionSystem.createItem("Smartphone", "A new smartphone", 300, 10);

        laptopItem.registerObserver(alice);
        laptopItem.registerObserver(bob);
        smartphoneItem.registerObserver(carol);
        smartphoneItem.registerObserver(alice);

        // Place bids
        auctionSystem.placeBid(laptopItem, alice, 550);
        auctionSystem.placeBid(laptopItem, bob, 600);

        auctionSystem.placeBid(smartphoneItem, alice, 350);
        auctionSystem.placeBid(smartphoneItem, carol, 400);

        // Close items (auctions)
        auctionSystem.closeItem(laptopItem);
        auctionSystem.closeItem(smartphoneItem);
    }
}
