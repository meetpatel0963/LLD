package DesignPatterns;

public class DecoratorPattern {
    /*
    * It is used when we have a base object with a set of features and many optional features to add on top of that.
    * Each optional feature can be considered as a decorator/wrapper on top of the given object.
    * Example: Base Pizza and Toppings
    * */

    abstract static class BasePizza {
        public abstract int cost();
    }

    public static class Farmhouse extends BasePizza {
        @Override
        public int cost() {
            return 200;
        }
    }

    public static class Margherita extends BasePizza {
        @Override
        public int cost() {
            return 100;
        }
    }


    abstract static class ToppingsDecorator extends BasePizza {

    }

    static class ExtraCheese extends ToppingsDecorator {
        private final BasePizza basePizza;

        public ExtraCheese(BasePizza basePizza) {
            this.basePizza = basePizza;
        }

        @Override
        public int cost() {
            return this.basePizza.cost() + 10;
        }
    }

    static class Mushroom extends ToppingsDecorator {
        private final BasePizza basePizza;

        public Mushroom(BasePizza basePizza) {
            this.basePizza = basePizza;
        }

        @Override
        public int cost() {
            return this.basePizza.cost() + 15;
        }
    }

    public static void main(String[] args) {
        BasePizza pizza = new ExtraCheese(new Mushroom(new Farmhouse()));
        System.out.println(pizza.cost());
    }
}
