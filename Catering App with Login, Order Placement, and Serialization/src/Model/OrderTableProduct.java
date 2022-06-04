package Model;

public class OrderTableProduct {
    public String title;
    public float price;
    public int qty;

    public OrderTableProduct() {
    }

    public OrderTableProduct(String title, float price, int qty) {
        this.title = title;
        this.price = price;
        this.qty = qty;
    }
}
