package store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class PromotionTest {
    @DisplayName("프로모션 정보가 정상적인 경우 성공적을 Promotion 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"반짝할인,1,1,2024-11-01,2024-11-30", "탄산2+1,2,1,2024-01-01,2024-12-31"})
    void createPromotionSuccess(String promotionInput) {
        // given
        String[] promotionInfos = promotionInput.trim().split(",");

        // when
        Promotion promotion = Promotion.from(promotionInfos);

        // then
        Assertions.assertThat(promotion.getName()).isEqualTo(promotionInfos[0]);
    }

    @DisplayName("프로모션의 가격과 수량이 정수가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"반짝할인,1j,1,2024-11-01,2024-11-30", "탄산2+1,zz2,1,2024-01-01,2024-12-31"})
    void nonIntegerExceptionTest(String promotionInput) {
        // given
        String[] promotionInfos = promotionInput.trim().split(",");

        // when & then
        assertThatThrownBy(() -> Promotion.from(promotionInfos))
                .isInstanceOf(DataValidationException.class)
                .hasMessageContaining("[ERROR] 프로모션 상품 수량은 0 이상의 정수여야 합니다.");
    }

    @DisplayName("프로모션의 날짜가 형식에 맞지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"반짝할인,1,1,ㅓ2024-11-01,2024-11-30", "탄산2+1,2,1,2024y-01m-01d,2024-12-31"})
    void nonValidDateExceptionTest(String promotionInput) {
        // given
        String[] promotionInfos = promotionInput.trim().split(",");

        // when & then
        assertThatThrownBy(() -> Promotion.from(promotionInfos))
                .isInstanceOf(DataValidationException.class)
                .hasMessageContaining("[ERROR] 프로모션 날짜가 잘못된 형식으로 등록되어 있습니다.");
    }
}
