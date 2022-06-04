package bll;

import model.Client;
import model.Order;
import model.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Class with Static method to generate a Bill in pdf format.
 */
public class Bill {
    private static int billCount = 0;
    public static void generateBill(Client client, Product product, Order order ){
        File bill = new File("./order" + (billCount) + ".pdf");
        try{
            bill.createNewFile();
            FileWriter writer = new FileWriter(bill);
            writer.write("Order ID: " + order.getId() + "\n");
            writer.write("Client ID: " + client.getId() +
                    " Name: " + client.getName() +
                    " Address: " + client.getAddress() +
                    " Email: " + client.getEmail() +
                    "\n");
            writer.write("Product ID: " + product.getId() +
                    " Name: " + product.getName() +
                    " x " + product.getQuantity() +
                    "\n");
            writer.close();
            billCount++;
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
