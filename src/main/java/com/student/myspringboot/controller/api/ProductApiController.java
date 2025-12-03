package com.student.myspringboot.controller.api;

import com.student.myspringboot.dto.ProductDto;
import com.student.myspringboot.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "API для роботи з продуктами")
public class ProductApiController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Отримати всі продукти", description = "Повертає список всіх продуктів або результати пошуку")
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @Parameter(description = "Пошуковий запит для фільтрації продуктів")
            @RequestParam(required = false) String search) {
        List<ProductDto> products = productService.searchProducts(search);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    @Operation(summary = "Створити новий продукт", description = "Створює новий продукт в базі даних")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto savedProduct = productService.save(productDto);
        return ResponseEntity.ok(savedProduct);
    }
}
