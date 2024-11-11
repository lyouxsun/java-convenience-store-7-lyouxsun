package store.context;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
import store.domain.Promotion;
import store.repository.PromotionRepository;
import store.utils.ExceptionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static store.enums.ErrorMessages.PROMOTION_FILE_ERROR;

public class ContextPromotionLoader {

    public PromotionRepository initializePromotions(Path path) {
        PromotionRepository promotionRepository = new PromotionRepository();
        try {
            loadPromotionContext(path, promotionRepository);
        } catch (IOException e) {
            throw new IllegalArgumentException(PROMOTION_FILE_ERROR.getMessage());
        } catch (DataValidationException e){
            ExceptionUtils.showException(e);
            throw e;
        }
        return promotionRepository;
    }

    private static void loadPromotionContext(Path path, PromotionRepository promotionRepository) throws IOException {
        Files.lines(path)
                .skip(1)
                .map(line -> line.split(","))
                .map(Promotion::from)
                .forEach(promotionRepository::addPromotion);
    }
}
