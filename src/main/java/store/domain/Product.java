package store.domain;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
import store.dto.PurchaseDto;
import store.repository.PromotionRepository;
import store.view.InputView;

import static store.enums.Messages.NO_PROMOTION;
import static store.enums.Messages.PROMOTION_MORE;

public class Product {

    private String name;
    private long price;
    private int quantity;
    private Promotion promotion;

    private Product(String name, long price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Product from(String[] info) {
        String name = validateName(info[0]);
        long price = validatePriceAndQuantity(info[1]);
        int quantity = validatePriceAndQuantity(info[2]);
        Promotion promotion = PromotionRepository.fromName(info[3]);
        return new Product(name, price, quantity, promotion);
    }

    private static String validateName(String rawName) {
        String name = rawName.trim();
        if (name.isBlank()) {
            throw new DataValidationException("[ERROR] 상품 이름이 잘못 등록되어 있습니다.");
        }
        return name;
    }

    private static int validatePriceAndQuantity(String numberInput) {
        String rawInput = numberInput.trim();
        validateNumber(rawInput);
        return Integer.parseInt(rawInput);
    }

    private static void validateNumber(String rawInput) {
        if (!rawInput.matches("\\d+")) {
            throw new DataValidationException("[ERROR] 상품 가격과 수량은 0 이상의 정수여야 합니다.");
        }
    }

    public boolean isPurchaseAvailable(long purchaseAmount) {
        return quantity >= purchaseAmount;
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (promotion != null) {
            return "- " + name + " "
                    + price + "원 "
                    + quantity + "개 "
                    + promotion.getName() + "\n";
        }
        return "- " + name + " "
                + price + "원 "
                + quantity + "개\n";
    }

    public boolean isPromotion() {
        if (promotion == null) {
            return false;
        }
        return this.promotion.isBetweenDates();
    }

    public PurchaseDto getPromotionAmount(final int purchaseAmount) {

        // 2+1이면 3, 1+1이면 2를 리턴
        int setQuantity = promotion.getPromotionQuantity();

        // 프로모션이 적용되는 세트 수
        int setNum = purchaseAmount / setQuantity;
        if (quantity < purchaseAmount) {
            setNum = quantity / setQuantity;
        }

        // 프로모션 적용이 안되는 물건 개수
        int noPromotionNum = purchaseAmount - setQuantity * setNum;


        if (morePromotion(purchaseAmount, setQuantity)) {
            boolean yes = InputView.requestYorN(PROMOTION_MORE.format(name));
            if (yes) {
                setNum++;
                return new PurchaseDto(price, purchaseAmount+1, setNum + 1, setQuantity);
            }
        }

        if (noPromotionNum > 1) {
            boolean noPromotion = InputView.requestYorN(NO_PROMOTION.format(name, noPromotionNum));
            if (noPromotion) {
                return new PurchaseDto(price, purchaseAmount, setNum, setQuantity);
            }
        }
        return new PurchaseDto(price, purchaseAmount, setNum, setQuantity);
    }

    private boolean morePromotion(int purchaseAmount, int promotionSet) {
        return (purchaseAmount + 1) <= quantity && ((purchaseAmount + 1) % promotionSet == 0);
    }

    public void reduceQuantity(int promotionAmount) {
        if (quantity >= promotionAmount) {
            quantity -= promotionAmount;
            return;
        }
        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
    }

    public int getQuantity() {
        return quantity;
    }

    public long getPrice(){
        return price;
    }
}
