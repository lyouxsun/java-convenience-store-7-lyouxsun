package store.repository;

import store.domain.Product;
import store.dto.InventoryDto;
import store.dto.PurchaseDto;

import java.util.*;

public class ProductRepository {
    private final Map<String, Product> products;

    public ProductRepository() {
        this.products = new LinkedHashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getName(), product);
    }

    public InventoryDto productsToDto() {
        StringBuilder sb = new StringBuilder();
        for (Product product : products.values()) {
            sb.append(product.toString());
        }
        return new InventoryDto(sb.toString());
    }

    public boolean availablePurchase(String name, int hopeQuantity) {
        Product product = products.get(name);
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
        return product.isPurchaseAvailable(hopeQuantity);
    }


    public Product findByName(String name) {
        return products.get(name);
    }
}
