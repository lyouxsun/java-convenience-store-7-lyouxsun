package store.controller;

import store.dto.InventoryDto;
import store.service.StoreService;
import store.view.InputView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static store.utils.ExceptionUtils.showException;

public class InputController {

    private final InputView inputView;
    private final StoreService storeService;

    public InputController() {
        inputView = new InputView();
        storeService = new StoreService();
    }

    public Map<String, Integer> getPurchaseInput() {
        showInventory();
        return readInput();
    }

    public void showInventory() {
        InventoryDto inventoryDto = storeService.findAllInventory();
        inputView.showInventory(inventoryDto);
    }

    private Map<String, Integer> readInput() {
        while (true) {
            try {
                String buy = inputView.requestPurchase();
                Map<String, Integer> inputs = validateBuy(buy);
                return storeService.validateInputs(inputs);
            } catch (IllegalArgumentException e) {
                showException(e);
            }
        }
    }

    public Map<String, Integer> validateBuy(String buyInput) {
        Map<String, Integer> inputs = new HashMap<>();
        List<String> buy = Arrays.stream(buyInput.split(","))
                .map(String::trim).toList();
        for (String item : buy) {
            String[] split = validateProductInput(item);
            int amount = validateAmount(split[1]);
            inputs.put(split[0], amount);
        }
        return inputs;
    }

    private static int validateAmount(String rawAmount) {
        try {
            return Integer.parseInt(rawAmount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private String[] validateProductInput(String item) {
        if (item.charAt(0) != '[' || item.charAt(item.length() - 1) != ']' || !item.contains("-")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
        String validatedInput = item.substring(1, item.length() - 1);
        return validatedInput.split("-");
    }
}
