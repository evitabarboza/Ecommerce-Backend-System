# 🛍️ E-Commerce Backend System

### 🚀 Overview
This project is a **production-grade e-commerce backend system** built using **Java 17** and **Spring Boot 3**, following a **layered architecture** and **industry-standard backend development practices**.

It provides a complete backend solution for managing:
- User registration & authentication  
- Product management  
- Shopping cart  
- Orders & payments (simulated)  
- Inventory management  

The backend exposes **RESTful APIs** that can be consumed by web or mobile clients.



## 🧩 Architecture

```
Controller → Service → Repository → Entity
```

### 📁 Package Structure

```
com.ecommerce
 ┣ 📂 controller      → REST API endpoints
 ┣ 📂 service         → Business logic
 ┣ 📂 repository      → JPA repositories
 ┣ 📂 entity          → JPA entities (database tables)
 ┣ 📂 dto             → Data Transfer Objects
 ┣ 📂 exception       → Custom exceptions & handlers
 ┣ 📂 config          → Security & app configurations
 ┗ 📂 utils           → Utility/helper classes
```

## ⚙️ Tech Stack

| Component | Technology |
|------------|-------------|
| **Language** | Java 17+ |
| **Framework** | Spring Boot 3+ |
| **ORM** | Spring Data JPA / Hibernate |
| **Database** | MySQL |
| **Build Tool** | Maven |
| **Testing** | JUnit 5, Mockito |
| **Logging** | SLF4J / Logback |



## 🧠 Core Features

### 👤 User Management
- Register and login
- Update profile and change password
- Role-based access: **ADMIN**, **CUSTOMER**
- Admin can manage all users (CRUD)

### 🛒 Product Management
- Admin: add, update, delete products
- Customers: view products with pagination, sorting, and filtering
- Product fields: name, description, price, stock, category, image URL, rating

### 🧺 Shopping Cart
- Each customer has their own cart
- Add, remove, or update product quantity
- Calculate total price before checkout

### 📦 Order Management
- Checkout converts cart → order
- Stores order details: date, items, total price, payment mode, status
- Update order status: `PLACED`, `SHIPPED`, `DELIVERED`, `CANCELLED`
- View order history

### 💳 Payment Management (Simulated)
- Simulate payment success/failure
- Update order & inventory accordingly

### 🏬 Inventory Management
- Reduce product stock after successful order
- Prevent out-of-stock purchases

## 🧱 Database Design

| Entity | Description |
|---------|--------------|
| **User** | `id`, `name`, `email`, `password`, `role` |
| **Product** | `id`, `name`, `description`, `price`, `stock`, `category`, `image_url`, `rating` |
| **Cart** | `id`, `user_id`, `total_price` |
| **CartItem** | `id`, `cart_id`, `product_id`, `quantity` |
| **Order** | `id`, `user_id`, `total_amount`, `order_date`, `payment_status`, `order_status` |
| **OrderItem** | `id`, `order_id`, `product_id`, `quantity`, `price` |


## 🌐 API Endpoints

### 👤 User APIs
| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/api/users/register` | Register new user |
| `POST` | `/api/users/login` | User login |
| `GET` | `/api/users/{id}` | Get user profile |
| `PUT` | `/api/users/{id}` | Update user profile |
| `DELETE` | `/api/users/{id}` | Delete user (Admin only) |

### 🛍️ Product APIs
| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/api/products` | Add new product (Admin) |
| `GET` | `/api/products` | Get all products (with pagination/filtering) |
| `GET` | `/api/products/{id}` | Get product details |
| `PUT` | `/api/products/{id}` | Update product (Admin) |
| `DELETE` | `/api/products/{id}` | Delete product (Admin) |

### 🧺 Cart APIs
| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/api/cart/add/{productId}` | Add product to cart |
| `PUT` | `/api/cart/update/{productId}` | Update product quantity in cart |
| `DELETE` | `/api/cart/remove/{productId}` | Remove product from cart |
| `GET` | `/api/cart` | View cart and total price |

### 📦 Order APIs
| Method | Endpoint | Description |
|--------|-----------|-------------|
| `POST` | `/api/orders/checkout` | Checkout and place order |
| `GET` | `/api/orders` | View all orders for current user |
| `GET` | `/api/orders/{id}` | Get order details |
| `PUT` | `/api/orders/{id}/status` | Update order status (Admin only) |


## 🔐 Bonus Features
- JWT Authentication & Role-based Access Control
- Swagger UI documentation at `/swagger-ui/index.html`
- Docker Compose for DB + App containerization
- Email notifications on successful order
- Unit testing with **JUnit 5** & **Mockito**


## 🧰 Setup Instructions

### 1️⃣ Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+
- (Optional) Docker

### 2️⃣ Clone Repository
```bash
git clone https://github.com/evitabarboza/Ecommerce-Backend-System.git
cd ecommerce-backend
```

### 3️⃣ Configure Database
Update the `application.properties` file:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 4️⃣ Build & Run
```bash
mvn clean install
mvn spring-boot:run
```

App will start at:  
👉 **http://localhost:8080**


## 🧪 Testing
Run all unit tests:
```bash
mvn test
```

## 📬 API Documentation

Swagger UI available at:  
👉 **http://localhost:8080/swagger-ui/index.html**

Or import the provided **Postman Collection** (`EcommerceAPI.postman_collection.json`) to test all APIs manually.


## 🗄️ Database Schema

Run the provided SQL script:  
`/db/ecommerce_schema.sql`  
or allow Spring JPA to auto-generate tables.




Services:
- **App:** `http://localhost:8080`
- **MySQL:** `localhost:3306`


## 🪪 License
This project is licensed under the **MIT License**.  
Feel free to use and modify for learning or production purposes.

