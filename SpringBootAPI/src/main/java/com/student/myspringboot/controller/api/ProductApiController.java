package com.student.myspringboot.controller.api;

import com.student.myspringboot.dto.ProductDto;
import com.student.myspringboot.mapper.ProductMapper;
import com.student.myspringboot.repository.ProductRepository;
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
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

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

    @GetMapping("/{id}")
    @Operation(summary = "Отримати продукт за ID", description = "Повертає продукт по ідентифікатору")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити продукт", description = "Оновлює існуючий продукт")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productDto.setId(id);
        ProductDto updatedProduct = productService.save(productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити продукт", description = "Видаляє продукт з бази даних")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
