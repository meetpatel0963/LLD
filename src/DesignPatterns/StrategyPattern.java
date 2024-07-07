package DesignPatterns;

public class StrategyPattern {
    /*
    * In the inheritance hierarchy, when multiple children want to provide a specific (different from the parent)
    * implementation of certain methods that are exactly similar among those children, we can reuse the code using
    * strategy pattern.
    * */

    interface DrivingStrategy {
        void drive();
    }

    static class NormalDrivingStrategy implements DrivingStrategy {
        @Override
        public void drive() {
            System.out.println("normal diriving capabilities");
        }
    }

    static class SportDrivingStrategy implements DrivingStrategy {
        @Override
        public void drive() {
            System.out.println("sports driving capabilities");
        }
    }

    static class Vehicle {
        private DrivingStrategy drivingStrategy;

        public Vehicle(DrivingStrategy drivingStrategy) {
            this.drivingStrategy = drivingStrategy;
        }

        public void drive() {
            drivingStrategy.drive();
        }
    }

    static class GoodsVehicle extends Vehicle {
        public GoodsVehicle() {
            super(new NormalDrivingStrategy());
        }
    }

    static class SportsCar extends Vehicle {
        public SportsCar() {
            super(new SportDrivingStrategy());
        }
    }

    static class OffRoadVehicle extends Vehicle {
        public OffRoadVehicle() {
            super(new NormalDrivingStrategy());
        }
    }

    public static void main(String[] args) {
        Vehicle vehicle = new SportsCar();
        vehicle.drive();

        vehicle = new GoodsVehicle();
        vehicle.drive();

        vehicle = new OffRoadVehicle();
        vehicle.drive();
    }
}