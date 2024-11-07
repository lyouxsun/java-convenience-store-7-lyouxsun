package store.domain.product;

public interface Product {

    Product from(String name, String rawPrice, String rawQuantity, String rawPromotion);

    boolean isPromotion();

    boolean isAvailable(int purchaseQuantity);
}
