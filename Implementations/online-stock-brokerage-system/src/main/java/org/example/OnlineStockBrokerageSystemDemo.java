package org.example;

import org.example.model.Order;
import org.example.model.OrderType;
import org.example.model.Stock;
import org.example.model.User;
import org.example.service.OrderService;
import org.example.service.StockService;
import org.example.service.UserService;

import java.util.concurrent.*;

public class OnlineStockBrokerageSystemDemo {
    public static void main(String[] args) {
        UserService userService = new UserService();
        StockService stockService = new StockService();
        OrderService orderService = new OrderService();
        OnlineStockBrokerageSystem onlineStockBrokerageSystem = new OnlineStockBrokerageSystem(userService, stockService, orderService);

        onlineStockBrokerageSystem.registerUser("testuser1", "test", "test@gmail.com", 10000);
        onlineStockBrokerageSystem.registerUser("testuser2", "test", "test@gmail.com", 10000);

        User user1 = onlineStockBrokerageSystem.loginUser("testuser1", "test");
        User user2 = onlineStockBrokerageSystem.loginUser("testuser2", "test");

        Stock apple = onlineStockBrokerageSystem.addStock("AAPL", 100.0);
        Stock google = onlineStockBrokerageSystem.addStock("GOOGL", 150.0);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

        executorService.scheduleAtFixedRate(() -> {
            double delta = getRandomNumber(5);
            onlineStockBrokerageSystem.addPricePoint(apple.getId(), onlineStockBrokerageSystem.getPrice(apple.getId()) + delta);
        }, 0, 100, TimeUnit.MILLISECONDS);

        executorService.scheduleAtFixedRate(() -> {
            double delta = getRandomNumber(10);
            onlineStockBrokerageSystem.addPricePoint(google.getId(), onlineStockBrokerageSystem.getPrice(google.getId()) + delta);
        }, 0, 100, TimeUnit.MILLISECONDS);

        ExecutorService orderExecutor = Executors.newFixedThreadPool(4);
        try {
            // Delay to allow price updates to start
            Thread.sleep(200);

            for(int i = 0 ; i < 5 ; i++) {
                System.out.println("APPL: " + onlineStockBrokerageSystem.getPrice(apple.getId()));
                System.out.println("GOOGL: " + onlineStockBrokerageSystem.getPrice(google.getId()));
                Thread.sleep(200);
            }

            // Create tasks for placing orders
            Callable<Order> orderTask1 = () -> onlineStockBrokerageSystem.placeOrder(user1.getId(), apple.getId(), 5, onlineStockBrokerageSystem.getPrice(apple.getId()), OrderType.BUY);
            Callable<Order> orderTask2 = () -> onlineStockBrokerageSystem.placeOrder(user2.getId(), apple.getId(), 2, onlineStockBrokerageSystem.getPrice(apple.getId()), OrderType.BUY);
            Callable<Order> orderTask3 = () -> onlineStockBrokerageSystem.placeOrder(user1.getId(), google.getId(), 3, onlineStockBrokerageSystem.getPrice(google.getId()), OrderType.BUY);
            Callable<Order> orderTask4 = () -> onlineStockBrokerageSystem.placeOrder(user2.getId(), google.getId(), 5, onlineStockBrokerageSystem.getPrice(google.getId()), OrderType.BUY);

            // Submit tasks to the executor
            Future<Order> futureOrder1 = orderExecutor.submit(orderTask1);
            Future<Order> futureOrder2 = orderExecutor.submit(orderTask2);
            Future<Order> futureOrder3 = orderExecutor.submit(orderTask3);
            Future<Order> futureOrder4 = orderExecutor.submit(orderTask4);

            // Wait for all orders to complete
            Order order1 = futureOrder1.get();
            Order order2 = futureOrder2.get();
            Order order3 = futureOrder3.get();
            Order order4 = futureOrder4.get();

            // Display orders
            System.out.println("Placed Orders:");
            System.out.println(order1.getUser().getUsername() + ": " + order1);
            System.out.println(order2.getUser().getUsername() + ": " + order2);
            System.out.println(order3.getUser().getUsername() + ": " + order3);
            System.out.println(order4.getUser().getUsername() + ": " + order4);

            // Delay to allow buy orders to complete
            Thread.sleep(2000);

            // Create tasks for placing orders
            Callable<Order> orderTask5 = () -> onlineStockBrokerageSystem.placeOrder(user1.getId(), apple.getId(), 3, onlineStockBrokerageSystem.getPrice(apple.getId()), OrderType.SELL);
            Callable<Order> orderTask6 = () -> onlineStockBrokerageSystem.placeOrder(user2.getId(), apple.getId(), 2, onlineStockBrokerageSystem.getPrice(apple.getId()), OrderType.SELL);
            Callable<Order> orderTask7 = () -> onlineStockBrokerageSystem.placeOrder(user1.getId(), google.getId(), 3, onlineStockBrokerageSystem.getPrice(google.getId()), OrderType.SELL);
            Callable<Order> orderTask8 = () -> onlineStockBrokerageSystem.placeOrder(user2.getId(), google.getId(), 1, onlineStockBrokerageSystem.getPrice(google.getId()), OrderType.SELL);

            // Submit tasks to the executor
            Future<Order> futureOrder5 = orderExecutor.submit(orderTask5);
            Future<Order> futureOrder6 = orderExecutor.submit(orderTask6);
            Future<Order> futureOrder7 = orderExecutor.submit(orderTask7);
            Future<Order> futureOrder8 = orderExecutor.submit(orderTask8);

            // Wait for all orders to complete
            Order order5 = futureOrder5.get();
            Order order6 = futureOrder6.get();
            Order order7 = futureOrder7.get();
            Order order8 = futureOrder8.get();

            // Display orders
            System.out.println("Placed Orders:");
            System.out.println(order5.getUser().getUsername() + ": " + order5);
            System.out.println(order6.getUser().getUsername() + ": " + order6);
            System.out.println(order7.getUser().getUsername() + ": " + order7);
            System.out.println(order8.getUser().getUsername() + ": " + order8);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Shutdown executors
            orderExecutor.shutdown();
            executorService.shutdown();
        }
    }

    private static double getRandomNumber(int max) {
        return 1 + (max - 1) * Math.random();
    }
}
