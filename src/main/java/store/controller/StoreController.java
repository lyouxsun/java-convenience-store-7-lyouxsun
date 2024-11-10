package store.controller;

import store.dto.PurchaseDto;
import store.dto.ResultDto;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

import java.util.Map;

import static store.enums.Messages.MEMBERSHIP_SALE;

public class StoreController {

    private final StoreService storeService;
    private final OutputView outputView;


    public StoreController() {
        this.storeService = new StoreService();
        outputView = new OutputView();
    }

    public void run(Map<String, Integer> purchase) {
        Map<String, PurchaseDto> purchaseResult = storeService.processPurchase(purchase);
        boolean isMembershipSale = InputView.requestYorN(MEMBERSHIP_SALE.getMessage());
        ResultDto resultDto = storeService.calculateAmount(purchaseResult, isMembershipSale);
    }

}
