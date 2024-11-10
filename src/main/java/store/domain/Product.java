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

    public static Product from(String[] info) {
        String name = validateName(info[0]);
        long price = validatePriceAndQuantity(info[1]);
        long quantity = validatePriceAndQuantity(info[2]);
        boolean isPromotion = !info[3].equals("null");
        String promotion = info[3];
        if (!isPromotion) {
            promotion = null;
        }
        return new Product(name, price, quantity, isPromotion, promotion);
    }

    private static String validateName(String rawName) {
        String name = rawName.trim();
        if (name.isBlank()) {
            throw new DataValidationException("[ERROR] 상품 이름이 잘못 등록되어 있습니다.");
        }
        return name;
    }

    private static long validatePriceAndQuantity(String numberInput) {
        String rawInput = numberInput.trim();
        validateNumber(rawInput);
        return Long.parseLong(rawInput);
    }

    private static void validateNumber(String rawInput) {
        if (!rawInput.matches("\\d+")) {
            throw new DataValidationException("[ERROR] 상품 가격과 수량은 0 이상의 정수여야 합니다.");
        }
    }

    public boolean isPurchaseAvailable(long purchaseAmount) {
        return quantity >= purchaseAmount;
    }

    public boolean isValidName(String name){
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (isPromotion) {
            return "- " + name + " "
                    + price + "원 "
                    + quantity + "개 "
                    + promotion + "\n";
        }
        return "- " + name + " "
                + price + "원 "
                + quantity + "개\n";
    }

    public long getAmount() {
        return quantity;
    }
}
