package com.utkarsh;

import com.utkarsh.payment.PaymentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderService {

    private final PaymentService paymentService;

    public OrderService(@Qualifier("cardPayment") PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder() {
        paymentService.pay();
        System.out.println("Order Placed");
    }
}
