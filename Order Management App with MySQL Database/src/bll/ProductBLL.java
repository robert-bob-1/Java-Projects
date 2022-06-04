package bll;

import bll.validators.QuantityValidator;
import bll.validators.Validator;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic for Product class (managing the DAO functions at a higher level)
 */

public class ProductBLL {
    private List<Validator<Product>> validators;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;

    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        validators.add(new QuantityValidator());
        productDAO = new ProductDAO();
        orderDAO = new OrderDAO();
    }

    public Product findProductById(int id) {
        Product st = productDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return st;
    }

    public ArrayList<Product> findAll(int id) {
        ArrayList<Product> products = (ArrayList<Product>) productDAO.findAll(id);
        if (products == null) {
            throw new NoSuchElementException("No products were found.");
        }
        return products;
    }

    public ArrayList<Product> getAll() {
        ArrayList<Product> products = (ArrayList<Product>) productDAO.getAll();
        if (products == null) {
            throw new NoSuchElementException("No products were found.");
        }
        return products;
    }

    public void insertProduct(Product product){
        this.validators.get(0).validate(product);
        productDAO.insert(product);
    }

    public void updateProduct(Product oldProduct, Product newProduct){
        productDAO.update(oldProduct, newProduct);
    }

    public void deleteProduct(Product product){
        productDAO.deleteAllOrders(product.getId());
        productDAO.delete(product);
    }
}
