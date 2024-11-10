package store.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.controller.InputController;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class StoreServiceTest {

    private final InputController inputController = new InputController();
    private final StoreService storeService = new StoreService();

    @DisplayName("구매 수량이 재고보다 작거나 같은 경우 성공적으로 처리한다.")
    @ParameterizedTest
    @ValueSource(strings = {"[사이다-15],[감자칩-1]", "[비타민워터-6]", "[탄산수-5]"})
    void successTest(String buy) {
        // given
        Map<String, Integer> tests = inputController.validateBuy(buy);

        // when & then
        assertDoesNotThrow(() -> storeService.validateInputs(tests));
    }

    @DisplayName("구매 수량이 재고보다 작거나 같은 경우 성공적으로 처리한다.")
    @ParameterizedTest
    @ValueSource(strings = {"[사이다-16],[감자칩-10]", "[비타민워터-7]", "[탄산수-6]"})
    void amountOverTest(String buy) {
        // given
        Map<String, Integer> tests = inputController.validateBuy(buy);

        // when & then
        assertThatThrownBy(() -> storeService.validateInputs(tests))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }
}
