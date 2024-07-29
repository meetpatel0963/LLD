package DesignPatterns;

import java.security.InvalidParameterException;

public class NullObjectPattern {
    /*
    * It is primarily used for the cases where the methods have some object as a parameter, and we need to apply
    * null checks for that object before any application logic.
    * In such cases, instead of passing null to those methods, we can pass a NullObject with some default behavior.
    * */

    interface Vehicle {
        int getTankCapacity();
        int getSeatCapacity();
    }

    static class Car implements Vehicle {
        @Override
        public int getTankCapacity() {
            return 40;
        }

        @Override
        public int getSeatCapacity() {
            return 5;
        }
    }

    static class NullObject implements Vehicle {
        @Override
        public int getTankCapacity() {
            return 0;
        }

        @Override
        public int getSeatCapacity() {
            return 0;
        }
    }

    enum VehicleType {
        CAR,
        BIKE
    }

    static class VehicleFactory {
        public Vehicle getVehicle(VehicleType vehicleType) {
            switch (vehicleType) {
                case CAR: return new Car();
                default: return new NullObject();
            }
        }
    }

    public static void main(String[] args) {
        VehicleFactory vehicleFactory = new VehicleFactory();
        Vehicle vehicle = vehicleFactory.getVehicle(VehicleType.CAR);
        System.out.println(vehicle.getTankCapacity() + " " + vehicle.getSeatCapacity());
        vehicle = vehicleFactory.getVehicle(VehicleType.BIKE);
        System.out.println(vehicle.getTankCapacity() + " " + vehicle.getSeatCapacity());
    }
}
