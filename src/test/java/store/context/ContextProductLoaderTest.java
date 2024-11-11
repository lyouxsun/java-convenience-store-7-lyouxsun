package store.context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
import store.dto.ProductDto;
import store.repository.PromotionRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ContextProductLoaderTest {

    private PromotionRepository promotionRepository;

    @BeforeEach
    void setUp() throws IOException {
        Path promotionTest = Paths.get("src/test/resources/promotions/promotionsSuccess.md");
        this.promotionRepository = new ContextPromotionLoader().initializePromotions(promotionTest);
    }

    @DisplayName("상품 목록이 정상적으로 입력된 경우")
    @Test
    void productLoadSuccessTest() throws IOException {
        // given
        Path productsTest = Paths.get("src/test/resources/products/productsSuccess.md");

        // when
        Map<String, ProductDto> productDtos = new ContextProductLoader().initializeProducts(productsTest);
        int productsCount = productDtos.size();

        // then
        Set<String> names = Files.lines(productsTest)
                .skip(1)
                .map(line -> line.split(",")[0])
                .collect(Collectors.toSet());

        assertThat(productsCount).isEqualTo(names.size());
    }

    @DisplayName("상품명이 적혀있지 않은 경우 예외가 발생한다")
    @Test
    void productNameExceptionTest() {
        // given
        Path nameFailTest = Paths.get("src/test/resources/products/productsFailName.md");

        // when & then
        ContextProductLoader contextProductLoader = new ContextProductLoader();
        assertThatThrownBy(() -> contextProductLoader.initializeProducts(nameFailTest))
                .isInstanceOf(DataValidationException.class)
                .hasMessageContaining("[ERROR] 상품 이름이 잘못 등록되어 있습니다.");
    }

    @DisplayName("상품 수량이 정수가 아니면 오류가 발생한다.")
    @Test
    void productQuantityExceptionTest() {
        // given
        Path productsTest = Paths.get("src/test/resources/products/productsFailQuantity.md");

        // when & then
        ContextProductLoader contextProductLoader = new ContextProductLoader();
        assertThatThrownBy(() -> contextProductLoader.initializeProducts(productsTest))
                .isInstanceOf(DataValidationException.class)
                .hasMessageContaining("[ERROR] 상품 가격과 수량은 0 이상의 정수여야 합니다.");
    }
}
