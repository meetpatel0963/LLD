package SOLID;

public class LiskovSubstitution {
    /*
    * If class B is subtype of class A, then we should be able to replace objects of A with B
    * without breaking the behavior of the program.
    * */

//    Don't do
    class Vehicle {
        public Boolean turnOnEngine() {
            return true;
        }
        public Integer getNumberOfWheels() {
            return 2;
        }
    }

    public class MotorCycle extends Vehicle {

    }

    public class Car extends Vehicle {
        @Override
        public Integer getNumberOfWheels() {
            return 4;
        }
    }

    public class Bicycle extends Vehicle {
        @Override
        public Boolean turnOnEngine() {
            throw new AssertionError("no engine found!");
        }
    }

//    Do
    public class Vehicle {
        public Integer getNumberOfWheels() {
            return 2;
        }
    }

    public class EngineVehicle extends Vehicle {
        public Boolean turnOnEngine() {
            return true;
        }
    }

    public class Bicycle extends Vehicle {

    }

    public class MotorCycle extends EngineVehicle {

    }

    public class Car extends EngineVehicle {
        @Override
        public Integer getNumberOfWheels() {
            return 4;
        }
    }
}
