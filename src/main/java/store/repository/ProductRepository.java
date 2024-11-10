package store.repository;

import store.domain.Product;

import java.util.LinkedHashSet;
import java.util.Set;

public class ProductRepository {
    private final Set products;

    public ProductRepository() {
        this.products = new LinkedHashSet();
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
