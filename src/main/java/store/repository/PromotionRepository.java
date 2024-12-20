package store.repository;

import store.domain.Promotion;

import java.util.LinkedHashMap;
import java.util.Map;

public class PromotionRepository {
    private static Map<String, Promotion> promotions;

    public PromotionRepository() {
        promotions = new LinkedHashMap<>();
    }

    public void addPromotion(Promotion promotion) {
        promotions.put(promotion.getName(), promotion);
    }

    public int getSize() {
        return promotions.size();
    }

    public Promotion findByName(String name) {
        return promotions.get(name);
    }
}
