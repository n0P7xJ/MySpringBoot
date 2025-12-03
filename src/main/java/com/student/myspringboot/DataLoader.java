package com.student.myspringboot;

import com.github.javafaker.Faker;
import com.student.myspringboot.entity.Category;
import com.student.myspringboot.entity.City;
import com.student.myspringboot.entity.Product;
import com.student.myspringboot.entity.User;
import com.student.myspringboot.repository.CategoryRepository;
import com.student.myspringboot.repository.CityRepository;
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
    private final CityRepository cityRepository;
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

        // Load cities if not already present
        if (cityRepository.count() == 0) {
            // Українські міста
            cityRepository.save(new City(null, "Київ", "Київська область", "Україна", 2962180, "01001", 50.4501, 30.5234));
            cityRepository.save(new City(null, "Харків", "Харківська область", "Україна", 1433886, "61000", 49.9935, 36.2304));
            cityRepository.save(new City(null, "Одеса", "Одеська область", "Україна", 1017699, "65000", 46.4825, 30.7233));
            cityRepository.save(new City(null, "Дніпро", "Дніпропетровська область", "Україна", 990724, "49000", 48.4647, 35.0462));
            cityRepository.save(new City(null, "Львів", "Львівська область", "Україна", 724713, "79000", 49.8397, 24.0297));
            cityRepository.save(new City(null, "Запоріжжя", "Запорізька область", "Україна", 750685, "69000", 47.8388, 35.1396));
            cityRepository.save(new City(null, "Кривий Ріг", "Дніпропетровська область", "Україна", 612750, "50000", 47.9086, 33.3432));
            cityRepository.save(new City(null, "Миколаїв", "Миколаївська область", "Україна", 476101, "54000", 46.9750, 31.9946));
            cityRepository.save(new City(null, "Маріуполь", "Донецька область", "Україна", 431859, "87500", 47.0956, 37.5431));
            cityRepository.save(new City(null, "Вінниця", "Вінницька область", "Україна", 372432, "21000", 49.2331, 28.4682));
            cityRepository.save(new City(null, "Полтава", "Полтавська область", "Україна", 283402, "36000", 49.5883, 34.5514));
            cityRepository.save(new City(null, "Чернігів", "Чернігівська область", "Україна", 285234, "14000", 51.4982, 31.2893));
            cityRepository.save(new City(null, "Черкаси", "Черкаська область", "Україна", 275987, "18000", 49.4285, 32.0614));
            cityRepository.save(new City(null, "Житомир", "Житомирська область", "Україна", 263507, "10000", 50.2547, 28.6587));
            cityRepository.save(new City(null, "Суми", "Сумська область", "Україна", 259660, "40000", 50.9077, 34.7981));
            cityRepository.save(new City(null, "Хмельницький", "Хмельницька область", "Україна", 274582, "29000", 49.4229, 26.9871));
            cityRepository.save(new City(null, "Рівне", "Рівненська область", "Україна", 245289, "33000", 50.6199, 26.2516));
            cityRepository.save(new City(null, "Івано-Франківськ", "Івано-Франківська область", "Україна", 238196, "76000", 48.9226, 24.7111));
            cityRepository.save(new City(null, "Тернопіль", "Тернопільська область", "Україна", 225238, "46000", 49.5535, 25.5948));
            cityRepository.save(new City(null, "Луцьк", "Волинська область", "Україна", 217197, "43000", 50.7472, 25.3254));
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
