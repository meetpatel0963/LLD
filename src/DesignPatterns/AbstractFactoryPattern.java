package DesignPatterns;

import java.security.InvalidParameterException;

public class AbstractFactoryPattern {
    /*
    * Same as Factory Pattern.
    * Here, we have a hierarchy of factories -> factory of factories based on some conditions.
    * */

    interface Vehicle {
        void getName();
    }

    static class Mercedes implements Vehicle {
        @Override
        public void getName() {
            System.out.println("Mercedes");
        }
    }

    static class BMW implements Vehicle {
        @Override
        public void getName() {
            System.out.println("BMW");
        }
    }

    static class Swift implements Vehicle {
        @Override
        public void getName() {
            System.out.println("Swift");
        }
    }

    static class Hyundai implements Vehicle {
        @Override
        public void getName() {
            System.out.println("Hyundai");
        }
    }

    enum VehicleType {
        MERCEDES,
        BMW,
        SWIFT,
        HYUNDAI
    }

    interface VehicleFactory {
        Vehicle getVehicle(VehicleType vehicleType);
    }

    static class LuxuryVehicleFactory implements VehicleFactory {
        @Override
        public Vehicle getVehicle(VehicleType vehicleType) {
            return switch (vehicleType) {
                case MERCEDES -> new Mercedes();
                case BMW -> new BMW();
                default -> throw new InvalidParameterException("Invalid Vehicle Type");
            };
        }
    }

    static class OrdinaryVehicleFactory implements VehicleFactory {
        @Override
        public Vehicle getVehicle(VehicleType vehicleType) {
            return switch (vehicleType) {
                case SWIFT -> new Swift();
                case HYUNDAI -> new Hyundai();
                default -> throw new InvalidParameterException("Invalid Vehicle Type");
            };
        }
    }

    static class VehicleFactoryWithType {
        public VehicleFactory getVehicleFactory(VehicleType vehicleType) {
            return switch (vehicleType) {
                case MERCEDES, BMW -> new LuxuryVehicleFactory();
                case SWIFT, HYUNDAI -> new OrdinaryVehicleFactory();
                default -> throw new InvalidParameterException("Invalid Vehicle Type");
            };
        }
    }

    public static void main(String[] args) {
        VehicleFactoryWithType vehicleFactoryWithType = new VehicleFactoryWithType();

        VehicleType vehicleType = VehicleType.MERCEDES;
        VehicleFactory vehicleFactory = vehicleFactoryWithType.getVehicleFactory(vehicleType);
        Vehicle vehicle = vehicleFactory.getVehicle(vehicleType);
        vehicle.getName();

        vehicleType = VehicleType.HYUNDAI;
        vehicleFactory = vehicleFactoryWithType.getVehicleFactory(vehicleType);
        vehicle = vehicleFactory.getVehicle(vehicleType);
        vehicle.getName();
    }


}
