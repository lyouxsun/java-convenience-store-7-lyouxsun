package store.service;

import store.domain.Product;
import store.dto.InventoryDto;
import store.dto.PurchaseDto;
import store.repository.ProductRepository;

import java.util.LinkedHashMap;
import java.util.Map;

public class PurchaseService {
    private final ProductRepository productRepository;
    private final Map<String, PurchaseDto> purchaseResult;

    public PurchaseService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.purchaseResult = new LinkedHashMap<>();
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
        for (Map.Entry<String, Integer> entry : purchaseList.entrySet()) {
            Product product = productRepository.findByName(entry.getKey());
            processPromotion(product, entry.getValue());
        }
        return purchaseResult;

    }

    public void processPromotion(Product product, int hopeQuantity) {
        PurchaseDto purchaseDto = createPurchaseDto(product, hopeQuantity);
        purchaseResult.put(product.getName(), purchaseDto);
        productRepository.purchase(product, product.isPromotion(), purchaseDto);
    }

    private PurchaseDto createPurchaseDto(Product product, int hopeQuantity) {
        if (product.isPromotion()) {
            return product.getPromotionAmount(hopeQuantity);
        }
        return new PurchaseDto(product.getPrice(), hopeQuantity, 0, 0);
    }

}
