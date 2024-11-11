package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.dto.InventoryDto;
import store.utils.ExceptionUtils;

import static store.enums.ErrorMessages.INVALID_INPUT;
import static store.enums.Messages.GREETING;
import static store.enums.Messages.REQUEST_PURCHASE;

public class InputView {
    public static void showInventory(InventoryDto inventoryDto) {
        System.out.println(GREETING.getMessage());
        System.out.println(inventoryDto.toString());
    }

    public String requestPurchase() {
        System.out.println(REQUEST_PURCHASE.getMessage());
        return Console.readLine();
    }

    public static boolean requestYorN(String message) {
        while (true) {
            try {
                System.out.println(message);
                String input = Console.readLine().trim();
                return validateYesNoInput(input);
            } catch (IllegalArgumentException e) {
                ExceptionUtils.showException(e);
            }
        }
    }
    private static boolean validateYesNoInput(String input) {
        if (input.equalsIgnoreCase("Y")) {
            return true;
        }
        if (input.equalsIgnoreCase("N")) {
            return false;
        }
        throw new IllegalArgumentException(INVALID_INPUT.getMessage());
    }

}
