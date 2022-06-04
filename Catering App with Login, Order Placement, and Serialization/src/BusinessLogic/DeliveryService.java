package BusinessLogic;

import Model.*;
import Model.MenuItem;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static BusinessLogic.Functions.populateFromCSV;

/**
 * @invariant wellFormedUsers()
 */

public class DeliveryService extends Observable implements IDeliveryServiceProcessing {
    public HashMap<String, MenuItem> menuItems;
    public HashMap<Order, ArrayList<OrderedProduct>> orders;
    public HashMap<String, User> users;
    public HashMap<String, OrderedProduct> currentOrder;
    public User currentUser;

    private final String productsFileName = "products.ser";
    private final String usersFileName = "users.ser";
    private final String ordersFileName = "orders.ser";

    public DeliveryService() {
        menuItems = new HashMap<>();
        orders = new HashMap<>();
        users = new HashMap<>();
        currentOrder = new HashMap<>();
    }

    /**
     * @post se importa produsele din csv
     * @throws IOException
     */
    @Override
    public void importProducts() throws IOException {
        menuItems.clear();

        ArrayList<BaseProduct> products = populateFromCSV();
        for (MenuItem product : products) {
            menuItems.putIfAbsent(product.toString(), product);
        }
    }

    /**
     * @post se serializeaza produsele
     * @throws IOException
     */
    public void serializeProductData() throws IOException {
        new FileOutputStream(productsFileName).close(); //reset file contents
        FileOutputStream f1 = new FileOutputStream(productsFileName);
        ObjectOutputStream o = new ObjectOutputStream(f1);
        o.writeObject(menuItems);
        System.out.println("serializare produse");
        o.close();
        f1.close();
    }
    public void serializeUserData() throws IOException {
        new FileOutputStream(usersFileName).close(); //reset file contents
        FileOutputStream f1 = new FileOutputStream(usersFileName);
        ObjectOutputStream o = new ObjectOutputStream(f1);

        o.writeInt(users.size());
        for (Map.Entry<String, User> user : users.entrySet()) {
            o.writeObject(user.getValue());
        }
        o.close();
        f1.close();
    }
    public void serializeOrderData() throws IOException {
        new FileOutputStream(ordersFileName).close(); //reset file contents
        FileOutputStream f = new FileOutputStream(ordersFileName);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeInt(Order.id);
        o.writeObject(orders);
        o.close();
        f.close();
    }

    public void deserializeProductData() throws IOException, ClassNotFoundException {
        menuItems.clear();
        FileInputStream f = new FileInputStream(productsFileName);
        ObjectInputStream o;
        o = new ObjectInputStream(f);
        menuItems = (HashMap<String, MenuItem>) o.readObject();
        for(int i=0; i<1; i++)
            System.out.println(menuItems.entrySet().isEmpty());
        o.close();
        f.close();
    }
    public void deserializeUserData() throws IOException, ClassNotFoundException {
        users.clear();
        FileInputStream f = new FileInputStream(usersFileName);
        ObjectInputStream o = new ObjectInputStream(f);

        int size = o.readInt();
        for (int i = 0; i < size; i++) {
            User user = (User) o.readObject();
            users.putIfAbsent(user.username, user);
        }
        o.close();
        f.close();
    }
    public void deserializeOrderData() throws IOException, ClassNotFoundException {
        orders.clear();
        FileInputStream f = new FileInputStream("orders.ser");
        ObjectInputStream o = new ObjectInputStream(f);
        Order.id = o.readInt();
        orders = (HashMap<Order, ArrayList<OrderedProduct>>) o.readObject();
        o.close();
    }

    /**
     * @param username numele utilizatorului pentru login
     * @param password parola utilizatorului
     * @param role     rolul utilizatorului: fie ADMIN fie CLIENT
     * @pre users.get(username) != null
     */
    public void registerUser(String username, String password, String role) {
        User test = users.get(username);
        if (test != null) {
            throw new RuntimeException("User already exists");
        }
        User user = new User(username, password, role);
        users.put(user.username, user);
        assert wellFormedUsers();
    }

    /**
     *
     * @param username
     * @param password
     */
    public String loginUser(String username, String password) {
        User user = users.get(username);
        if (user == null)
            throw new RuntimeException("Username not registered");
        if (!password.equals(user.password))
            throw new RuntimeException("Password incorrect");
        currentUser = user;
        return user.role;
    }
    public void deleteProducts(ArrayList<String> titles){
        if(titles.isEmpty())
            throw new IllegalArgumentException ("nu exista produse selectate");
        for(String title : titles){
            menuItems.remove(title);
        }
    }
    public void editProduct(BaseProduct product){
        menuItems.remove(product.title);
        menuItems.put(product.title, product);
    }


    public void addToOrder(ArrayList<String> newProducts){
        for(String productTitle : newProducts){
            MenuItem product = menuItems.get(productTitle);
            OrderedProduct newOrderedProduct = new OrderedProduct(product);
            String newProductTitle = newOrderedProduct.product.title;
            if(currentOrder.containsKey(newProductTitle)){
                currentOrder.get(newProductTitle).quantity++;
            }
            else{
                currentOrder.put(newProductTitle, newOrderedProduct);
            }
        }
    }
    public void createOrder() throws IOException {
        if(currentOrder.size() == 0)
            throw new RuntimeException("Nu s-au ales produse");
        Order newOrder = new Order();
        newOrder.orderDate = LocalDateTime.now();
        orders.put(newOrder, Functions.hashMapToArrayListO(currentOrder));
        ArrayList<OrderedProduct> orderedProducts = orders.get(newOrder);
        for(OrderedProduct product : orderedProducts){
            product.product.timesOrdered += product.quantity;
            newOrder.price += product.product.price * product.quantity;
        }
        Functions.generateBill(newOrder, Functions.hashMapToArrayListO(currentOrder));
        currentUser.ordersMade.add(newOrder);
        currentOrder.clear();
    }

    public void intervalReport(String lowerString, String upperString) throws IOException {
        int lower = Integer.parseInt(lowerString);
        int upper = Integer.parseInt(upperString);
        List<Order> ordersInInterval = orders.keySet().stream()
                .filter(order -> order.getHour() > lower && order.getHour() < upper)
                .toList();
        ArrayList<Order> ordersInIntervalAL = new ArrayList<Order>(ordersInInterval);
        Functions.generateTimeIntervalReport(ordersInIntervalAL, lowerString, upperString);
    }
    public void productsReport1(String orderCountString) throws IOException {
        int orderCount = Integer.parseInt(orderCountString);
        List<MenuItem> productsOrdered = menuItems.values().stream()
                .filter(product -> product.timesOrdered > orderCount)
                .toList();
        ArrayList<MenuItem> ordersInIntervalAL = new ArrayList<MenuItem>(productsOrdered);
        Functions.generateProductsReport1(ordersInIntervalAL, orderCountString);
    }
    public void clientsReport(String minOrdersString, String minPriceString) throws IOException {
        int minOrders = Integer.parseInt(minOrdersString);
        float minPrice = Float.parseFloat(minPriceString);
        List<User> clients = users.values().stream()
                .filter(user -> {
                    List<Order> ordersMade = user.ordersMade.stream()
                            .filter(order -> order.price > minPrice)
                            .toList();
                    return ordersMade.size() > minOrders;
                })
                .toList();
        ArrayList<User> clientsEligible = new ArrayList<User>(clients);
        Functions.generateClientsReport(clientsEligible, minOrdersString, minPriceString);
    }
    public void productsReport2(String dayString, String monthString, String yearString) throws IOException {
        //int orderCount = Integer.parseInt(orderCountString);
        int day = Integer.parseInt(dayString);
        int year = Integer.parseInt(yearString);
        HashMap<Order, ArrayList<OrderedProduct>> productsOrdered = (HashMap<Order, ArrayList<OrderedProduct>>)
                orders.entrySet()
                .stream()
                .filter(order -> order.getKey().orderDate.getDayOfMonth() == day &&
                        order.getKey().orderDate.getMonth().toString().equals(monthString.toUpperCase()) &&
                        order.getKey().orderDate.getYear() == year)
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

        HashMap<MenuItem, Integer> productInfo = new HashMap<>();
        for(Map.Entry<Order, ArrayList<OrderedProduct>> o : productsOrdered.entrySet())
            for(OrderedProduct m : o.getValue())
                if(productInfo.containsKey(m.product)){
                    int quantity = productInfo.remove(m.product);
                    quantity += m.quantity;
                    productInfo.put(m.product, quantity);
                }
                else{productInfo.put(m.product, m.quantity);}
        Functions.generateProductsDayReport(productInfo, dayString, monthString, yearString);
    }

    /**
     * @param title
     * @param selected
     */
    public void createComposite(String title, ArrayList<String> selected) {
        ArrayList<MenuItem> subProducts = new ArrayList<>();
        for(String titles : selected){
            subProducts.add(menuItems.get(titles));
        }
        CompositeProduct newProduct = new CompositeProduct(title, subProducts);
        assert compositeCreatedSuccesfully(newProduct, selected);
        menuItems.put(title, newProduct);
    }
    private boolean wellFormedUsers(){
        for(User u : users.values()){
            if(u.password.isEmpty() || u.username.isEmpty() || u.role.isEmpty())
                return false;
        }
        return true;
    }
    //returns true if composite product contains all products
    private boolean compositeCreatedSuccesfully(CompositeProduct cp, ArrayList<String> titles){
        for(String title : titles){
            if(!cp.products.contains(menuItems.get(title)))
                return false;
        }
        return true;
    }



}