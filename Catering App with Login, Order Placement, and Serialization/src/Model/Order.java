package Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Order implements Serializable {
    public int orderID;
    public LocalDateTime orderDate;
    public double price;
    public static int id = 0;
    public Order(){
        this.price = 0;
        this.orderID = Order.id;
    }

    public int getHour(){
        return orderDate.getHour();
    }

    @Override
    public int hashCode(){
        int h = 0;
        h += orderID;
        h += orderDate.hashCode();
        return h;
    }
}
