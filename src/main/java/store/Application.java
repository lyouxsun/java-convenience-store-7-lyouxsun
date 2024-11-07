package store;

import store.context.ContextProductLoader;
import store.context.ContextPromotionLoader;
import store.domain.product.Promotion;

public class Application {
    public static void main(String[] args) {
        new ContextProductLoader().initializeData();
        new ContextPromotionLoader().initializeData();
    }
}
