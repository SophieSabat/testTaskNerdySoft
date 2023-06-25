import java.time.LocalDate;

public class ProductVirtual extends Product{
    private String code;
    private LocalDate expirationDate;

    public ProductVirtual(String name, double price, String code, LocalDate expirationDate) {
        super(name, price);
        this.code = code;
        this.expirationDate = expirationDate;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
