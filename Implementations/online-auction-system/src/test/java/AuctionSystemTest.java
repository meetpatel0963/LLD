import org.example.AuctionSystem;
import org.example.model.Item;
import org.example.model.User;
import org.example.service.ItemService;
import org.example.service.PaymentService;
import org.example.service.UserService;
import org.example.strategy.DefaultPaymentStrategy;
import org.example.strategy.PaymentStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AuctionSystemTest {
    @Test
    public void testConcurrentRequests() throws InterruptedException {
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

        // Register observers for items
        laptopItem.registerObserver(alice);
        laptopItem.registerObserver(bob);
        smartphoneItem.registerObserver(carol);
        smartphoneItem.registerObserver(alice);

        CountDownLatch startLatch = new CountDownLatch(1);

        // Create an executor service for concurrent bidding
        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.submit(() -> {
            try {
                startLatch.await();
                auctionSystem.placeBid(laptopItem, alice, 550);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.submit(() -> {
            try {
                startLatch.await();
                auctionSystem.placeBid(laptopItem, bob, 600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.submit(() -> {
            try {
                startLatch.await();
                auctionSystem.placeBid(smartphoneItem, alice, 350);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.submit(() -> {
            try {
                startLatch.await();
                auctionSystem.placeBid(smartphoneItem, carol, 400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        startLatch.countDown();

        // Shutdown the executor after all tasks are submitted
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS); // Wait for all bidding tasks to complete

        // Assert expected state after bidding
        Assertions.assertEquals("Bob", laptopItem.getHighestBid().getBidder().getUsername());
        Assertions.assertEquals("Carol", smartphoneItem.getHighestBid().getBidder().getUsername());
        Assertions.assertTrue(laptopItem.getHighestBid().getAmount() >= 600);
        Assertions.assertTrue(smartphoneItem.getHighestBid().getAmount() >= 400);

        // Create another executor service for closing auctions concurrently
        ExecutorService closingExecutor = Executors.newFixedThreadPool(2);

        // Runnable for closing laptopItem
        closingExecutor.submit(() -> {
            try {
                Thread.sleep(7000); // Simulate some delay until auction ends
                auctionSystem.closeItem(laptopItem);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Runnable for closing smartphoneItem
        closingExecutor.submit(() -> {
            try {
                Thread.sleep(6000); // Simulate some delay until auction ends
                auctionSystem.closeItem(smartphoneItem);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Shutdown the closing executor after all tasks are submitted
        closingExecutor.shutdown();
        closingExecutor.awaitTermination(10, TimeUnit.SECONDS); // Wait for all closing tasks to complete

        // Assert expected state after closing auctions
        Assertions.assertFalse(laptopItem.isOpen());
        Assertions.assertFalse(smartphoneItem.isOpen());
        Assertions.assertNotNull(laptopItem.getHighestBid());
        Assertions.assertNotNull(smartphoneItem.getHighestBid());
    }
}
