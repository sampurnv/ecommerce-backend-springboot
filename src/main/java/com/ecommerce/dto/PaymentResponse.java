package com.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private boolean success;
    private String paymentId;
    private String transactionId;
    private Double amount;
    private String currency;
    private String status;
    private String message;
}