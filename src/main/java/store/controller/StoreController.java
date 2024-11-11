package store.controller;

import store.dto.PurchaseDto;
import store.dto.ResultDto;
import store.service.MembershipService;
import store.service.PurchaseService;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

import static store.enums.Messages.MEMBERSHIP_SALE;
import static store.enums.Messages.PURCHASE_MORE;

public class StoreController {

    private final PurchaseService purchaseService;
    private final MembershipService membershipService;
    private final OutputView outputView;


    public StoreController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
        this.membershipService = new MembershipService();
        outputView = new OutputView();
    }

    public boolean run(Map<String, PurchaseDto> results, Map<String, Integer> purchase) {
        Map<String, PurchaseDto> purchaseResult = purchaseService.processPurchase(purchase);
        boolean isMembershipSale = InputView.requestYorN(MEMBERSHIP_SALE.getMessage());
        updateResults(results, purchaseResult);
        ResultDto resultDto = membershipService.calculateAmount(results, isMembershipSale);
        outputView.showReceipt(results, resultDto);
        return InputView.requestYorN(PURCHASE_MORE.getMessage());
    }

    private void updateResults(Map<String, PurchaseDto> results, Map<String, PurchaseDto> purchaseResult) {
        for (Map.Entry<String, PurchaseDto> stringPurchaseDtoEntry : purchaseResult.entrySet()) {
            String productName = stringPurchaseDtoEntry.getKey();
            PurchaseDto newPurchaseDto = stringPurchaseDtoEntry.getValue();
            if (results.containsKey(productName)) {
                update(results, productName, newPurchaseDto);
                continue;
            }
            results.put(productName, newPurchaseDto);
        }
    }

    private static void update(Map<String, PurchaseDto> results, String productName, PurchaseDto newPurchaseDto) {
        PurchaseDto purchaseDto = results.get(productName);
        purchaseDto.update(newPurchaseDto);
        results.put(productName, purchaseDto);
    }

}
