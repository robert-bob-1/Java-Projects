package BusinessLogic;

import Model.*;
import View.ClientView;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Functions{
    public static final String delimiter = ",";
    public static ArrayList<BaseProduct> currentProducts = new ArrayList<>();

    public static ArrayList<BaseProduct> populateFromCSV() throws IOException {
        FileReader fr = new FileReader("productscsv.csv");
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        ClientView view;
        ArrayList<BaseProduct> products = new ArrayList<>();

        line = br.readLine();
        while ((line = br.readLine()) != null) {
            String[] temp = line.split(delimiter);
            ArrayList<String> product;
            product = new ArrayList<>(Arrays.asList(temp));
            BaseProduct newProduct = new BaseProduct(product);
            if(isDuplicate(newProduct, products) )
                continue;
            products.add(newProduct);
        }
        currentProducts = products;
        return products;
    }
    public static boolean isDuplicate(BaseProduct newProduct, ArrayList<BaseProduct> products){
        for(BaseProduct product : products){
            if(product.title.equals(newProduct.title))
                return true;
        }
        return false;
    }

    public static ArrayList<MenuItem> hashMapToArrayList(HashMap<String, MenuItem> map){
        Collection<MenuItem> productsCollection = map.values();
        return new ArrayList<>(productsCollection);
    }
    public static ArrayList<OrderedProduct> hashMapToArrayListOP(HashMap<String, OrderedProduct> map){
        Collection<OrderedProduct> productsCollection = map.values();
        return new ArrayList<>(productsCollection);
    }
    public static ArrayList<OrderedProduct> hashMapToArrayListO(HashMap<String, OrderedProduct> map){
        Collection<OrderedProduct> productsCollection = map.values();
        return new ArrayList<>(productsCollection);
    }
    public static ArrayList<OrderTableProduct> oPToOTP(HashMap<String, OrderedProduct> map){
        Collection<OrderedProduct> productsCollection = map.values();
        ArrayList<OrderedProduct> orderedProducts = new ArrayList<>(productsCollection);
        ArrayList<OrderTableProduct> orderedTableProducts = new ArrayList<>();
        for(OrderedProduct product : orderedProducts){
            orderedTableProducts.add(new OrderTableProduct(product.product.title, product.product.price, product.quantity));
        }
        return orderedTableProducts;
    }


    public static HashMap<String, MenuItem> searchProperties(HashMap<String, MenuItem> products,
                                                       ArrayList<String> searchProperties){
        
        HashMap<String, MenuItem> temp = (HashMap<String, MenuItem>)products
                .entrySet()
                .stream()
                .filter(product -> product.getValue().title.toLowerCase().contains(searchProperties.get(0)))
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

        if(!searchProperties.get(1).isEmpty())
            temp = (HashMap<String, MenuItem>)products
                    .entrySet()
                    .stream()
                    .filter(product -> product.getValue().rating == Float.parseFloat(searchProperties.get(1)))
                    .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        if(!searchProperties.get(2).isEmpty())
            temp = (HashMap<String, MenuItem>)products
                    .entrySet()
                    .stream()
                    .filter(product -> product.getValue().calories == Integer.parseInt(searchProperties.get(2)))
                    .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        if(!searchProperties.get(3).isEmpty())
            temp = (HashMap<String, MenuItem>)products
                    .entrySet()
                    .stream()
                    .filter(product -> product.getValue().proteins == Integer.parseInt(searchProperties.get(3)) )
                    .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        if(!searchProperties.get(4).isEmpty())
            temp = (HashMap<String, MenuItem>)products
                    .entrySet()
                    .stream()
                    .filter(product -> product.getValue().fats == Integer.parseInt(searchProperties.get(4)) )
                    .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        if(!searchProperties.get(5).isEmpty())
            temp = (HashMap<String, MenuItem>)products
                    .entrySet()
                    .stream()
                    .filter(product -> product.getValue().sodium == Integer.parseInt(searchProperties.get(5)) )
                    .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        if(!searchProperties.get(6).isEmpty())
            temp = (HashMap<String, MenuItem>)products
                    .entrySet()
                    .stream()
                    .filter(product -> product.getValue().price == Integer.parseInt(searchProperties.get(6)) )
                    .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        return temp;
    }

    public static HashMap<String, MenuItem> searchByName(HashMap<String, MenuItem> products, String name){
        HashMap<String, MenuItem> newProducts = new HashMap<>();
        for(Map.Entry<String, MenuItem> entry : products.entrySet()){
            if(entry.getKey().toLowerCase().contains(name)){
                newProducts.put(entry.getKey(), entry.getValue());
            }
        }
        return newProducts;
    }

    public static void generateBill(Order order, ArrayList<OrderedProduct> products) throws IOException {
        String fileName = "bill_number_" + ++Order.id + ".txt";
        File bill = new File(fileName);
        bill.createNewFile();
        FileWriter fw = new FileWriter(fileName);
        fw.write("Order date: " + order.orderDate + "\n");
        fw.write("Ordered products: \n");
        for(OrderedProduct op : products){
            fw.write("  " + op.product.title + "\n");
            fw.write("      x" + op.quantity + "\n");
        }
        fw.write("Total price: " + order.price);
        fw.close();
    }
    public static void generateTimeIntervalReport(ArrayList<Order> orders, String lower, String upper) throws IOException {
        String fileName = "OrdersInTimeInterval.txt";
        File report = new File(fileName);
        report.createNewFile();
        FileWriter fw = new FileWriter(fileName);
        fw.write("Orders in hour interval: " + lower + " - " + upper +"\n");
        for(Order o : orders){
            fw.write("  ID " + o.orderID + "\n");
            fw.write("      Price: " + o.price + "\n");
            fw.write("      Date: " + o.orderDate + "\n");
        }
        fw.close();
    }
    public static void generateProductsReport1(ArrayList<MenuItem> products, String orderCount) throws IOException {
        String fileName = "ProductsOrderedXTimes.txt";
        File report = new File(fileName);
        report.createNewFile();
        FileWriter fw = new FileWriter(fileName);
        fw.write("Products ordered at least " + orderCount + " times\n");
        for(MenuItem p : products){
            fw.write("  Product title: " + p.title + "\n");
            fw.write("      Ordered " + p.timesOrdered + " times\n");
        }
        fw.close();
    }

    public static void generateClientsReport(ArrayList<User> clientsEligible, String minOrdersString, String minPriceString) throws IOException {
        String fileName = "ClientsReport.txt";
        File report = new File(fileName);
        report.createNewFile();
        FileWriter fw = new FileWriter(fileName);
        fw.write("Clients that placed more than " + minOrdersString + " orders, each with more than " + minPriceString + " value\n");
        for(User u : clientsEligible){
            fw.write("  User name: " + u.username + "\n");
            fw.write("       Number of orders placed " + u.ordersMade.size() + " times\n");
        }
        fw.close();
    }
    public static void generateProductsDayReport(HashMap<MenuItem, Integer> productInfo, String dayString, String monthString, String yearString) throws IOException {
        String fileName = "ProductsDayReport.txt";
        File report = new File(fileName);
        report.createNewFile();
        FileWriter fw = new FileWriter(fileName);
        fw.write("Products ordered on " + dayString + " " + monthString + " " + yearString + "\n");
        for(Map.Entry<MenuItem, Integer> product : productInfo.entrySet()){
            fw.write("  Product title: " + product.getKey().title + "\n");
            fw.write("      Ordered " + product.getValue() + " times in this day\n");
        }
        fw.close();
    }

}
