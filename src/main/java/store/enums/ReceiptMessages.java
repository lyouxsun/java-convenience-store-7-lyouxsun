package store.enums;

import java.text.DecimalFormat;

public enum ReceiptMessages {

    STORE_INFO("==============W 편의점================"),
    ITEM_LIST_HEADER("%-10s\t\t\t%2s\t%11s"),
    ITEM_LIST_FORMAT("%-10s %10d %,12d"),
    PROMOTION_LIST_HEADER("=============증\t\t정==============="),
    PROMOTION_LIST_FORMAT("%-10s\t\t\t%2d"),
    LINE("===================================="),
    TOTAL_AMOUNT_INFO("총구매액\t\t\t\t%2d\t%,12d"),
    PROMOTION_AMOUNT_INFO("행사할인\t\t\t\t\t%12s"),
    MEMBERSHIP_AMOUNT_INFO("멤버십할인\t\t\t\t\t%12s"),
    TOTAL_PAYMENT_AMOUNT_INFO("내실돈\t\t\t\t\t%,12d");


    private final String message;

    ReceiptMessages(String message) {
        this.message = message;
    }

    public String formatHead() {
        return String.format(message, "상품명", "수량", "금액");
    }

    public String format(String productName, int num, long amount) {
        return String.format(message, productName, num, amount);
    }

    public String format(String productName, int num) {
        return String.format(message, productName, num);
    }

    public String format(int num, long amount) {
        return String.format(message, num, amount);
    }

    public String formatNegative(long amount) {
        DecimalFormat formatter = new DecimalFormat("-#,##0");
        return String.format(message, formatter.format(amount));
    }

    public String format(long amount) {
        return String.format(message, amount);
    }

    public String getMessage() {
        return message;
    }
}
