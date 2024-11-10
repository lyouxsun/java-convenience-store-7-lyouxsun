package store.dto;

public record PurchaseDto(int totalAmount, int promotionAmount, int setQuantity) {
    public PurchaseDto {
        validateNumber();
    }

    private void validateNumber() {
        if (totalAmount < 0 || promotionAmount < 0) {
            throw new IllegalArgumentException("[ERROR] 수량은 항상 0 이상의 정수여야 합니다.");
        }
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}
