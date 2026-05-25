package com.utkarsh.interviewprep.systemdesign.designpatterns.structural.decorator;

public class DecoratorPatternDemo {

    // Component
    interface Coffee {
        String getDescription();
        double cost();
    }

    // Concrete Component
    static class SimpleCoffee implements Coffee {
        public String getDescription() {
            return "Simple Coffee";
        }

        public double cost() {
            return 5.0;
        }
    }

    // Base Decorator
    static abstract class CoffeeDecorator implements Coffee {
        protected Coffee coffee;

        public CoffeeDecorator(Coffee coffee) {
            this.coffee = coffee;
        }

        public String getDescription() {
            return coffee.getDescription();
        }

        public double cost() {
            return coffee.cost();
        }
    }

    // Concrete Decorator: Milk
    static class MilkDecorator extends CoffeeDecorator {
        public MilkDecorator(Coffee coffee) {
            super(coffee);
        }

        public String getDescription() {
            return coffee.getDescription() + ", Milk";
        }

        public double cost() {
            return coffee.cost() + 1.5;
        }
    }

    // Concrete Decorator: Sugar
    static class SugarDecorator extends CoffeeDecorator {
        public SugarDecorator(Coffee coffee) {
            super(coffee);
        }

        public String getDescription() {
            return coffee.getDescription() + ", Sugar";
        }

        public double cost() {
            return coffee.cost() + 0.5;
        }
    }

    // Main method
    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();

        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);

        System.out.println(coffee.getDescription());
        System.out.println("Cost: " + coffee.cost());
    }
}