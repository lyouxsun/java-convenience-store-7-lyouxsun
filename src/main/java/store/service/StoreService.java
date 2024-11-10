package store.service;

import store.context.ContextProductLoader;
import store.context.ContextPromotionLoader;
import store.domain.Product;
import store.dto.InventoryDto;
import store.dto.PurchaseDto;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class StoreService {
    private static final String productsPath = "src/main/resources/products.md";
    private static final String promotionsPath = "src/main/resources/promotions.md";
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    Map<Product, Integer> noPromotionProducts = new LinkedHashMap<>();

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

    public Map<String, PurchaseDto> processPurchase(Map<String, Integer> purchaseList) {
        Map<String, PurchaseDto> purchaseResult = new LinkedHashMap<>();
        processPromotion(purchaseList, purchaseResult);
        processNoPromotion(purchaseList, purchaseResult);
        return purchaseResult;
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
            productRepository.purchase(promotionProduct.getName(), true, purchaseDto);
        }
    }

    public void processNoPromotion(Map<String, Integer> purchaseList, Map<String, PurchaseDto> purchaseResult) {
        Set<Product> noPromotionProducts = purchaseList.entrySet().stream()
                .map(purchase -> productRepository.findByNameAndPromotion(purchase.getKey(), true))
                .filter(Objects::isNull)
                .collect(Collectors.toSet());

        for (Product noPromotionProduct : noPromotionProducts) {
            int hopeAmount = purchaseList.get(noPromotionProduct.getName());
            PurchaseDto purchaseDto = new PurchaseDto(hopeAmount, 0, 0);
            purchaseResult.put(noPromotionProduct.getName(), purchaseDto);
            productRepository.purchase(noPromotionProduct.getName(), false, purchaseDto);
        }
    }

}
