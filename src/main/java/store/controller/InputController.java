package store.controller;

import store.dto.InventoryDto;
import store.service.PurchaseService;
import store.view.InputView;

import java.util.*;

import static store.enums.ErrorMessages.INVALID_FORMAT;
import static store.enums.ErrorMessages.NON_INTEGER_QUANTITY;
import static store.utils.ExceptionUtils.showException;

public class InputController {

    private final InputView inputView;
    private final PurchaseService purchaseService;

    public InputController(PurchaseService purchaseService) {
        inputView = new InputView();
        this.purchaseService = purchaseService;
    }

    public void getPurchaseInput(Map<String, Integer> input) {
        showInventory();
        readInput(input);
    }

    public void showInventory() {
        InventoryDto inventoryDto = purchaseService.findAllInventory();
        inputView.showInventory(inventoryDto);
    }

    private void readInput(Map<String, Integer> input) {
        while (true) {
            try {
                String buy = inputView.requestPurchase();
                Map<String, Integer> currentInputs = validateBuy(buy);
                purchaseService.validateInputs(currentInputs);
                putNewItem(input, currentInputs);
                return;
            } catch (IllegalArgumentException e) {
                showException(e);
            }
        }
    }

    private void putNewItem(Map<String, Integer> input, Map<String, Integer> currentInputs) {
        for (Map.Entry<String, Integer> entry : currentInputs.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if (input.containsKey(key)) {
                input.put(key, input.get(key) + value);
                continue;
            }
            input.put(key, value);
        }
    }

    public Map<String, Integer> validateBuy(String buyInput) {
        Map<String, Integer> inputs = new LinkedHashMap<>();
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
        if (rawAmount.matches("\\d+")) {
            return Integer.parseInt(rawAmount);
        }
        throw new IllegalArgumentException(NON_INTEGER_QUANTITY.getMessage());
    }

    private String[] validateProductInput(String item) {
        if (item.charAt(0) != '[' || item.charAt(item.length() - 1) != ']' || !item.contains("-")) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }
        String validatedInput = item.substring(1, item.length() - 1);
        return validatedInput.split("-");
    }
}
