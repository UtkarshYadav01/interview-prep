package com.utkarsh.interviewprep.systemdesign.designpatterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

// Observer interface
interface Observer {
    void update(String message);
}

// Subject interface
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}

// Concrete Subject
class YouTubeChannel implements Subject {

    private List<Observer> subscribers = new ArrayList<>();
    private String latestVideo;

    @Override
    public void attach(Observer observer) {
        subscribers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        subscribers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : subscribers) {
            observer.update(latestVideo);
        }
    }

    public void uploadVideo(String title) {
        latestVideo = title;

        System.out.println("\nNew Video Uploaded: " + title);

        notifyObservers();
    }
}

// Concrete Observer
class Subscriber implements Observer {

    private String name;

    public Subscriber(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name +
                " received notification: New video -> " + message);
    }
}

// Main class
public class ObserverPatternDemo {

    public static void main(String[] args) {

        YouTubeChannel channel = new YouTubeChannel();

        Observer user1 = new Subscriber("Alice");
        Observer user2 = new Subscriber("Bob");
        Observer user3 = new Subscriber("Charlie");

        // Subscribe users
        channel.attach(user1);
        channel.attach(user2);
        channel.attach(user3);

        // Upload first video
        channel.uploadVideo("Observer Pattern in Java");

        // Unsubscribe Bob
        channel.detach(user2);

        // Upload second video
        channel.uploadVideo("Design Patterns Tutorial");
    }
}