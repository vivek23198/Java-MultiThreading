package DesignPatterns;

import java.util.concurrent.CompletableFuture;



public class DecoratorPatternImpl {

    interface Coffee {
        String getDescription();
        double getCost();
    }

    static class SimpleCoffee implements Coffee {

        @Override
        public String getDescription() {
            return "Simple Coffee";
        }

        @Override
        public double getCost() {
            return 50;
        }
    }

    static abstract class CoffeeDecorator implements Coffee{
        protected Coffee coffee;

        public CoffeeDecorator(Coffee coffee) {
            this.coffee = coffee;
        }
    }

    static class MilkDecorator extends CoffeeDecorator {

        public MilkDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String getDescription() {
            return coffee.getDescription() + ", Milk";
        }

        @Override
        public double getCost() {
            return coffee.getCost() + 20;
        }
    }

    static class ChocolateDecorator extends CoffeeDecorator {
        public ChocolateDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String getDescription() {
            return coffee.getDescription() + ", Chocolate";
        }

        @Override
        public double getCost() {
            return coffee.getCost() + 50;
        }
    }

    static class SugarDecorator extends CoffeeDecorator {

        public SugarDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String getDescription() {
            return coffee.getDescription() + ", Sugar";
        }

        @Override
        public double getCost() {
            return coffee.getCost() + 10;
        }
    }


    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription());
        System.out.println(coffee.getCost());

        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription());
        System.out.println(coffee.getCost());

        coffee = new ChocolateDecorator(coffee);
        System.out.println(coffee.getDescription());
        System.out.println(coffee.getCost());
    }
}
