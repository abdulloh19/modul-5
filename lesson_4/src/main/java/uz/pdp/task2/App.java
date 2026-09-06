package uz.pdp.task2;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<Product> products = List.of(
                new Product("Laptop", "Electronics", 12000),
                new Product("Smartphone", "Electronics", 8000),
                new Product("Book", "Books", 20000)
        );

        Filter filter = (Product product) -> product.getPrice() > 10000;
        Filter filter1 = (Product product) -> product.getCategory().equals("Electronics");

        System.out.println(getProductsFilter(products, filter));
        System.out.println(getProductsFilter(products, filter1));
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


