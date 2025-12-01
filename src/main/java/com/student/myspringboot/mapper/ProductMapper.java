package com.student.myspringboot.mapper;

import com.student.myspringboot.dto.ProductDto;
import com.student.myspringboot.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toDto(Product entity);
    Product toEntity(ProductDto dto);
}
