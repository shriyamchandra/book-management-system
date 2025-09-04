package com.example.BMS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List; // Import List

@Entity
@Table(name = "customers")
@Data
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long customerId;

  private String fullName;
  private String mobileNumber;
  private LocalDate registerOn;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id", referencedColumnName = "addressId")
  private Address address;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  // A customer can write many reviews
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  @JsonIgnore // Important to prevent infinite loops
  private List<Review> reviews;
}