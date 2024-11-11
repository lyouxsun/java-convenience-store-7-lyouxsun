package store;

import store.context.ContextPromotionLoader;
import store.controller.InputController;
import store.controller.StoreController;
import store.repository.PromotionRepository;

import java.nio.file.Paths;
import java.util.Map;

import static store.enums.FilePath.PROMOTION_FILE;

public class Application {
    public static void main(String[] args) {
        PromotionRepository promotionRepository = new ContextPromotionLoader()
                .initializePromotions(Paths.get(PROMOTION_FILE.path()));
        StoreController storeController = new StoreController();
        InputController inputController = new InputController();
        Map<String, Integer> purchaseInput = inputController.getPurchaseInput();
        storeController.run(purchaseInput);
    }
}
