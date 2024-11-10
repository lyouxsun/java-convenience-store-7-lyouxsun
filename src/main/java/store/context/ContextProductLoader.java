package store.context;

import store.domain.Product;
import store.repository.ProductRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContextProductLoader {

    public ProductRepository initializeProducts() {
        ProductRepository productRepository = new ProductRepository();
        try {
            Files.lines(Paths.get("src/main/resources/products.md"))
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(Product::from)
                    .forEach(productRepository::addProduct);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productRepository;
    }
}
