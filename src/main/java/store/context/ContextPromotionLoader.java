package store.context;

import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContextPromotionLoader {

    public Promotions initializePromotions() {
        Promotions promotions = new Promotions();
        try {
            Files.lines(Paths.get("src/main/resources/promotions.md"))
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(Promotion::from)
                    .forEach(promotions::addPromotion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return promotions;
    }
}
