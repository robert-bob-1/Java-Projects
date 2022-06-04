package presentation;

import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Vector;

/**
*   The View class describes multiple frames for each window we need: client manager, product manager, order insertion and menu to manage all three of these.
 *   We have multiple Swing visual and functional elements (panels, buttons, text fields etc.).
 *
 *   This is the only class that manages the visual components.
 *
*/

public class View {
    //Client
    private JFrame clientFrame = new JFrame();
    private JPanel clientPanel = new JPanel();
    private JPanel clientPanelName = new JPanel();
    private JPanel clientPanelEmail = new JPanel();
    private JPanel clientPanelAddress = new JPanel();

    private JTextField clientName = new JTextField(20);
    private JTextField clientEmail = new JTextField(20);
    private JTextField clientAddress = new JTextField(20);

    private JScrollPane clientScrollPane; //= new JScrollPane();
    private DefaultTableModel clientModel = new DefaultTableModel();
    private JTable clientTable = new JTable();

    private JPanel clientPanelButtons = new JPanel();
    private JButton addClientBtn = new JButton("Add Client");
    private JButton editClientBtn = new JButton("Edit Client");
    private JButton deleteClientBtn = new JButton("Delete Client");

    //Product
    private JFrame productFrame = new JFrame();
    private JPanel productPanel = new JPanel();
    private JPanel productPanelName = new JPanel();
    private JPanel productPanelQuantity = new JPanel();

    private JTextField productId = new JTextField(7);
    private JTextField productName = new JTextField(20);
    private JTextField productQuantity = new JTextField(20);

    private JScrollPane productScrollPane;
    private DefaultTableModel productModel = new DefaultTableModel();
    private JTable productTable = new JTable();

    private JPanel productPanelButtons = new JPanel();
    private JButton addProductBtn = new JButton("Add Product");
    private JButton editProductBtn = new JButton("Edit Product");
    private JButton deleteProductBtn = new JButton("Delete Product");
    private JButton viewProductBtn = new JButton("View Product");

    //Orders
    private JFrame orderFrame = new JFrame();
    private JPanel orderPanel = new JPanel();

    private JScrollPane orderProductScrollPane;
    private JTable orderProductTable = new JTable();
    private JScrollPane orderClientScrollPane;
    private JTable orderClientTable = new JTable();

    private JPanel orderQuantityPanel = new JPanel();
    private JTextField orderQuantityField = new JTextField(7);

    private JButton setOrderBtn = new JButton("Set Order");

    //Menu
    private JFrame menuFrame = new JFrame();
    private JPanel menuPanel = new JPanel();
    private JButton manageProductBtn = new JButton("Manage Products");
    private JButton manageClientBtn = new JButton("Manage Clients");
    private JButton manageOrdersBtn = new JButton("Add Order");

    /**
     * initialization of the menus and frames
     */
    public View() {
        //menu
        menuPanel.setLayout(new GridLayout(2, 3));
        menuPanel.add(manageProductBtn);
        menuPanel.add(manageClientBtn);
        menuPanel.add(manageOrdersBtn);

        addToClientPanel();
        addToProductPanel();
        addToOrderPanel();

        viewMenuFrame();
    }

    /**
     * Generate table model through reflection
     * @param object
     * @return DefaultTableModel
     */
    public DefaultTableModel createTableModel(Object object) {
        ArrayList<String> properties = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            properties.add(field.getName());
        }
        DefaultTableModel tableModel = new DefaultTableModel(null, properties.toArray()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        return tableModel;
    }

    /**
     * Refresh table Models through reflection
     * @param objects
     * @param model
     * @param <T>
     */
    public <T> void refreshTable(ArrayList<T> objects, DefaultTableModel model){
        model.setRowCount(0);
        for(Object object : objects){
            ArrayList<Object> values = new ArrayList<>();
            for(Field field : object.getClass().getDeclaredFields()){
                field.setAccessible(true);
                try {
                    values.add(field.get(object));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(values.toArray());
        }
    }

    /**
     * Return selected items from tables
     * @param t
     * @param <T>
     * @return
     */
    public <T> T getSelected(T t){
        int selectedRow;
        ArrayList<Object> parameters = new ArrayList<>();
        if(t.getClass().toString().equals("class model.Client")){
            selectedRow = clientTable.getSelectedRow();
            for(int i=0; i < clientModel.getColumnCount();i++){
                parameters.add(clientModel.getValueAt(selectedRow,i));
            }
            return (T) new Client((int) parameters.get(0), (String) parameters.get(1), (String) parameters.get(2),
                                    (String) parameters.get(3));
        }

        else if(t.getClass().toString().equals("class model.Product")){
            selectedRow = productTable.getSelectedRow();
            for(int i=0; i < productModel.getColumnCount();i++){
                parameters.add(productModel.getValueAt(selectedRow,i));
            }
            return (T) new Product((int) parameters.get(0), (String) parameters.get(1), (int) parameters.get(2));
        }

        System.out.println("greseala in functia de selectie");
        return null;

    }

    void addToClientPanel() {
        clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.Y_AXIS));

        clientPanelName.setLayout(new BoxLayout(clientPanelName, BoxLayout.X_AXIS));
        clientPanelName.add(new JLabel("Client Name:"));
        clientPanelName.add(clientName);
        clientName.setMaximumSize(new Dimension(200, 20));

        clientPanelEmail.setLayout(new BoxLayout(clientPanelEmail, BoxLayout.X_AXIS));
        clientPanelEmail.add(new JLabel("Client Email:"));
        clientPanelEmail.add(clientEmail);
        clientEmail.setMaximumSize(new Dimension(200, 20));

        clientPanelAddress.setLayout(new BoxLayout(clientPanelAddress, BoxLayout.X_AXIS));
        clientPanelAddress.add(new JLabel("Client Address:"));
        clientPanelAddress.add(clientAddress);
        clientAddress.setMaximumSize(new Dimension(200, 20));

        clientPanelButtons.setLayout(new BoxLayout(clientPanelButtons, BoxLayout.X_AXIS));
        clientPanelButtons.add(addClientBtn);
        clientPanelButtons.add(editClientBtn);
        clientPanelButtons.add(deleteClientBtn);

        clientModel = createTableModel(new Client());
        clientTable.setModel(clientModel);
        clientScrollPane = new JScrollPane(clientTable);
        clientScrollPane.setMaximumSize(new Dimension(600, 700));

        clientPanel.add(clientPanelName);
        clientPanel.add(clientPanelAddress);
        clientPanel.add(clientPanelEmail);
        clientPanel.add(clientScrollPane);
        clientPanel.add(clientPanelButtons);
    }

    void addToProductPanel() {
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));

        productPanelName.setLayout(new BoxLayout(productPanelName, BoxLayout.X_AXIS));
        productPanelName.add(new JLabel("Product Name:"));
        productPanelName.add(productName);
        productName.setMaximumSize(new Dimension(200, 20));

        productPanelQuantity.setLayout(new BoxLayout(productPanelQuantity, BoxLayout.X_AXIS));
        productPanelQuantity.add(new JLabel("Product Quantity:"));
        productPanelQuantity.add(productQuantity);
        productQuantity.setMaximumSize(new Dimension(200, 20));

        productPanelButtons.setLayout(new BoxLayout(productPanelButtons, BoxLayout.X_AXIS));
        productPanelButtons.add(addProductBtn);
        productPanelButtons.add(editProductBtn);
        productPanelButtons.add(deleteProductBtn);

        productModel = createTableModel(new Product());
        productTable.setModel(productModel);
        productScrollPane = new JScrollPane(productTable);
        productScrollPane = new JScrollPane(productTable);
        productScrollPane.setMaximumSize(new Dimension(600, 700));

        productPanel.add(productPanelName);
        productPanel.add(productPanelQuantity);
        productPanel.add(productScrollPane);
        productPanel.add(productPanelButtons);
    }

    void addToOrderPanel() {
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));

        orderClientTable.setModel(clientModel);
        orderClientScrollPane = new JScrollPane(orderClientTable);
        orderClientScrollPane.setMaximumSize(new Dimension(600, 700));

        orderProductTable.setModel(productModel);
        orderProductScrollPane = new JScrollPane(orderProductTable);
        orderProductScrollPane.setMaximumSize(new Dimension(600, 700));

        orderQuantityPanel.setLayout(new BoxLayout(orderQuantityPanel, BoxLayout.X_AXIS));
        orderQuantityPanel.add(new JLabel("Product Quantity:"));
        orderQuantityPanel.add(orderQuantityField);
        orderQuantityField.setMaximumSize(new Dimension(200, 20));

        orderPanel.add(orderClientScrollPane);
        orderPanel.add(orderProductScrollPane);
        orderPanel.add(orderQuantityPanel);
        orderPanel.add(setOrderBtn);
    }
    void viewMenuFrame() {
        menuFrame.setLocation(700, 400);
        menuFrame.setSize(300, 200);
        menuFrame.setContentPane(menuPanel);
        menuFrame.setTitle("Menu");

        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);
    }
    void viewClientFrame() {
        clientFrame.setSize(800, 1300);
        clientFrame.setContentPane(clientPanel);
        clientFrame.setTitle("Client Menu");

        clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clientFrame.setVisible(true);
    }
    void viewProductFrame() {
        productFrame.setSize(800, 1300);
        productFrame.setContentPane(productPanel);
        productFrame.setTitle("Product Menu");

        productFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        productFrame.setVisible(true);
    }
    void viewOrderFrame() {
        orderFrame.setSize(500, 800);
        orderFrame.setContentPane(orderPanel);
        orderFrame.setTitle("Order Menu");

        orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        orderFrame.setVisible(true);
    }
    void manageClientListener(ActionListener al) {
        manageClientBtn.addActionListener(al);
    }
    void manageProductListener(ActionListener al) {
        manageProductBtn.addActionListener(al);
    }
    void manageOrdersListener(ActionListener al) {
        manageOrdersBtn.addActionListener(al);
    }
    void addClientListener(ActionListener al) {
        addClientBtn.addActionListener(al);
    }
    void editClientListener(ActionListener al) {
        editClientBtn.addActionListener(al);
    }
    void deleteClientListener(ActionListener al) {
        deleteClientBtn.addActionListener(al);
    }
    void addProductListener(ActionListener al) {
        addProductBtn.addActionListener(al);
    }
    void editProductListener(ActionListener al) {
        editProductBtn.addActionListener(al);
    }
    void deleteProductListener(ActionListener al) {
        deleteProductBtn.addActionListener(al);
    }
    void setOrderListener(ActionListener al) {
        setOrderBtn.addActionListener(al);
    }
    public String getClientName() {
        return clientName.getText();
    }
    public String getClientEmail() {
        return clientEmail.getText();
    }
    public String getClientAddress() {
        return clientAddress.getText();
    }
    public JTable getClientTable() {
        return clientTable;
    }
    public String getProductId() {
        return productId.getText();
    }
    public String getProductName() {return productName.getText();}
    public String getProductQuantity() { return productQuantity.getText(); }
    public JTable getProductTable() {return productTable;}
    public String getOrderQuantity() {return orderQuantityField.getText();}
    public DefaultTableModel getClientModel() {return clientModel;}
    public DefaultTableModel getProductModel() {return productModel;}
    public JTable getOrderProductTable() {return orderProductTable;}
    public JTable getOrderClientTable() {return orderClientTable;}

    void showError(String errMessage) {
        JOptionPane.showMessageDialog(menuFrame, errMessage);
    }


    public static void main(String[] args) {
        View view = new View();
    }
}
