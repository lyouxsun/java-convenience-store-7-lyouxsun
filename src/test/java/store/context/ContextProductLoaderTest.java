package store.context;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.repository.ProductRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContextProductLoaderTest {

    @DisplayName("상품 목록이 정상적으로 입력된 경우")
    @Test
    void productLoadSuccessTest() throws IOException {
        // given
        Path productsTest = Paths.get("src/test/resources/productsSuccess.md");

        // when
        ProductRepository promotionRepository = new ContextProductLoader().initializeProducts(productsTest);
        int productsCount = promotionRepository.getSize();

        // then
        long lineCount = Files.lines(productsTest).count();
        Assertions.assertThat(productsCount).isEqualTo(lineCount - 1);
    }

}
