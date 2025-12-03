package com.student.myspringboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Назва міста обов'язкова")
    @Size(min = 2, max = 100, message = "Назва міста: 2-100 символів")
    @Column(nullable = false)
    private String name;

    @Size(max = 100, message = "Назва регіону: максимум 100 символів")
    private String region;

    @Size(max = 100, message = "Назва країни: максимум 100 символів")
    private String country;

    private Integer population;

    @Column(name = "postal_code")
    private String postalCode;

    private Double latitude;

    private Double longitude;
}
