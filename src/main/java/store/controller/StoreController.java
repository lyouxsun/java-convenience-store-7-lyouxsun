package store.controller;

import store.dto.InventoryDto;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.util.*;

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
        inputView.showInventory(inventoryDto);
        String buy = inputView.requestPurchase();
        Map<String, String> inputs = validateBuy(buy);
    }

    private Map<String, String> validateBuy(String buy) {
        Map<String, String> inputs = new HashMap<>();
        List<String> buyList = Arrays.stream(buy.split(","))
                .map(String::trim).toList();
        for (String item : buyList) {
            String[] split = validateProductInput(item);
            inputs.put(split[0], split[1]);
        }
        return inputs;
    }

    private String[] validateProductInput(String item) {
        if (item.charAt(0) != '[' || item.charAt(item.length() - 1) != ']' || !item.contains("-")) {
            throw new IllegalArgumentException("[ERROR] 구매할 상품과 수량 형식이 올바르지 않습니다.");
        }
        String validatedInput = item.substring(1, item.length() - 1);
        return validatedInput.split("-");
    }
}
