package store.dto;

public class PurchaseDto {

    private final long onePrice;
    private final int totalPurchaseQuantity;
    private final int setNum;
    private final int oneSetQuantity;


    public PurchaseDto(long onePrice, int totalPurchaseQuantity, int setNum, int oneSetQuantity){
        this.onePrice = onePrice;
        this.totalPurchaseQuantity = totalPurchaseQuantity;
        this.setNum = setNum;
        this.oneSetQuantity = oneSetQuantity;

    }

    public int getTotalNum() {
        return totalPurchaseQuantity;
    }

    public boolean isPromotion(){
        return setNum > 0;
    }
    public int getSetNum() {
        return setNum;
    }
    public long getPromotionSetPrice() {
        return onePrice * setNum * oneSetQuantity;
    }

    public long getTotalPrice(){
        return onePrice * totalPurchaseQuantity;
    }

    public long promotionSalePrice(){
        return onePrice * setNum;
    }
}
