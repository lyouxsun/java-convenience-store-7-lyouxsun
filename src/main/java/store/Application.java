package store;

import store.context.ContextProductLoader;
import store.context.ContextPromotionLoader;
import store.controller.StoreController;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController();
        storeController.run();
    }
}
