package com.student.myspringboot.mapper;

import com.student.myspringboot.dto.ProductDto;
import com.student.myspringboot.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "categoryName", source = "category.name")
    ProductDto toDto(Product entity);

    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDto dto);
}
