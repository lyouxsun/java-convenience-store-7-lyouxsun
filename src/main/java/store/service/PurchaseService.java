package store.service;

import store.domain.Product;
import store.dto.InventoryDto;
import store.dto.PurchaseDto;
import store.repository.ProductRepository;

import java.util.LinkedHashMap;
import java.util.Map;

import static store.enums.ErrorMessages.QUANTITY_OVERFLOW;

public class PurchaseService {
    private final ProductRepository productRepository;
    private Map<String, PurchaseDto> purchaseResult;

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
                throw new IllegalArgumentException(QUANTITY_OVERFLOW.getMessage());
            }
        }
    }

    public Map<String, PurchaseDto> processPurchase(Map<String, Integer> purchaseList) {
        this.purchaseResult = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : purchaseList.entrySet()) {
            Product product = productRepository.findByName(entry.getKey());
            updateInventory(product, entry.getValue());
        }
        return purchaseResult;

    }

    public void updateInventory(Product product, int hopeQuantity) {
        PurchaseDto purchaseDto = createPurchaseDto(product, hopeQuantity);
        purchaseResult.put(product.getName(), purchaseDto);
        int hopeAmount = purchaseDto.getTotalNum();
        product.reduceQuantity(hopeAmount);
    }

    private PurchaseDto createPurchaseDto(Product product, int hopeQuantity) {
        if (product.isPromotion()) {
            return product.getPromotionAmount(hopeQuantity);
        }
        return new PurchaseDto(product.getPrice(), hopeQuantity, 0, 0);
    }

}
