package com.example.BMS.controller;

import com.example.BMS.model.Book;
import com.example.BMS.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

  @Autowired
  private BookService bookService;

  // 🔹 GET all books
  @GetMapping
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  // 🔹 GET book by ID
  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable Long id) {
    Book book = bookService.getBookById(id);
    return ResponseEntity.ok(book);
  }

  // 🔹 POST create new book
  @PostMapping
  public ResponseEntity<Book> createBook(@RequestBody Book book) {
    Book createdBook = bookService.createBook(book);
    return ResponseEntity.ok(createdBook);
  }

  // 🔹 PUT update book
  @PutMapping("/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
    Book updatedBook = bookService.updateBook(id, bookDetails);
    return ResponseEntity.ok(updatedBook);
  }

  // 🔹 DELETE book
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteBook(@PathVariable Long id) {
    bookService.deleteBook(id);
    return ResponseEntity.ok("Book with ID " + id + " was deleted successfully.");
  }
}
