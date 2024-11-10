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
        Path promotionSuccess = Paths.get("src/test/resources/promotionsSuccess.md");

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
        Path nameFailTest = Paths.get("src/test/resources/promotionsFailName.md");

        // when & then
        ContextPromotionLoader contextPromotionLoader = new ContextPromotionLoader();
        Assertions.assertThatThrownBy(() -> contextPromotionLoader.initializePromotions(nameFailTest))
                .isInstanceOf(DataValidationException.class)
                .hasMessageContaining("[ERROR] 프로모션 이름이 잘못 등록되어 있습니다.");
    }
}
