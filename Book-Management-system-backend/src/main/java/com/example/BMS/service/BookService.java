package com.example.BMS.service;

import com.example.BMS.exception.ResourceNotFoundException;
import com.example.BMS.model.Book;
import com.example.BMS.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public Book createBook(Book book) {
        // In a real app, you might add validation here
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book bookDetails) {
        // First, get the existing book. This will throw an exception if it's not found.
        Book existingBook = getBookById(id);

        // Update the fields with the new details
        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setAuthor(bookDetails.getAuthor());
        existingBook.setIsbn(bookDetails.getIsbn());
        existingBook.setPrice(bookDetails.getPrice());

        // Save the updated book back to the database
        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        // Check if the book exists before trying to delete it.
        // The getBookById method will throw ResourceNotFoundException if it doesn't.
        Book existingBook = getBookById(id);
        bookRepository.delete(existingBook);
    }
}