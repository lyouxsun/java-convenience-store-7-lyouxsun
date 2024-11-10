package store.context;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
import store.repository.PromotionRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContextPromotionLoaderTest {
    @DisplayName("프로모션 정보가 정상적으로 입력된 경우")
    @Test
    void promotionsLoadSuccessTest() throws IOException {
        // given
        Path promotionSuccess = Paths.get("src/test/resources/promotions/promotionsSuccess.md");

        // when
        PromotionRepository promotionRepository = new ContextPromotionLoader().initializePromotions(promotionSuccess);
        int promotionsCount = promotionRepository.getSize();

        // then
        long lineCount = Files.lines(promotionSuccess).count();
        Assertions.assertThat(promotionsCount).isEqualTo(lineCount - 1);
    }

    @DisplayName("프로모션 이름이 적혀있지 않은 경우 예외가 발생한다")
    @Test
    void promotionNameExceptionTest() {
        // given
        Path nameFailTest = Paths.get("src/test/resources/promotions/promotionsFailName.md");

        // when & then
        ContextPromotionLoader contextPromotionLoader = new ContextPromotionLoader();
        Assertions.assertThatThrownBy(() -> contextPromotionLoader.initializePromotions(nameFailTest))
                .isInstanceOf(DataValidationException.class)
                .hasMessageContaining("[ERROR] 프로모션 이름이 잘못 등록되어 있습니다.");
    }

    @DisplayName("프로모션 수량이 정수가 아니면 오류가 발생한다.")
    @Test
    void promotionQuantityExceptionTest() {
        // given
        Path quantityFailTest = Paths.get("src/test/resources/promotions/promotionsFailQuantity.md");

        // when & then
        ContextPromotionLoader contextPromotionLoader = new ContextPromotionLoader();
        Assertions.assertThatThrownBy(() -> contextPromotionLoader.initializePromotions(quantityFailTest))
                .isInstanceOf(DataValidationException.class)
                .hasMessageContaining("[ERROR] 프로모션 상품 수량은 0 이상의 정수여야 합니다.");
    }

    @DisplayName("프로모션 날짜 형식이 잘못되어 있으면 오류가 발생한다.")
    @Test
    void promotionDateExceptionTest() {
        // given
        Path dateFailTest = Paths.get("src/test/resources/promotions/promotionsFailDate.md");

        // when & then
        ContextPromotionLoader contextPromotionLoader = new ContextPromotionLoader();
        Assertions.assertThatThrownBy(() -> contextPromotionLoader.initializePromotions(dateFailTest))
                .isInstanceOf(DataValidationException.class)
                .hasMessageContaining("[ERROR] 프로모션 날짜가 잘못된 형식으로 등록되어 있습니다.");
    }
}
