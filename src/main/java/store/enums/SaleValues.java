package store.enums;

public enum SaleValues {
    MEMBERSHIP_DISCOUNT_RATE(0.3),
    MEMBERSHIP_DISCOUNT_LIMIT(8_000);

    private final double value;

    SaleValues(double value) {
        this.value = value;
    }

    public double valueToDouble() {
        return value;
    }

    public long valueToLong() {
        return (long) value;
    }
}
