package store.dto;

public record ResultDto(long totalAmount, long promotionAmount, long membershipAmount) {

    public long getTotalAmount() {
        return totalAmount;
    }

    public long getPromotionAmount() {
        return promotionAmount;
    }

    public long getMembershipAmount() {
        return membershipAmount;
    }

    public long getTotalPaymentAmount() {
        return totalAmount - promotionAmount - membershipAmount;
    }
}
