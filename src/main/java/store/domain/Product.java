package store.domain;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;

public class Product {

    private String name;
    private long price;
    private long quantity;
    private boolean isPromotion;
    private String promotion;

    private Product(String name, long price, long quantity, boolean isPromotion, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isPromotion = isPromotion;
        this.promotion = promotion;
    }

    public static Product from(String[] info){
        String name = validateName(info[0]);
        long price = validatePriceAndQuantity(info[1]);
        long quantity = validatePriceAndQuantity(info[2]);
        boolean isPromotion = !info[3].equals("null");
        String promotion = info[3];
        if (!isPromotion){
            promotion = null;
        }
        return new Product(name, price, quantity, isPromotion, promotion);
    }

    private static String validateName(String rawName) {
        String name = rawName.trim();
        if (name.isBlank()) {
            throw new DataValidationException("[ERROR] 상품 이름이 잘못 등록되어 있습니다. products.md 파일을 확인해주세요.");
        }
        return name;
    }

    private static long validatePriceAndQuantity(String priceInput) {
        String rawPrice = priceInput.trim();
        if (!rawPrice.matches("\\d+")) {
            throw new DataValidationException("[ERROR] 상품 가격은 정수여야 합니다. products.md 파일을 확인해주세요.");
        }
        long price = Long.parseLong(rawPrice);
        if (price < 0) {
            throw new DataValidationException("[ERROR] 상품 가격은 0원 이상의 정수여야 합니다. products.md 파일을 확인해주세요.");
        }
        return price;
    }

    public boolean isPurchaseAvailable(long purchaseAmount) {
        return quantity >= purchaseAmount;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Product: {" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", isPromotion=" + isPromotion +
                ", promotion='" + promotion + '\'' +
                '}';
    }
}
