package DesignPatterns;

public class FactoryPatternImpl {

    // =========================
    // PRODUCT INTERFACE
    // =========================

    interface Shoes {
        void wear();
    }


    // =========================
    // CONCRETE PRODUCTS
    // =========================

    static class AdidasShoes implements Shoes {

        @Override
        public void wear() {
            System.out.println("Wearing Adidas Shoes");
        }
    }

    static class RevokeShoes implements Shoes {

        @Override
        public void wear() {
            System.out.println("Wearing Revoke Shoes");
        }
    }


    // =========================
    // FACTORY
    // =========================

    static class ShoesFactory {

        public Shoes createShoes(String brand) {

            if (brand.equalsIgnoreCase("adidas")) {
                return new AdidasShoes();
            }

            if (brand.equalsIgnoreCase("revoke")) {
                return new RevokeShoes();
            }

            throw new IllegalArgumentException(
                    "Unknown brand: " + brand
            );
        }
    }


    // =========================
    // CLIENT
    // =========================

    public static void main(String[] args) {

        ShoesFactory factory = new ShoesFactory();

        Shoes shoes1 = factory.createShoes("adidas");
        shoes1.wear();

        Shoes shoes2 = factory.createShoes("revoke");
        shoes2.wear();
    }
}
