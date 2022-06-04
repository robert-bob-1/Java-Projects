package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class OrderedProduct implements Serializable {
    public MenuItem product;
    public int quantity;
    public OrderedProduct(){
    }

    public OrderedProduct(MenuItem product){
        this.product = product;
        this.quantity = 1;
    }
    public OrderedProduct(MenuItem product,int qty){
        this.product = product;
        this.quantity = qty;
    }
}
