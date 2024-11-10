package store.context;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
import store.domain.Product;
import store.repository.ProductRepository;
import store.utils.ExceptionUtils;

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
            throw new IllegalArgumentException("[ERROR] 상품 목록을 입력받을 수 없습니다.");
        } catch (DataValidationException e){
            ExceptionUtils.showException(e);
        }
        return productRepository;
    }
}
