package bll;

import bll.validators.Validator;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * Business Logic for Order class (managing the DAO functions at a higher level)
 */
public class OrderBLL {
    private List<Validator<Order>> validators;
    private OrderDAO orderDAO;
    private ProductDAO productDAO;

    public OrderBLL() {
        validators = new ArrayList<Validator<Order>>();
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
    }

    public Order findClientById(int id) {
        Order st = orderDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return st;
    }

    public ArrayList<Order> findAll() {
        ArrayList<Order> orders = (ArrayList<Order>) orderDAO.getAll();
        if (orders == null) {
            throw new NoSuchElementException("No orders were found.");
        }
        return orders;
    }

    public void insertOrder(Order order, Product product) throws NotEnoughProductsException {
        int newQuantity = product.getQuantity()-order.getProductQuantity();
        if(newQuantity<0)
            throw new NotEnoughProductsException("Nu sunt destule produse pentru aceasta comanda!\nCantitate disponibila: "
                    + product.getQuantity());
        orderDAO.insert(order);
        productDAO.update(product, new Product( product.getId(), product.getName(),
                newQuantity));
    }

    public void updateOrder(Order order){
        orderDAO.update(order, order);
    }

    public void deleteClient(Order order){
        orderDAO.delete(order);
    }
}
class NotEnoughProductsException extends Exception {
    public NotEnoughProductsException(String errorMessage) {
        super(errorMessage);
    }
}