cat << 'EOF' > README.md
# 📚 Book Management System

A full-stack web application for managing books, reviews, and orders.  
Built with **Spring Boot + PostgreSQL** for backend and **Angular** for frontend.

---

## 🚀 Features
- **Authentication & Authorization**
  - Users can register & log in
  - Roles: `ROLE_USER` and `ROLE_ADMIN`
- **Book Management**
  - Admins can add, update, and delete books
  - Users can view available books
- **Orders**
  - Users can place orders
  - Stock updates automatically
- **Reviews**
  - Users can add reviews to books

---

## 🛠 Tech Stack
- **Backend**: Spring Boot, Spring Security, JPA, PostgreSQL
- **Frontend**: Angular
- **Tools**: Postman, GitHub, Maven, Node.js

---

## ⚙️ Setup

### Backend (Spring Boot)
```bash
cd Book-Management-system-backend
./mvnw spring-boot:run
