package com.utkarsh.interviewprep.systemdesign.designpatterns.structural.proxy;

public class Proxy {

    // Subject interface
    interface Image {
        void display();
    }

    // RealSubject
    static class RealImage implements Image {
        private String filename;

        public RealImage(String filename) {
            this.filename = filename;
            loadFromDisk();
        }

        private void loadFromDisk() {
            System.out.println("Loading " + filename);
        }

        public void display() {
            System.out.println("Displaying " + filename);
        }
    }

    // Proxy
    static class ProxyImage implements Image {
        private RealImage realImage;
        private String filename;

        public ProxyImage(String filename) {
            this.filename = filename;
        }

        public void display() {
            if (realImage == null) {
                realImage = new RealImage(filename); // lazy loading
            }
            realImage.display();
        }
    }

    // Client
    public static void main(String[] args) {
        Image image = new ProxyImage("photo.jpg");

        image.display(); // loads + displays
        image.display(); // just displays (no loading again)
    }
}
