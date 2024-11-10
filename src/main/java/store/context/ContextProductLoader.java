package store.context;

import store.domain.Product;
import store.repository.ProductRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ContextProductLoader {

    public ProductRepository initializeProducts(Path productsList) {
        ProductRepository productRepository = new ProductRepository();
        try {
            Files.lines(productsList)
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(Product::from)
                    .forEach(productRepository::addProduct);
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 상품 목록이 잘못된 형식으로 되어 있습니다.");
        }
        return productRepository;
    }
}
