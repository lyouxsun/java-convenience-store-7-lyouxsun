package store.domain;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;

import java.time.LocalDate;

public class Promotion {

    private String name;
    private int buyQuantity;
    private int getQuantity;
    private LocalDate startDate;
    private LocalDate endDate;

    private Promotion(String name, int buyQuantity, int getQuantity, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion from(String[] info) {
        String name = validateName(info[0]);
        int buyQuantity = Integer.parseInt(info[1]);
        int getQuantity = Integer.parseInt(info[2]);
        LocalDate startDate = LocalDate.parse(info[3]);
        LocalDate endDate = LocalDate.parse(info[4]);
        return new Promotion(name, buyQuantity, getQuantity, startDate, endDate);
    }

    private static String validateName(String rawName) {
        String name = rawName.trim();
        if (name.isBlank()) {
            throw new DataValidationException("[ERROR] 상품 이름이 잘못 등록되어 있습니다. products.md 파일을 확인해주세요.");
        }
        return name;
    }

    private static long validateQuantity(String quantityInput) {
        String rawQuantity = quantityInput.trim();
        if (!rawQuantity.matches("\\d+")) {
            throw new DataValidationException("[ERROR] 상품 가격은 정수여야 합니다. products.md 파일을 확인해주세요.");
        }
        long quantity = Long.parseLong(rawQuantity);
        if (quantity < 0) {
            throw new DataValidationException("[ERROR] 상품 가격은 0원 이상의 정수여야 합니다. products.md 파일을 확인해주세요.");
        }
        return quantity;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Promotion: {" +
                "name='" + name + '\'' +
                ", buyQuantity=" + buyQuantity +
                ", getQuantity=" + getQuantity +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

}
