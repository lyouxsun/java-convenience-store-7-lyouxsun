package store.repository;

import store.domain.Product;
import store.dto.InventoryDto;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

public class ProductRepository {
    private final Set<Product> products;

    public ProductRepository() {
        this.products = new LinkedHashSet();
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
                .anyMatch(product -> product.getName().equals(name));
        if (!isPresent) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .mapToLong(Product::getAmount)
                .sum();
    }
}
