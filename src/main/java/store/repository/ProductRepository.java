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

    public InventoryDto productToDto(){
        StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            sb.append(product.toString());
        }
        return new InventoryDto(sb.toString());
    }
}
