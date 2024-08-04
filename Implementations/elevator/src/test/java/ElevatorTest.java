import org.example.Direction;
import org.example.Elevator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElevatorTest {

    private Elevator elevator;

    @AfterEach
    void tearDown() {
        // Clean up after each test
        if (elevator != null) {
            elevator.stopMoving(); // Ensure the elevator stops moving
        }
    }

    @Test
    void testBasicOperation() throws InterruptedException {
        elevator = new Elevator(1, 0);

        elevator.addRequest(8);
        waitForElevator(5, elevator);
        elevator.addRequest(3);
        waitForElevator(10, elevator);

        // Assert elevator reaches the correct final state
        assertEquals(3, elevator.getCurrentFloor());
        assertEquals(Direction.IDLE, elevator.getCurrentDirection());
        assertTrue(elevator.getRequests().isEmpty());
    }

    @Test
    void testConcurrentRequests() throws InterruptedException {
        elevator = new Elevator(1, 0);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        CountDownLatch latch = new CountDownLatch(5);

        // Add concurrent requests
        for (int i = 0; i < 5; i++) {
            int floor = i * 2 + 1; // Odd floors
            executorService.execute(() -> {
                elevator.addRequest(floor);
                latch.countDown();
            });
        }

        // Wait for all requests to be processed
        latch.await();

        // Wait for elevator to finish handling requests
        waitForElevator(10, elevator);

        // Assert elevator reaches the correct final state
        assertTrue(elevator.getCurrentFloor() > 0); // Elevator moved
        assertEquals(Direction.IDLE, elevator.getCurrentDirection());
        assertTrue(elevator.getRequests().isEmpty());

        // Shutdown executor service
        executorService.shutdown();
    }

    @Test
    void testEdgeCases() throws InterruptedException {
        elevator = new Elevator(1, 0);

        // Add request to current floor
        elevator.addRequest(0);
        waitForElevator(5, elevator);

        // Add request when elevator is idle
        elevator.addRequest(5);
        waitForElevator(5, elevator);

        // Add request in opposite direction
        elevator.addRequest(-2);
        waitForElevator(5, elevator);

        // Add request at higher floor
        elevator.addRequest(10);
        waitForElevator(10, elevator);

        // Assert elevator reaches the correct final state
        assertEquals(10, elevator.getCurrentFloor());
        assertEquals(Direction.IDLE, elevator.getCurrentDirection());
        assertTrue(elevator.getRequests().isEmpty());
    }

    private void waitForElevator(int timeoutSeconds, Elevator elevator) throws InterruptedException {
        // Wait for the elevator to finish processing all requests
        long endTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(timeoutSeconds);
        while (!elevator.getRequests().isEmpty() && System.currentTimeMillis() < endTime) {
            Thread.sleep(100);
        }
    }
}
