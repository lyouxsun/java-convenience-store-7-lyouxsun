package store.view;

import store.dto.PurchaseDto;
import store.dto.ResultDto;

import java.util.Map;

public class OutputView {
    public static void showReceipt(Map<String, PurchaseDto> purchaseResult, ResultDto resultDto) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
        System.out.println("콜라\t\t3 \t3,000");
        for (Map.Entry<String, PurchaseDto> entry : purchaseResult.entrySet()) {
            String productName = entry.getKey();
            int totalNum = entry.getValue().getTotalNum();
            System.out.println(entry.getKey()+"\t\t");
        }
    }
}
