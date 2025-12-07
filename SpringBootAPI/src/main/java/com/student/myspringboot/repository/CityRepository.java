package com.student.myspringboot.repository;

import com.student.myspringboot.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);
    List<City> findByRegion(String region);
    List<City> findByCountry(String country);
    List<City> findByNameContainingIgnoreCase(String name);
}
