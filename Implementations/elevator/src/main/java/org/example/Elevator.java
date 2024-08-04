package org.example;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter
public class Elevator {
    private final int id;
    private int currentFloor;
    private Direction currentDirection;
    private final TreeSet<Integer> requests;
    private final ScheduledExecutorService executorService;

    public Elevator(final int id, final int startFloor) {
        this.id = id;
        this.currentFloor = startFloor;
        this.currentDirection = Direction.IDLE;
        requests = new TreeSet<>();
        executorService = Executors.newScheduledThreadPool(1);
    }

    public synchronized void addRequest(int floor) {
        requests.add(floor);
        if (currentDirection == Direction.IDLE) {
            int nextFloor = requests.first();
            currentDirection = getDirectionTo(nextFloor);
            startMoving();
        }
    }

    private Direction getDirectionTo(int nextFloor) {
        return currentFloor < nextFloor ? Direction.UP : Direction.DOWN;
    }

    private void startMoving() {
        executorService.scheduleAtFixedRate(() -> {
            synchronized (this) {
                if (!requests.isEmpty()) {
                    int nextFloor;
                    if (currentDirection == Direction.UP) {
                        nextFloor = requests.ceiling(currentFloor);
                    } else {
                        nextFloor = requests.floor(currentFloor);
                    }

                    moveTowards(nextFloor);
                } else {
                    stopMoving();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void moveTowards(int targetFloor) {
        if (currentFloor < targetFloor) {
            currentFloor++;
            log("Elevator " + id + " moving UP, currently at floor " + currentFloor);
        } else if (currentFloor > targetFloor) {
            currentFloor--;
            log("Elevator " + id + " moving DOWN, currently at floor " + currentFloor);
        }

        if (requests.contains(currentFloor)) {
            requests.remove(currentFloor);
            log("Elevator " + id + " opened doors at floor " + currentFloor);
        }

        if (currentFloor == targetFloor) {
            adjustDirection(); // Adjust direction after reaching target floor
        }
    }

    private void adjustDirection() {
        if (requests.isEmpty()) {
            stopMoving();
        } else {
            int nextFloor = currentDirection == Direction.UP ? requests.last() : requests.first();
            if (currentDirection == Direction.UP && currentFloor >= nextFloor) {
                currentDirection = Direction.DOWN;
            } else if (currentDirection == Direction.DOWN && currentFloor <= nextFloor) {
                currentDirection = Direction.UP;
            }
        }
    }

    public void stopMoving() {
        currentDirection = Direction.IDLE;
    }

    private void log(String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now) + " - " + message);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

}
