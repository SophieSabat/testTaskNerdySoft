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
        private User user;
        private List<Product> productList;

        public void setUser(User user) {
            this.user = user;
        }

        public void setProductList(List<Product> productList) {
            this.productList = productList;
        }

        public Order build() {
            return createOrder(user, productList);
        }
    }
}
