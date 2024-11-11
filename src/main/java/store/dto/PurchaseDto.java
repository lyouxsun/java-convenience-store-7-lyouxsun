package store.dto;

public class PurchaseDto {

    private long onePrice;
    private int totalPurchaseQuantity;
    private int setNum;
    private int oneSetQuantity;


    public PurchaseDto(long onePrice, int totalPurchaseQuantity, int setNum, int oneSetQuantity){
        this.onePrice = onePrice;
        this.totalPurchaseQuantity = totalPurchaseQuantity;
        this.setNum = setNum;
        this.oneSetQuantity = oneSetQuantity;

        System.out.println("onePrice = " + onePrice);
        System.out.println("totalPurchaseQuantity = " + totalPurchaseQuantity);
        System.out.println("setNum = " + setNum);
        System.out.println("oneSetQuantity = " + oneSetQuantity);
        System.out.println();
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
