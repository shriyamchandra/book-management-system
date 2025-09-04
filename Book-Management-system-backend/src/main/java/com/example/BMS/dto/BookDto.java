// In src/main/java/com/example/BMS/dto/BookDto.java
package com.example.BMS.dto;

import lombok.Data;
import java.math.BigDecimal; // <-- FIX THE TYPO HERE

@Data
public class BookDto {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private BigDecimal price;
}