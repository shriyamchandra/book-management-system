package com.example.BMS.dto;

import lombok.Data;

@Data
public class OrderItemDto {
  private Long bookId;
  private int quantity;
}