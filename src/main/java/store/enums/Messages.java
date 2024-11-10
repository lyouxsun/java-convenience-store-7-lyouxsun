package store.enums;

public enum Messages {

    PROMOTION_MORE("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    NO_PROMOTION("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    MEMBERSHIP_SALE("멤버십 할인을 받으시겠습니까? (Y/N)");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String format(String name){
        return String.format(message, name);
    }

    public String format(String name, int quantity){
        return String.format(message, name, quantity);
    }

    public String getMessage(){
        return message;
    }
}
