package store.service;

import store.context.ContextProductLoader;
import store.context.ContextPromotionLoader;
import store.dto.InventoryDto;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class StoreService {
    private static final String productsPath = "src/main/resources/products.md";
    private static final String promotionsPath = "src/main/resources/promotions.md";
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;

    public StoreService() {
        Path promotionsFile = Paths.get(promotionsPath);
        Path productsFile = Paths.get(productsPath);
        this.promotionRepository = new ContextPromotionLoader().initializePromotions(promotionsFile);
        this.productRepository = new ContextProductLoader().initializeProducts(productsFile);
    }

    public InventoryDto findAllInventory() {
        return productRepository.productsToDto();
    }

    public void validateInputs(Map<String, Integer> inputs) {
        for (Map.Entry<String, Integer> entry : inputs.entrySet()) {
            long quantity = productRepository.findQuantityByName(entry.getKey());
            if (quantity < entry.getValue()) {
                throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }
}
