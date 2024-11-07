package store.context;

import store.domain.product.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

public class ContextProductLoader {
    private LinkedHashMap<Product, Long> products = new LinkedHashMap<>();

    public void initializeData() {
        try {
            Files.lines(Paths.get("src/main/resources/products.md"))
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(Product::from)
                    .forEach(product -> products.put(product, product.getQuantity()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
