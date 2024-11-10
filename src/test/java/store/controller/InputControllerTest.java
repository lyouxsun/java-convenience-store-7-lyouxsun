package store.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class InputControllerTest {

    private final InputController inputController = new InputController();

    @DisplayName("구매 수량이 형식에 맞게 입력된 경우 성공적으로 처리한다")
    @ParameterizedTest
    @ValueSource(strings = {"[사이다-5],[감자칩-1]", "[비타민워터-6]", "[탄산수-6]"})
    void validateSuccessTest(String buy) {

        // when
        int expectedSize = buy.split(",").length;
        Map<String, Integer> purchases = inputController.validateBuy(buy);

        // then
        assertThat(purchases).hasSize(expectedSize);
    }
}
