package store.dto;


import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;

public class ProductDto {
    private final String name;
    private final long price;
    private String promotion;
    private int promotionQuantity;
    private int originalQuantity;

    public ProductDto(String name, long price, String promotion, int promotionQuantity, int originalQuantity) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.promotionQuantity = promotionQuantity;
        this.originalQuantity = originalQuantity;
    }

    public static ProductDto from(String[] info) {
        String name = validateName(info[0]);
        long price = validatePrice(info[1]);
        int quantity = validateQuantity(info[2]);
        if (info[3].equals("null")) {
            return new ProductDto(name, price, null, 0, quantity);
        }
        return new ProductDto(name, price, info[3], quantity, 0);
    }

    private static String validateName(String rawName) {
        String name = rawName.trim();
        if (name.isBlank()) {
            throw new DataValidationException("[ERROR] 상품 이름이 잘못 등록되어 있습니다.");
        }
        return name;
    }

    private static int validateQuantity(String numberInput) {
        String rawInput = numberInput.trim();
        validateNumber(rawInput);
        return Integer.parseInt(rawInput);
    }

    private static long validatePrice(String numberInput) {
        String rawInput = numberInput.trim();
        validateNumber(rawInput);
        return Long.parseLong(rawInput);
    }

    private static void validateNumber(String rawInput) {
        if (!rawInput.matches("\\d+")) {
            throw new DataValidationException("[ERROR] 상품 가격과 수량은 0 이상의 정수여야 합니다.");
        }
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public int getOriginalQuantity() {
        return originalQuantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setInfo(String[] info) {
        int quantity = validateQuantity(info[2]);
        if (info[3].equals("null")) {       // 일반재고 재등록
            this.originalQuantity = quantity;
        }
        // 할인재고 재등록
        this.promotion = info[3];
        this.promotionQuantity = quantity;
    }
}
