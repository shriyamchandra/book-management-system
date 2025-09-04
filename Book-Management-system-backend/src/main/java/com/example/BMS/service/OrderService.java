package com.example.BMS.service;

import com.example.BMS.dto.OrderItemDto;
import com.example.BMS.dto.OrderRequestDto;
import com.example.BMS.exception.ResourceNotFoundException;
import com.example.BMS.model.*;
import com.example.BMS.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private BookOrderRepository bookOrderRepository;

  @Transactional // Ensures the entire method is atomic
  public BookOrder createOrder(OrderRequestDto orderRequest, String userEmail) {
    // 1. Find the customer placing the order
    User user = userRepository.findByEmail(userEmail)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));
    Customer customer = user.getCustomer();

    // 2. Create the main order object
    BookOrder bookOrder = new BookOrder();
    bookOrder.setCustomer(customer);
    bookOrder.setOrderDate(LocalDate.now());
    bookOrder.setStatus("PENDING");
    bookOrder.setPaymentMethod(orderRequest.getPaymentMethod());
    bookOrder.setRecipientName(orderRequest.getRecipientName());
    bookOrder.setRecipientPhone(orderRequest.getRecipientPhone());

    List<OrderDetails> orderDetailsList = new ArrayList<>();
    double totalAmount = 0.0;

    // 3. Process each item in the order
    for (OrderItemDto itemDto : orderRequest.getItems()) {
      Book book = bookRepository.findById(itemDto.getBookId())
          .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + itemDto.getBookId()));

      // Check stock
      if (book.getQuantityInStock() < itemDto.getQuantity()) {
        throw new IllegalArgumentException("Not enough stock for book: " + book.getTitle());
      }

      // Decrement and persist stock
      book.setQuantityInStock(book.getQuantityInStock() - itemDto.getQuantity());
      bookRepository.save(book); // ✅ persist stock change

      // Create order detail
      OrderDetails detail = new OrderDetails();
      detail.setBook(book);
      detail.setQuantity(itemDto.getQuantity());
      detail.setSubtotal(book.getPrice() * itemDto.getQuantity());
      detail.setBookOrder(bookOrder); // Link to main order

      orderDetailsList.add(detail);
      totalAmount += detail.getSubtotal();
    }

    // 4. Save order
    bookOrder.setOrderTotal(totalAmount);
    bookOrder.setOrderDetails(orderDetailsList);

    return bookOrderRepository.save(bookOrder);
  }
}