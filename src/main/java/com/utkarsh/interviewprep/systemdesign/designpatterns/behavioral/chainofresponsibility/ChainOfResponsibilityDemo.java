package com.utkarsh.interviewprep.systemdesign.designpatterns.behavioral.chainofresponsibility;

public class ChainOfResponsibilityDemo {

    // Abstract Handler
    static abstract class MoneyHandler {
        protected MoneyHandler nextHandler;

        public void setNextHandler(MoneyHandler nextHandler) {
            this.nextHandler = nextHandler;
        }

        public abstract void dispense(int amount);
    }

    // Handler for 1000 notes
    static class ThousandHandler extends MoneyHandler {

        public void dispense(int amount) {
            int notes = amount / 1000;
            int remainder = amount % 1000;

            if (notes > 0) {
                System.out.println("1000 Notes: " + notes);
            }

            if (remainder != 0 && nextHandler != null) {
                nextHandler.dispense(remainder);
            }
        }
    }

    // Handler for 500 notes
    static class FiveHundredHandler extends MoneyHandler {

        public void dispense(int amount) {
            int notes = amount / 500;
            int remainder = amount % 500;

            if (notes > 0) {
                System.out.println("500 Notes: " + notes);
            }

            if (remainder != 0 && nextHandler != null) {
                nextHandler.dispense(remainder);
            }
        }
    }

    // Handler for 100 notes
    static class HundredHandler extends MoneyHandler {

        public void dispense(int amount) {
            int notes = amount / 100;
            int remainder = amount % 100;

            if (notes > 0) {
                System.out.println("100 Notes: " + notes);
            }

            if (remainder != 0) {
                System.out.println("Remaining amount cannot be dispensed: " + remainder);
            }
        }
    }

    public static void main(String[] args) {

        // Create handlers
        MoneyHandler h1000 = new ThousandHandler();
        MoneyHandler h500 = new FiveHundredHandler();
        MoneyHandler h100 = new HundredHandler();

        // Build chain
        h1000.setNextHandler(h500);
        h500.setNextHandler(h100);

        int amount = 3700;

        System.out.println("Dispensing Amount: " + amount);
        h1000.dispense(amount);
    }
}