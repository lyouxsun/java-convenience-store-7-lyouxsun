package store.repository;

import store.domain.Product;
import store.dto.InventoryDto;

import java.util.LinkedHashMap;
import java.util.Map;

import static store.enums.ErrorMessages.PRODUCT_NOT_FOUND;

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
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND.getMessage());
        }
        return product.isPurchaseAvailable(hopeQuantity);
    }


    public Product findByName(String name) {
        return products.get(name);
    }
}
