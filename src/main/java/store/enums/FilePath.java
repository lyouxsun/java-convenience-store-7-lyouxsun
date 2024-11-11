package store.enums;

public enum FilePath {

    PRODUCTS_FILE("src/main/resources/products.md"),
    PROMOTION_FILE("src/main/resources/promotions.md");

    private final String path;
    FilePath(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}
