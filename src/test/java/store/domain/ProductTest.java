package store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
import store.context.ContextPromotionLoader;
import store.dto.ProductDto;
import store.repository.PromotionRepository;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ProductTest {

    private PromotionRepository promotionRepository;

    @BeforeEach
    public void setUp() {
        String promotionsPath = "src/test/resources/promotions/promotionsSuccess.md";
        Path promotionsFile = Paths.get(promotionsPath);
        promotionRepository = new ContextPromotionLoader().initializePromotions(promotionsFile);
    }

    @DisplayName("상품 정보가 정상적인 경우 성공적으로 Product 객체를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"콜라,100012,10,탄산2+1", "사이다,12000,700,null"})
    void createProductSuccess(String productInput) {
        // given
        String[] productInfos = productInput.trim().split(",");
        ProductDto productDto = ProductDto.from(productInfos);
        Promotion promotion = promotionRepository.findByName(productDto.getPromotion());

        // when
        Product product = Product.from(productDto, promotion);

        // then
        Assertions.assertThat(product.getName()).isEqualTo(productInfos[0]);
        Assertions.assertThat(product.getPrice()).isEqualTo(Long.parseLong(productInfos[1]));
    }

    @DisplayName("상품의 가격과 수량이 정수가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"콜라,1000j12,10,탄산2+1", "사이다,12000,700aa,null"})
    void nonIntegerExceptionTest(String productInput) {
        // given
        String[] productInfos = productInput.trim().split(",");

        // when & then
        assertThatThrownBy(() -> ProductDto.from(productInfos))
                .isInstanceOf(DataValidationException.class)
                .hasMessageContaining("[ERROR] 상품 가격과 수량은 0 이상의 정수여야 합니다.");
    }

    @DisplayName("상품의 프로모션 정보가 있는 경우 프로모션이 있다고 저장해야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"콜라,10001,10,탄산2+1", "사이다,12000,700,반짝할인"})
    void promotionTest(String productInput) {
        // given
        String[] productInfos = productInput.trim().split(",");
        ProductDto productDto = ProductDto.from(productInfos);
        Promotion promotion = promotionRepository.findByName(productDto.getPromotion());

        // when
        Product product = Product.from(productDto, promotion);

        // then
        Assertions.assertThat(product.isPromotion()).isEqualTo(true);
        Assertions.assertThat(product.isPromotion()).isEqualTo(true);
    }

    @DisplayName("상품의 프로모션 정보가 null인 경우 프로모션이 없다고 저장해야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"콜라,10001,10,null", "사이다,12000,700,null"})
    void noPromotionTest(String productInput) {
        // given
        String[] productInfos = productInput.trim().split(",");
        ProductDto productDto = ProductDto.from(productInfos);
        Promotion promotion = promotionRepository.findByName(productDto.getPromotion());

        // when
        Product product = Product.from(productDto, promotion);

        // then
        Assertions.assertThat(product.isPromotion()).isEqualTo(false);
        Assertions.assertThat(product.isPromotion()).isEqualTo(false);
    }
}
