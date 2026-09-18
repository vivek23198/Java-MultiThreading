package DesignPatterns;

import java.util.ArrayList;
import java.util.List;

public class ObserverDesignPatternImpl {

    // =========================
    // OBSERVER
    // =========================

    interface Subscriber {

        void update(String video);
    }


    // =========================
    // CONCRETE OBSERVERS
    // =========================

    static class User implements Subscriber {

        private String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public void update(String video) {
            System.out.println(
                    name + " received notification: " + video
            );
        }
    }


    // =========================
    // SUBJECT
    // =========================

    static class YouTubeChannel {

        private List<Subscriber> subscribers = new ArrayList<>();


        // Subscribe
        public void subscribe(Subscriber subscriber) {
            subscribers.add(subscriber);
        }


        // Unsubscribe
        public void unsubscribe(Subscriber subscriber) {
            subscribers.remove(subscriber);
        }


        // Notify all subscribers
        public void uploadVideo(String video) {

            System.out.println(
                    "\nNew Video Uploaded: " + video
            );

            notifySubscribers(video);
        }


        private void notifySubscribers(String video) {

            for (Subscriber subscriber : subscribers) {
                subscriber.update(video);
            }
        }
    }


    // =========================
    // MAIN
    // =========================

    public static void main(String[] args) {

        YouTubeChannel channel = new YouTubeChannel();


        Subscriber user1 = new User("Vivek");
        Subscriber user2 = new User("Rahul");
        Subscriber user3 = new User("Amit");


        // Subscribe
        channel.subscribe(user1);
        channel.subscribe(user2);
        channel.subscribe(user3);


        // Upload video
        channel.uploadVideo("Java Design Patterns");


        // Rahul unsubscribes
        channel.unsubscribe(user2);

        // Upload another video
        channel.uploadVideo("Spring Boot Microservices");
    }
}
