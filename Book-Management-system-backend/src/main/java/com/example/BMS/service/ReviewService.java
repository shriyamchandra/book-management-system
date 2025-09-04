package com.example.BMS.service;

import com.example.BMS.dto.ReviewDto;
import com.example.BMS.exception.ResourceNotFoundException;
import com.example.BMS.model.Book;
import com.example.BMS.model.Customer;
import com.example.BMS.model.Review;
import com.example.BMS.model.User;
import com.example.BMS.repository.BookRepository;
import com.example.BMS.repository.ReviewRepository;
import com.example.BMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private UserRepository userRepository;

  public Review addReview(ReviewDto reviewDto, String userEmail) {
    // Find the book that the review is for
    Book book = bookRepository.findById(reviewDto.getBookId())
        .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + reviewDto.getBookId()));

    // Find the user (and their customer profile) who is writing the review
    User user = userRepository.findByEmail(userEmail)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

    Customer customer = user.getCustomer();

    // Create the new review entity
    Review newReview = new Review();
    newReview.setHeadline(reviewDto.getHeadline());
    newReview.setComment(reviewDto.getComment());
    newReview.setRating(reviewDto.getRating());
    newReview.setReviewOn(LocalDate.now());
    newReview.setBook(book);
    newReview.setCustomer(customer);

    return reviewRepository.save(newReview);
  }
}