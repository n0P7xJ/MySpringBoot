package com.student.myspringboot.service;

import com.student.myspringboot.dto.CityDto;
import com.student.myspringboot.entity.City;
import com.student.myspringboot.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CityService {

    private final CityRepository cityRepository;
    private final PhotoService photoService;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Місто з ID " + id + " не знайдено"));
    }

    public City getCityByName(String name) {
        return cityRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Місто '" + name + "' не знайдено"));
    }

    public List<City> getCitiesByRegion(String region) {
        return cityRepository.findByRegion(region);
    }

    public List<City> getCitiesByCountry(String country) {
        return cityRepository.findByCountry(country);
    }

    public List<City> searchCities(String query) {
        return cityRepository.findByNameContainingIgnoreCase(query);
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public City saveCityWithPhotos(CityDto cityDto) {
        City city = new City();
        city.setId(cityDto.getId());
        city.setName(cityDto.getName());
        city.setRegion(cityDto.getRegion());
        city.setCountry(cityDto.getCountry());
        city.setPopulation(cityDto.getPopulation());
        city.setPostalCode(cityDto.getPostalCode());
        city.setLatitude(cityDto.getLatitude());
        city.setLongitude(cityDto.getLongitude());
        city.setDescription(cityDto.getDescription());

        // Спочатку зберігаємо місто
        City savedCity = cityRepository.save(city);

        // Потім прив'язуємо фото
        if (cityDto.getPhotoIds() != null && !cityDto.getPhotoIds().isEmpty()) {
            photoService.attachPhotosToCity(cityDto.getPhotoIds(), savedCity);
        }

        return savedCity;
    }

    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new IllegalArgumentException("Місто з ID " + id + " не знайдено");
        }
        cityRepository.deleteById(id);
    }
}
