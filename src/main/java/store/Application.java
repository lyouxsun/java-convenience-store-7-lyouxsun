package store;

import store.controller.InputController;
import store.controller.StoreController;

import java.util.Map;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController();
        InputController inputController = new InputController();
        Map<String, Integer> purchaseInput = inputController.getPurchaseInput();
        storeController.run(purchaseInput);
    }
}
