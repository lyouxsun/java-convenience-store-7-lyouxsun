package store.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.repository.ProductRepository;
import store.service.PurchaseService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InputControllerTest {


    PurchaseService purchaseService = new PurchaseService(new ProductRepository());
    private final InputController inputController = new InputController(purchaseService);

    @DisplayName("구매 수량이 형식에 맞게 입력된 경우 성공적으로 처리한다")
    @ParameterizedTest
    @ValueSource(strings = {"[사이다-5],[감자칩-1]", "[비타민워터-6]", "[탄산수-6]"})
    void inputSuccessTest(String buy) {

        // when
        int expectedSize = buy.split(",").length;
        Map<String, Integer> purchases = inputController.validateBuy(buy);

        // then
        assertThat(purchases).hasSize(expectedSize);
    }

    @DisplayName("구매 상품들이 []로 감싸있지 않지 않거나 상품명과 수량이 ,로 구분되어 있지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"사이다-5,감자칩-1", "비타민워터-6", "탄산수-6",
            "[사이다 5,감자칩 1]", "[비타민워터 6]", "[탄산수,6]"})
    void FormatExceptionTest(String buy) {

        // when & then
        assertThatThrownBy(() -> inputController.validateBuy(buy))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @DisplayName("구매 수량이 정수가 아닌 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"[사이다-5ㅓ],[감자칩-1a]", "[비타민워터-*6]", "[탄산수--6]"})
    void nonIntegerExceptionTest(String buy) {

        // when & then
        assertThatThrownBy(() -> inputController.validateBuy(buy))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구매 수량은 정수여야 합니다. 다시 입력해 주세요.");
    }

}
