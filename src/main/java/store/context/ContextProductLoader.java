package store.context;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
import store.dto.ProductDto;
import store.repository.PromotionRepository;
import store.utils.ExceptionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ContextProductLoader {

    public List<ProductDto> initializeProducts(Path productsList, PromotionRepository promotionRepository) {
        List<ProductDto> productDtos = new ArrayList<>();
        try {
            productDtos = loadProductContext(productsList, promotionRepository);
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 상품 목록을 입력받을 수 없습니다.");
        } catch (DataValidationException e) {
            ExceptionUtils.showException(e);
            throw e;
        }
        return productDtos;
    }

    private static List<ProductDto> loadProductContext(Path productsList, PromotionRepository promotionRepository) throws IOException {
        return Files.lines(productsList)
                .skip(1)
                .map(line -> line.split(","))
                .map(ProductDto::new)
                .toList();
    }
}
