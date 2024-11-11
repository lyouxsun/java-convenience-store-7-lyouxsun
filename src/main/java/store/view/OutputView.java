package store.view;

import store.dto.PurchaseDto;
import store.dto.ResultDto;

import java.util.Map;

import static store.enums.ReceiptMessages.*;

public class OutputView {
    public void showReceipt(Map<String, PurchaseDto> purchaseResult, ResultDto resultDto) {
        showHeader();
        int totalPurchaseNum = purchaseResult.values().stream()
                .mapToInt(PurchaseDto::getTotalNum)
                .sum();
        showProducts(purchaseResult);
        showPromotions(purchaseResult);
        showResult(totalPurchaseNum, resultDto);
    }

    private void showHeader() {
        System.out.println(STORE_INFO.getMessage());
        System.out.println(ITEM_LIST_HEADER.formatHead());
    }

    private void showProducts(Map<String, PurchaseDto> purchaseResult) {
        for (Map.Entry<String, PurchaseDto> entry : purchaseResult.entrySet()) {
            String productName = entry.getKey();
            PurchaseDto purchaseDto = entry.getValue();
            long amount = purchaseDto.getTotalPrice();
            System.out.println(ITEM_LIST_FORMAT.format(productName, purchaseDto.getTotalNum(), amount));
        }
    }

    private void showPromotions(Map<String, PurchaseDto> purchaseResult) {
        System.out.println(PROMOTION_LIST_HEADER.getMessage());
        purchaseResult.entrySet().stream()
                .filter(entry -> entry.getValue().getSetNum() > 0)
                .forEach(this::printPromotionItem);
    }

    private void printPromotionItem(Map.Entry<String, PurchaseDto> entry) {
        System.out.println(PROMOTION_LIST_FORMAT.format(entry.getKey(), entry.getValue().getSetNum()));
    }

    private void showResult(int totalPurchaseNum, ResultDto resultDto) {
        System.out.println(LINE.getMessage());
        System.out.println(TOTAL_AMOUNT_INFO.format(totalPurchaseNum, resultDto.getTotalAmount()));
        System.out.println(PROMOTION_AMOUNT_INFO.formatNegative(resultDto.getPromotionAmount()));
        System.out.println(MEMBERSHIP_AMOUNT_INFO.formatNegative(resultDto.getMembershipAmount()));
        System.out.println(TOTAL_PAYMENT_AMOUNT_INFO.format(resultDto.getTotalPaymentAmount()));
    }

}
