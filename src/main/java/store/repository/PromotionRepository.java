package store.repository;

import store.domain.Promotion;

import java.util.LinkedHashMap;
import java.util.Map;

public class PromotionRepository {
    private final Map<String, Promotion> promotions;

    public PromotionRepository() {
        this.promotions = new LinkedHashMap<>();
    }

    public void addPromotion(Promotion promotion) {
        promotions.put(promotion.getName(), promotion);
    }

    public int getSize(){
        return promotions.size();
    }
}
