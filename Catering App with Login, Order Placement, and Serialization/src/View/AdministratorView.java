package View;

import Model.BaseProduct;
import kotlin.random.AbstractPlatformRandom;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class AdministratorView extends View{
    private JPanel adminPanel = new JPanel();

    private JButton importProducts = new JButton("Import Products from .CSV");

    private JLabel  infoLabel = new JLabel("New product info (comma separated):");
    private JTextArea textArea = new JTextArea(2, 25);

    private JButton addProductButton = new JButton("Add Product");
    private JButton deleteButton = new JButton("Delete Selected");
    private JButton editButton = new JButton("Edit Product");
    private JButton createButton = new JButton("Create Product");
    private JTextField createText = new JTextField(20);

    private DefaultTableModel tableModel;
    private JTable productTable = new JTable();
    private JScrollPane tablePane;

    private JLabel generateReportsLabel = new JLabel("Generate Reports");

    private JLabel lowerLabel = new JLabel("Lower time interval");
    private JLabel upperLabel = new JLabel("Upper time interval");
    private JTextField lowerText = new JTextField(8);
    private JTextField upperText = new JTextField(8);
    private JButton intervalReport = new JButton("Orders in time interval");

    private JLabel minOrdersLabel = new JLabel("Minimum orders for products");
    private JTextField minOrdersText = new JTextField(8);
    private JButton minOrdersButton  = new JButton("Show Products");

    private JLabel minOrders2Label   = new JLabel("Minimum orders for clients");
    private JLabel minValueLabel     = new JLabel("Minimum value of orders");
    private JTextField minOrders2Text= new JTextField(8);
    private JTextField minValueText  = new JTextField(8);
    private JButton showClientsButton= new JButton("Show Clients");

    private JLabel dateLabel           = new JLabel("Day to check for products:");
    private String[] years = {"2021","2022","2023","2024","2025"};
    private JComboBox<String> yearBox = new JComboBox<String>(years);

    private String[] months = {"January","February","March","April","May","June","July",
                    "August","September","October","November","December"};
    private JComboBox<String> monthBox = new JComboBox<String>(months);

   //                         "Saturday","Sunday"};
    private JComboBox<String> dayBox = new JComboBox<String>();
    private JButton showProductsButton = new JButton("Show products ordered in specified day");

    public AdministratorView(){
        frame = new JFrame();

        for(int i=1; i < 32; i++)
            dayBox.addItem(String.valueOf(i));

        adminPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        adminPanel.add(importProducts,gbc);

        gbc.gridy = 1;
        adminPanel.add(infoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        adminPanel.add(textArea, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        adminPanel.add(addProductButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        adminPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        adminPanel.add(editButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        adminPanel.add(createButton, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 3;
        gbc.gridy = 3;
        adminPanel.add(createText, gbc);
        gbc.gridwidth = 0;

        tableModel = createTableModel(new BaseProduct());
        productTable.setModel(tableModel);
        tablePane = new JScrollPane(productTable);
        tablePane.setMinimumSize(new Dimension(1000, 1100));
        gbc.gridwidth = 7;
        gbc.gridx = 0;
        gbc.gridy = 4;
        adminPanel.add(tablePane, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 5;
        adminPanel.add(generateReportsLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        adminPanel.add(lowerLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        adminPanel.add(upperLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        adminPanel.add(lowerText, gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        adminPanel.add(upperText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        adminPanel.add(intervalReport, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        adminPanel.add(minOrdersLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        adminPanel.add(minOrdersText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        adminPanel.add(minOrdersButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        adminPanel.add(minOrders2Label, gbc);
        gbc.gridx = 1;
        gbc.gridy = 12;
        adminPanel.add(minValueLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 13;
        adminPanel.add(minOrders2Text, gbc);
        gbc.gridx = 1;
        gbc.gridy = 13;
        adminPanel.add(minValueText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 14;
        adminPanel.add(showClientsButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 15;
        adminPanel.add(dateLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 16;
        adminPanel.add(dayBox, gbc);
        gbc.gridx = 1;
        gbc.gridy = 16;
        adminPanel.add(monthBox, gbc);
        gbc.gridx = 2;
        gbc.gridy = 16;
        adminPanel.add(yearBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 17;
        adminPanel.add(showProductsButton, gbc);
    }

    public void importListener(ActionListener al){ importProducts.addActionListener(al);}
    public void addProductListener(ActionListener al){ addProductButton.addActionListener(al);}
    public void deleteListener(ActionListener al){ deleteButton.addActionListener(al);}
    public void editListener(ActionListener al){ editButton.addActionListener(al);}
    public void createListener(ActionListener al){ createButton.addActionListener(al);}
    public void reportsIntervalListener(ActionListener al){ intervalReport.addActionListener(al);}
    public void reportsProductsMinimumListener(ActionListener al){ minOrdersButton.addActionListener(al);}
    public void reportsClientsListener(ActionListener al){ showClientsButton.addActionListener(al);}
    public void reportsProductsDayListener(ActionListener al){ showProductsButton.addActionListener(al);}

    public DefaultTableModel getTableModel() {return tableModel;}
    public String getProductText() { return textArea.getText();}
    public String getCreateText() { return createText.getText();}
    public String getIntervalTextLower() { return lowerText.getText();}
    public String getIntervalTextUpper() { return upperText.getText();}
    public String getMinimumOrdersText() { return minOrdersText.getText();}
    public String getMinimumOrdersTextClients() { return minOrders2Text.getText();}
    public String getMinimumValueText() { return minValueText.getText();}
    public String getDayText() { return (String) dayBox.getSelectedItem();}
    public String getMonthText() { return (String) monthBox.getSelectedItem();}
    public String getYearText() { return (String) yearBox.getSelectedItem();}


    public ArrayList<String> getSelected(){
        int[] selectedRows = productTable.getSelectedRows();
        ArrayList<String> titles = new ArrayList<>();
        for(int i=0; i<selectedRows.length; i++){
            titles.add((String) tableModel.getValueAt(selectedRows[i],0));
        }
        return titles;
    }
    @Override
    public DefaultTableModel createTableModel(Object object) {
        ArrayList<String> properties = new ArrayList<>();
        for (Field field : object.getClass().getFields()) {
            properties.add(field.getName());
        }
        DefaultTableModel tableModel = new DefaultTableModel(null, properties.toArray()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        return tableModel;
    }
    public void viewFrame(){
        frame.setLocation(600, 100);
        frame.setContentPane(adminPanel);
        frame.setTitle("Admin Window");

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }

    public static void main(String[] args){
        AdministratorView view = new AdministratorView();
        view.viewFrame();
    }
}
