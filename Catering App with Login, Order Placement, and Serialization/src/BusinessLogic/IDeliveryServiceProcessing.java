package BusinessLogic;

import java.io.IOException;
import java.util.ArrayList;

public interface IDeliveryServiceProcessing {
    void importProducts() throws IOException;

    /**
     *
     * @pre true
     */
    void serializeProductData() throws IOException;
    void serializeUserData() throws IOException;
    void serializeOrderData() throws IOException;
    /**
     *
     * @pre true
     */
    void deserializeProductData() throws IOException, ClassNotFoundException;
    void deserializeUserData() throws IOException, ClassNotFoundException;
    void deserializeOrderData() throws IOException, ClassNotFoundException;
    /**
     * logs a user in
     * @pre !username.isEmpty() && !password.isEmpty()
     * @post currentUser == users.get(username)
     */
    String loginUser(String username, String password);
    /**
     * adds new user to user list
     * @pre !username.isEmpty() && !password.isEmpty() && !role.isEmpty()
     * @post
     */
    void registerUser(String username, String password, String role);

    /**
     *
     * @param titles is array of titles of products for deletion
     * @throws IllegalArgumentException if titles.size() == 0
     */
    void deleteProducts(ArrayList<String> titles);
    void intervalReport(String lowerString, String upperString) throws IOException;
    void productsReport1(String orderCountString) throws IOException;
    void clientsReport(String minOrdersString, String minPriceString) throws IOException;
    void productsReport2(String dayString, String monthString, String yearString) throws IOException;

    void addToOrder(ArrayList<String> newProducts);
    void createOrder() throws IOException;

    void createComposite(String title, ArrayList<String> selected);


}
