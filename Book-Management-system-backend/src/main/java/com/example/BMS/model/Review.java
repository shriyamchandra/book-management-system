package com.example.BMS.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "reviews")
@Data
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewId;

  private String headline;
  private String comment;
  private double rating;
  private LocalDate reviewOn;

  // A review is written by one customer
  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
  private Customer customer;

  // A review is for one book
  @ManyToOne
  @JoinColumn(name = "book_id", referencedColumnName = "bookId")
  private Book book;
}