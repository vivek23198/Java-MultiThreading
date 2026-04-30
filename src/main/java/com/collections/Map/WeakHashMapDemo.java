package com.collections.Map;

import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapDemo {

    public static void main(String[] args) {

        /*
         * WEAKHASHMAP NOTES (Interview Revision)
         * --------------------------------------
         *
         * 1. Stores keys as WEAK REFERENCES
         * 2. If key has no strong reference → eligible for GC
         * 3. Entry is automatically removed when key is GC'ed
         *
         * 👉 Key point:
         * WeakHashMap cleans entries automatically
         */


        Map<String, Image> imageCache = new WeakHashMap<>();

        loadCache(imageCache);

        System.out.println("Before GC: " + imageCache);


        /*
         * Suggest JVM to run GC (not guaranteed)
         */
        System.gc();


        simulateApplicationRunning();

        /*
         * After GC → entries may disappear
         */
        System.out.println("After GC: " + imageCache);
    }



    public static void loadCache(Map<String, Image> imageCache) {

        /*
         * IMPORTANT:
         * Using new String() → ensures different objects (not string pool)
         */
        String k1 = new String("img1");
        String k2 = new String("img2");

        imageCache.put(k1, new Image("Image 1"));
        imageCache.put(k2, new Image("Image 2"));

        /*
         * After this method ends:
         * k1, k2 → NO strong reference
         * Only WeakHashMap holds them → weak reference
         */
    }



    private static void simulateApplicationRunning() {
        try {
            System.out.println("Simulating application running...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


/*
 * SIMPLE CLASS FOR VALUE
 */
class Image {

    private String name;

    public Image(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}