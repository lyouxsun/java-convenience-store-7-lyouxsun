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
            throw new IllegalArgumentException("[ERROR] 프로모션 목록이 잘못된 형식으로 되어 있습니다.");
        }
        return promotionRepository;
    }
}
