package store.service;

import store.dto.PurchaseDto;
import store.dto.ResultDto;

import java.util.Map;

import static store.enums.SaleValues.MEMBERSHIP_DISCOUNT_LIMIT;
import static store.enums.SaleValues.MEMBERSHIP_DISCOUNT_RATE;

public class MembershipService {
    public ResultDto calculateAmount(Map<String, PurchaseDto> purchaseResult, boolean isMembershipSale) {
        long totalAmount = getTotalAmount(purchaseResult);
        long promotionAmount = getPromotionAmount(purchaseResult);
        if (isMembershipSale) {
            long membershipAmount = getMembershipAmount(purchaseResult, totalAmount);
            return new ResultDto(totalAmount, promotionAmount, membershipAmount);
        }
        return new ResultDto(totalAmount, promotionAmount, 0);
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
        long promotionAmount = purchaseResult.values().stream()
                .filter(PurchaseDto::isPromotion)
                .mapToLong(PurchaseDto::getPromotionSetPrice)
                .sum();
        long membershipTarget = (long) ((totalAmount - promotionAmount) * MEMBERSHIP_DISCOUNT_RATE.valueToDouble());
        return Math.min(membershipTarget, MEMBERSHIP_DISCOUNT_LIMIT.valueToLong());
    }
}
