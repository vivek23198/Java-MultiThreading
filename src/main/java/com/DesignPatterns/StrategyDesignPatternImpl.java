package DesignPatterns;

public class StrategyDesignPatternImpl {

    // STRATEGY
    interface PaymentStrategy {
        void pay(double amount);
    }

    // CONCRETE STRATEGIES
    static class CreditCardPayment implements PaymentStrategy {

        @Override
        public void pay(double amount) {
            System.out.println(
                    "Paid ₹" + amount + " using Credit Card"
            );
        }
    }


    static class UPIPayment implements PaymentStrategy {

        @Override
        public void pay(double amount) {
            System.out.println(
                    "Paid ₹" + amount + " using UPI"
            );
        }
    }


    static class PayPalPayment implements PaymentStrategy {

        @Override
        public void pay(double amount) {
            System.out.println(
                    "Paid ₹" + amount + " using PayPal"
            );
        }
    }


    // CONTEXT
    static class PaymentContext {

        private PaymentStrategy strategy;

        public PaymentContext(PaymentStrategy strategy) {
            this.strategy = strategy;
        }

        public void makePayment(double amount) {
            strategy.pay(amount);
        }
    }



    // MAIN
    public static void main(String[] args) {

        PaymentContext payment;


        // Credit Card Strategy
        payment = new PaymentContext(new CreditCardPayment());
        payment.makePayment(1000);


        // UPI Strategy
        payment = new PaymentContext(new UPIPayment());
        payment.makePayment(2000);


        // PayPal Strategy
        payment = new PaymentContext(new PayPalPayment());
        payment.makePayment(3000);
    }
}
