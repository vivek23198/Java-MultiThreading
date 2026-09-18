package DesignPatterns;

public class NullObjectPatternImpl {

    // Common interface
    interface Discount {
        double calculateDiscount(double amount);
    }

    // Real Object
    static class FestivalDiscount implements Discount {

        @Override
        public double calculateDiscount(double amount) {
            return amount * 0.20; // 20% discount
        }
    }

    // Null Object
    static class NoDiscount implements Discount {

        @Override
        public double calculateDiscount(double amount) {
            return 0; // No discount
        }
    }

    // Factory-like method to decide which object to return
    static Discount getDiscount(boolean festivalSale) {

        if (festivalSale) {
            return new FestivalDiscount();
        }

        return new NoDiscount();
    }

    public static void main(String[] args) {

        double amount = 1000;

        // Festival sale
        Discount discount1 = getDiscount(true);

        double discountAmount1 =
                discount1.calculateDiscount(amount);

        System.out.println("Original Amount: ₹" + amount);
        System.out.println("Discount: ₹" + discountAmount1);
        System.out.println(
                "Final Amount: ₹" + (amount - discountAmount1)
        );

        System.out.println("-------------------------");

        // No festival sale
        Discount discount2 = getDiscount(false);

        double discountAmount2 =
                discount2.calculateDiscount(amount);

        System.out.println("Original Amount: ₹" + amount);
        System.out.println("Discount: ₹" + discountAmount2);
        System.out.println(
                "Final Amount: ₹" + (amount - discountAmount2)
        );
    }
}
