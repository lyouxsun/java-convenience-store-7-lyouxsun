package store.context;

import store.domain.Promotion;
import store.repository.PromotionRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ContextPromotionLoader {

    public PromotionRepository initializePromotions(Path path) {
        PromotionRepository promotionRepository = new PromotionRepository();
        try {
            Files.lines(path)
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
