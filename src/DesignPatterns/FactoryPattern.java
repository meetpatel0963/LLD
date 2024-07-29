package DesignPatterns;

import java.security.InvalidParameterException;

public class FactoryPattern {
    /*
    * It is used when we need to create a particular type of object based on some condition.
    * Especially, when there are multiple children extending/implementing a parent class/interface.
    * Instead of repeating the code everytime we need to create an object based on some condition,
    * we can use an object factory.
    * */

    interface Shape {
        void draw();
    }

    static class Circle implements Shape {
        @Override
        public void draw() {
            System.out.println("Circle");
        }
    }

    static class Rectangle implements Shape {
        @Override
        public void draw() {
            System.out.println("Rectangle");
        }
    }

    enum ShapeType {
        CIRCLE,
        RECTANGLE
    }

    static class ShapeFactory {
        public Shape getShape(ShapeType shapeType) {
            return switch (shapeType) {
                case CIRCLE -> new Circle();
                case RECTANGLE -> new Rectangle();
                default -> throw new InvalidParameterException("Invalid Shape Type");
            };
        }
    }

    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();

        Shape shape = shapeFactory.getShape(ShapeType.CIRCLE);
        shape.draw();

        shape = shapeFactory.getShape(ShapeType.RECTANGLE);
        shape.draw();
    }
}
