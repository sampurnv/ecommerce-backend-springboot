package com.ecommerce.service;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {
    
    @Value("${sms.api.key:your-twilio-api-key}")
    private String apiKey;
    
    @Value("${sms.api.secret:your-twilio-api-secret}")
    private String apiSecret;
    
    @Value("${sms.from.number:+1234567890}")
    private String fromNumber;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Async
    public void sendOrderConfirmationSms(User user, Order order) {
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            log.warn("User {} has no phone number", user.getId());
            return;
        }
        
        String message = String.format(
            "Order Confirmed! Order #%d - Total: $%.2f. Thank you for shopping with E-Shop!",
            order.getId(),
            order.getTotalAmount()
        );
        
        sendSms(user.getPhone(), message);
    }
    
    @Async
    public void sendOrderStatusUpdateSms(User user, Order order) {
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            log.warn("User {} has no phone number", user.getId());
            return;
        }
        
        String message = String.format(
            "Order #%d status updated to: %s. Track your order at http://localhost:3000/orders",
            order.getId(),
            order.getStatus()
        );
        
        sendSms(user.getPhone(), message);
    }
    
    @Async
    public void sendWelcomeSms(User user) {
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            log.warn("User {} has no phone number", user.getId());
            return;
        }
        
        String message = String.format(
            "Welcome to E-Shop, %s! Start shopping now at http://localhost:3000/products",
            user.getName()
        );
        
        sendSms(user.getPhone(), message);
    }
    
    private void sendSms(String toNumber, String message) {
        try {
            // Integration with Twilio, AWS SNS, or any SMS provider
            // This is a placeholder implementation
            log.info("SMS sent to {}: {}", toNumber, message);
            
            // Example Twilio integration (uncomment when configured):
            /*
            String url = "https://api.twilio.com/2010-04-01/Accounts/" + apiKey + "/Messages.json";
            
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("From", fromNumber);
            params.add("To", toNumber);
            params.add("Body", message);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth(apiKey, apiSecret);
            
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            restTemplate.postForEntity(url, request, String.class);
            */
            
        } catch (Exception e) {
            log.error("Failed to send SMS to {}", toNumber, e);
        }
    }
}