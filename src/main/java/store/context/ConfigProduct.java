package store.context;

import store.domain.Product;
import store.domain.Promotion;
import store.dto.ProductDto;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.util.Map;

public class ConfigProduct {
    public static ProductRepository getProductRepository(PromotionRepository promotionRepository, Map<String, ProductDto> productDtos) {
        ProductRepository productRepository = new ProductRepository();
        for (ProductDto productDto : productDtos.values()) {
            Promotion promotion = promotionRepository.findByName(productDto.getPromotion());
            Product product = Product.from(productDto, promotion);
            productRepository.addProduct(product);
        }
        return productRepository;
    }
}
