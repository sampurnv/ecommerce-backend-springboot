# Advanced Features Documentation

## 🚀 Overview

This document covers all the advanced features added to the E-Commerce backend application.

## 📧 Email Notifications

### Features
- Order confirmation emails
- Order status update notifications
- Welcome emails for new users
- HTML-formatted professional emails

### Configuration
Update `application-advanced.properties`:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

### Usage
```java
@Autowired
private EmailService emailService;

// Send order confirmation
emailService.sendOrderConfirmationEmail(user, order);

// Send status update
emailService.sendOrderStatusUpdateEmail(user, order);

// Send welcome email
emailService.sendWelcomeEmail(user);
```

## 📱 SMS Notifications

### Features
- Order confirmation SMS
- Order status updates via SMS
- Welcome SMS for new users
- Integration-ready for Twilio, AWS SNS

### Configuration
```properties
sms.api.key=your-twilio-account-sid
sms.api.secret=your-twilio-auth-token
sms.from.number=+1234567890
```

### Usage
```java
@Autowired
private SmsService smsService;

// Send SMS notifications
smsService.sendOrderConfirmationSms(user, order);
smsService.sendOrderStatusUpdateSms(user, order);
```

## 💳 Payment Gateway Integration

### Supported Gateways
1. **Stripe** - Credit/Debit cards
2. **PayPal** - PayPal accounts
3. **Razorpay** - Indian payment methods

### API Endpoints
```
POST /api/payments/process
POST /api/payments/stripe
POST /api/payments/paypal
POST /api/payments/razorpay
```

### Request Format
```json
{
  "amount": 99.99,
  "currency": "USD",
  "paymentMethod": "stripe",
  "orderId": 123,
  "cardNumber": "4242424242424242",
  "cardExpiry": "12/25",
  "cardCvv": "123",
  "email": "customer@example.com"
}
```

### Configuration
```properties
# Stripe
stripe.api.key=sk_test_your_stripe_secret_key

# PayPal
paypal.client.id=your_paypal_client_id
paypal.client.secret=your_paypal_client_secret

# Razorpay
razorpay.key.id=your_razorpay_key_id
razorpay.key.secret=your_razorpay_key_secret
```

## 🤖 AI-Based Product Recommendations

### Features
- Personalized recommendations based on purchase history
- Similar products by category and price
- Popular/trending products
- Frequently bought together

### API Endpoints
```
GET /api/recommendations/personalized/{userId}?limit=10
GET /api/recommendations/similar/{productId}?limit=6
GET /api/recommendations/popular?limit=10
GET /api/recommendations/frequently-bought-together/{productId}?limit=4
```

### Algorithm
- Analyzes user purchase history
- Category-based filtering
- Price range similarity (±30%)
- Co-occurrence analysis for "bought together"

## 💬 Live Chat Support

### Features
- Real-time WebSocket communication
- Multiple users support
- Join/leave notifications
- Typing indicators
- Message history

### WebSocket Configuration
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // Configuration for STOMP over WebSocket
}
```

### Endpoints
- WebSocket: `ws://localhost:8080/ws`
- Send Message: `/app/chat.sendMessage`
- Add User: `/app/chat.addUser`
- Subscribe: `/topic/public`

## 🔐 Social Media Login (OAuth2)

### Supported Providers
1. **Google OAuth2**
2. **Facebook OAuth2**
3. **GitHub OAuth2**

### Configuration
```properties
# Google
spring.security.oauth2.client.registration.google.client-id=your-google-client-id
spring.security.oauth2.client.registration.google.client-secret=your-google-client-secret

# Facebook
spring.security.oauth2.client.registration.facebook.client-id=your-facebook-app-id
spring.security.oauth2.client.registration.facebook.client-secret=your-facebook-app-secret

# GitHub
spring.security.oauth2.client.registration.github.client-id=your-github-client-id
spring.security.oauth2.client.registration.github.client-secret=your-github-client-secret
```

### Setup Steps

#### Google OAuth2
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project
3. Enable Google+ API
4. Create OAuth 2.0 credentials
5. Add authorized redirect URI: `http://localhost:8080/login/oauth2/code/google`

#### Facebook OAuth2
1. Go to [Facebook Developers](https://developers.facebook.com/)
2. Create a new app
3. Add Facebook Login product
4. Configure OAuth redirect URI: `http://localhost:8080/login/oauth2/code/facebook`

#### GitHub OAuth2
1. Go to GitHub Settings > Developer settings > OAuth Apps
2. Create new OAuth App
3. Set callback URL: `http://localhost:8080/login/oauth2/code/github`

## 📊 Dependencies Added

```xml
<!-- Email Support -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>

<!-- Security & OAuth2 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
</dependency>

<!-- WebSocket for Live Chat -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

## 🔧 Testing

### Email Testing
Use [Mailtrap](https://mailtrap.io/) for development email testing.

### Payment Testing
- **Stripe Test Cards**: `4242 4242 4242 4242`
- **PayPal Sandbox**: Use PayPal sandbox accounts
- **Razorpay Test Mode**: Enable test mode in dashboard

### WebSocket Testing
Use browser console or tools like [WebSocket King](https://websocketking.com/)

## 🚀 Deployment Considerations

1. **Email**: Use production SMTP servers (SendGrid, AWS SES)
2. **SMS**: Configure production Twilio/AWS SNS credentials
3. **Payments**: Switch to production API keys
4. **OAuth2**: Update redirect URIs for production domain
5. **WebSocket**: Configure for production load balancing

## 📝 Best Practices

1. Store sensitive credentials in environment variables
2. Use HTTPS in production for OAuth2 and payments
3. Implement rate limiting for email/SMS
4. Add payment webhook handlers for async updates
5. Monitor WebSocket connections and implement reconnection logic
6. Cache recommendation results for performance

## 🐛 Troubleshooting

### Email not sending
- Check SMTP credentials
- Enable "Less secure app access" for Gmail
- Use app-specific passwords

### Payment failures
- Verify API keys are correct
- Check test/production mode
- Review payment gateway logs

### WebSocket connection issues
- Check CORS configuration
- Verify WebSocket endpoint URL
- Check firewall/proxy settings

## 📚 Additional Resources

- [Spring Boot Mail Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.email)
- [Spring Security OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [Spring WebSocket](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket)
- [Stripe API Docs](https://stripe.com/docs/api)
- [PayPal API Docs](https://developer.paypal.com/docs/api/overview/)
- [Razorpay API Docs](https://razorpay.com/docs/api/)