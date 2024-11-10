package store.context;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
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

    @DisplayName("상품명이 적혀있지 않은 경우 예외가 발생한다")
    @Test
    void productNameExceptionTest() {
        // given
        Path nameFailTest = Paths.get("src/test/resources/productsFailName.md");

        // when & then
        ContextProductLoader contextProductLoader = new ContextProductLoader();
        Assertions.assertThatThrownBy(() -> contextProductLoader.initializeProducts(nameFailTest))
                .isInstanceOf(DataValidationException.class)
                .hasMessageContaining("[ERROR] 상품 이름이 잘못 등록되어 있습니다.");

    }

}
