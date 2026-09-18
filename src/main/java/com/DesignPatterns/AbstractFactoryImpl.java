package DesignPatterns;

public class AbstractFactoryImpl {

    // =========================
    // ABSTRACT PRODUCTS
    // =========================

    interface Shoes {
        void wear();
    }

    interface TShirt {
        void wear();
    }

    interface Lower {
        void wear();
    }


    // =========================
    // ADIDAS PRODUCTS
    // =========================

    static class AdidasShoes implements Shoes {
        @Override
        public void wear() {
            System.out.println("Wearing Adidas Shoes");
        }
    }

    static class AdidasTShirt implements TShirt {
        @Override
        public void wear() {
            System.out.println("Wearing Adidas T-Shirt");
        }
    }

    static class AdidasLower implements Lower {
        @Override
        public void wear() {
            System.out.println("Wearing Adidas Lower");
        }
    }


    // =========================
    // REVOKE PRODUCTS
    // =========================

    static class RevokeShoes implements Shoes {
        @Override
        public void wear() {
            System.out.println("Wearing Revoke Shoes");
        }
    }

    static class RevokeTShirt implements TShirt {
        @Override
        public void wear() {
            System.out.println("Wearing Revoke T-Shirt");
        }
    }

    static class RevokeLower implements Lower {
        @Override
        public void wear() {
            System.out.println("Wearing Revoke Lower");
        }
    }


    // =========================
    // ABSTRACT FACTORY
    // =========================

    interface ClothingFactory {

        Shoes createShoes();

        TShirt createTShirt();

        Lower createLower();
    }


    // =========================
    // ADIDAS FACTORY
    // =========================

    static class AdidasFactory implements ClothingFactory {

        @Override
        public Shoes createShoes() {
            return new AdidasShoes();
        }

        @Override
        public TShirt createTShirt() {
            return new AdidasTShirt();
        }

        @Override
        public Lower createLower() {
            return new AdidasLower();
        }
    }


    // =========================
    // REVOKE FACTORY
    // =========================

    static class RevokeFactory implements ClothingFactory {

        @Override
        public Shoes createShoes() {
            return new RevokeShoes();
        }

        @Override
        public TShirt createTShirt() {
            return new RevokeTShirt();
        }

        @Override
        public Lower createLower() {
            return new RevokeLower();
        }
    }


    // =========================
    // SHOWROOM
    // =========================

    static class Showroom {

        private ClothingFactory factory;

        Showroom(ClothingFactory factory) {
            this.factory = factory;
        }

        public void showProducts() {

            Shoes shoes = factory.createShoes();
            TShirt tShirt = factory.createTShirt();
            Lower lower = factory.createLower();

            shoes.wear();
            tShirt.wear();
            lower.wear();
        }
    }


    // =========================
    // MAIN
    // =========================

    public static void main(String[] args) {

        // Adidas showroom
        System.out.println("---- ADIDAS ----");

        ClothingFactory adidasFactory = new AdidasFactory();

        Showroom adidasShowroom =
                new Showroom(adidasFactory);

        adidasShowroom.showProducts();


        // Revoke showroom
        System.out.println("\n---- REVOKE ----");

        ClothingFactory revokeFactory = new RevokeFactory();

        Showroom revokeShowroom =
                new Showroom(revokeFactory);

        revokeShowroom.showProducts();
    }
}
