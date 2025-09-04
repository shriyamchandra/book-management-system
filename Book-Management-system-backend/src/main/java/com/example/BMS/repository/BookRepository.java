package com.example.BMS.repository;

// In src/main/java/com/yourproject/repository/BookRepository.java
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BMS.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}