package bll.validators;

import model.Client;
import model.Product;

import java.util.regex.Pattern;
/**
 * Quantity Validator for Products
 */
public class QuantityValidator implements Validator<Product> {
    public void validate(Product t) {
        if (t.getQuantity()<0) {
            throw new IllegalArgumentException("Quantity is invalid!");
        }
    }
}
