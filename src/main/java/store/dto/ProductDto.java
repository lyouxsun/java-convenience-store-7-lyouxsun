package store.dto;


import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;

public class ProductDto {
    private final String name;
    private final long price;
    private final int quantity;
    private final String promotion;

    public ProductDto(String[] info) {
        this.name = validateName(info[0]);
        this.price = validatePriceAndQuantity(info[1]);
        this.quantity = validatePriceAndQuantity(info[2]);
        this.promotion = info[3];
    }

    private String validateName(String rawName) {
        String name = rawName.trim();
        if (name.isBlank()) {
            throw new DataValidationException("[ERROR] 상품 이름이 잘못 등록되어 있습니다.");
        }
        return name;
    }


    private int validatePriceAndQuantity(String numberInput) {
        String rawInput = numberInput.trim();
        validateNumber(rawInput);
        return Integer.parseInt(rawInput);
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

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

}
