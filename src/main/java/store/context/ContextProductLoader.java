package store.context;

import store.domain.product.Product;
import store.domain.product.Products;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContextProductLoader {

    public Products initializeProducts() {
        Products products = new Products();
        try {
            Files.lines(Paths.get("src/main/resources/products.md"))
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(Product::from)
                    .forEach(products::addProduct);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
