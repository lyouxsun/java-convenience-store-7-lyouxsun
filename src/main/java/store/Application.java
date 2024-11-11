package store;

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

import static store.enums.FilePath.PRODUCTS_FILE;
import static store.enums.FilePath.PROMOTION_FILE;

public class Application {
    public static void main(String[] args) {
        PromotionRepository promotionRepository = new ContextPromotionLoader()
                .initializePromotions(Paths.get(PROMOTION_FILE.path()));
        List<ProductDto> productDtos = new ContextProductLoader()
                .initializeProducts(Paths.get(PRODUCTS_FILE.path()), promotionRepository);
        ProductRepository productRepository = getProductRepository(promotionRepository, productDtos);

        PurchaseService purchaseService = new PurchaseService(productRepository);
        StoreController storeController = new StoreController(productRepository);
        InputController inputController = new InputController(purchaseService);
        Map<String, Integer> purchaseInput = inputController.getPurchaseInput();
        storeController.run(purchaseInput);
    }

    private static ProductRepository getProductRepository(PromotionRepository promotionRepository, List<ProductDto> productDtos) {
        ProductRepository productRepository = new ProductRepository();
        for (ProductDto productDto : productDtos) {
            Promotion promotion = promotionRepository.findByName(productDto.getPromotion());
            Product product = Product.from(productDto, promotion);
            productRepository.addProduct(product);
        }
        return productRepository;
    }
}
