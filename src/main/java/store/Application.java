package store;

import store.context.ContextLoader;

public class Application {
    public static void main(String[] args) {
        ContextLoader contextLoader = new ContextLoader();
        contextLoader.initializeData();
    }
}
