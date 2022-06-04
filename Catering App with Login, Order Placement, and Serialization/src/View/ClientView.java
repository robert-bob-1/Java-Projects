package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;

import Model.BaseProduct;
import Model.OrderTableProduct;
import Model.OrderedProduct;
import Model.StringInt;


public class ClientView extends View{
    private JFrame clientFrame = new JFrame();
    private JPanel clientPanel = new JPanel();

    private JButton importProducts = new JButton("Import Products");

    private JPanel labelTextPanel  = new JPanel();
    private JLabel nameLabel       = new JLabel("Product Name");
    private JLabel ratingLabel     = new JLabel("Rating");
    private JLabel caloriesLabel   = new JLabel("Calories");
    private JLabel proteinsLabel   = new JLabel("Proteins");
    private JLabel fatsLabel       = new JLabel("Fats");
    private JLabel sodiumLabel     = new JLabel("Sodium");
    private JLabel priceLabel      = new JLabel("Price");

    private JTextField nameText     = new JTextField(30);
    private JTextField ratingText   = new JTextField(30);
    private JTextField caloriesText = new JTextField(30);
    private JTextField proteinsText = new JTextField(30);
    private JTextField fatsText     = new JTextField(30);
    private JTextField sodiumText   = new JTextField(30);
    private JTextField priceText    = new JTextField(30);

    private JPanel searchPanel      = new JPanel();
    private JPanel buttonsPanel     = new JPanel();
    private JButton searchButton    = new JButton("Search");
    private JButton addProductsButton = new JButton("Add products to order");

    private JTable searchTable      = new JTable();
    private DefaultTableModel searchModel = new DefaultTableModel();

    private JScrollPane productsScrollPane;
    private DefaultTableModel productsModel = new DefaultTableModel();
    private JTable productsTable = new JTable();

    private JPanel orderPanel= new JPanel();
    private JLabel orderLabel = new JLabel("Current Order");
    private JScrollPane orderScrollPane;
    private DefaultTableModel orderModel;
    private JTable orderTable    = new JTable();
    private JButton orderButton  = new JButton("Place Order");

    public ClientView(){
        frame = new JFrame();
        clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.X_AXIS));

        //labelTextPanel.setLayout(new GridLayout(7,2));
        labelTextPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        labelTextPanel.add(nameLabel,gbc);
        gbc.gridx = 1;
        labelTextPanel.add(nameText,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        labelTextPanel.add(ratingLabel,gbc);
        gbc.gridx = 1;
        labelTextPanel.add(ratingText,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        labelTextPanel.add(caloriesLabel,gbc);
        gbc.gridx = 1;
        labelTextPanel.add(caloriesText,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        labelTextPanel.add(proteinsLabel,gbc);
        gbc.gridx = 1;
        labelTextPanel.add(proteinsText,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        labelTextPanel.add(fatsLabel,gbc);
        gbc.gridx = 1;
        labelTextPanel.add(fatsText,gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        labelTextPanel.add(sodiumLabel,gbc);
        gbc.gridx = 1;
        labelTextPanel.add(sodiumText,gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        labelTextPanel.add(priceLabel,gbc);
        gbc.gridx = 1;
        labelTextPanel.add(priceText,gbc);

        //search side of the panel
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.add(labelTextPanel);

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(searchButton);
        buttonsPanel.add(addProductsButton);
        searchPanel.add(buttonsPanel);

        searchModel = createTableModel(new BaseProduct( ));
        searchTable.setModel(searchModel);

        productsScrollPane = new JScrollPane(searchTable);
        productsScrollPane.setMaximumSize(new Dimension(600, 700));
        searchPanel.add(productsScrollPane);

        clientPanel.add(searchPanel);

        //order side of the panel
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
        orderPanel.add(orderLabel);

        orderModel = createTableModel(new OrderTableProduct());
        orderTable.setModel(orderModel);
        orderScrollPane = new JScrollPane(orderTable);
        orderScrollPane.setMaximumSize(new Dimension(500, 300));
        orderPanel.add(orderScrollPane);

        //button
        //orderButton.setMinimumSize(new Dimension(300, 100));
        orderPanel.add(orderButton);

        clientPanel.add(orderPanel);
    }

    public DefaultTableModel getTableModel() {return searchModel;}
    public DefaultTableModel getOrderTableModel() {return orderModel;}
    public String getProductName(){ return nameText.getText();}
    public String getProductRating(){ return ratingText.getText();}
    public String getProductCalories(){ return caloriesText.getText();}
    public String getProductProteins(){ return proteinsText.getText();}
    public String getProductFats(){ return fatsText.getText();}
    public String getProductSodium(){ return sodiumText.getText();}
    public String getProductPrice(){ return priceText.getText();}

    public ArrayList<String> getSelected(){
        int[] selectedRows = searchTable.getSelectedRows();
        ArrayList<String> products = new ArrayList<>();
        for(int i=0; i<selectedRows.length; i++){
            products.add((String) searchTable.getValueAt(selectedRows[i],0));
        }
        return products;
    }
    public ArrayList<String> getAllProperties(){
        ArrayList<String> properties = new ArrayList<>();
        properties.add(getProductName());
        properties.add(getProductRating());
        properties.add(getProductCalories());
        properties.add(getProductProteins());
        properties.add(getProductFats());
        properties.add(getProductSodium());
        properties.add(getProductPrice());
        return properties;
    }

    public void searchListener(ActionListener al){ searchButton.addActionListener(al); }
    public void addListener(ActionListener al){ addProductsButton.addActionListener(al); }
    public void orderListener(ActionListener al){ orderButton.addActionListener(al); }



    public void viewFrame(){
        frame.setLocation(600, 300);
        frame.setContentPane(clientPanel);
        frame.setTitle("Client Window");

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }


    public static void main(String[] args){
        ClientView view = new ClientView();
    }
}


