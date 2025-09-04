package com.example.BMS.controller;

import com.example.BMS.dto.ReviewDto;
import com.example.BMS.model.Review;
import com.example.BMS.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

  @Autowired
  private ReviewService reviewService;

  @PostMapping
  public ResponseEntity<Review> addReview(@RequestBody ReviewDto reviewDto, Authentication authentication) {
    // Get the email of the currently logged-in user from the authentication token
    String userEmail = authentication.getName();
    Review newReview = reviewService.addReview(reviewDto, userEmail);
    return ResponseEntity.ok(newReview);
  }
}