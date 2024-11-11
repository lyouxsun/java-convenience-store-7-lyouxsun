package store.enums;

public enum ErrorMessages {

    INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),
    PROMOTION_FILE_ERROR("프로모션 목록을 입력받을 수 없습니다."),
    PRODUCT_FILE_ERROR("상품 목록을 입력받을 수 없습니다."),
    NON_INTEGER_QUANTITY("구매 수량은 정수여야 합니다. 다시 입력해 주세요."),
    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    PROMOTION_NAME("프로모션 이름이 잘못 등록되어 있습니다."),
    PROMOTION_QUANTITY("프로모션 상품 수량은 0 이상의 정수여야 합니다."),
    PRODUCT_QUANTITY("상품 수량 또는 가격은 0 이상의 정수여야 합니다."),
    PROMOTION_DATE_FORMAT("프로모션 날짜가 잘못된 형식으로 등록되어 있습니다."),
    QUANTITY_OVERFLOW("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    PRODUCT_NAME("상품 이름이 잘못 등록되어 있습니다.");


    private static final String ERROR = "[ERROR] ";

    private final String message;

    ErrorMessages(String message) {
        this.message = ERROR + message;
    }

    public String getMessage() {
        return message;
    }
}
