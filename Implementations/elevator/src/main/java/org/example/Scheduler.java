package org.example;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Getter
public class Scheduler {
    private final List<Elevator> elevators;

    public Scheduler(int numOfElevators, int startFloor) {
        elevators = new ArrayList<>();
        for (int i = 0; i < numOfElevators; i++) {
            elevators.add(new Elevator(i + 1, startFloor));
        }
    }

    public void scheduleRequest(int floor) {
        Elevator bestElevator = findBestElevator(floor);
        bestElevator.addRequest(floor);
    }

    private Elevator findBestElevator(int floor) {
        Optional<Elevator> bestElevator = elevators.stream()
                .min(Comparator.comparingInt(elevator -> calculateDistance(elevator, floor)));

        // Handle the case where no elevator is found
        if (bestElevator.isPresent()) {
            return bestElevator.get();
        } else {
            throw new IllegalStateException("No elevators available");
        }
    }

    private int calculateDistance(Elevator elevator, int floor) {
        int distance;
        int currentFloor = elevator.getCurrentFloor();
        Direction direction = elevator.getCurrentDirection();

        if (direction == Direction.IDLE || (direction == Direction.UP && floor >= currentFloor) || (direction == Direction.DOWN && floor <= currentFloor)) {
            distance = Math.abs(currentFloor - floor);
        } else {
            int edgeFloor = elevator.getCurrentDirection() == Direction.UP
                    ? elevator.getRequests().last() : elevator.getRequests().first();
            distance = Math.abs(edgeFloor - floor) + Math.abs(edgeFloor - currentFloor);
        }
        return distance;
    }

    public void shutdown() {
        for (Elevator elevator : elevators) {
            elevator.shutdown();
        }
    }

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(3, 0);
        scheduler.scheduleRequest(8);

        // Simulate delay and then add another request
        try {
            Thread.sleep(5000); // Wait 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduler.scheduleRequest(3);

        // Simulate delay and then add another request
        try {
            Thread.sleep(3000); // Wait 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduler.scheduleRequest(5);
        scheduler.scheduleRequest(6);
        scheduler.scheduleRequest(1);

        // Shut down the scheduler after all requests are done
        scheduler.shutdown();
    }
}