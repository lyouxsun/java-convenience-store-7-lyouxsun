package store.dto;

public class InventoryDto {
    private final String inventory;

    public InventoryDto(String inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return inventory;
    }
}
