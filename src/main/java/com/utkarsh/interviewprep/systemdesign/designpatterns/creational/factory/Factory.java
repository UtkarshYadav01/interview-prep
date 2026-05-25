package com.utkarsh.interviewprep.systemdesign.designpatterns.creational.factory;

public class Factory {
    public static void main(String[] args) {
        Vehicle v1 = VehicleFactory.getVehicle("car");
        v1.drive();

        Vehicle v2 = VehicleFactory.getVehicle("bike");
        v2.drive();
    }
}

interface Vehicle {
    void drive();
}

class Car implements Vehicle {
    public void drive() {
        System.out.println("Driving Car");
    }
}

class Bike implements Vehicle {
    public void drive() {
        System.out.println("Riding Bike");
    }
}

class VehicleFactory {

    // Factory method → returns object based on input
    public static Vehicle getVehicle(String type) {

        if (type.equalsIgnoreCase("car")) {
            return new Car();
        } else if (type.equalsIgnoreCase("bike")) {
            return new Bike();
        }

        return null;
    }
}