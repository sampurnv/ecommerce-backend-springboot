package com.ecommerce.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Double amount;
    private String currency = "USD";
    private String paymentMethod; // stripe, paypal, razorpay
    private Long orderId;
    private String cardNumber;
    private String cardExpiry;
    private String cardCvv;
    private String email;
}