package store.domain;

import store.dto.ProductDto;
import store.dto.PurchaseDto;
import store.view.InputView;

import static store.enums.Messages.*;

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
            String promotionProduct = getPromotionProduct();
            sb.append(promotionProduct);
        }
        String noPromotionProduct = getNoPromotionProduct();
        sb.append(noPromotionProduct);
        return sb.toString();
    }

    private String getPromotionProduct() {
        return String.format(INVENTORY_WITH_PROMOTION.getMessage(),
                name,
                price,
                quantityToString(promotionQuantity),
                promotion.getName());
    }

    private String getNoPromotionProduct() {
        return String.format(INVENTORY_WITHOUT_PROMOTION.getMessage(),
                name,
                price,
                quantityToString(originalQuantity));
    }

    public boolean isPromotion() {
        if (promotion == null) {
            return false;
        }
        return this.promotion.isBetweenDates();
    }

    public PurchaseDto getPromotionAmount(final int purchaseAmount) {
        int oneSet = promotion.getPromotionQuantity();
        int setNum = calculateSetNum(purchaseAmount, oneSet);
        int noPromotionNum = purchaseAmount - oneSet * setNum;
        if (shouldAddMorePromotion(purchaseAmount, oneSet)) {
            return handleAdditionalPromotion(purchaseAmount, setNum, oneSet, price);
        }
        if (shouldProceedWithoutPromotion(noPromotionNum)) {
            return new PurchaseDto(price, purchaseAmount, setNum, oneSet);
        }
        return new PurchaseDto(price, purchaseAmount, setNum, oneSet);
    }

    private static PurchaseDto handleAdditionalPromotion(int purchaseAmount, int setNum, int oneSet, long price) {
        setNum++;
        return new PurchaseDto(price, purchaseAmount + 1, setNum + 1, oneSet);
    }

    private boolean shouldProceedWithoutPromotion(int noPromotionNum) {
        return noPromotionNum > 1 && InputView.requestYorN(NO_PROMOTION.format(name, noPromotionNum));
    }

    private int calculateSetNum(int purchaseAmount, int oneSet) {
        int setNum = purchaseAmount / oneSet;
        if (promotionQuantity < purchaseAmount) {
            setNum = promotionQuantity / oneSet;
        }
        return setNum;
    }

    private boolean shouldAddMorePromotion(int purchaseAmount, int oneSet) {
        return morePromotion(purchaseAmount, oneSet) && InputView.requestYorN(PROMOTION_MORE.format(name));
    }

    private boolean morePromotion(int purchaseAmount, int promotionSet) {
        return (purchaseAmount + 1) <= promotionQuantity && ((purchaseAmount + 1) % promotionSet == 0);
    }

    public void reduceQuantity(int hope) {
        if (promotionQuantity < hope) {
            hope -= promotionQuantity;
            promotionQuantity = 0;
            originalQuantity -= hope;
            return;
        }
        originalQuantity -= hope;
    }

    public long getPrice() {
        return price;
    }
}
