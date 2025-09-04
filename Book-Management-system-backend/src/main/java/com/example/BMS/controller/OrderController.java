package com.example.BMS.controller;

import com.example.BMS.dto.OrderRequestDto;
import com.example.BMS.model.BookOrder;
import com.example.BMS.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping
  public ResponseEntity<BookOrder> createOrder(@RequestBody OrderRequestDto orderRequest,
      Authentication authentication) {
    String userEmail = authentication.getName();
    BookOrder createdOrder = orderService.createOrder(orderRequest, userEmail);
    return ResponseEntity.ok(createdOrder);
  }
}