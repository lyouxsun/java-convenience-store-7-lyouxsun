package store.enums;

public enum ReceiptMessages {

    STORE_INFO("==============W 편의점================"),
    ITEM_LIST_HEADER("상품명\t\t\t\t수량\t\t  금액"),
    ITEM_LIST_FORMAT("{}\t\t\t\t{}\t\t  {}"),
    PROMOTION_LIST_HEADER("=============증\t\t정==============="),
    PROMOTION_LIST_FORMAT("{}\t\t\t\t{}"),
    LINE("===================================="),
    TOTAL_AMOUNT_INFO("총구매액\t\t\t\t{}\t\t  {}"),
    PROMOTION_AMOUNT_INFO("행사할인\t\t\t\t\t\t  -{}"),
    MEMBERSHIP_AMOUNT_INFO("멤버십할인\t\t\t\t\t\t  -{}"),
    TOTAL_PAYMENT_AMOUNT_INFO("내실돈\t\t\t\t\t\t  {}");


    private final String message;
    ReceiptMessages(String message) {
        this.message = message;
    }

    public String format(String productName, int num, String amount) {
        return String.format(message, productName, num, amount);
    }

    public String format(String productName, int num) {
        return String.format(message, productName, num);
    }

    public String format(int num, String amount) {
        return String.format(message, num, amount);
    }

    public String format(String amount) {
        return String.format(message, amount);
    }

    public String getMessage(){
        return message;
    }
}
