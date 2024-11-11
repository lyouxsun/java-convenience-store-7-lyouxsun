package store.repository;

import store.domain.Product;
import store.dto.InventoryDto;
import store.dto.PurchaseDto;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class ProductRepository {
    private final Set<Product> products;

    public ProductRepository() {
        this.products = new LinkedHashSet<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public InventoryDto productsToDto() {
        StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            sb.append(product.toString());
        }
        return new InventoryDto(sb.toString());
    }

    public long findQuantityByName(String name) {
        boolean isPresent = products.stream()
                .anyMatch(product -> product.isSameName(name));
        if (!isPresent) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
        return products.stream()
                .filter(product -> product.isSameName(name))
                .mapToLong(Product::getQuantity)
                .sum();
    }

    public Product findByNameAndPromotion(String name, boolean isPromotion) {
        return products.stream()
                .filter(product -> product.isSameName(name))
                .filter(product -> product.isPromotion() == isPromotion)
                .findAny()
                .orElse(null);
    }

    public int getSize() {
        return products.size();
    }

    public void purchase(String productName, boolean isPromotion, PurchaseDto purchaseDto) {
        Product product = findByNameAndPromotion(productName, isPromotion);
        int hopeAmount = purchaseDto.getTotalNum();
        if (product.getQuantity() < hopeAmount) {
            hopeAmount -= product.getQuantity();
            product.reduceQuantity(product.getQuantity());
            Product noPromotion = findByNameAndPromotion(productName, !isPromotion);
            noPromotion.reduceQuantity(hopeAmount);
        }

    }

    public long findAmountByName(String name) {
        Optional<Product> optionalProduct = products.stream()
                .filter(product -> product.isSameName(name))
                .findFirst();
        Product product = optionalProduct.get();
        return product.getQuantity();
    }
}
