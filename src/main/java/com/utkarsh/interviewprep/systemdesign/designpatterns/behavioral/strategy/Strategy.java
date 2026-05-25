package com.utkarsh.interviewprep.systemdesign.designpatterns.behavioral.strategy;

public class Strategy {
    public static void main(String[] args) {

        // Using Credit Card strategy
        PaymentService payment1 =
                new PaymentService(new CreditCardPayment());

        payment1.processPayment(1000);

        // Switching to UPI strategy
        PaymentService payment2 =
                new PaymentService(new UpiPayment());

        payment2.processPayment(500);
    }
}

//Step 1: Strategy Interface
interface PaymentStrategy {
    void pay(int amount);
}

//Step 2: Different Strategies
class CreditCardPayment implements PaymentStrategy {

    public void pay(int amount) {
        System.out.println("Paid using Credit Card: " + amount);
    }
}

class UpiPayment implements PaymentStrategy {

    public void pay(int amount) {
        System.out.println("Paid using UPI: " + amount);
    }
}

//Step 3: Context Class
class PaymentService {

    // strategy can change at runtime
    private final PaymentStrategy strategy;

    public PaymentService(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void processPayment(int amount) {
        strategy.pay(amount);
    }
}