package store.view;

import store.dto.InventoryDto;

public class InputView {
    public void requestPurchase(InventoryDto inventoryDto) {
        System.out.println("안녕하세요. W편의점입니다.\n" +
                "현재 보유하고 있는 상품입니다.\n");
        System.out.println(inventoryDto.toString());
    }
}
