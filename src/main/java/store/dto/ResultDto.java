package store.dto;

public record ResultDto(long totalAmount, long promotionAmount, long membershipAmount) {

    public String getTotalAmount() {
        return String.format("%,d", totalAmount);
    }
    public String getPromotionAmount() {
        return String.format("%,d", promotionAmount);
    }
    public String getMembershipAmount() {
        return String.format("%,d", membershipAmount);
    }

    public String getTotalPaymentAmount() {
        long totalPayment = totalAmount - promotionAmount - membershipAmount;
        return String.format("%,d", totalPayment);
    }
}
