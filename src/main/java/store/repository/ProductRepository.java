package store.repository;

import store.domain.Product;
import store.dto.InventoryDto;

import java.util.*;
import java.util.stream.Collectors;

public class ProductRepository {
    private final Map<String, Product> products;

    public ProductRepository() {
        this.products = new LinkedHashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getName(), product);
    }

    public InventoryDto productsToDto() {
        StringBuilder sb = new StringBuilder();
        for (Product product : products.values()) {
            sb.append(product.toString());
        }
        return new InventoryDto(sb.toString());
    }

    public boolean availablePurchase(String name, int hopeQuantity) {
        Product product = products.get(name);
        if (product == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
        return product.isPurchaseAvailable(hopeQuantity);
    }


    // 없애야됨
    public Product findByNameAndPromotion(String name, boolean isPromotion) {
        return products.values().stream()
                .filter(product -> product.isSameName(name))
                .filter(product -> product.isPromotion() == isPromotion)
                .findAny()
                .orElse(null);
    }

    public int getSize() {
        return products.size();
    }

//    public void purchase(String productName, boolean isPromotion, PurchaseDto purchaseDto) {
//        Product product = findByName(productName);
//        int hopeAmount = purchaseDto.getTotalNum();
//        if (product.getQuantity() < hopeAmount) {
//            hopeAmount -= product.getQuantity();
//            product.reduceQuantity(product.getOriginalQuantity());
//            Product noPromotion = findByName(productName);
//            noPromotion.reduceQuantity(hopeAmount);
//        }
//
//    }
//
//    public long findAmountByName(String name) {
//        Optional<Product> optionalProduct = products.stream()
//                .filter(product -> product.isSameName(name))
//                .findFirst();
//        Product product = optionalProduct.get();
//        return product.getOriginalQuantity();
//    }

    public Set<String> findAllName() {
        return products.values().stream()
                .map(Product::getName)
                .collect(Collectors.toSet());
    }

    public Product findByName(String name) {
        return products.get("name");
    }
}
