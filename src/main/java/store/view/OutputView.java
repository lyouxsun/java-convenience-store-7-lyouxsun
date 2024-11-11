package store.view;

import store.dto.PurchaseDto;
import store.dto.ResultDto;

import java.util.Map;

import static store.enums.ReceiptMessages.*;

public class OutputView {
    public static void showReceipt(Map<String, PurchaseDto> purchaseResult, ResultDto resultDto) {
        showHeader();
        int totalPurchaseNum = showProducts(purchaseResult);
        showPromotions(purchaseResult);
        showResult(totalPurchaseNum, resultDto);
    }

    private static void showHeader() {
        System.out.println(STORE_INFO.getMessage());
        System.out.println(ITEM_LIST_HEADER.getMessage());
    }

    private static int showProducts(Map<String, PurchaseDto> purchaseResult) {
        int totalPurchaseNum = 0;
        for (Map.Entry<String, PurchaseDto> entry : purchaseResult.entrySet()) {
            String productName = entry.getKey();
            int totalNum = entry.getValue().getTotalNum();
            totalPurchaseNum += totalNum;
            String amount = String.format("%,d", entry.getValue().getTotalPrice());
            System.out.println(ITEM_LIST_FORMAT.format(productName, totalNum , amount));
        }
        return totalPurchaseNum;
    }

    private static void showPromotions(Map<String, PurchaseDto> purchaseResult) {
        System.out.println(PROMOTION_LIST_HEADER.getMessage());
        for (Map.Entry<String, PurchaseDto> entry : purchaseResult.entrySet()) {
            PurchaseDto purchaseDto = entry.getValue();
            if (purchaseDto.getSetNum() > 0) {
                String productName = entry.getKey();
                int setNum = entry.getValue().getSetNum();
                System.out.println(PROMOTION_LIST_FORMAT.format(productName, setNum));
            }
        }
    }

    private static void showResult(int totalPurchaseNum, ResultDto resultDto) {
        System.out.println(LINE.getMessage());
        System.out.println();
        System.out.println(TOTAL_AMOUNT_INFO.format(totalPurchaseNum, resultDto.getTotalAmount()));
        System.out.println(PROMOTION_AMOUNT_INFO.format(resultDto.getPromotionAmount()));
        System.out.println(MEMBERSHIP_AMOUNT_INFO.format(resultDto.getMembershipAmount()));
        System.out.println(TOTAL_PAYMENT_AMOUNT_INFO.format(resultDto.getTotalPaymentAmount()));

    }
}
