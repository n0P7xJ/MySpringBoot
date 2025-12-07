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
            createCity("Київ", "Київська область", "Україна", 2962180, "01001", 50.4501, 30.5234, "Capital and largest city of Ukraine", "https://images.unsplash.com/photo-1543699539-33a389c5b1bc");
            createCity("Харків", "Харківська область", "Україна", 1433886, "61000", 49.9935, 36.2304, "Second largest city, major industrial and educational center", "https://images.unsplash.com/photo-1618588856351-241978c0b3c4");
            createCity("Одеса", "Одеська область", "Україна", 1017699, "65000", 46.4825, 30.7233, "Major port city on the Black Sea coast", "https://images.unsplash.com/photo-1625503252413-5825bfe5721e");
            createCity("Дніпро", "Дніпропетровська область", "Україна", 990724, "49000", 48.4647, 35.0462, "Important industrial center in central Ukraine", "https://images.unsplash.com/photo-1606220838315-056192d5e927");
            createCity("Львів", "Львівська область", "Україна", 724713, "79000", 49.8397, 24.0297, "Cultural capital of Ukraine, UNESCO World Heritage Site", "https://images.unsplash.com/photo-1558618666-fcd25c85cd64");
            createCity("Запоріжжя", "Запорізька область", "Україна", 750685, "69000", 47.8388, 35.1396, "Major industrial city on the Dnieper River", "https://images.unsplash.com/photo-1534237886190-ced735ca4b73");
            createCity("Кривий Ріг", "Дніпропетровська область", "Україна", 612750, "50000", 47.9086, 33.3432, "One of the largest mining cities in Ukraine", "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab");
            createCity("Миколаїв", "Миколаївська область", "Україна", 476101, "54000", 46.9750, 31.9946, "Historic shipbuilding center", "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b");
            createCity("Маріуполь", "Донецька область", "Україна", 431859, "87500", 47.0956, 37.5431, "Important port city on the Azov Sea", "https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1");
            createCity("Вінниця", "Вінницька область", "Україна", 372432, "21000", 49.2331, 28.4682, "Major city in central Ukraine", "https://images.unsplash.com/photo-1480714378408-67cf0d13bc1b");
            createCity("Полтава", "Полтавська область", "Україна", 283402, "36000", 49.5883, 34.5514, "Historic city, famous for the Battle of Poltava", "https://images.unsplash.com/photo-1449824913935-59a10b8d2000");
            createCity("Чернігів", "Чернігівська область", "Україна", 285234, "14000", 51.4982, 31.2893, "One of the oldest cities in Ukraine", "https://images.unsplash.com/photo-1513407030348-c983a97b98d8");
            createCity("Черкаси", "Черкаська область", "Україна", 275987, "18000", 49.4285, 32.0614, "City on the Dnieper River", "https://images.unsplash.com/photo-1469854523086-cc02fe5d8800");
            createCity("Житомир", "Житомирська область", "Україна", 263507, "10000", 50.2547, 28.6587, "Historic city in northern Ukraine", "https://images.unsplash.com/photo-1444723121867-7a241cacace9");
            createCity("Суми", "Сумська область", "Україна", 259660, "40000", 50.9077, 34.7981, "Industrial city in northeastern Ukraine", "https://images.unsplash.com/photo-1477959858617-67f85cf4f1df");
            createCity("Хмельницький", "Хмельницька область", "Україна", 274582, "29000", 49.4229, 26.9871, "Regional center in western Ukraine", "https://images.unsplash.com/photo-1496564203457-11bb12075d90");
            createCity("Рівне", "Рівненська область", "Україна", 245289, "33000", 50.6199, 26.2516, "City in northwestern Ukraine", "https://images.unsplash.com/photo-1519681393784-d120267933ba");
            createCity("Івано-Франківськ", "Івано-Франківська область", "Україна", 238196, "76000", 48.9226, 24.7111, "Gateway to the Carpathian Mountains", "https://images.unsplash.com/photo-1506905925346-21bda4d32df4");
            createCity("Тернопіль", "Тернопільська область", "Україна", 225238, "46000", 49.5535, 25.5948, "City in western Ukraine", "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05");
            createCity("Луцьк", "Волинська область", "Україна", 217197, "43000", 50.7472, 25.3254, "Historic city with medieval castle", "https://images.unsplash.com/photo-1467269204594-9661b134dd2b");
            createCity("Ужгород", "Закарпатська область", "Україна", 115163, "88000", 48.6208, 22.2879, "Westernmost city of Ukraine, multicultural center", "https://images.unsplash.com/photo-1441974231531-c6227db76b6e");
            createCity("Чернівці", "Чернівецька область", "Україна", 264298, "58000", 48.2921, 25.9358, "Cultural center, UNESCO World Heritage Site", "https://images.unsplash.com/photo-1501594907352-04cda38ebc29");
            createCity("Кременчук", "Полтавська область", "Україна", 217710, "39600", 49.0659, 33.4167, "Industrial city on the Dnieper River", "https://images.unsplash.com/photo-1473496169904-658ba7c44d8a");
            createCity("Кропивницький", "Кіровоградська область", "Україна", 222695, "25000", 48.5079, 32.2623, "Regional center in central Ukraine", "https://images.unsplash.com/photo-1452457807411-4979b707c5be");
            createCity("Херсон", "Херсонська область", "Україна", 283649, "73000", 46.6354, 32.6169, "Port city near the Black Sea", "https://images.unsplash.com/photo-1418065460487-3e41a6c84dc5");
            createCity("Біла Церква", "Київська область", "Україна", 211080, "09100", 49.7878, 30.1119, "Historic city south of Kyiv", "https://images.unsplash.com/photo-1472214103451-9374bd1c798e");
            createCity("Бердянськ", "Запорізька область", "Україна", 107928, "71100", 46.7554, 36.7985, "Resort city on the Azov Sea", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e");
            createCity("Ялта", "Крим", "Україна", 76746, "98600", 44.4952, 34.1661, "Famous resort city on the Black Sea", "https://images.unsplash.com/photo-1506905925346-21bda4d32df4");
            createCity("Мукачево", "Закарпатська область", "Україна", 85796, "89600", 48.4394, 22.7183, "Historic city in Transcarpathia", "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429");
            createCity("Ковель", "Волинська область", "Україна", 68796, "45000", 51.2153, 24.7086, "Important railway junction", "https://images.unsplash.com/photo-1475776408506-9a5371e7a068");
        }

        if (productRepository.count() > 0) return; // don't reseed

        // Create categories
        Category electronics = new Category(null, "Electronics");
        Category clothing = new Category(null, "Clothing");
        Category books = new Category(null, "Books");
        Category home = new Category(null, "Home & Garden");
        Category sports = new Category(null, "Sports & Outdoors");
        
        categoryRepository.save(electronics);
        categoryRepository.save(clothing);
        categoryRepository.save(books);
        categoryRepository.save(home);
        categoryRepository.save(sports);

        // Create real products
        // Electronics
        createProduct("iPhone 15 Pro", "Latest Apple smartphone with A17 Pro chip, titanium design, and advanced camera system", 
            new BigDecimal("999.99"), "SKU-IP15PRO", 50, "https://images.unsplash.com/photo-1695048133142-1a20484d2569?w=400", electronics);
        
        createProduct("MacBook Air M3", "Thin and light laptop with Apple M3 chip, 13.6-inch display, and all-day battery life", 
            new BigDecimal("1299.99"), "SKU-MBA-M3", 30, "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400", electronics);
        
        createProduct("Samsung Galaxy S24 Ultra", "Premium Android smartphone with S Pen, 200MP camera, and AI features", 
            new BigDecimal("1199.99"), "SKU-SGS24U", 40, "https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=400", electronics);
        
        createProduct("Sony WH-1000XM5", "Industry-leading noise canceling wireless headphones with exceptional sound quality", 
            new BigDecimal("399.99"), "SKU-SONYWH5", 75, "https://images.unsplash.com/photo-1546435770-a3e426bf472b?w=400", electronics);
        
        createProduct("iPad Air", "Powerful tablet with M1 chip, perfect for creativity and productivity", 
            new BigDecimal("599.99"), "SKU-IPADAIR", 60, "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=400", electronics);

        createProduct("Dell XPS 15", "High-performance laptop with Intel Core i7, 16GB RAM, and stunning display", 
            new BigDecimal("1499.99"), "SKU-DELLXPS15", 25, "https://images.unsplash.com/photo-1593642632823-8f785ba67e45?w=400", electronics);

        createProduct("AirPods Pro 2", "Active noise cancellation wireless earbuds with spatial audio", 
            new BigDecimal("249.99"), "SKU-AIRPODSP2", 100, "https://images.unsplash.com/photo-1606841837239-c5a1a4a07af7?w=400", electronics);

        createProduct("LG OLED TV 55\"", "4K OLED television with perfect blacks and stunning color accuracy", 
            new BigDecimal("1799.99"), "SKU-LGOLED55", 15, "https://images.unsplash.com/photo-1593359677879-a4bb92f829d1?w=400", electronics);

        // Clothing
        createProduct("Levi's 501 Original Jeans", "Classic straight fit jeans, the original since 1873", 
            new BigDecimal("89.99"), "SKU-LEVIS501", 150, "https://images.unsplash.com/photo-1542272604-787c3835535d?w=400", clothing);

        createProduct("Nike Air Max 90", "Iconic sneakers with visible Air cushioning and timeless style", 
            new BigDecimal("129.99"), "SKU-NIKEAM90", 200, "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400", clothing);

        createProduct("The North Face Jacket", "Waterproof outdoor jacket perfect for hiking and camping", 
            new BigDecimal("199.99"), "SKU-TNFJACKET", 80, "https://images.unsplash.com/photo-1551028719-00167b16eac5?w=400", clothing);

        createProduct("Adidas Ultraboost", "Premium running shoes with responsive cushioning", 
            new BigDecimal("179.99"), "SKU-ADIDASUB", 120, "https://images.unsplash.com/photo-1608231387042-66d1773070a5?w=400", clothing);

        createProduct("Patagonia Fleece", "Sustainable fleece jacket made from recycled materials", 
            new BigDecimal("149.99"), "SKU-PATAFLEECE", 90, "https://images.unsplash.com/photo-1521223890158-f9f7c3d5d504?w=400", clothing);

        // Books
        createProduct("The Art of Computer Programming", "Donald Knuth's comprehensive guide to programming fundamentals", 
            new BigDecimal("79.99"), "SKU-TAOCP", 40, "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=400", books);

        createProduct("Clean Code", "Robert C. Martin's guide to writing maintainable and elegant code", 
            new BigDecimal("44.99"), "SKU-CLEANCODE", 100, "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400", books);

        createProduct("Design Patterns", "Gang of Four's essential guide to software design patterns", 
            new BigDecimal("54.99"), "SKU-DESIGNPAT", 60, "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400", books);

        createProduct("Sapiens", "Yuval Noah Harari's brief history of humankind", 
            new BigDecimal("24.99"), "SKU-SAPIENS", 150, "https://images.unsplash.com/photo-1589998059171-988d887df646?w=400", books);

        // Home & Garden
        createProduct("Dyson V15 Detect", "Cordless vacuum with laser detection and LCD screen", 
            new BigDecimal("649.99"), "SKU-DYSONV15", 35, "https://images.unsplash.com/photo-1558317374-067fb5f30001?w=400", home);

        createProduct("Instant Pot Duo", "7-in-1 multi-cooker: pressure cooker, slow cooker, and more", 
            new BigDecimal("99.99"), "SKU-INSTANTPOT", 120, "https://images.unsplash.com/photo-1585515320310-259814833e62?w=400", home);

        createProduct("Philips Hue Starter Kit", "Smart LED lighting system with app and voice control", 
            new BigDecimal("199.99"), "SKU-PHILIPSHUE", 70, "https://images.unsplash.com/photo-1558089687-e5d5d03ae1b0?w=400", home);

        createProduct("KitchenAid Stand Mixer", "Professional 5-quart mixer for all your baking needs", 
            new BigDecimal("379.99"), "SKU-KITCHENAID", 50, "https://images.unsplash.com/photo-1578643463396-0997cb5328c1?w=400", home);

        // Sports & Outdoors
        createProduct("Peloton Bike", "Interactive indoor cycling bike with live and on-demand classes", 
            new BigDecimal("1495.00"), "SKU-PELOTON", 20, "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=400", sports);

        createProduct("Yeti Rambler", "Insulated stainless steel tumbler keeps drinks cold/hot for hours", 
            new BigDecimal("34.99"), "SKU-YETIRAMB", 200, "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=400", sports);

        createProduct("Coleman Camping Tent", "6-person weatherproof tent perfect for family camping", 
            new BigDecimal("189.99"), "SKU-COLEMANT6", 45, "https://images.unsplash.com/photo-1478131143081-80f7f84ca84d?w=400", sports);

        createProduct("Fitbit Charge 6", "Advanced fitness tracker with heart rate monitoring and GPS", 
            new BigDecimal("159.99"), "SKU-FITBITC6", 100, "https://images.unsplash.com/photo-1575311373937-040b8e1fd5b6?w=400", sports);
    }

    private void createProduct(String name, String description, BigDecimal price, String sku, 
                               Integer quantity, String imageUrl, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setSku(sku);
        product.setQuantity(quantity);
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        product.setCreatedAt(Instant.now());
        productRepository.save(product);
    }

    private void createCity(String name, String region, String country, Integer population, 
                           String postalCode, Double latitude, Double longitude, String description, String imageUrl) {
        City city = new City();
        city.setName(name);
        city.setRegion(region);
        city.setCountry(country);
        city.setPopulation(population);
        city.setPostalCode(postalCode);
        city.setLatitude(latitude);
        city.setLongitude(longitude);
        city.setDescription(description);
        city.setImageUrl(imageUrl);
        cityRepository.save(city);
    }
}
