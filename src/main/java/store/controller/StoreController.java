package store.controller;

import store.dto.InventoryDto;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final StoreService storeService;
    private final InputView inputView;
    private final OutputView outputView;


    public StoreController() {
        this.storeService = new StoreService();
        inputView = new InputView();
        outputView = new OutputView();
    }

    public void run() {
        showInventory();
    }

    private void showInventory() {
        InventoryDto inventoryDto = storeService.findAllInventory();
        inputView.requestPurchase(inventoryDto);
    }
}