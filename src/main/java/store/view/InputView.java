package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.dto.InventoryDto;
import store.enums.Messages;
import store.utils.ExceptionUtils;

public class InputView {
    public static void showInventory(InventoryDto inventoryDto) {
        System.out.println("안녕하세요. W편의점입니다.\n" +
                "현재 보유하고 있는 상품입니다.\n");
        System.out.println(inventoryDto.toString());
    }

    public String requestPurchase() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public static boolean requestYorN(String message){
        while (true){
            try{
                System.out.println(message);
                String input = Console.readLine().trim();
                if (input.equals("Y")||input.equals("y")) {
                    return true;
                }
                if (input.equals("N")||input.equals("n")) {
                    return false;
                }
                throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
            } catch (IllegalArgumentException e) {
                ExceptionUtils.showException(e);
            }
        }

    }
}
