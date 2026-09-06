package uz.pdp.task1;

class CategoryFilter implements Filter {
    private final String category;

    CategoryFilter(String category) {
        this.category = category;
    }

    @Override
    public boolean test(Product product) {
        return product.getCategory().equals(this.category);
    }
}
