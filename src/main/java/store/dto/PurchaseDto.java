package store.dto;

public record PurchaseDto(int totalPurchaseQuantity, int setNum, int oneSetQuantity) {
    public PurchaseDto {
        validateNumber();
    }

    private void validateNumber() {
        if (totalPurchaseQuantity < 0 || setNum < 0 || oneSetQuantity < 0) {
            throw new IllegalArgumentException("[ERROR] 수량은 항상 0 이상의 정수여야 합니다.");
        }
    }

    public int getTotalNum() {
        return totalPurchaseQuantity;
    }

    public boolean isPromotion(){
        return setNum > 0;
    }
    public int getSetNum() {
        return setNum;
    }
    public int getPromotionSet() {
        return setNum * oneSetQuantity;
    }
}
