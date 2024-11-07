package store.context;

import store.domain.product.Promotion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

public class ContextPromotionLoader {
    private LinkedHashMap<String, Promotion> promotions = new LinkedHashMap<>();

    public void initializeData() {
        try {
            Files.lines(Paths.get("src/main/resources/promotions.md"))
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(Promotion::from)
                    .forEach(promotion -> promotions.put(promotion.getName(), promotion));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
