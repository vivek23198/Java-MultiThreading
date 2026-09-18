package DesignPatterns;

class EagerSingleton {

    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {
    }

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}

class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {

        if (instance == null) {
            instance = new LazySingleton();
        }

        return instance;
    }
}

class SynchronizedSingleton {

    private static SynchronizedSingleton instance;

    private SynchronizedSingleton() {
    }

    public static synchronized SynchronizedSingleton getInstance() {

        if (instance == null) {
            instance = new SynchronizedSingleton();
        }

        return instance;
    }
}

class DoubleCheckedSingleton {

    private static volatile DoubleCheckedSingleton instance;

    private DoubleCheckedSingleton() {
    }

    public static DoubleCheckedSingleton getInstance() {

        if (instance == null) {

            synchronized (DoubleCheckedSingleton.class) {

                if (instance == null) {

                    instance = new DoubleCheckedSingleton();
                }
            }
        }

        return instance;
    }
}

class BillPughSingleton {

    private BillPughSingleton() {
    }

    private static class SingletonHelper {

        private static final BillPughSingleton INSTANCE =
                new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {

        return SingletonHelper.INSTANCE;
    }
}



public class CreateSingletonObj {
    public static void main(String[] args) {
        /**
         * For most applications, I'd use the Bill Pugh Singleton because it's lazy,
         * thread-safe, and leverages JVM class-loading guarantees without
         * synchronization overhead
         */

    }
}
