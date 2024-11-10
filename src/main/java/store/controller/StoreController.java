package store.controller;

import store.service.StoreService;
import store.view.OutputView;

import java.util.Map;

public class StoreController {

    private final StoreService storeService;
    private final OutputView outputView;


    public StoreController() {
        this.storeService = new StoreService();
        outputView = new OutputView();
    }

    public void run(Map<String, Integer> purchaseInput) {

    }

}
