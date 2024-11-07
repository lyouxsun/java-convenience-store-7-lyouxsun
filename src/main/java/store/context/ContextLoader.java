package store.context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContextLoader {
    public void initializeData() {
        try {
            Files.lines(Paths.get("src/main/resources/products.md"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
