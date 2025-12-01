package com.student.myspringboot.service;

import com.student.myspringboot.dto.ProductDto;
import com.student.myspringboot.entity.Product;
import com.student.myspringboot.mapper.ProductMapper;
import com.student.myspringboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDto save(ProductDto dto) {
        Product entity = productMapper.toEntity(dto);
        Product saved = productRepository.save(entity);
        return productMapper.toDto(saved);
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}
