package store;

import store.context.ContextProductLoader;

public class Application {
    public static void main(String[] args) {
        ContextProductLoader contextProductLoader = new ContextProductLoader();
        contextProductLoader.initializeData();
    }
}
