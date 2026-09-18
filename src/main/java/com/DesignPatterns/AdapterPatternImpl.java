package DesignPatterns;

public class AdapterPatternImpl{

    interface PaymentProcessor  {
        void pay(double amount);
    }

    static class RazorPayAPI {
        public void makePayment(double amount) {
            System.out.println(
                    "Payment processed using Razorpay: "
                            + amount
            );
        }
    }

    static class PaypalAPI implements PaymentProcessor{
        public void pay(double amount) {
            System.out.println("Payment processed using Paypal: "+amount);
        }
    }

    static class RazorPayAdapter implements PaymentProcessor {

        RazorPayAPI razorPayAPI;

        public RazorPayAdapter(RazorPayAPI razorPayAPI) {
            this.razorPayAPI = razorPayAPI;
        }

        @Override
        public void pay(double amount) {
            razorPayAPI.makePayment(amount);
        }
    }

    public static void main(String[] args) {
        PaymentProcessor paymentProcessor = new RazorPayAdapter(new RazorPayAPI());
        paymentProcessor.pay(100);

        PaymentProcessor paymentProcessor1 = new PaypalAPI();
        paymentProcessor1.pay(20.5);
    }




}
