package store.domain.product;

import java.util.LinkedHashMap;
import java.util.Map;

public class Products {
    private final Map<String, Product> products;

    public Products(){
        this.products = new LinkedHashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getName(), product);
    }
}
