package Controllers;

import BusinessLogic.DeliveryService;
import BusinessLogic.Functions;
import Model.BaseProduct;
import Model.MenuItem;
import View.AdministratorView;
import View.ClientView;
import View.LoginView;
import View.RegisterView;

import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
    private LoginView loginView;
    private RegisterView registerView;
    private ClientView clientView;
    private AdministratorView administratorView;
    DeliveryService deliveryService;

    Thread shutDownHook = new Thread(() -> {
        try {
            System.out.println("hook");
            deliveryService.serializeProductData();
            deliveryService.serializeUserData();
            deliveryService.serializeOrderData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    });

    Controller () throws IOException, ClassNotFoundException {
        Runtime.getRuntime().addShutdownHook(shutDownHook);

        this.loginView = new LoginView();
        this.clientView = new ClientView();
        this.administratorView = new AdministratorView();
        this.registerView = new RegisterView();
        this.deliveryService = new DeliveryService();

        loginView.loginListener(new LoginListener());
        loginView.registerListener(new RegisterWindowListener());
        registerView.registerListener(new RegisterListener());
        administratorView.importListener(new ImportProductsListener());
        administratorView.addProductListener(new AddProductListener());
        administratorView.deleteListener(new DeleteSelectedListener());
        administratorView.createListener(new CreateProductListener());
        administratorView.editListener(new EditProductListener());
        administratorView.reportsIntervalListener(new ReportsIntervalListener());
        administratorView.reportsProductsMinimumListener(new ReportsProductsMinimumListener());
        administratorView.reportsClientsListener(new ReportsClientsListener());
        administratorView.reportsProductsDayListener(new ReportProductsDay());
        clientView.searchListener(new SearchProductsListener());
        clientView.addListener(new AddProductsToOrderListener());
        clientView.orderListener(new OrderListener());

        this.createFiles();

        deliveryService.deserializeProductData();
        deliveryService.deserializeUserData();
        deliveryService.deserializeOrderData();

        administratorView.refreshTable(Functions.hashMapToArrayList(deliveryService.menuItems),
                administratorView.getTableModel());
        clientView.refreshTable(Functions.hashMapToArrayList(deliveryService.menuItems),
                clientView.getTableModel());


        loginView.viewFrame();
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try{
                String username = loginView.getUsernameText();
                String password = loginView.getPasswordText();

                String role = deliveryService.loginUser(username, password);

                if(role.equals("CLIENT")){
                    clientView.viewFrame();
                    //loginView.hideFrame();
                } else if(role.equals("ADMIN")){
                    administratorView.viewFrame();
                    //loginView.hideFrame();
                }

            } catch (Exception exception){
                loginView.showError(exception.getMessage());
            }
        }
    }

    class RegisterWindowListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                registerView.viewFrame();
            } catch(Exception exception){
                loginView.showError(exception.getMessage());
            }
        }
    }

    class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try{
                String username = registerView.getUsername();
                String password = registerView.getPassword();
                String role     = registerView.getRole();

                deliveryService.registerUser(username, password, role);
                registerView.showError("User " + username + " added succesfully");
                registerView.hideFrame();
            } catch (Exception exception){
                registerView.showError(exception.getMessage());
            }
        }
    }

    //TODO Admin Buttons
    class ImportProductsListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                deliveryService.importProducts();
                administratorView.refreshTable(Functions.hashMapToArrayList(deliveryService.menuItems),
                        administratorView.getTableModel());
                administratorView.showError("Products imported succesfully");
            } catch (Exception exception){
                administratorView.showError(exception.getMessage());
            }
        }
    }
    class AddProductListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                String productInfo = administratorView.getProductText();
                BaseProduct newProduct = new BaseProduct(productInfo);
                if(deliveryService.menuItems.containsKey(newProduct.title)){
                    throw new RuntimeException("Produs cu acest titlu deja existent");
                }
                deliveryService.menuItems.put(newProduct.title, newProduct);
                administratorView.refreshTable(Functions.hashMapToArrayList(deliveryService.menuItems),
                        administratorView.getTableModel());
                administratorView.showError("Produs adaugat");
            } catch (Exception exception){
                administratorView.showError(exception.getMessage());
            }
        }
    }
    class DeleteSelectedListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                deliveryService.deleteProducts(administratorView.getSelected());
                administratorView.refreshTable(Functions.hashMapToArrayList(deliveryService.menuItems),
                        administratorView.getTableModel());
            } catch (Exception exception){
                administratorView.showError(exception.getMessage());
            }
        }
    }
    class CreateProductListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                String newTitle = administratorView.getCreateText();
                if(deliveryService.menuItems.containsKey(newTitle)){
                    throw new RuntimeException("Produs cu acest titlu deja existent");
                }
                deliveryService.createComposite(newTitle, administratorView.getSelected());
                administratorView.refreshProductsTable(Functions.hashMapToArrayList(deliveryService.menuItems),
                        administratorView.getTableModel());
            } catch (Exception exception){
                administratorView.showError(exception.getMessage());
            }
        }
    }
    class EditProductListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                String newProduct = administratorView.getProductText();
                BaseProduct newBP = new BaseProduct(newProduct);
                if(!deliveryService.menuItems.containsKey(newBP.title)){
                    throw new RuntimeException("Produs cu acest titlu nu exista");
                }
                deliveryService.editProduct(newBP);
                administratorView.refreshProductsTable(Functions.hashMapToArrayList(deliveryService.menuItems),
                        administratorView.getTableModel());
            } catch (Exception exception){
                administratorView.showError(exception.getMessage());
            }
        }
    }
              //TODO admin reports
    class ReportsIntervalListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
          try{
              String lower = administratorView.getIntervalTextLower();
              String upper = administratorView.getIntervalTextUpper();
              deliveryService.intervalReport(lower, upper);

          } catch (Exception exception){
              administratorView.showError(exception.getMessage());
          }
      }
    }
    class ReportsProductsMinimumListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                String orderCount = administratorView.getMinimumOrdersText();
                deliveryService.productsReport1(orderCount);
            } catch (Exception exception){
                administratorView.showError(exception.getMessage());
            }
        }
    }
    class ReportsClientsListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                String minOrders = administratorView.getMinimumOrdersTextClients();
                String minValue = administratorView.getMinimumValueText();
                deliveryService.clientsReport(minOrders, minValue);
            } catch (Exception exception){
                administratorView.showError(exception.getMessage());
            }
        }
    }
    class ReportProductsDay implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                String day = administratorView.getDayText();
                String month = administratorView.getMonthText();
                String year = administratorView.getYearText();
                deliveryService.productsReport2(day,month,year);
            } catch (Exception exception){
                administratorView.showError(exception.getMessage());
            }
        }
    }

    //TODO Client Buttons
    class SearchProductsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ArrayList<String> properties = clientView.getAllProperties();
                HashMap<String, MenuItem> newProducts = Functions.searchProperties(deliveryService.menuItems, properties);
                clientView.refreshProductsTable(Functions.hashMapToArrayList(newProducts),
                        clientView.getTableModel());

            } catch(Exception exception){
                clientView.showError(exception.getMessage());
            }
        }
    }
    class AddProductsToOrderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ArrayList<String> orderedProducts = clientView.getSelected();
                deliveryService.addToOrder(orderedProducts);
                clientView.refreshTable(Functions.oPToOTP(deliveryService.currentOrder),
                        clientView.getOrderTableModel());
            } catch(Exception exception){
                clientView.showError(exception.getMessage());
            }
        }
    }
    class OrderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                deliveryService.createOrder();
                clientView.refreshProductsTable(Functions.hashMapToArrayList(deliveryService.menuItems),
                        clientView.getTableModel());
                clientView.refreshTable(Functions.oPToOTP(deliveryService.currentOrder),
                        clientView.getOrderTableModel());
            } catch(Exception exception){
                clientView.showError(exception.getMessage());
            }
        }
    }

    public void createFiles() throws IOException {
        File products = new File("products.ser");
        if(products.createNewFile()){ System.out.println("Created products"); }
        else{ System.out.println("Already exists"); }

        File users = new File("users.ser");
        if(users.createNewFile()){ System.out.println("Created users"); }
        else{ System.out.println("Already exists"); }

        File orders = new File("orders.ser");
        if(orders.createNewFile()){ System.out.println("Created orders"); }
        else{ System.out.println("Already exists"); }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Controller controller = new Controller();
    }
}
