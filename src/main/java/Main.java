import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        //TODO Create User class with method createUser
        // User class fields: name, age;
        // Notice that we can only create user with createUser method without using constructor or builder
        User user1 = User.createUser("Alice", 32);
        User user2 = User.createUser("Bob", 19);
        User user3 = User.createUser("Charlie", 20);
        User user4 = User.createUser("John", 27);

        //TODO Create factory that can create a product for a specific type: Real or Virtual
        // Product class fields: name, price
        // Product Real class additional fields: size, weight
        // Product Virtual class additional fields: code, expiration date

        Product realProduct1 = ProductFactory.createRealProduct("Product A", 20.50, 10, 25);
        Product realProduct2 = ProductFactory.createRealProduct("Product B", 50, 6, 17);

        Product virtualProduct1 = ProductFactory.createVirtualProduct("Product C", 100, "xxx", LocalDate.of(2023, 5, 12));
        Product virtualProduct2 = ProductFactory.createVirtualProduct("Product D", 81.25, "yyy", LocalDate.of(2024, 6, 20));


        //TODO Create Order class with method createOrder
        // Order class fields: User, List<Price>
        // Notice that we can only create order with createOrder method without using constructor or builder
        List<Order> orders = new ArrayList<>() {{
            add(Order.createOrder(user1, List.of(realProduct1, virtualProduct1, virtualProduct2)));
            add(Order.createOrder(user2, List.of(realProduct1, realProduct2)));
            add(Order.createOrder(user3, List.of(realProduct1, virtualProduct2)));
            add(Order.createOrder(user4, List.of(virtualProduct1, virtualProduct2, realProduct1, realProduct2)));
        }};

        //TODO 1). Create singleton class which will check the code is used already or not
        // Singleton class should have the possibility to mark code as used and check if code used
        // Example:
        // singletonClass.useCode("xxx")
        // boolean isCodeUsed = virtualProductCodeManager.isCodeUsed("xxx") --> true;
        // boolean isCodeUsed = virtualProductCodeManager.isCodeUsed("yyy") --> false;
        System.out.println("1. Create singleton class VirtualProductCodeManager \n");
        VirtualProductCodeManager virtualProductCodeManager = new VirtualProductCodeManager();
        var isUsed = virtualProductCodeManager.isCodeUsed("xxx");
        virtualProductCodeManager.useCode("xxx");
        var isUsed2 = virtualProductCodeManager.isCodeUsed("xxx");
        System.out.println("Is code used: " + isUsed + "\n");
        System.out.println("Is code used: " + isUsed2 + "\n");

        //TODO 2). Create a functionality to get the most expensive ordered product
        Product mostExpensive = getMostExpensiveProduct(orders);
        System.out.println("2. Most expensive product: " + mostExpensive.name + "\n");

        //TODO 3). Create a functionality to get the most popular product(product bought by most users) among users
        Product mostPopular = getMostPopularProduct(orders);
        System.out.println("3. Most popular product: " + mostPopular.name + "\n");

        //TODO 4). Create a functionality to get average age of users who bought realProduct2
        double averageAge = calculateAverageAge(realProduct2, orders);
        System.out.println("4. Average age is: " + averageAge + "\n");

        //TODO 5). Create a functionality to return map with products as keys and a list of users
        // who ordered each product as values
        Map<Product, List<User>> productUserMap = getProductUserMap(orders);
        System.out.println("5. Map with products as keys and list of users as value \n");
        productUserMap.forEach((key, value) -> {
            System.out.print("key: " + key.name + " " + "value: ");
            value.forEach(user -> System.out.print(user.getName() + ", "));
            System.out.println("\n");
        });

        //TODO 6). Create a functionality to sort/group entities:
        // a) Sort Products by price
        // b) Sort Orders by user age in descending order
        List<Product> productsByPrice = sortProductsByPrice(List.of(realProduct1, realProduct2, virtualProduct1, virtualProduct2));
        System.out.print("6. a) List of products sorted by price: ");
        productsByPrice.forEach(product -> System.out.print(product.getName() + "  -  " + product.getPrice() + "    |   "));
        System.out.println("\n");

        List<Order> ordersByUserAgeDesc = sortOrdersByUserAgeDesc(orders);
//        System.out.println("6. b) List of orders sorted by user agge in descending order: " + ordersByUserAgeDesc + "\n");
        System.out.println("6. b) List of orders sorted by user agge in descending order: ");
        ordersByUserAgeDesc.forEach(order -> {
            System.out.print(order.getUser().getAge() + "    -   order: [ " );
            order.getProductList().forEach(product -> System.out.print(product.getName() + " "));
            System.out.println("]" + "\n");
        });

        //TODO 7). Calculate the total weight of each order
        Map<Order, Integer> result = calculateWeightOfEachOrder(orders);
        System.out.println("7. Calculate the total weight of each order \n");
        result.forEach((key, value) -> System.out.println("order: " + key.getUser() + " " + "total weight: " + value + "\n"));
    }

    private static Product getMostExpensiveProduct(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return null;
        } else {
            Product mostExpensiveProduct = null;
            for (Order o : orders) {
                mostExpensiveProduct = o.getProductList().stream().max(Comparator.comparing(Product::getPrice)).get();
            }
            return mostExpensiveProduct;
        }
    }

    private static Product getMostPopularProduct(List<Order> orders) {
        Map<Product, Long> productCounts = new HashMap<>();
        Product result = null;

        for (Order order : orders) {
            List<Product> productList = order.getProductList();

            for (Product product : productList) {
                long count = productCounts.getOrDefault(product, 0L);
                productCounts.put(product, count + 1);
            }
        }

        Long max = Collections.max(productCounts.values());

        for (Map.Entry<Product, Long> entry : productCounts.entrySet()) {
            if (entry.getValue().equals(max)) {
                result = entry.getKey();
            }
        }
        return result;
    }

    private static double calculateAverageAge(Product product, List<Order> orders) {
        int userAgeSum = 0;
        int countOfUser = 0;

        for (Order o : orders) {
            if (o.getProductList().contains(product)) {
                int age = o.getUser().getAge();
                userAgeSum += age;
                countOfUser++;
            }
        }

        return userAgeSum / countOfUser;
    }

    private static Map<Product, List<User>> getProductUserMap(List<Order> orders) {
        Map<Product, List<User>> productUserMap = new HashMap<>();

        for (Order o : orders) {
            List<Product> productList = o.getProductList();
            User user = o.getUser();

            productList.forEach(product -> {
                List<User> users = productUserMap.getOrDefault(product, new ArrayList<>());
                users.add(user);
                productUserMap.put(product, users);
            });
        }

        return productUserMap;
    }

    private static List<Product> sortProductsByPrice(List<Product> products) {
        List<Product> sortedProducts = new ArrayList<>(products);

        Comparator<Product> comparator = Comparator.comparing(Product::getPrice);

        // Sorted by ascending price
        sortedProducts.sort(comparator);
        // Sorted by descending price
        sortedProducts.sort(Collections.reverseOrder(comparator));

        return sortedProducts;
    }

    private static List<Order> sortOrdersByUserAgeDesc(List<Order> orders) {
        List<Order> sortedOrders = new ArrayList<>(orders);
        Comparator<Order> comparing = Comparator.comparing(order -> order.getUser().getAge());

        sortedOrders.sort(Collections.reverseOrder(comparing));

        return sortedOrders;
    }

    private static Map<Order, Integer> calculateWeightOfEachOrder(List<Order> orders) {
        int totalWeight = 0;
        Map<Order, Integer> hashMap = new HashMap<>();

        for (Order order : orders) {
            List<Product> productList = order.getProductList();

            for (Product p : productList) {
                if (p instanceof ProductReal) {
                    totalWeight+= ((ProductReal) p).getWeight();
                }
            }
            hashMap.put(new Order.OrderBuilder().build(), totalWeight);
            totalWeight = 0;
        }

        return hashMap;
    }
}
