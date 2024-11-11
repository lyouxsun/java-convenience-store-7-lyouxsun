package store.service;

import store.domain.Product;
import store.dto.InventoryDto;
import store.dto.PurchaseDto;
import store.repository.ProductRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PurchaseService {
    private final ProductRepository productRepository;
    Map<Product, Integer> noPromotionProducts = new LinkedHashMap<>();

    public PurchaseService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public InventoryDto findAllInventory() {
        return productRepository.productsToDto();
    }

    public void validateInputs(Map<String, Integer> inputs) {
        for (Map.Entry<String, Integer> entry : inputs.entrySet()) {
            boolean availablePurchase = productRepository.availablePurchase(entry.getKey(), entry.getValue());
            if (!availablePurchase) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }

    public Map<String, PurchaseDto> processPurchase(Map<String, Integer> purchaseList) {
        Map<String, PurchaseDto> purchaseResult = new LinkedHashMap<>();
//        processPromotion(purchaseList, purchaseResult);
//        processNoPromotion(purchaseList, purchaseResult);
//        return purchaseResult;

        for (Map.Entry<String, Integer> entry : purchaseList.entrySet()) {
            Product product = productRepository.findByName(entry.getKey());

        }
        return null;

    }

    public void processPromotion(Map<String, Integer> purchaseList, Map<String, PurchaseDto> purchaseResult) {
        Set<Product> products = purchaseList.entrySet().stream()
                .map(purchase -> productRepository.findByNameAndPromotion(purchase.getKey(), true))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());


        this.noPromotionProducts = new LinkedHashMap<>();
        for (Product promotionProduct : products) {
            int hopeAmount = purchaseList.get(promotionProduct.getName());
            PurchaseDto purchaseDto = promotionProduct.getPromotionAmount(hopeAmount);
            purchaseResult.put(promotionProduct.getName(), purchaseDto);
//            productRepository.purchase(promotionProduct.getName(), true, purchaseDto);
        }
    }

    public void processNoPromotion(Map<String, Integer> purchaseList, Map<String, PurchaseDto> purchaseResult) {
        Set<Product> noPromotionProducts = purchaseList.keySet().stream()
                .map(productName -> {
                    Product productWithPromotion = productRepository.findByNameAndPromotion(productName, true);
                    return (productWithPromotion == null) ? productRepository.findByNameAndPromotion(productName, false) : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        for (Product noPromotionProduct : noPromotionProducts) {
            int hopeAmount = purchaseList.get(noPromotionProduct.getName());
            PurchaseDto purchaseDto = new PurchaseDto(noPromotionProduct.getPrice(), hopeAmount, 0, 0);
            purchaseResult.put(noPromotionProduct.getName(), purchaseDto);

//            productRepository.purchase(noPromotionProduct.getName(), false, purchaseDto);
        }
    }

    private Set<Product> filterByPromotion(Map<String, Integer> purchaseList, Predicate<Product> predicate) {
        return purchaseList.entrySet().stream()
                .map(purchase -> productRepository.findByNameAndPromotion(purchase.getKey(), true))
                .filter(predicate)
                .collect(Collectors.toSet());
    }
}
