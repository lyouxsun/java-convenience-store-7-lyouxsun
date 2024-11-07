package store;

import store.context.ContextProductLoader;
import store.context.ContextPromotionLoader;
import store.domain.product.Products;
import store.domain.promotion.Promotions;

public class Application {
    public static void main(String[] args) {
        Products products = new ContextProductLoader().initializeProducts();
        Promotions promotions = new ContextPromotionLoader().initializePromotions();
    }
}
