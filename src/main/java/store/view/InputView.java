package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.dto.InventoryDto;

public class InputView {
    public void showInventory(InventoryDto inventoryDto) {
        System.out.println("안녕하세요. W편의점입니다.\n" +
                "현재 보유하고 있는 상품입니다.\n");
        System.out.println(inventoryDto.toString());
    }

    public String requestPurchase() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }
}
