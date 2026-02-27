# 🛒 Spring Boot Microservices E‑Commerce Platform

A production‑style **Microservices Architecture** built using **Spring Boot + Angular**, demonstrating real‑world backend design including API Gateway security, Keycloak authentication, Kafka event‑driven communication, Resilience4j fault tolerance, and polyglot persistence with MySQL & MongoDB.

---

## 🚀 Project Overview

This project simulates a scalable e‑commerce backend where multiple independent services communicate through both **synchronous** and **asynchronous** mechanisms.

It was built to gain hands‑on experience with **distributed systems**, **secure microservices**, and **containerized deployment**.

---

## 🧩 Architecture

```
                        Angular Frontend
                              ↓
                         API Gateway
                              ↓
           ┌──────────────┬──────────────┬──────────────┐
           | Product      | Inventory    | Order        |
           | Service      | Service      | Service      |
           | (MongoDB)    | (MySQL)      | (MySQL)      |
           └──────────────┴──────────────┴──────────────┘
                                ↓
                              Kafka
                                ↓
                       Notification Service
```

---

## 🛠️ Tech Stack

### Backend

* **Java Spring Boot** – Microservices development
* **Spring Cloud Gateway** – API Gateway & routing
* **Keycloak** – Authentication & Authorization (OAuth2 / OpenID Connect)
* **Resilience4j** – Circuit Breaker, Retry, Fault tolerance
* **Apache Kafka** – Event‑driven asynchronous communication

### Frontend

* **Angular** – UI and client‑side interaction

### Databases

* **MySQL** – Order & Inventory Services (transactional data)
* **MongoDB** – Product Service (flexible catalog data)

### DevOps

* **Docker & Docker Compose** – Multi‑container deployment
* Environment‑based configuration
* Ready for Kubernetes deployment

---

## 🧩 Microservices Details

### 1️⃣ Product Service

* Stores product catalog
* Uses MongoDB for flexible schema
* CRUD operations for products

### 2️⃣ Inventory Service

* Tracks product stock levels
* Uses MySQL for transactional integrity
* Validates stock before order placement

### 3️⃣ Order Service

* Handles order creation & management
* Uses MySQL database
* Communicates with Inventory Service synchronously
* Publishes Kafka events after order confirmation

### 4️⃣ Notification Service

* Consumes Kafka events
* Sends order confirmation notifications
* Demonstrates async microservice workflow

### 5️⃣ API Gateway

* Central entry point
* Request routing
* Security enforcement
* Token validation using Keycloak

---

## 🔐 Authentication & Authorization

Implemented using **Keycloak**:

* OAuth2 / OpenID Connect
* Token‑based authentication
* Secure API Gateway routing

---

## 🔄 Communication Patterns

### ✔ Synchronous Communication

Order Service → Inventory Service

* Implemented using REST APIs
* Fault tolerance with Resilience4j
* Circuit Breaker + Retry pattern

### ✔ Asynchronous Communication

Order Service → Notification Service

* Implemented using Apache Kafka
* Event‑driven architecture
* Improved scalability & decoupling

---

## 📂 Folder Structure

```
root
 ├── api-gateway
 ├── product-service
 ├── inventory-service
 ├── order-service
 ├── notification-service
 ├── angular-frontend
 └── README.md
```

---

## 📌 Key Learnings

* Designing scalable microservices architecture
* Implementing centralized authentication using Keycloak
* Handling service failures with Resilience4j
* Event‑driven architecture using Kafka
* Managing multiple databases in distributed systems
* Containerizing microservices with Docker

---

## ⭐ Future Improvements

* Kubernetes deployment
* Distributed tracing (Zipkin / Jaeger)
* Centralized logging (ELK / Loki)
* CI/CD pipeline integration
* API rate limiting
* Service discovery (Eureka / Consul)

---

**Adarsh Raj**
If you like this project, give it a ⭐ on GitHub!

