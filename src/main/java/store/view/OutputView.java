package store.view;

import store.dto.PurchaseDto;
import store.dto.ResultDto;

import java.util.Map;

public class OutputView {
    public static void showReceipt(Map<String, PurchaseDto> purchaseResult, ResultDto resultDto) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t\t\t수량\t\t  금액");

        int totalPurchaseNum = 0;
        long totalPurchaseAmount = 0;

        for (Map.Entry<String, PurchaseDto> entry : purchaseResult.entrySet()) {
            String productName = entry.getKey();
            int totalNum = entry.getValue().getTotalNum();
            totalPurchaseNum += totalNum;
            String amount = String.format("%,d", entry.getValue().getTotalPrice());
            System.out.println(productName + "\t\t\t\t" + totalNum + "\t\t  " + amount);
        }
        System.out.println("=============증\t\t정===============");
        for (Map.Entry<String, PurchaseDto> entry : purchaseResult.entrySet()) {
            PurchaseDto purchaseDto = entry.getValue();
            if (purchaseDto.getSetNum() > 0) {
                String productName = entry.getKey();
                int setNum = entry.getValue().getSetNum();
                System.out.println(productName + "\t\t\t\t" + setNum);
            }
        }
        System.out.println("====================================");


        System.out.println("총구매액\t\t\t\t" + totalPurchaseNum + "\t\t  " + resultDto.getTotalAmount());
        System.out.println("행사할인\t\t\t\t\t\t  -" + resultDto.getPromotionAmount());
        System.out.println("멤버십할인\t\t\t\t\t\t  -" + resultDto.getMembershipAmount());
        System.out.println("내실돈\t\t\t\t\t\t  " + resultDto.getTotalPaymentAmount());
    }
}
