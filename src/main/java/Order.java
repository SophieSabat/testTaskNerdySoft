import java.util.List;

public class Order {
    private User user;
    private List<Product> productList;

    public static Order createOrder(User user, List<Product> products) {
        Order order = new Order();

        order.user = user;
        order.productList = products;

        return order;
    }

    public User getUser() {
        return user;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public static class OrderBuilder {
        private Order order;

        public OrderBuilder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Order build() {
            return this.order;
        }
    }
}
