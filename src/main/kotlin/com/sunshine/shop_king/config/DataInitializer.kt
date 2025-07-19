package com.sunshine.shop_king.config

import com.sunshine.shop_king.entity.*
import com.sunshine.shop_king.repository.*
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DataInitializer(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (userRepository.count() == 0L) {
            initializeData()
        }
    }

    private fun initializeData() {
        // 1. 사용자 데이터 생성
        val adminUser = User(
            email = "admin@shopking.com",
            password = passwordEncoder.encode("admin123"),
            name = "관리자",
            phoneNumber = "010-1234-5678",
            address = "서울시 강남구 테헤란로 123",
            role = UserRole.ADMIN
        )

        val customer1 = User(
            email = "customer1@gmail.com",
            password = passwordEncoder.encode("password123"),
            name = "김고객",
            phoneNumber = "010-2345-6789",
            address = "서울시 마포구 홍대로 456",
            role = UserRole.CUSTOMER
        )

        val customer2 = User(
            email = "customer2@gmail.com",
            password = passwordEncoder.encode("password123"),
            name = "이구매",
            phoneNumber = "010-3456-7890",
            address = "부산시 해운대구 마린시티 789",
            role = UserRole.CUSTOMER
        )

        val savedAdmin = userRepository.save(adminUser)
        val savedCustomer1 = userRepository.save(customer1)
        val savedCustomer2 = userRepository.save(customer2)

        // 2. 카테고리 데이터 생성
        val electronics = Category(
            name = "전자제품",
            description = "스마트폰, 노트북, 가전제품 등"
        )

        val fashion = Category(
            name = "패션",
            description = "의류, 신발, 액세서리 등"
        )

        val books = Category(
            name = "도서",
            description = "소설, 전문서적, 만화 등"
        )

        val sports = Category(
            name = "스포츠",
            description = "운동용품, 스포츠웨어 등"
        )

        val home = Category(
            name = "홈&리빙",
            description = "가구, 인테리어 소품, 생활용품 등"
        )

        val savedElectronics = categoryRepository.save(electronics)
        val savedFashion = categoryRepository.save(fashion)
        val savedBooks = categoryRepository.save(books)
        val savedSports = categoryRepository.save(sports)
        val savedHome = categoryRepository.save(home)

        // 3. 상품 데이터 생성
        val products = listOf(
            // 전자제품
            Product(
                name = "iPhone 15 Pro",
                description = "Apple의 최신 프리미엄 스마트폰",
                price = BigDecimal("1299000"),
                stockQuantity = 50,
                imageUrl = "https://example.com/iphone15pro.jpg",
                category = savedElectronics
            ),
            Product(
                name = "MacBook Air M3",
                description = "13인치 MacBook Air, M3 칩 탑재",
                price = BigDecimal("1590000"),
                stockQuantity = 30,
                imageUrl = "https://example.com/macbook-air-m3.jpg",
                category = savedElectronics
            ),
            Product(
                name = "Samsung Galaxy S24",
                description = "삼성 갤럭시 S24 스마트폰",
                price = BigDecimal("999000"),
                stockQuantity = 40,
                imageUrl = "https://example.com/galaxy-s24.jpg",
                category = savedElectronics
            ),
            Product(
                name = "LG 올레드 TV 55인치",
                description = "4K OLED 스마트 TV",
                price = BigDecimal("1890000"),
                stockQuantity = 15,
                imageUrl = "https://example.com/lg-oled-tv.jpg",
                category = savedElectronics
            ),

            // 패션
            Product(
                name = "나이키 에어맥스 270",
                description = "나이키 운동화 에어맥스 270",
                price = BigDecimal("149000"),
                stockQuantity = 100,
                imageUrl = "https://example.com/nike-airmax270.jpg",
                category = savedFashion
            ),
            Product(
                name = "유니클로 히트텍 이너웨어",
                description = "발열 기능성 이너웨어",
                price = BigDecimal("19900"),
                stockQuantity = 200,
                imageUrl = "https://example.com/uniqlo-heattech.jpg",
                category = savedFashion
            ),
            Product(
                name = "자라 코트",
                description = "겨울 롱코트, 울 혼방",
                price = BigDecimal("89000"),
                stockQuantity = 50,
                imageUrl = "https://example.com/zara-coat.jpg",
                category = savedFashion
            ),

            // 도서
            Product(
                name = "클린 코드",
                description = "로버트 C. 마틴 저, 프로그래밍 필독서",
                price = BigDecimal("31500"),
                stockQuantity = 80,
                imageUrl = "https://example.com/clean-code.jpg",
                category = savedBooks
            ),
            Product(
                name = "코틀린 인 액션",
                description = "코틀린 프로그래밍 가이드북",
                price = BigDecimal("36000"),
                stockQuantity = 60,
                imageUrl = "https://example.com/kotlin-in-action.jpg",
                category = savedBooks
            ),
            Product(
                name = "데미안",
                description = "헤르만 헤세 대표작",
                price = BigDecimal("12600"),
                stockQuantity = 70,
                imageUrl = "https://example.com/demian.jpg",
                category = savedBooks
            ),

            // 스포츠
            Product(
                name = "요가매트",
                description = "두께 6mm, 논슬립 요가매트",
                price = BigDecimal("29000"),
                stockQuantity = 150,
                imageUrl = "https://example.com/yoga-mat.jpg",
                category = savedSports
            ),
            Product(
                name = "덤벨 세트",
                description = "조절식 덤벨 20kg 세트",
                price = BigDecimal("89000"),
                stockQuantity = 30,
                imageUrl = "https://example.com/dumbbell-set.jpg",
                category = savedSports
            ),
            Product(
                name = "런닝화",
                description = "경량 러닝화, 쿠션 뛰어남",
                price = BigDecimal("129000"),
                stockQuantity = 80,
                imageUrl = "https://example.com/running-shoes.jpg",
                category = savedSports
            ),

            // 홈&리빙
            Product(
                name = "이케아 침대 프레임",
                description = "심플한 디자인의 퀸 사이즈 침대",
                price = BigDecimal("199000"),
                stockQuantity = 25,
                imageUrl = "https://example.com/ikea-bed.jpg",
                category = savedHome
            ),
            Product(
                name = "무인양품 수납박스",
                description = "천연 소재 수납박스 세트",
                price = BigDecimal("45000"),
                stockQuantity = 100,
                imageUrl = "https://example.com/muji-storage.jpg",
                category = savedHome
            ),
            Product(
                name = "공기청정기",
                description = "샤오미 공기청정기 3H",
                price = BigDecimal("169000"),
                stockQuantity = 40,
                imageUrl = "https://example.com/air-purifier.jpg",
                category = savedHome
            )
        )

        val savedProducts = productRepository.saveAll(products)

        // 4. 주문 데이터 생성 (간단한 버전)
        val order1 = Order(
            user = savedCustomer1,
            totalAmount = savedProducts[0].price.add(savedProducts[4].price),
            status = OrderStatus.DELIVERED,
            shippingAddress = savedCustomer1.address
        )

        val order2 = Order(
            user = savedCustomer2,
            totalAmount = savedProducts[7].price.multiply(BigDecimal("2")).add(savedProducts[10].price),
            status = OrderStatus.SHIPPED,
            shippingAddress = savedCustomer2.address
        )

        orderRepository.save(order1)
        orderRepository.save(order2)

        println("✅ 샘플 데이터가 성공적으로 초기화되었습니다!")
        println("👤 사용자: ${userRepository.count()}명")
        println("📁 카테고리: ${categoryRepository.count()}개")
        println("🛍️ 상품: ${productRepository.count()}개")
        println("📦 주문: ${orderRepository.count()}건")
    }
}