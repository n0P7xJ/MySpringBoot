package com.student.myspringboot.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private Integer quantity;
    private Instant createdAt;
}
