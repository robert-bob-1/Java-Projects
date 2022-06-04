package model;

/**
 * Order model.
 */
public class Order {
    private int id;
    private int idProduct;
    private int idClient;
    private int productQuantity;

    public Order(int idProduct, int idClient, int productQuantity) {
        this.id = 0;
        this.idProduct = idProduct;
        this.idClient = idClient;
        this.productQuantity = productQuantity;
    }
    public Order(Product product, Client client, int productQuantity) {
        this.id = 0;
        this.idProduct = product.getId();
        this.idClient = client.getId();
        this.productQuantity = productQuantity;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getIdProduct() {return idProduct;}
    public void setIdProduct(int idProduct) {this.idProduct = idProduct;}
    public int getIdClient() {return idClient;}
    public void setIdClient(int idClient) {this.idClient = idClient;}
    public int getProductQuantity() {return productQuantity;}
    public void setProductQuantity(int productQuantity) {this.productQuantity = productQuantity;}

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", idProduct=" + idProduct + ", idClient=" + idClient + ", productQuantity=" + productQuantity + '}';
    }
}
