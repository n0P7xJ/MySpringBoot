package com.student.myspringboot.controller.api;

import com.student.myspringboot.entity.City;
import com.student.myspringboot.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@Tag(name = "Cities", description = "API для управління містами")
public class CityApiController {

    private final CityService cityService;

    @GetMapping
    @Operation(summary = "Отримати всі міста", description = "Повертає список всіх міст")
    public ResponseEntity<List<City>> getAllCities(@RequestParam(required = false) String search) {
        List<City> cities;
        if (search != null && !search.isEmpty()) {
            cities = cityService.searchCities(search);
        } else {
            cities = cityService.getAllCities();
        }
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати місто за ID", description = "Повертає місто по ідентифікатору")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        City city = cityService.getCityById(id);
        return ResponseEntity.ok(city);
    }

    @GetMapping("/by-region")
    @Operation(summary = "Отримати міста за регіоном", description = "Повертає міста по назві регіону")
    public ResponseEntity<List<City>> getCitiesByRegion(@RequestParam String region) {
        List<City> cities = cityService.getCitiesByRegion(region);
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/by-country")
    @Operation(summary = "Отримати міста за країною", description = "Повертає міста по назві країни")
    public ResponseEntity<List<City>> getCitiesByCountry(@RequestParam String country) {
        List<City> cities = cityService.getCitiesByCountry(country);
        return ResponseEntity.ok(cities);
    }

    @PostMapping
    @Operation(summary = "Створити нове місто", description = "Створює нове місто в базі даних")
    public ResponseEntity<City> createCity(@Valid @RequestBody City city) {
        City savedCity = cityService.saveCity(city);
        return ResponseEntity.ok(savedCity);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити місто", description = "Оновлює існуюче місто")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @Valid @RequestBody City city) {
        city.setId(id);
        City updatedCity = cityService.saveCity(city);
        return ResponseEntity.ok(updatedCity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити місто", description = "Видаляє місто з бази даних")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
