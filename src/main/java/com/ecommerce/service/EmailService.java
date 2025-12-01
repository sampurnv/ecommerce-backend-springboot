package com.ecommerce.service;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    
    private final JavaMailSender mailSender;
    
    @Async
    public void sendOrderConfirmationEmail(User user, Order order) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(user.getEmail());
            helper.setSubject("Order Confirmation - Order #" + order.getId());
            
            String emailContent = buildOrderConfirmationEmail(user, order);
            helper.setText(emailContent, true);
            
            mailSender.send(message);
            log.info("Order confirmation email sent to: {}", user.getEmail());
        } catch (MessagingException e) {
            log.error("Failed to send order confirmation email", e);
        }
    }
    
    @Async
    public void sendOrderStatusUpdateEmail(User user, Order order) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(user.getEmail());
            helper.setSubject("Order Status Update - Order #" + order.getId());
            
            String emailContent = buildOrderStatusUpdateEmail(user, order);
            helper.setText(emailContent, true);
            
            mailSender.send(message);
            log.info("Order status update email sent to: {}", user.getEmail());
        } catch (MessagingException e) {
            log.error("Failed to send order status update email", e);
        }
    }
    
    @Async
    public void sendWelcomeEmail(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to E-Shop!");
            
            String emailContent = buildWelcomeEmail(user);
            helper.setText(emailContent, true);
            
            mailSender.send(message);
            log.info("Welcome email sent to: {}", user.getEmail());
        } catch (MessagingException e) {
            log.error("Failed to send welcome email", e);
        }
    }
    
    private String buildOrderConfirmationEmail(User user, Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='font-family: Arial, sans-serif;'>");
        sb.append("<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>");
        sb.append("<h2 style='color: #2c3e50;'>Order Confirmation</h2>");
        sb.append("<p>Dear ").append(user.getName()).append(",</p>");
        sb.append("<p>Thank you for your order! Your order has been confirmed.</p>");
        sb.append("<div style='background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;'>");
        sb.append("<h3 style='color: #2c3e50;'>Order Details</h3>");
        sb.append("<p><strong>Order ID:</strong> #").append(order.getId()).append("</p>");
        sb.append("<p><strong>Order Date:</strong> ").append(order.getCreatedAt()).append("</p>");
        sb.append("<p><strong>Total Amount:</strong> $").append(String.format("%.2f", order.getTotalAmount())).append("</p>");
        sb.append("<p><strong>Status:</strong> ").append(order.getStatus()).append("</p>");
        sb.append("</div>");
        sb.append("<p><strong>Shipping Address:</strong><br>").append(order.getShippingAddress()).append("</p>");
        sb.append("<p>We'll send you another email when your order ships.</p>");
        sb.append("<p>Best regards,<br>E-Shop Team</p>");
        sb.append("</div></body></html>");
        return sb.toString();
    }
    
    private String buildOrderStatusUpdateEmail(User user, Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='font-family: Arial, sans-serif;'>");
        sb.append("<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>");
        sb.append("<h2 style='color: #2c3e50;'>Order Status Update</h2>");
        sb.append("<p>Dear ").append(user.getName()).append(",</p>");
        sb.append("<p>Your order status has been updated.</p>");
        sb.append("<div style='background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0;'>");
        sb.append("<p><strong>Order ID:</strong> #").append(order.getId()).append("</p>");
        sb.append("<p><strong>New Status:</strong> <span style='color: #28a745; font-weight: bold;'>").append(order.getStatus()).append("</span></p>");
        sb.append("</div>");
        sb.append("<p>Thank you for shopping with us!</p>");
        sb.append("<p>Best regards,<br>E-Shop Team</p>");
        sb.append("</div></body></html>");
        return sb.toString();
    }
    
    private String buildWelcomeEmail(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='font-family: Arial, sans-serif;'>");
        sb.append("<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>");
        sb.append("<h2 style='color: #2c3e50;'>Welcome to E-Shop!</h2>");
        sb.append("<p>Dear ").append(user.getName()).append(",</p>");
        sb.append("<p>Thank you for joining E-Shop! We're excited to have you as part of our community.</p>");
        sb.append("<p>Start exploring our amazing products and enjoy exclusive deals!</p>");
        sb.append("<div style='text-align: center; margin: 30px 0;'>");
        sb.append("<a href='http://localhost:3000/products' style='background-color: #007bff; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; display: inline-block;'>Start Shopping</a>");
        sb.append("</div>");
        sb.append("<p>Best regards,<br>E-Shop Team</p>");
        sb.append("</div></body></html>");
        return sb.toString();
    }
}