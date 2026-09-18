package DesignPatterns;

public class TemplatePatternImpl {

    static  abstract class FoodOrder{
        public final void processOrder() {
            takeOrder();
            prepareFood();
            packFood();
            deliverFood();
        }

        private void takeOrder() {
            System.out.println("Taking Order");
        }

        protected abstract void prepareFood();

        private void packFood() {
            System.out.println("Packing Food");
        }

        private void deliverFood() {
            System.out.println("Delivering Food");
        }
    }

    static class PizzaOrder extends FoodOrder {

        @Override
        public void prepareFood() {
            System.out.println("Baking Pizza");
        }
    }

    static class BiryaniOrder extends FoodOrder {

        @Override
        public void prepareFood() {
            System.out.println("Cooking Biryani");
        }
    }

    public static void main(String[] args) {
        FoodOrder foodOrder1 = new PizzaOrder();
        foodOrder1.processOrder();

        System.out.println("************************************");

        FoodOrder foodOrder2 = new BiryaniOrder();
        foodOrder2.processOrder();
    }
}
