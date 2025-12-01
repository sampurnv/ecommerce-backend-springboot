package com.ecommerce.dto;

public class UpdatePaymentRequest {
    private String paymentId;

    public UpdatePaymentRequest() {}

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
}