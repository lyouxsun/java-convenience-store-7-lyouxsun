package store;

import store.context.ConfigProduct;
import store.context.ContextProductLoader;
import store.context.ContextPromotionLoader;
import store.controller.InputController;
import store.controller.StoreController;
import store.domain.Product;
import store.domain.Promotion;
import store.dto.ProductDto;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.PurchaseService;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static store.context.ConfigProduct.*;
import static store.enums.FilePath.PRODUCTS_FILE;
import static store.enums.FilePath.PROMOTION_FILE;

public class Application {
    public static void main(String[] args) {
        PromotionRepository promotionRepository = getPromotionRepository();
        Map<String, ProductDto> productDtos = getProductDtos(promotionRepository);
        ProductRepository productRepository = getProductRepository(promotionRepository, productDtos);

        PurchaseService purchaseService = new PurchaseService(productRepository);
        StoreController storeController = new StoreController(purchaseService);
        InputController inputController = new InputController(purchaseService);
        Map<String, Integer> purchaseInput = inputController.getPurchaseInput();
        storeController.run(purchaseInput);
    }

    private static PromotionRepository getPromotionRepository() {
        return new ContextPromotionLoader()
                .initializePromotions(Paths.get(PROMOTION_FILE.path()));
    }

    private static Map<String, ProductDto> getProductDtos(PromotionRepository promotionRepository) {
        return new ContextProductLoader()
                .initializeProducts(Paths.get(PRODUCTS_FILE.path()));
    }

}
