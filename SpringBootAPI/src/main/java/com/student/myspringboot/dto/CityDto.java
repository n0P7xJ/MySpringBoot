package com.student.myspringboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDto {
    private Long id;

    @NotBlank(message = "Назва міста обов'язкова")
    @Size(min = 2, max = 100, message = "Назва міста: 2-100 символів")
    private String name;

    @Size(max = 100, message = "Назва регіону: максимум 100 символів")
    private String region;

    @Size(max = 100, message = "Назва країни: максимум 100 символів")
    private String country;

    private Integer population;
    private String postalCode;
    private Double latitude;
    private Double longitude;
    private String description;

    // ID фотографій для прив'язки
    private List<Long> photoIds;
}
