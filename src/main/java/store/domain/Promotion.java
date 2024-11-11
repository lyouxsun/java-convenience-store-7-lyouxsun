package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Promotion {

    private String name;
    private int buyQuantity;
    private int getQuantity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Promotion(String name, int buyQuantity, int getQuantity, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion from(String[] info) {
        String name = validateName(info[0]);
        int buyQuantity = validateQuantity(info[1]);
        int getQuantity = validateQuantity(info[2]);
        LocalDateTime startDate = validateDate(info[3]);
        LocalDateTime endDate = validateDate(info[4]);
        return new Promotion(name, buyQuantity, getQuantity, startDate, endDate);
    }

    private static String validateName(String rawName) {
        String name = rawName.trim();
        if (name.isBlank()) {
            throw new DataValidationException("[ERROR] 프로모션 이름이 잘못 등록되어 있습니다.");
        }
        return name;
    }

    private static int validateQuantity(String quantityInput) {
        String rawQuantity = quantityInput.trim();
        if (!rawQuantity.matches("\\d+")) {
            throw new DataValidationException("[ERROR] 프로모션 상품 수량은 0 이상의 정수여야 합니다.");
        }
        int quantity = Integer.parseInt(rawQuantity);
        return quantity;
    }

    private static LocalDateTime validateDate(String rawDate) {
        try {
            LocalDate date = LocalDate.parse(rawDate, DateTimeFormatter.ISO_LOCAL_DATE);
            return date.atStartOfDay();
//            return LocalDateTime.parse(rawDate, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new DataValidationException("[ERROR] 프로모션 날짜가 잘못된 형식으로 등록되어 있습니다.");
        }
    }

    public String getName() {
        return name;
    }

    public boolean isBetweenDates(){
        LocalDateTime now = DateTimes.now();
        return now.isAfter(startDate) && now.isBefore(endDate);
    }

    @Override
    public String toString() {
        return "Promotion: {" +
                "name='" + name + '\'' +
                ", buyQuantity=" + buyQuantity +
                ", getOriginalQuantity=" + getQuantity +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public int getPromotionQuantity(){
        return this.buyQuantity + this.getQuantity;
    }

}
