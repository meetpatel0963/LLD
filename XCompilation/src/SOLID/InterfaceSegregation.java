package SOLID;

public class InterfaceSegregation {
//    Interfaces should be such that the clients need not implement unnecessary functions they do not need.

//    Don't do
    interface RestaurantEmployee {
        void washDishes();
        void serveCustomers();
        void cookFood();
    }

    class Waiter implements RestaurantEmployee {
        @Override
        public void washDishes() {
//            unnecessary
        }

        @Override
        public void serveCustomers() {
//            do something
        }

        @Override
        public void cookFood() {
//            unnecessary
        }
    }

//    Do
    interface WaiterInterface {
        void serveCustomers();
        void takeOrder();
    }

    interface ChefInterface {
        void cookFood();
        void decideMenu();
    }

    class Waiter implements WaiterInterface {
        @Override
        public void serveCustomers() {

        }

        @Override
        public void takeOrder() {

        }
    }
}

