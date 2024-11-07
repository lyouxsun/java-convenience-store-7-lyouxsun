package store;

import store.context.ContextProductLoader;
import store.context.ContextPromotionLoader;
import store.domain.product.Products;

public class Application {
    public static void main(String[] args) {
        Products products = new ContextProductLoader().initializeProducts();
        new ContextPromotionLoader().initializeData();
    }
}
