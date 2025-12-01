# E-Commerce Backend - Spring Boot

Full-featured e-commerce backend built with Java Spring Boot and MySQL.

## Features

- **Product Management**: CRUD operations for products with categories and search
- **User Management**: User registration, profile management
- **Shopping Cart**: Add/remove items, update quantities, calculate totals
- **Order Management**: Create orders, track status, payment integration
- **RESTful API**: Clean REST endpoints with proper HTTP methods
- **MySQL Database**: Relational database with JPA/Hibernate

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- MySQL 8.0
- Lombok
- Maven

## Prerequisites

- JDK 17 or higher
- Maven 3.6+
- MySQL 8.0+

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/sampurnv/ecommerce-backend-springboot.git
cd ecommerce-backend-springboot
```

### 2. Configure MySQL Database

Create a MySQL database:

```sql
CREATE DATABASE ecommerce_db;
```

Update `src/main/resources/application.properties` with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 3. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Products

- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/search?keyword={keyword}` - Search products
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Users

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Cart

- `GET /api/cart/{userId}` - Get user's cart
- `POST /api/cart/add` - Add item to cart
- `PUT /api/cart/update` - Update cart item quantity
- `DELETE /api/cart/{userId}/item/{itemId}` - Remove item from cart
- `DELETE /api/cart/{userId}/clear` - Clear cart

### Orders

- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/user/{userId}` - Get user's orders
- `POST /api/orders` - Create new order
- `PUT /api/orders/{id}/status?status={status}` - Update order status
- `PUT /api/orders/{id}/payment` - Update payment ID
- `DELETE /api/orders/{id}` - Delete order

## Database Schema

The application automatically creates the following tables:

- `products` - Product catalog
- `users` - User accounts
- `carts` - Shopping carts
- `cart_items` - Items in carts
- `orders` - Customer orders
- `order_items` - Items in orders

## Sample Data

You can add sample products using POST requests to `/api/products`:

```json
{
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 999.99,
  "category": "Electronics",
  "imageUrl": "https://example.com/laptop.jpg",
  "stockQuantity": 50
}
```

## License

MIT License