package uz.pdp.task1;

class PriceFilter implements Filter {
    private final double minPrice;

    PriceFilter(double minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public boolean test(Product product) {
        return product.getPrice() >= this.minPrice;
    }
}
