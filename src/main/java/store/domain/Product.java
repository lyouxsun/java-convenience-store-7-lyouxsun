package store.domain;

import store.dto.ProductDto;
import store.dto.PurchaseDto;
import store.view.InputView;

import static store.enums.Messages.NO_PROMOTION;
import static store.enums.Messages.PROMOTION_MORE;

public class Product {

    private final String name;
    private final long price;
    private final Promotion promotion;
    private int originalQuantity = 0;
    private int promotionQuantity = 0;


    private Product(String name, long price, int originalQuantity, int promotionQuantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.originalQuantity = originalQuantity;
        this.promotionQuantity = promotionQuantity;
        this.promotion = promotion;
    }

    public static Product from(ProductDto productDto, Promotion promotion) {
        String name = productDto.getName();
        long price = productDto.getPrice();
        int originalQuantity = productDto.getOriginalQuantity();
        int promotionQuantity = productDto.getPromotionQuantity();
        return new Product(name, price, originalQuantity, promotionQuantity, promotion);
    }

    public boolean isPurchaseAvailable(long purchaseAmount) {
        return originalQuantity + promotionQuantity >= purchaseAmount;
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public String quantityToString(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return String.format("%d개", quantity);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (promotion != null) {
            String promotionProduct = String.format("- %s %,d원 %s %s\n",
                    name,
                    price,
                    quantityToString(promotionQuantity),
                    promotion.getName());
            sb.append(promotionProduct);
        }
        String noPromotionProduct = String.format("- %s %,d원 %s\n",
                name,
                price,
                quantityToString(originalQuantity));
        sb.append(noPromotionProduct);
        return sb.toString();
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
        if (promotionQuantity < purchaseAmount) {
            setNum = promotionQuantity / setQuantity;
        }

        // 프로모션 적용이 안되는 물건 개수
        int noPromotionNum = purchaseAmount - setQuantity * setNum;


        if (morePromotion(purchaseAmount, setQuantity)) {
            boolean yes = InputView.requestYorN(PROMOTION_MORE.format(name));
            if (yes) {
                setNum++;
                return new PurchaseDto(price, purchaseAmount + 1, setNum + 1, setQuantity);
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
        return (purchaseAmount + 1) <= promotionQuantity && ((purchaseAmount + 1) % promotionSet == 0);
    }

    public void reduceQuantity(int promotionAmount) {
        if (promotionQuantity >= promotionAmount) {
            promotionQuantity -= promotionAmount;
            return;
        }
        throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다.");
    }

    public int getQuantity() {
        return originalQuantity + promotionQuantity;
    }

    public long getPrice() {
        return price;
    }
}
