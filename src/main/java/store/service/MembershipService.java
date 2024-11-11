package store.service;

import store.dto.PurchaseDto;
import store.dto.ResultDto;

import java.util.Map;

public class MembershipService {
    public ResultDto calculateAmount(Map<String, PurchaseDto> purchaseResult, boolean isMembershipSale) {

        long totalAmount = getTotalAmount(purchaseResult);
        long promotionAmount = getPromotionAmount(purchaseResult);
        long membershipAmount = 0;
        if (isMembershipSale) {
            membershipAmount = getMembershipAmount(purchaseResult, totalAmount);
        }
        return new ResultDto(totalAmount, promotionAmount, membershipAmount);
    }

    private long getTotalAmount(Map<String, PurchaseDto> purchaseResult) {
        return purchaseResult.values().stream()
                .mapToLong(PurchaseDto::getTotalPrice)
                .sum();
    }

    private long getPromotionAmount(Map<String, PurchaseDto> purchaseResult) {
        return purchaseResult.values().stream()
                .filter(PurchaseDto::isPromotion)
                .mapToLong(PurchaseDto::promotionSalePrice)
                .sum();
    }


    private long getMembershipAmount(Map<String, PurchaseDto> purchaseResult, long totalAmount) {
        long membershipTarget = totalAmount;
        long promotionAmount = purchaseResult.values().stream()
                .filter(PurchaseDto::isPromotion)
                .mapToLong(PurchaseDto::getPromotionSetPrice)
                .sum();
        membershipTarget -= promotionAmount;

        membershipTarget *= 0.3;
        if (membershipTarget < 8_000L){
            return membershipTarget;
        }
        return 8_000;
    }
}
