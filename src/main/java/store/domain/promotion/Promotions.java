package store.domain.promotion;

import java.util.LinkedHashMap;
import java.util.Map;

public class Promotions {
    private final Map<String, Promotion> promotions;

    public Promotions() {
        this.promotions = new LinkedHashMap<>();
    }

    public void addPromotion(Promotion promotion) {
        promotions.put(promotion.getName(), promotion);
    }
}
