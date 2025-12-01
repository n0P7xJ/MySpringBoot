package com.student.myspringboot;

import com.github.javafaker.Faker;
import com.student.myspringboot.entity.Product;
import com.student.myspringboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() > 0) return; // don't reseed

        Faker faker = new Faker(new Locale("en"));

        for (int i = 0; i < 50; i++) {
            Product p = new Product();
            p.setName(faker.commerce().productName());
            p.setDescription(faker.lorem().sentence(10));
            p.setPrice(new BigDecimal(faker.commerce().price().replaceAll(",", "")));
            p.setSku("SKU-" + faker.number().digits(8));
            p.setQuantity(faker.number().numberBetween(0, 200));
            p.setCreatedAt(Instant.now());
            productRepository.save(p);
        }
    }
}
