package store.service;

import store.context.ContextProductLoader;
import store.context.ContextPromotionLoader;
import store.dto.InventoryDto;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.util.Map;

public class StoreService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public StoreService() {
        this.productRepository = new ContextProductLoader().initializeProducts();
        this.promotionRepository = new ContextPromotionLoader().initializePromotions();
    }

    public InventoryDto findAllInventory() {
        return productRepository.productsToDto();
    }

    public Map<String, Integer> validateInputs(Map<String, Integer> inputs) {
        for (Map.Entry<String, Integer> entry : inputs.entrySet()) {
            long quantity = productRepository.findQuantityByName(entry.getKey());
            if (quantity < entry.getValue()) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요");
            }
        }
        return inputs;
    }
}
