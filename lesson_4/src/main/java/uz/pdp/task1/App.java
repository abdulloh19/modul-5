package uz.pdp.task1;

import java.util.ArrayList;
import java.util.List;

class App {
    public static void main(String[] args) {
        List<Product> products = List.of(
                new Product("Laptop", "Electronics", 12000),
                new Product("Smartphone", "Electronics", 8000),
                new Product("Book", "Books", 20000)
        );

        PriceFilter priceFilter = new PriceFilter(10000);
        List<Product> filteredProducts = getProductsFilter(products, priceFilter);
        System.out.println(filteredProducts);

        /*CategoryFilter categoryFilter = new CategoryFilter("Electronics");
        List<Product> filteredByCategory = getProductsFilter(products, categoryFilter);
        System.out.println(filteredByCategory);*/
    }

    public static List<Product> getProductsFilter(List<Product> products, Filter filter) {
       List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (filter.test(product)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
}
