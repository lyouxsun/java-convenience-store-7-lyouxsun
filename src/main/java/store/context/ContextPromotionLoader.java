package store.context;

import store.domain.Promotion;
import store.repository.PromotionRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContextPromotionLoader {

    public PromotionRepository initializePromotions() {
        PromotionRepository promotionRepository = new PromotionRepository();
        try {
            Files.lines(Paths.get("src/main/resources/promotions.md"))
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(Promotion::from)
                    .forEach(promotionRepository::addPromotion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return promotionRepository;
    }
}
