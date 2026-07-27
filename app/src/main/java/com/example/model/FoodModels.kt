package com.example.model

import com.example.R

data class Product(
    val id: String,
    val nameLine1: String,
    val nameLine2: String,
    val fullName: String = "$nameLine1 $nameLine2",
    val price: Double,
    val rating: Double = 4.8,
    val prepTime: String = "20 mins",
    val description: String,
    val imageRes: Int = R.drawable.img_burger_cheeseburger_1785072915476,
    val videoResId: Int? = null,
    val isFavorite: Boolean = false,
    val category: String = "Burgers",
    val spicyLevel: Int = 1 // 1 to 3
)

data class Topping(
    val id: String,
    val name: String,
    val price: Double = 1.00,
    val iconEmoji: String,
    val isSelected: Boolean = false
)

data class SideOption(
    val id: String,
    val name: String,
    val price: Double = 2.50,
    val iconEmoji: String,
    val isSelected: Boolean = false
)

data class PaymentMethod(
    val id: String,
    val brand: String,
    val cardType: String,
    val cardNumber: String,
    val logoType: String = "mastercard", // mastercard or visa
    val isSelected: Boolean = false
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: String = "Just now"
)

object SampleData {
    val sampleProducts = listOf(
        Product(
            id = "1",
            nameLine1 = "Cheeseburger",
            nameLine2 = "Wendy's Burger",
            price = 8.24,
            rating = 4.9,
            prepTime = "26 mins",
            description = "The Cheeseburger Wendy's Burger is a classic fast food burger that packs a punch of flavor in every bite. Made with a juicy beef patty cooked to perfection, it's topped with melted American cheese, crispy lettuce, ripe tomato, and crunchy pickles.",
            imageRes = R.drawable.img_burger_cheeseburger_1785072915476,
            videoResId = R.raw.v1,
            isFavorite = true,
            category = "Burgers",
            spicyLevel = 1
        ),
        Product(
            id = "2",
            nameLine1 = "Supreme Pizza",
            nameLine2 = "Pepperoni & Cheese",
            price = 14.99,
            rating = 4.9,
            prepTime = "22 mins",
            description = "Hot oven-baked pizza with crispy crust, rich tomato passata sauce, double layer of spicy pepperoni, mozzarella cheese and fresh basil leaves.",
            imageRes = R.drawable.img_burger_cheeseburger_1785072915476,
            videoResId = R.raw.v2,
            isFavorite = false,
            category = "Pizza",
            spicyLevel = 2
        ),
        Product(
            id = "3",
            nameLine1 = "Crispy Fried",
            nameLine2 = "Chicken Bucket",
            price = 18.50,
            rating = 4.9,
            prepTime = "18 mins",
            description = "Golden, crunchy fried chicken marinated in secret blend of 11 herbs and spices. Deep fried to juicy perfection with honey mustard dip.",
            imageRes = R.drawable.img_burger_chicken_1785072942831,
            videoResId = R.raw.v3,
            isFavorite = true,
            category = "Chicken",
            spicyLevel = 3
        ),
        Product(
            id = "4",
            nameLine1 = "Spicy Beef",
            nameLine2 = "Taco Supreme",
            price = 6.75,
            rating = 4.7,
            prepTime = "12 mins",
            description = "Crispy corn tortilla shell stuffed with seasoned minced beef, jalapeños, shredded cheddar cheese, diced tomatoes and creamy sour cream.",
            imageRes = R.drawable.img_burger_veggie_1785072930721,
            videoResId = R.raw.v4,
            isFavorite = false,
            category = "Tacos & Wraps",
            spicyLevel = 3
        ),
        Product(
            id = "5",
            nameLine1 = "Loaded Golden",
            nameLine2 = "French Fries",
            price = 5.49,
            rating = 4.8,
            prepTime = "10 mins",
            description = "Extra crispy skin-on potato fries smorgasbord with melted cheddar cheese sauce, crispy bacon bits, jalapeño slices and chopped spring onions.",
            imageRes = R.drawable.img_burger_cheeseburger_1785072915476,
            videoResId = R.raw.v5,
            isFavorite = true,
            category = "Sides & Fries",
            spicyLevel = 1
        ),
        Product(
            id = "6",
            nameLine1 = "Hamburger",
            nameLine2 = "Veggie Burger",
            price = 9.99,
            rating = 4.8,
            prepTime = "14 mins",
            description = "Enjoy our delicious Hamburger Veggie Burger, made with a savory blend of fresh vegetables and herbs, topped with crisp lettuce, juicy tomatoes, and tangy pickles.",
            imageRes = R.drawable.img_burger_veggie_1785072930721,
            videoResId = R.raw.v6,
            isFavorite = false,
            category = "Burgers",
            spicyLevel = 1
        ),
        Product(
            id = "7",
            nameLine1 = "Double Bacon",
            nameLine2 = "Monster Burger",
            price = 13.49,
            rating = 4.9,
            prepTime = "20 mins",
            description = "Dual flame-grilled beef patties stacked with smoked bacon strips, double cheddar cheese, caramelized onions and signature barbecue glaze.",
            imageRes = R.drawable.img_burger_cheeseburger_1785072915476,
            videoResId = R.raw.v7,
            isFavorite = true,
            category = "Burgers",
            spicyLevel = 2
        ),
        Product(
            id = "8",
            nameLine1 = "Chicken Club",
            nameLine2 = "Grilled Wrap",
            price = 8.99,
            rating = 4.6,
            prepTime = "15 mins",
            description = "Toasted tortilla stuffed with grilled chicken tenderloins, avocado slices, crispy lettuce, tomato and light garlic yogurt dressing.",
            imageRes = R.drawable.img_burger_chicken_1785072942831,
            videoResId = R.raw.v1,
            isFavorite = false,
            category = "Tacos & Wraps",
            spicyLevel = 1
        ),
        Product(
            id = "9",
            nameLine1 = "Four Cheese",
            nameLine2 = "Quattro Pizza",
            price = 16.25,
            rating = 4.8,
            prepTime = "25 mins",
            description = "A cheesy dream featuring Mozzarella, Gorgonzola, Parmesan and Fontina melted over garlic infused olive oil dough.",
            imageRes = R.drawable.img_burger_cheeseburger_1785072915476,
            videoResId = R.raw.v2,
            isFavorite = false,
            category = "Pizza",
            spicyLevel = 1
        ),
        Product(
            id = "10",
            nameLine1 = "Spicy Zesty",
            nameLine2 = "Chicken Tenders",
            price = 11.80,
            rating = 4.7,
            prepTime = "16 mins",
            description = "Juicy tender chicken strips tossed in fiery buffalo glaze served with celery sticks and ranch dipping sauce.",
            imageRes = R.drawable.img_burger_chicken_1785072942831,
            videoResId = R.raw.v3,
            isFavorite = true,
            category = "Chicken",
            spicyLevel = 3
        ),
        Product(
            id = "11",
            nameLine1 = "Chili Cheese",
            nameLine2 = "New York Hotdog",
            price = 6.49,
            rating = 4.6,
            prepTime = "10 mins",
            description = "Grilled beef sausage in warm steamed bun covered in spicy beef chili, diced onions and melted yellow cheddar cheese.",
            imageRes = R.drawable.img_burger_cheeseburger_1785072915476,
            videoResId = R.raw.v4,
            isFavorite = false,
            category = "Burgers",
            spicyLevel = 2
        ),
        Product(
            id = "12",
            nameLine1 = "Crispy Golden",
            nameLine2 = "Onion Rings",
            price = 4.50,
            rating = 4.5,
            prepTime = "8 mins",
            description = "Thick cut sweet onion rings battered in beer batter dough and fried to dark golden perfection with tangy BBQ sauce.",
            imageRes = R.drawable.img_burger_veggie_1785072930721,
            videoResId = R.raw.v5,
            isFavorite = false,
            category = "Sides & Fries",
            spicyLevel = 1
        ),
        Product(
            id = "13",
            nameLine1 = "Chocolate Lava",
            nameLine2 = "Thick Milkshake",
            price = 4.99,
            rating = 4.9,
            prepTime = "5 mins",
            description = "Creamy Belgian chocolate gelato blended with fresh whole milk, topped with whipped cream and chocolate drizzle.",
            imageRes = R.drawable.img_burger_cheeseburger_1785072915476,
            videoResId = R.raw.v6,
            isFavorite = true,
            category = "Drinks",
            spicyLevel = 1
        ),
        Product(
            id = "14",
            nameLine1 = "Nutella Glazed",
            nameLine2 = "Warm Donuts",
            price = 5.99,
            rating = 4.9,
            prepTime = "5 mins",
            description = "Freshly fried soft dough rings coated with rich hazelnut Nutella spread and roasted hazelnut sprinkles.",
            imageRes = R.drawable.img_burger_chicken_1785072942831,
            videoResId = R.raw.v7,
            isFavorite = false,
            category = "Desserts",
            spicyLevel = 1
        )
    )

    val sampleCategories = listOf("All", "Burgers", "Pizza", "Chicken", "Tacos & Wraps", "Sides & Fries", "Drinks", "Desserts")

    val defaultToppings = listOf(
        Topping("t1", "Tomato", 1.20, "🍅", true),
        Topping("t2", "Onions", 0.80, "🧅", true),
        Topping("t3", "Pickles", 1.00, "🥒", true),
        Topping("t4", "Bacons", 2.00, "🥓", true),
        Topping("t5", "Cheese", 1.50, "🧀", true)
    )

    val defaultSideOptions = listOf(
        SideOption("s1", "Fries", 3.50, "🍟", true),
        SideOption("s2", "Coleslaw", 2.00, "🥗", false),
        SideOption("s3", "Salad", 2.50, "🥬", false),
        SideOption("s4", "Onion", 3.00, "🧅", false)
    )

    val samplePaymentMethods = listOf(
        PaymentMethod("p1", "Mastercard", "Credit card", "5105 **** **** 0505", "mastercard", true),
        PaymentMethod("p2", "VISA", "Debit card", "3566 **** **** 0505", "visa", false)
    )

    val initialChatMessages = listOf(
        ChatMessage("c1", "Hi, how can I help you?", false, "28 minutes ago"),
        ChatMessage("c2", "Hello, I ordered two fried chicken burgers. Can I know how much time it will get to arrive?", true, "27 minutes ago"),
        ChatMessage("c3", "Ok, please let me check!", false, "26 minutes ago"),
        ChatMessage("c4", "Sure...", true, "26 minutes ago"),
        ChatMessage("c5", "It'll get 25 minutes to arrive to your address", false, "25 minutes ago"),
        ChatMessage("c6", "Ok, thanks you for your support", true, "25 minutes ago")
    )
}
