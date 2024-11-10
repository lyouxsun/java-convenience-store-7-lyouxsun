package store.service;

import store.context.ContextProductLoader;
import store.context.ContextPromotionLoader;
import store.dto.InventoryDto;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

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
}
