package DesignPatterns;

public class ProxyPatternImpl {

    interface IImage {
        void display();
    }

    static class RealImage implements IImage {
        private String fileName;

        public RealImage(String fileName) {
            this.fileName = fileName;
            loadFromDisk();
        }

        private void loadFromDisk() {
            System.out.println("Loading " + fileName);
        }

        @Override
        public void display() {
            System.out.println("Displaying " + fileName);
        }
    }

    static class RealImageProxy implements IImage {

        private RealImage realImage;
        private String fileName;

        public RealImageProxy(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void display() {
            if(realImage == null) {
                realImage = new RealImage(fileName);
            }
            realImage.display();
        }
    }

    public static void main(String[] args) {
        IImage image = new RealImageProxy("car.jpg");

        System.out.println("Image object created");

        System.out.println("First display:");
        image.display();

        System.out.println("Second display:");
        image.display();
    }
}
