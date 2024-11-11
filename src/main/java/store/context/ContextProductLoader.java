package store.context;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
import store.dto.ProductDto;
import store.utils.ExceptionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ContextProductLoader {

    public Map<String, ProductDto> initializeProducts(Path productsList) {
        Map<String, ProductDto> productDtos;
        try {
            productDtos = loadProductContext(productsList);
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 상품 목록을 입력받을 수 없습니다.");
        } catch (DataValidationException e) {
            ExceptionUtils.showException(e);
            throw e;
        }
        return productDtos;
    }

    private static Map<String, ProductDto> loadProductContext(Path productsList) throws IOException {
        Map<String, ProductDto> productDtos = new LinkedHashMap<>();
        List<String[]> strings = Files.lines(productsList).skip(1)
                .map(line -> line.split(","))
                .toList();

        for (String[] string : strings) {
            productDtos.computeIfPresent(string[0], (key, productDto) -> {
                productDto.setInfo(string);
                return productDto;
            });

            productDtos.computeIfAbsent(string[0], key -> ProductDto.from(string));
        }
        return productDtos;
    }
}
