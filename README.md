# E-Commerce Full System

A full-stack e-commerce web application built with **Spring Boot**, **Thymeleaf**, **MySQL**, and **Spring Security**.

## Features

- User Registration & Login (Spring Security + BCrypt)
- Product Listing + Product Details Page
- Product Search (name/description)
- Shopping Cart (add/remove/update quantity)
- Cart Item Count Badge in Navbar
- Checkout flow + Order confirmation

## Tech Stack

**Backend:** Java 17, Spring Boot, Spring MVC, Spring Data JPA, Spring Security  
**Frontend:** Thymeleaf, Bootstrap 5, HTML/CSS  
**Database:** MySQL  
**Build Tool:** Maven

##  Setup & Run Locally

### Prerequisites
- Java 17+
- MySQL 8+
- Maven
- IntelliJ IDEA

## Main Routes

- `/products` — Browse products and search
- `/products/{id}` — View product details
- `/cart` — View shopping cart
- `/checkout` — Checkout process
- `/login` — User login
- `/register` — User registration

## Project Structure

src/main/java/com/example/ecommerce
- config # Security configuration
- controller # MVC controllers
- model # Entities (Product, User, CartItem, etc.)
- repository # JPA repositories
- service # Business logic

src/main/resources
- templates # Thymeleaf pages
- application.properties

##  Highlights

- Implemented secure authentication with Spring Security + BCrypt
- Built a session-based cart with live cart count badge
- Used Spring Data JPA + MySQL for persistence
- Designed UI pages with Thymeleaf and Bootstrap
