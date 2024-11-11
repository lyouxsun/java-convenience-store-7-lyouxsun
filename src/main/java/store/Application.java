package store;

import store.context.ContextProductLoader;
import store.context.ContextPromotionLoader;
import store.controller.InputController;
import store.controller.StoreController;
import store.dto.ProductDto;
import store.dto.PurchaseDto;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.PurchaseService;

import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import static store.context.ConfigProduct.getProductRepository;
import static store.enums.FilePath.PRODUCTS_FILE;
import static store.enums.FilePath.PROMOTION_FILE;

public class Application {

    public static void main(String[] args) {
        PromotionRepository promotionRepository = getPromotionRepository();
        Map<String, ProductDto> productDtos = getProductDtos(promotionRepository);
        Map<String, PurchaseDto> results = new LinkedHashMap<>();
        ProductRepository productRepository = getProductRepository(promotionRepository, productDtos);

        PurchaseService purchaseService = new PurchaseService(productRepository);
        StoreController storeController = new StoreController(purchaseService);
        InputController inputController = new InputController(purchaseService);
        while (true){
            Map<String, Integer> hopePurchase = inputController.getPurchaseInput();
            if (!storeController.run(results, hopePurchase)){
                break;
            }
        }
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
