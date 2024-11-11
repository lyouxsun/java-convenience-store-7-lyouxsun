package store.controller;

import store.dto.PurchaseDto;
import store.dto.ResultDto;
import store.service.MembershipService;
import store.service.PurchaseService;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

import static store.enums.Messages.MEMBERSHIP_SALE;

public class StoreController {

    private final PurchaseService purchaseService;
    private final MembershipService membershipService;
    private final OutputView outputView;


    public StoreController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
        this.membershipService = new MembershipService();
        outputView = new OutputView();
    }

    public void run(Map<String, Integer> purchase) {
        Map<String, PurchaseDto> purchaseResult = purchaseService.processPurchase(purchase);
        boolean isMembershipSale = InputView.requestYorN(MEMBERSHIP_SALE.getMessage());
        ResultDto resultDto = membershipService.calculateAmount(purchaseResult, isMembershipSale);
        outputView.showReceipt(purchaseResult, resultDto);
    }

}
