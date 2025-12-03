package com.student.myspringboot;

import com.github.javafaker.Faker;
import com.student.myspringboot.entity.Category;
import com.student.myspringboot.entity.Product;
import com.student.myspringboot.entity.User;
import com.student.myspringboot.repository.CategoryRepository;
import com.student.myspringboot.repository.ProductRepository;
import com.student.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Locale;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Load test users if not already present
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@example.com");
            admin.setPhone("+380501234567");
            admin.setImage("https://i.pravatar.cc/150?img=1");
            admin.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(admin);

            User testUser = new User();
            testUser.setFirstName("Test");
            testUser.setLastName("User");
            testUser.setEmail("user@example.com");
            testUser.setPhone("+380509876543");
            testUser.setImage("https://i.pravatar.cc/150?img=2");
            testUser.setPassword(passwordEncoder.encode("user"));
            userRepository.save(testUser);
        }

        if (productRepository.count() > 0) return; // don't reseed

        // Create categories
        Category electronics = new Category(null, "Електроніка");
        Category clothing = new Category(null, "Одяг");
        Category books = new Category(null, "Книги");
        Category home = new Category(null, "Для дому");
        Category sports = new Category(null, "Спорт");
        
        categoryRepository.save(electronics);
        categoryRepository.save(clothing);
        categoryRepository.save(books);
        categoryRepository.save(home);
        categoryRepository.save(sports);
        
        Category[] categories = {electronics, clothing, books, home, sports};
        
        // Sample images from placeholder services
        String[] images = {
            "https://picsum.photos/400/300?random=1",
            "https://picsum.photos/400/300?random=2",
            "https://picsum.photos/400/300?random=3",
            "https://picsum.photos/400/300?random=4",
            "https://picsum.photos/400/300?random=5",
            "https://picsum.photos/400/300?random=6",
            "https://picsum.photos/400/300?random=7",
            "https://picsum.photos/400/300?random=8",
            "https://picsum.photos/400/300?random=9",
            "https://picsum.photos/400/300?random=10"
        };

        Faker faker = new Faker(Locale.ENGLISH);
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            Product p = new Product();
            p.setName(faker.commerce().productName());
            p.setDescription(faker.lorem().sentence(10));
            p.setPrice(new BigDecimal(faker.commerce().price().replaceAll(",", "")));
            p.setSku("SKU-" + faker.number().digits(8));
            p.setQuantity(faker.number().numberBetween(0, 200));
            p.setImageUrl(images[random.nextInt(images.length)]);
            p.setCategory(categories[random.nextInt(categories.length)]);
            p.setCreatedAt(Instant.now());
            productRepository.save(p);
        }
    }
}
